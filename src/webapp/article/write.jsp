<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.Map" %>
<%@ page import="sbs.jsp.board.Rq" %>

<%
  Rq rq = new Rq(request, response);
  Map<String, Object> articleRow = (Map<String, Object>) rq.getAttr("articleRow");
%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>게시물 작성</title>
</head>
<body>
  <style>
    body, ul, li {
      margin: 0;
    }
  </style>

  <section class="write_section">
    <div class="con">
      <form action="doWrite" method="POST">
        <div>제목 : <input autocomplete="off" placeholder="제목을 입력해주세요." name="title" type="text"></div>
        <div>내용 : <textarea autocomplete="off" placeholder="내용을 입력해주세요." name="content" type="text"></textarea></div>
        <div class="btn-group">
          <button type="submit">글 작성</button>
          <a href="list">리스트로 돌아가기</a>
        </div>
      </form>
    </div>
  </section>

</body>
</html>
