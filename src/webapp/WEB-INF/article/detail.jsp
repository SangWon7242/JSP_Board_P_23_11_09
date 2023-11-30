<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="sbs.jsp.board.dto.Article" %>

<%
  Article article = (Article) request.getAttribute("article");
%>

<%@ include file="../part/head.jspf"%>
  <style>
    .section {
      display: flex;
      justify-content: center;
    }
  </style>

  <section class="section">
    <div class="con">
      <h1>게시물 상세보기</h1>

      <table border="1" style="border-collapse: collapse; text-align:center;">
        <colgroup>
          <col width="50">
          <col width="200">
          <col width="200">
          <col width="200">
          <col width="200">
          <col width="150">
        </colgroup>
        <thead>
        <tr>
          <th>번호</th>
          <th>작성날짜</th>
          <th>수정날짜</th>
          <th>제목</th>
          <th>내용</th>
          <th>작성자</th>
        </tr>
        </thead>
        <tbody>
          <tr>
            <td><%= article.getId() %></td>
            <td><%= article.getRegDate() %></td>
            <td><%= article.getUpdateDate() %></td>
            <td><%= article.getTitle() %></td>
            <td><%= article.getContent() %></td>
            <td><%= article.getExtra__writerName() %></td>
          </tr>
        </tbody>
      </table>
      <div class="btn-group">
        <a href="list">리스트</a>
        &nbsp;
        <a href="doDelete?id=${param.id}">삭제</a>
        &nbsp;
        <a href="modify?id=${param.id}">수정</a>
      </div>
    </div>
  </section>

<%@ include file="../part/foot.jspf"%>
