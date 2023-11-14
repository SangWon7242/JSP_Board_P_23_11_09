<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="sbs.jsp.board.Rq" %>

<%
  Rq rq = new Rq(request, response);
  List<Map<String, Object>> articleListMap = (List<Map<String, Object>>) rq.getAttr("articleListMap");
%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>게시물 리스트</title>
</head>
<body>
  <style>
    body, ul, li {
      margin: 0;
    }
  </style>

  <h1>게시물 리스트 v1</h1>
  <ul>
    <li>
      <%= articleListMap.get(0).get("id") %>
      <%= articleListMap.get(0).get("regDate") %>
      <%= articleListMap.get(0).get("updateDate") %>
      <%= articleListMap.get(0).get("title") %>
    </li>
    <li>
      <%= articleListMap.get(1).get("id") %>
      <%= articleListMap.get(1).get("regDate") %>
      <%= articleListMap.get(1).get("updateDate") %>
      <%= articleListMap.get(1).get("title") %>
    </li>
    <li>
      <%= articleListMap.get(2).get("id") %>
      <%= articleListMap.get(2).get("regDate") %>
      <%= articleListMap.get(2).get("updateDate") %>
      <%= articleListMap.get(2).get("title") %>
    </li>
  </ul>

  <h1>게시물 리스트 v2</h1>
  <% for(int i = 0; i < 3; i++) { %>
  <ul>
    <li>
      <%= articleListMap.get(i).get("id") %>
      <%= articleListMap.get(i).get("regDate") %>
      <%= articleListMap.get(i).get("updateDate") %>
      <%= articleListMap.get(i).get("title") %>
    </li>
  </ul>
  <% } %>


   <h1>게시물 리스트 v3</h1>
   <% for(int i = 0; i < 3; i++) {
     Map<String, Object> articleRow = articleListMap.get(i);
   %>
   <ul>
     <li>
       <%= articleRow.get("id") %>
       <%= articleRow.get("regDate") %>
       <%= articleRow.get("updateDate") %>
       <%= articleRow.get("title") %>
     </li>
   </ul>
   <% } %>

  <h1>게시물 리스트 v4</h1>
  <% for(int i = 0; i < articleListMap.size(); i++) {
    Map<String, Object> articleRow = articleListMap.get(i);
  %>
  <ul>
    <li>
      <%= articleRow.get("id") %>
      <%= articleRow.get("regDate") %>
      <%= articleRow.get("updateDate") %>
      <%= articleRow.get("title") %>
    </li>
  </ul>
  <% } %>

  <h1>게시물 리스트 v5</h1>
  <% for(Map<String, Object> articleRow : articleListMap) {
  %>
  <ul>
    <li>
      <%= articleRow.get("id") %>
      <%= articleRow.get("regDate") %>
      <%= articleRow.get("updateDate") %>
      <%= articleRow.get("title") %>
    </li>
  </ul>
  <% } %>

</body>
</html>