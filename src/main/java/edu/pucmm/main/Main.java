package edu.pucmm.main;

import edu.pucmm.controllers.*;
import edu.pucmm.models.*;
import edu.pucmm.services.BootStrapServices;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;


import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;


/**
 * Created by Jhon on 11/6/2017.
 */
public class Main {
    private static Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
    private static FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

    public static void main(String[] args) {
        BootStrapServices.getInstancia().init();

        //initSpark();
        
        /*User user = new User();
        user.setAdmin(true);
        user.setAuthor(true);
        user.setUserName("locon");
        user.setPassword("1234");
        user.setName("jhon");

        UserDao.getInstance().crear(user);
        */
        Tag tag = new Tag("comida");
        Tag tag1 = new Tag("comida1");
        User user = new User("xpaladix","marlon","1234",true,true);
        Article article = new Article("titulo","cuerpo",Date.valueOf(LocalDate.now()),user);

        TagDao.getInstance().create(tag);
        TagDao.getInstance().create(tag1);
        UserDao.getInstance().create(user);




        ArrayList<Tag> tags = new ArrayList<>();

        article.setTagList(tags);
        try{
            ArticleDao.getInstance().create(article);
        }catch (Exception e){

            System.out.println(e.toString());
        }

        System.out.println(ArticleDao.getInstance().findAll());
        System.out.println(UserDao.getInstance().findAll());
        System.out.println(UserDao.getInstance().find("xpaladix").getArticleList());
        //System.out.println(ArticleDao.getInstance().getInstance().find(1).getTagList());
        System.out.println(TagDao.getInstance().find(1).getArticleList());
        System.out.println(ArticleDao.getInstance().find(1).getTagList());
        System.out.println(TagDao.getInstance().findAll());
    }

/*
    private static void initSpark() {

        port(getHerokuAssignedPort());
        //indicando los recursos publicos.
        //staticFiles.location("/META-INF/resources"); //para utilizar los WebJars.
        staticFileLocation("/publico");
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        

        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            response.redirect("/article/all");
            return new ModelAndView(attributes, "login.ftl");
        }, freeMarkerEngine);

        loginPages();
        articlePages();

    }

    public static void loginPages() {
        path("/login", () -> {
            get("", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("wrongUserName", false);
                return new ModelAndView(attributes, "login.ftl");
            }, freeMarkerEngine);

            post("", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                String userName = request.queryParams("userName");
                String pass = request.queryParams("password");
                User user = UserDao.getInstance().find(userName);
                if (user != null && pass != null && user.getUserName().equalsIgnoreCase(userName) && user.getPassword().equals(pass)) {
                    request.session().attribute("user", userName);
                    response.redirect("/article/all");
                } else {
                    attributes.put("wrong", true);
                }

                return new ModelAndView(attributes, "login.ftl");
            }, freeMarkerEngine);
        });
    }

    public static void articlePages() {
        path("/article", () -> {
            articleFilter();
            commentPages();
            get("/all", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<Article> articulos = ArticleDao.getInstance().findAll();
                articulos.sort((o1, o2) -> o1.getReleaseDate().compareTo(o2.getReleaseDate()));
                attributes.put("user", request.session().attribute("user"));
                attributes.put("hostUrl", request.host());
                attributes.put("articles", articulos);

                return new ModelAndView(attributes, "article-all.ftl");
            }, freeMarkerEngine);

            get("/create", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("hostUrl", request.host());
                return new ModelAndView(attributes, "article-create.ftl");
            }, freeMarkerEngine);

            post("/create", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                ArrayList<Tag> tags = new ArrayList<>();
                System.out.println(1);
                try {
                    for (String tagName : request.queryParams("tags").split(",")) {
                        List<Tag> tagsAux = TagDao.getInstance().findAll().stream()
                                .filter(tg -> tg.getName().equalsIgnoreCase(tagName))
                                .collect(Collectors.toList());
                        if (tagsAux != null) {
                            tags.add(tagsAux.get(0));
                        }

                    }
                    System.out.println(2);
                    ArticleDao.getInstance().create(new Article(
                            request.queryParams("title"),
                            request.queryParams("body"),
                            Date.valueOf(LocalDate.now()),
                            UserDao.getInstance().find(request.queryParams("author")),
                            tags
                    ));


                    response.redirect("/article/all");
                } catch (NoSuchElementException e) {
                    response.redirect("/invalid-parameter.html");
                }

                return new ModelAndView(attributes, "article-all.ftl");
            }, freeMarkerEngine);

            get("/view/:articleId", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                String userName = request.session().attribute("user");
                attributes.put("article", ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId"))));
                attributes.put("hostUrl", request.host());
                attributes.put("canComment", userName != null);
                attributes.put("user", request.session().attribute("user"));
                return new ModelAndView(attributes, "article-view.ftl");
            }, freeMarkerEngine);

            get("/update/:articleId", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();

                attributes.put("article", ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId"))));
                attributes.put("tagList", TagDao.getInstance().findAll());
                attributes.put("hostUrl", request.host());

                return new ModelAndView(attributes, "article-update.ftl");
            }, freeMarkerEngine);

            post("/update/:articleId", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();

                User user = UserDao.getInstance().find(request.session().attribute("user"));
                User articleAuthor = UserDao.getInstance().find(request.queryParams("author"));
                if (user.getAdmin() || user.getUserName().equals(articleAuthor.getUserName())) {
                    ArticleDao.getInstance().edit(new Article(
                            Integer.parseInt(request.params("articleId")),
                            request.queryParams("title"),
                            articleAuthor,
                            request.queryParams("body"),
                            Date.valueOf(LocalDate.now())
                    ));


                    ArticleTagDao.getInstance().findAll().stream()
                            .filter(articleTag -> articleTag.getIdArticle().equals(Integer.parseInt(request.params("articleId"))))
                            .forEach(articleTag -> ArticleTagDao.getInstance().destroy(articleTag));

                    if (request.queryParamsValues("tags[]") != null) {
                        for (String tagId : request.queryParamsValues("tags[]")) {
                            ArticleTagDao.getInstance().create(new ArticleTag(Integer.parseInt(request.queryParams("articleId")), Integer.parseInt(tagId)));
                        }
                    }
                }

                response.redirect("/article/view/" + request.queryParams("articleId"));
                return new ModelAndView(attributes, "article-update.ftl");
            }, freeMarkerEngine);

            get("/delete/:articleId", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                ArticleDao.getInstance().getInstance().destroy(ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId"))));
                response.redirect("/article/all");
                return new ModelAndView(attributes, "article-all.ftl");
            }, freeMarkerEngine);

        });
    }

    public static void commentPages() {
        path("/comment", () -> {
            post("/create/:articleId", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                CommentDao.getInstance().create(new Comment(++commentCount,
                        ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId"))),
                        request.queryParams("commentBody"),
                        UserDao.getInstance().find(request.session().attribute("user"))));

                attributes.put("article", ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId"))));

                return new ModelAndView(attributes, "article-read.ftl");
            }, freeMarkerEngine);


            get("/delete/:articleId", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                CommentDao.getInstance().destroy(CommentDao.getInstance().find(Integer.parseInt(request.params("articleId"))));

                response.redirect("/article/all");
                return new ModelAndView(attributes, "article-read.ftl");
            }, freeMarkerEngine);

        });
    }

    public static void articleFilter() {
        before("/create", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            String userName = request.session().attribute("user");
            boolean allowed = false;

            if (userName != null) {
                allowed = UserDao.getInstance().find(userName).getAuthor();
            }
            if (!allowed) {
                response.redirect("/login");
            }
        });

        before("/update", (request, response) -> {
            String userName = request.session().attribute("user");
            boolean allowed = false;
            if (userName != null) {
                allowed = UserDao.getInstance().find(userName).getAuthor();
            }
            if (!allowed) {
                response.redirect("/login");
            }
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

*/

}
