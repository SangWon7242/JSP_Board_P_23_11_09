<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.Map" %>

<%
  Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
%>

<%@ include file="../part/head.jspf"%>
  <style>
    body, ul, li {
      margin: 0;
    }
  </style>

  <section class="write_section">
    <div class="con">
      <h1>글 작성</h1>
      <form action="doWrite" method="POST">
        <div>제목 : <input autocomplete="off" placeholder="제목을 입력해주세요." name="title" type="text"></div>
        <div>내용 : <textarea autocomplete="off" placeholder="내용을 입력해주세요." name="content" type="text"></textarea></div>
        <div class="btn-group">
          <button type="submit">작성</button>
          <a href="list">취소</a>
        </div>
      </form>
    </div>
  </section>
<%@ include file="../part/foot.jspf"%>
