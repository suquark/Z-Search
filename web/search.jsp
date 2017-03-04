<%--
  Created by IntelliJ IDEA.
  User: suquarker
  Date: 16/12/11
  Time: 下午4:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:directive.page import="lab1.*"/>
<%@ page import="lab1.Result" %>
<%@ page import="java.util.HashMap" %>
<%--<%--%>
<%--String path = request.getContextPath();--%>
<%--String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";--%>
<%--%>--%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="css/material_icons.css" media="screen,projection"/>

    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>

    <%--<base href="<%=basePath%>">--%>
    <title>Search Result</title>

    <script type="text/javascript">
        $(document).ready(function () {
            $('.scrollspy').scrollSpy();
        });

        function checkAll() {
            return !!$("#search").val().trim();
        }
    </script>
</head>

<body>

<!-- Search bar -->
<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper">
            <form action="search.jsp" name="search" method="get" onsubmit="return checkAll()">
                <div class="input-field">
                    <input id="search" type="search" name="keyword" required>
                    <label for="search"><i class="material-icons">search</i></label>
                    <i class="material-icons">close</i>
                </div>
            </form>
        </div>
    </nav>
</div>

<%
    int pageSize = Settings.maxCountofResultsPerPage;

    Result results = (Result) session.getAttribute("result");
    String keyword = (String) session.getAttribute("keyword");
    String correct = (String) session.getAttribute("correct");

    if (keyword != request.getParameter("keyword") || results == null) {
        keyword = request.getParameter("keyword");

        results = Search.search(keyword);
        correct = SpellCheck.suggest(keyword);

        session.setAttribute("result", results);
        session.setAttribute("keyword", keyword);
        session.setAttribute("correct", correct);
    }


    String start = request.getParameter("start");  // as page


    //int totalItems = Math.min(results.totalDocs, maxResult);
    int totalItems = results.docIDs.size();// totalDocs;

    int totalPages = (totalItems - 1) / pageSize + 1;

    // get current page
    int curPage;
    try {
        curPage = Integer.parseInt(start);
    } catch (NumberFormatException e) {
        curPage = 1;
    }
    curPage = Math.min(Math.max(curPage, 1), totalPages);

    int begin = pageSize * (curPage - 1);
    int end = Math.min(begin + pageSize, totalItems);

%>

<script>
    $("#search").val("<%=keyword%>");
</script>

<div class="container">

    <!-- header info -->
    <div class="col s12 m9 l10">
        <p style="color: #a9a9a9;font-size: small; margin-top: 60px">
            共查询到 <%=results.totalDocs%> 条结果, 第 <%=curPage%>/<%=totalPages%> 页
        </p>

        <% if (correct != null) { %>
        <p style="color: #31b0d5">您是不是要找:
            <a style="color: #31b0d5" href="search.jsp?keyword=<%=correct%>"><%=correct%>
            </a>
        </p>
        <% } %>
    </div>


    <% if (totalItems <= 0) { %>
    <h1>Oops, nothing found!</h1>
    <% } %>

    <div class="row" style="margin-right: -8rem">
        <!-- content -->
        <div class="col s8">
            <% for (int i = begin; i < end; i++) {
                int docID = results.docIDs.get(i);
                HashMap<String, String> docInfo = Search.highlighter.docFormat(docID);
            %>

            <div id="item<%=i - begin + 1%>" class="section scrollspy" style="">
                <p style="font-size: 18px;">
                    <a href="<%=docInfo.get("url")%>">
                        <%=docInfo.get("title")%>
                    </a>
                </p>
                <p style="color: #556b2f;font-size: small;margin-top: 1px">
                    <%=docInfo.get("url")%>
                </p>
                <p>
                    <%=docInfo.get("content")%>
                </p>

                <!-- related -->
                <% String[] tuple = SimilarDocs.searchSimilarDoc(results.docIDs.get(i)); %>
                <% if (tuple[0] != null) { %>
                <p style="color: sandybrown">相关新闻:
                    <a href="<%=tuple[1]%>" style="color: sandybrown;">
                        <%=tuple[0]%>
                    </a>
                </p>
                <% } %>

                <p style="color: #a9a9a9"><%=docInfo.get("publishTime")%>
                </p>
            </div>
            <% } %>
        </div>

        <!-- right wrapper -->
        <div class="col hide-on-small-only s4">
            <div class="toc-wrapper" style="width: 300px;top:100px">
                <ul class="section table-of-contents">
                    <% for (int i = begin; i < end; i++) {
                        int docID = results.docIDs.get(i);
                    %>
                    <div id="item<%=i - begin + 1%>" class="">
                        <li>
                            <a href="#item<%=i - begin + 1%>">
                                <%=Search.highlighter.docFormatTitle(docID)%>
                            </a>
                        </li>
                    </div>
                    <% } %>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- pagination  -->
<div class="container">
    <ul class="pagination">
        <%
            int paginationLen = 10;
            int mid = (1 + paginationLen) / 2 + 1;
            int shiftleft = mid - 1;
            int shiftright = paginationLen - mid;

            int paginationHead;
            if (curPage + shiftright >= totalPages) {
                paginationHead = totalPages - paginationLen + 1;
            } else {
                paginationHead = Math.max(curPage - shiftleft, 1);
            }
            paginationHead = Math.max(paginationHead, 1);
            int paginationTail = Math.min(paginationHead + paginationLen - 1, totalPages);
        %>

        <% if (curPage > 1) { %>
        <li class="waves-effect">
            <a href="search.jsp?start=<%=curPage - 1%>&keyword=<%=keyword%>">
                <i class="material-icons">chevron_left</i>
            </a>
        </li>
        <% } else { %>
        <li class="disabled">
            <a>
                <i class="material-icons">chevron_left</i>
            </a>
        </li>
        <% } %>

        <% for (int i = paginationHead; i <= paginationTail; i++) { %>
        <li class="<%= curPage == i ? "active":"waves-effect" %>">
            <a href="<%= curPage == i ? "#" : "search.jsp?start=" + i %>&keyword=<%=keyword%>">
                <%=i%>
            </a>
        </li>
        <% } %>

        <% if (curPage < totalPages) { %>
        <li class="waves-effect">
            <a href="search.jsp?start=<%=curPage+1%>&keyword=<%=keyword%>">
                <i class="material-icons">chevron_right</i>
            </a>
        </li>
        <% } else { %>
        <li class="disabled">
            <a>
                <i class="material-icons">chevron_right</i>
            </a>
        </li>
        <% } %>

    </ul>

</div>

<br>

<footer class="page-footer">
    <div class="footer-copyright">
        <div class="container">
            © 2016 Copyright SiYuan Zhuang
        </div>
    </div>
</footer>

<script>
    var tocHeight = $('.toc-wrapper .table-of-contents').length ? $('.toc-wrapper .table-of-contents').height() : 0;
    var footerOffset = $('body > footer').first().length ? $('body > footer').first().offset().top : 0;
    var bottomOffset = footerOffset - tocHeight;

    $('.toc-wrapper').pushpin({
        top: $('nav').height() + 20,
        offset: $('nav').height() + 20,
        bottom: bottomOffset - 50
    });

</script>

</body>
</html>
