<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="sbs.jsp.board.dto.Article" %>
<%@ page import="java.util.List" %>

<%
  List<Article> articles = (List<Article>) request.getAttribute("articles");
  int cPage = (int) request.getAttribute("page");
  int totalPage = (int) request.getAttribute("totalPage");
%>

<%@ include file="../part/head.jspf"%>
  <style>
    .section {
      display: flex;
      justify-content: center;
    }

    .page > a.red {
      color: red;
    }
  </style>

  <section class="section">
    <div class="con">
      <h1>게시물 리스트</h1>

      <table border="1" style="border-collapse: collapse; text-align:center;">
        <colgroup>
          <col width="50">
          <col width="200">
          <col width="200">
          <col width="200">
          <col width="100">
        </colgroup>
        <thead>
        <tr>
          <th>번호</th>
          <th>작성날짜</th>
          <th>수정날짜</th>
          <th>제목</th>
          <th>작성자</th>
        </tr>
        </thead>
        <tbody>
        <% for(Article article : articles) {
        %>
        <tr>
          <td><%= article.getId() %></td>
          <td><%= article.getRegDate() %></td>
          <td><%= article.getUpdateDate() %></td>
          <td>
            <a href="detail?id=<%= article.getId()%>"><%= article.getTitle() %></a>
          </td>
          <td><%= article.getExtra__writerName() %></td>
        </tr>
        <% } %>
        </tbody>
      </table>
      <div class="btn-group">
        <a href="write">게시물 작성</a>
      </div>

      <div class="page" style="display:flex; justify-content: center; gap: 0 10px;">
        <% if(cPage > 1) { %>
        <a href="list?page=1">◀</a>
        <% } %>
        <%
        int pageMenuSize = 5;
        int from = cPage - pageMenuSize;

        if(from < 1) {
        from = 1;
        }

        int end = cPage + 10;
        if(end > totalPage) {
        end = totalPage;
        }

        for(int i = from; i <= end; i++) {%>
        <a class="<%= cPage == i ? "red" : "" %>" href="list?page=<%=i%>"><%=i%></a>
        <% } %>
        <% if(cPage < totalPage) { %>
        <a href="list?page=<%=totalPage%>">▶</a>
        <% } %>
      </div>
    </div>
  </section>

<%@ include file="../part/foot.jspf"%>