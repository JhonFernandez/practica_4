<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
</head>
<body>
<div class="container">
    <h1>Create New Student: </h1>
    <form method="post" action="/article/update/${article.id}">
        <div class="form-group">
            <label>Id: </label>
            <!--cuan enviabas antes no se estaba enviando el articleId por tanto mandaba el post con null-->
            <input type="text" class="form-control" name="articleId" value="${article.id}" readonly>
        </div>
        <div class="form-group">
            <label for="title">Title: </label>
            <input type="text" class="form-control" id="title" name="title" value="${article.title}">
        </div>
        <div class="form-group">
            <label for="author">Author:</label>
            <!--Creo que no seria correcto poder editar el author;
            en el pdf dice solo (indicar el titulo, el cuerpo
            del artículo y las etiquetas asociadas) para crear tambien aplica-->
            <input class="form-control" id="author" name="author" value="${article.author.userName}" readonly>
        </div>
        <div class="form-group">
            <label for="body">Content:</label>
            <textarea class="form-control" rows="5" id="body" name="body">${article.body}</textarea>
        </div>
        <!--<div class="form-group">-->
            <!--<label for="tags">Tags:</label>-->
            <!--<input class="form-control" id="tags" name="tags" value="{{tags}}">-->
        <!--</div>-->

        <div class="form-group">
            <label for="tags[]">Tags:</label>
            <select class="form-control"  name="tags[]" size="5" multiple>
                <#list tagList as tag>
                    <option value="${tag.id}">${tag.name}</option>
                </#list>
            </select>
        </div>


        <button type="submit" class="btn btn-default">Submit</button>
    </form>

    <!-- Pager -->
    <ul class="pager">
        <li class="previous">
            <a href="http://${hostUrl}/article/view/${article.id}">&larr; Volver</a>
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

</div>

<script src="/js/vendor/jquery.min.js"></script>
<script src="/js/bootstrap.js"></script>
</body>
</html>
