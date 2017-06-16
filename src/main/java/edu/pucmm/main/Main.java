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

import static spark.Spark.*;


/**
 * Created by Jhon on 11/6/2017.
 */
public class Main {
    private static Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
    private static FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

    public static void main(String[] args) {

        BootStrapServices.getInstancia().init();

        System.out.println(CommentDao.getInstance().findAll());
        /*try {
            CommentDao.getInstance().create(new Comment("un comentario genial",
                    ArticleDao.getInstance().find(164),UserDao.getInstance().findAll().get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            UserDao.getInstance().create(new User("master","marlon","1234",true,true));
            UserDao.getInstance().create(new User("vladi","vladi","1234",false,true));
            UserDao.getInstance().create(new User("veronica","veronica","1234",false,true));
            UserDao.getInstance().create(new User("jhon","jhon","1234",false,false));

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        initSpark();

        /*User user = new User();
        user.setAdmin(true);
        user.setAuthor(true);
        user.setUserName("locon");
        user.setPassword("1234");
        user.setName("jhon");

        UserDao.getInstance().crear(user);
        */

        /*Tag tag = new Tag("comida");
        Tag tag1 = new Tag("comida1");
        User user = new User("xpaladix","marlon","1234",true,true);
        Article article = new Article("titulo","cuerpo",Date.valueOf(LocalDate.now()),user);

        TagDao.getInstance().create(tag);
        TagDao.getInstance().create(tag1);
        UserDao.getInstance().create(user);
            


        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);


        article.setTagList(tags);
        
            ArticleDao.getInstance().create(article);

        System.out.println(ArticleDao.getInstance().findAll());
        System.out.println(UserDao.getInstance().findAll());
        System.out.println(UserDao.getInstance().find("xpaladix").getArticleList());
        //System.out.println(ArticleDao.getInstance().getInstance().find(1).getTagList());
        System.out.println(TagDao.getInstance().find(1).getArticleList());
        System.out.println(ArticleDao.getInstance().find(1).getTagList());
        System.out.println(TagDao.getInstance().findAll());*/

    }


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
                if (articulos != null) {
                    Collections.reverse(articulos);
                    attributes.put("articles", articulos);
                } else {
                    attributes.put("articles", new ArrayList<Article>());
                }
                attributes.put("user", request.session().attribute("user"));
                attributes.put("hostUrl", request.host());

                return new ModelAndView(attributes, "article-all.ftl");
            }, freeMarkerEngine);

            get("/create", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("hostUrl", request.host());
                attributes.put("authorList", UserDao.getInstance().findAll());
                return new ModelAndView(attributes, "article-create.ftl");
            }, freeMarkerEngine);

            post("/create", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                ArrayList<Tag> tags = new ArrayList<>();

                for (String tagName : request.queryParams("tags").split(",")) {
                    Tag tag = TagDao.getInstance().findByName(tagName);
                    if (tag != null) {
                        tags.add(tag);
                    } else {
                        TagDao.getInstance().create(new Tag(tagName));
                        tags.add(TagDao.getInstance().findByName(tagName));
                    }
                }
                Article article = new Article(
                        request.queryParams("title"),
                        request.queryParams("body"),
                        Date.valueOf(LocalDate.now()),
                        UserDao.getInstance().find(request.session().attribute("user")),
                        tags
                );
                try {
                    ArticleDao.getInstance().create(article);
                } catch (Exception e) {
                    response.redirect("/article/all");
                    System.out.println("Trato de duplicar un articulo");
                }

                response.redirect("/article/all");


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


                Article article = ArticleDao.getInstance().find(Integer.parseInt(request.queryParams("articleId")));
                article.setBody(request.queryParams("body"));
                article.setTitle(request.queryParams("title"));
                article.setReleaseDate(Date.valueOf(LocalDate.now()));

                ArrayList<Tag> tags = new ArrayList<>();
                if (request.queryParamsValues("tags[]") != null) {
                    for (String tagId : request.queryParamsValues("tags[]")) {
                        tags.add(TagDao.getInstance().find(Integer.parseInt(tagId)));
                    }
                }

                article.setTagList(tags);

                ArticleDao.getInstance().edit(article);

                response.redirect("/article/view/" + request.queryParams("articleId"));
                return new ModelAndView(attributes, "article-update.ftl");
            }, freeMarkerEngine);

            get("/delete/:articleId", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                String userName = request.session().attribute("user");
                if (userName != null) {

                    Article article = ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId")));
                    CommentDao.getInstance().findAll().stream()
                            .filter(c -> c.getArticle().getId().equals(article.getId()))
                            .forEach(c -> {
                                try {
                                    CommentDao.getInstance().destroy(c.getId());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                    try {
                        ArticleDao.getInstance().destroy(article.getId());
                    } catch (Exception e) {
                        System.out.println("problemas");
                        System.out.println(e.toString());
                    }
                }

                response.redirect("/article/all");
                return new ModelAndView(attributes, "article-all.ftl");
            }, freeMarkerEngine);

        });
    }

    public static void commentPages() {
        path("/comment", () -> {
            commentFilter();
            post("/create/:articleId", (request, response) -> {
                User user = UserDao.getInstance().find(request.session().attribute("user"));
                Map<String, Object> attributes = new HashMap<>();

                if (user != null && (user.getAdmin() || user.getAuthor())){
                    CommentDao.getInstance().create(new Comment(
                            request.queryParams("commentBody"),
                            ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId"))),
                            user));

                    System.out.println(CommentDao.getInstance().findAll());
                }
                attributes.put("article", ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId"))));
                attributes.put("hostUrl", request.host());
                return new ModelAndView(attributes, "article-view.ftl");

            }, freeMarkerEngine);


            get("/delete/:articleId/:commentId", (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                User user = UserDao.getInstance().find(request.session().attribute("user"));
                if (user != null && (user.getAdmin() || user.getAuthor())){
                    CommentDao.getInstance().destroy(Integer.parseInt(request.params("commentId")));
                }

                attributes.put("article", ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId"))));
                attributes.put("hostUrl", request.host());

                return new ModelAndView(attributes, "article-view.ftl");
            }, freeMarkerEngine);

        });
    }

    public static void articleFilter() {
        before("/create", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            String userName = request.session().attribute("user");
            User user = UserDao.getInstance().find(userName);
            boolean allowed = false;

            if (user != null) {
                allowed = user.getAuthor() || user.getAdmin();
            }
            if (!allowed) {
                response.redirect("/login");
            }
        });

        before("/update/:articleId", (request, response) -> {
            String userName = request.session().attribute("user");
            if (userName != null) {
                User user = UserDao.getInstance().find(userName);
                Article article = ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId")));
                if (!user.getAdmin()) {
                    if (user.getAuthor()) {
                        if (!article.getAuthor().equals(user)) {
                            response.redirect("/login");
                        }
                    } else {
                        response.redirect("/login");
                    }
                }
            } else {
                response.redirect("/login");
            }

        });

        before("/delete/:articleId", (request, response) -> {
            String userName = request.session().attribute("user");
            if (userName != null) {
                User user = UserDao.getInstance().find(userName);
                Article article = ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId")));
                if (!user.getAdmin()) {
                    if (user.getAuthor()) {
                        if (!article.getAuthor().equals(user)) {
                            response.redirect("/login");
                        }
                    } else {
                        response.redirect("/login");
                    }
                }
            } else {
                response.redirect("/login");
            }

        });
    }

    public static void commentFilter() {
        before("/create/:articleId", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            String userName = request.session().attribute("user");
            User user = UserDao.getInstance().find(userName);
            boolean allowed = false;

            if (user != null) {
                allowed = user.getAuthor() || user.getAdmin();
            }
            if (!allowed) {
                response.redirect("/login");
            }
        });

        before("/delete/:articleId/:commentId", (request, response) -> {
            String userName = request.session().attribute("user");
            if (userName != null) {
                User user = UserDao.getInstance().find(userName);
                Article article = ArticleDao.getInstance().find(Integer.parseInt(request.params("articleId")));
                if (!user.getAdmin()) {
                    if (user.getAuthor()) {
                        if (!article.getAuthor().equals(user)) {
                            response.redirect("/login");
                        }
                    } else {
                        response.redirect("/login");
                    }
                }
            } else {
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


}
