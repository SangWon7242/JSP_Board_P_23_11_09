<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="sbs.jsp.board.dto.Article" %>
<%@ page import="java.util.Map" %>

<%
  Article article = (Article) request.getAttribute("article");
%>

<%@ include file="../part/head.jspf"%>
  <style>
    body, ul, li {
      margin: 0;
    }
  </style>

  <section class="modify_section">
    <div class="con">
      <h1>글 수정</h1>
      <form action="doModify" method="POST">
        <input type="hidden" name="redirectUri" value="../article/detail?id=[NEW_ID]">

        <input type="hidden" name="id" value="${param.id}">
        <div>제목 : <input autocomplete="off" placeholder="제목을 입력해주세요." name="title" type="text" value="<%= article.getTitle() %>"></div>
        <div>내용 : <textarea autocomplete="off" placeholder="내용을 입력해주세요." name="content" type="text"><%= article.getContent()%></textarea></div>
        <div class="btn-group">
          <button type="submit">수정</button>
          <a href="detail?id=${param.id}">취소</a>
          <a href="list">리스트</a>
        </div>
      </form>
    </div>
  </section>

<%@ include file="../part/foot.jspf"%>
