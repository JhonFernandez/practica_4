<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
</head>
<body>
<div class="container">
    <h1>Create New Article: </h1>
    <form method="post" action="/article/create">
        <div class="form-group">
            <label for="title">Title: </label>
            <input type="text" class="form-control" id="title" name="title">
        </div>
        <#--<div class="form-group">
            <div class="form-group">
                <label for="author">Author:</label>
                <select class="form-control"  name="author" size="1">
                <#list authorList as author>
                    <option value="${author.userName}">${author.userName}</option>
                </#list>
                </select>
            </div>
        </div>-->
        <div class="form-group">
            <label for="body">Content:</label>
            <textarea class="form-control" rows="5" id="body" name="body"></textarea>
        </div>
        <div class="form-group">
            <label for="tags">Tags:</label>
            <input class="form-control" id="tags" name="tags">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
    </form>
</div>

<!-- Pager -->
<ul class="pager">
    <li class="previous">
        <a href="http://${hostUrl}/article/all">&larr; Volver</a>
    </li>
</ul>

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

<script src="/js/vendor/jquery.min.js"></script>
<script src="/js/bootstrap.js"></script>
</body>
</html>