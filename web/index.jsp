<%--
  Created by IntelliJ IDEA.
  User: suquarker
  Date: 16/12/11
  Time: 上午9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<%@ page import="lab1.SpellCheck" %>
<%@ page import="lab1.Search" %>
<%
    // warm-up
//    Search.search("讯 0 1 2 3 4 5 6 7 8 9 10 11 报道 记者 今天", 100);
    SpellCheck.initSpellChecker();
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>

    <base href="<%=basePath%>">
    <title>Z-Search</title>

    <style>
        /* fallback */
        @font-face {
            font-family: 'Material Icons';
            font-style: normal;
            font-weight: 400;
            src: local('Material Icons'), local('MaterialIcons-Regular'), url(http://fonts.gstatic.com/s/materialicons/v19/2fcrYFNaTjcS6g4U3t-Y5UEw0lE80llgEseQY3FEmqw.woff2) format('woff2');
        }

        .material-icons {
            font-family: 'Material Icons';
            font-weight: normal;
            font-style: normal;
            font-size: 24px;
            line-height: 1;
            letter-spacing: normal;
            text-transform: none;
            display: inline-block;
            white-space: nowrap;
            word-wrap: normal;
            direction: ltr;
            -webkit-font-feature-settings: 'liga';
            -webkit-font-smoothing: antialiased;
        }
    </style>

    <script type="text/javascript">
        function checkAll() {
            return !!$('#search').val().trim();
        }
    </script>
</head>

<body style="background-color: black">

<div class="container">

    <p align="center" style="margin-top: 100px"><img src="ZSearch.svg"/></p>

    <div class="nav-wrapper">
        <form action="search.jsp" name="search" method="get" onsubmit="return checkAll()">
            <div class="input-field">
                <input id="search" class="transparent white-text" type="search" name="keyword" required>
                <label for="search"><i class="material-icons">search</i></label>
                <i class="material-icons" style="padding-top: 7px">close</i>
            </div>
        </form>
    </div>

</div>

<script type="text/javascript" color="120,190,250" opacity="0.9" count="500" src="js/canvas-nest.min.js"></script>

</body>
</html>
