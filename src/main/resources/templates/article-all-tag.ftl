<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/blog-home.css">

</head>
<body>

<!-- Navigation -->

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="navbar-collapse collapse container">
        <ul class="nav navbar-nav navbar-left">
            <li class="active"><a href="/article/all/0"><span class="lyphicon glyphicon glyphicon-home"></span> Home</a></li>
            <li class=""><a href="/article/create"><span class="lyphicon glyphicon glyphicon-plus"></span> New Article</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right" style="margin-right: 10px">
            <li class=""><a href="/login" style="font-size: 1.25em">${user!"Login"}</a></li>
        </ul>
    </div>
</nav>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Entries Column -->
        <div class="col-md-8">

            <h1 class="page-header">
                Articles
                <small>By Jhon</small>
            </h1>

            <br>
            <#if articles??>
                <#list articles as article >
                    <h2>${article.title}</h2>
                    <p class="lead">by ${article.author.userName}</p>
                    <p><span class="glyphicon glyphicon-time"></span> Posted on August ${article.releaseDate}</p>
                    <hr>
                    <#if article.body?length < 70 >
                        <p class="articleBody">${article.body}...</p>
                    <#else>
                        <p class="articleBody">${article.body?substring(0,70)}...</p>
                    </#if>
                    <a class="btn btn-primary" href="/article/view/${article.id}">Read More <span class="glyphicon glyphicon-chevron-right"></span></a>

                    <#if article.tagList??>
                        <#list article.tagList>
                        <br>
                        <h5 style="display: inline;width: auto">tags: </h5>
                            <ul style="display: inline">
                                <#items as tag>
                                    <a href="/article/all/tag/${tag.name}"><span class="label label-default">${tag.name}<#--<#sep>,</#sep>--></span></a></h3>
                                </#items>
                            </ul>
                        </#list>
                    </#if >
                    <a href="/article/valoracion/1/${article.id}"><span class="glyphicon glyphicon-thumbs-up">${article.CountValoracion(1)}</span></a>
                    <a href="/article/valoracion/0/${article.id}"><span class="glyphicon glyphicon-thumbs-down">${article.CountValoracion(0)}</span></a>
                    <hr>
                </#list>
            </#if>




            <!-- Pager -->
            <ul class="pager">
                <li class="previous">
                    <a href="/article/all/0">&larr; Older</a>
                </li>
            </ul>

        </div>


    </div>
    <!-- /.row -->

    <hr>

    <!-- Footer -->
    <footer>
        <div class="row">
            <div class="col-lg-12">
                <p>Copyright &copy; Your Website 2014</p>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </footer>

</div>
<!-- /.container -->
<script src="/js/vendor/jquery.min.js"></script>
<script src="/js/bootstrap.js"></script>
</body>
</html>