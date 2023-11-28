<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.Map" %>

<%
  Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
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
            <td><%= articleRow.get("id") %></td>
            <td><%= articleRow.get("regDate") %></td>
            <td><%= articleRow.get("updateDate") %></td>
            <td><%= articleRow.get("title") %></td>
            <td><%= articleRow.get("content") %></td>
            <td><%= articleRow.get("writerName") %></td>
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
