<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="sbs.jsp.board.Rq" %>

<%
Rq rq = new Rq(request, response);
boolean isLogined = (boolean) rq.getAttr("isLogined");
int loginedMemberId = (int) rq.getAttr("loginedMemberId");
%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>메인페이지</title>
</head>
<body>
  <style>
    body, ul, li {
      margin: 0;
      padding: 0;
      list-style : none;
  </style>

  <h1>메인페이지입니다.</h1>

  <% if(isLogined) { %>
    <div>
      <%=loginedMemberId%> 번 회원님 환영합니다.
    </div>
  <% } %>

  <nav class="menu-box-1">
    <ul style="display:flex; gap: 0 10px">
      <li><a href="/usr/article/list">게시물 리스트</a></li>
      <li><a href="/usr/member/join">회원가입</a></li>
      <% if(!isLogined) { %>
        <li><a href="/usr/member/login">로그인</a></li>
      <% } %>
      <% if(isLogined) { %>
        <li><a href="/usr/member/doLogout">로그아웃</a></li>
      <% } %>
    </ul>
  </nav>
</body>
</html>
