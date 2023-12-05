<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="sbs.jsp.board.dto.Article" %>
<%@ page import="java.util.List" %>

<%
  List<Article> articles = (List<Article>) request.getAttribute("articles");
  int cPage = (int) request.getAttribute("page");
  int totalPage = (int) request.getAttribute("totalPage");
%>

<%@ include file="../part/head.jspf"%>
  <section class="article-list-wrap">
    <div class="con mx-auto w-[1100px]">
      <h1 class="badge badge-neutral my-[10px]">게시물 리스트</h1>
      <div>
        <table class="table text-center">
          <thead>
            <tr class="border-y !border-black">
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
      </div>
      <div class="btn-group mt-[10px]">
        <a href="write" class="btn btn-primary">게시물 작성</a>
      </div>

      <div class="page flex justify-center mt-[15px]">
        <% if(cPage > 1) { %>
        <a href="list?page=1" class="btn">◀</a>
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
        <a class="btn <%= cPage == i ? "btn-active" : "" %>" href="list?page=<%=i%>"><%=i%></a>
        <% } %>
        <% if(cPage < totalPage) { %>
        <a href="list?page=<%=totalPage%>" class="btn">▶</a>
        <% } %>
      </div>
    </div>
  </section>

<%@ include file="../part/foot.jspf"%>