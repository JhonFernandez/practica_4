<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/blog-home.css">
    <link rel="stylesheet" type="text/css" href="/css/comentarios.css">

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
                ${article.title}
                <br>
                <small>by ${article.author.userName} &nbsp&nbsp&nbsp&nbsp&nbsp</small>
                    <a href="http://${hostUrl!"locahost:4567"}/article/update/${article.id}" class="btn btn-info">Edit</a>
                    <a href="http://${hostUrl!"locahost:4567"}/article/delete/${article.id}" class="btn btn-danger">Delete</a>
            </h1>
            <br>
            <p>${article.body}</p>
            <p><span class="glyphicon glyphicon-time"></span> Posted on August ${article.releaseDate}</p>
            <hr>
            <#if article.tagList??>
                <#list article.tagList>
                    <h5 style="display: inline;width: auto">tags: </h5>
                    <ul style="display: inline">
                        <#items as tag>
                            <a href="/article/all/tag/${tag.name}"><span class="label label-default">${tag.name}<#--<#sep>,</#sep>--></span></a></h3>
                        </#items>
                    </ul>
                </#list>
            </#if >

            <hr>
            <div class="comment-header">
                <h3>Comments:</h3>
                <hr>
            <#list article.commentList as comment>
                <div class="comment">
                    <div>
                        <h4>${comment.author.userName}</h4>
                        <hr>
                        <p>${comment.body}</p>
                        <a href="/article/valoracion/1/comment/${comment.id}"><span class="glyphicon glyphicon-thumbs-up">${comment.CountValoracion(1)}</span></a>
                        <a href="/article/valoracion/0/comment/${comment.id}"><span class="glyphicon glyphicon-thumbs-down">${comment.CountValoracion(0)}</span></a>
                        <a href="http://${hostUrl!"localhost:4567"}/article/comment/delete/${comment.article.id}/${comment.id}">
                            <button class="button">Delete</button>
                        </a>
                    </div>
                </div>
            </#list>
            </div>


            <form method="post" action="/article/comment/create/${article.id}">
                <div class="form-group">
                    <label for="commentBody">Comment:</label>
                    <textarea class="form-control" rows="10" name="commentBody"></textarea>
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>


            <!-- Pager -->
            <ul class="pager">
                <li class="previous">
                    <a href="/article/all/0">&larr; Volver</a>
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