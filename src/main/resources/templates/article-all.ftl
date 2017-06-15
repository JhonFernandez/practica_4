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
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Start Bootstrap</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/article/create">New Article</a>
                </li>
                <li>
                    <h2 style="border: 1px solid #999999">user:<a href="" style="color: #999999">${user!"Usted no esta logeado"}</a></h2>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Entries Column -->
        <div class="col-md-8">

            <h1 class="page-header">
                Articulas
                <small>By Jaime and Jhon</small>
            </h1>

            <br>
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
                <ul>
                    <#items as tag>
                        <span class="label label-default">${tag.name}<#--<#sep>,</#sep>--></span></h3>
                    </#items>
                </ul>
                </#list>
                </#if >
                <hr>
            </#list>



            <!-- Pager -->
            <ul class="pager">
                <li class="previous">
                    <a href="#">&larr; Older</a>
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