<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
          <c:forEach items="${articles}" var="article">
          <tr>
            <td>${article.id}</td>
            <td>${article.regDate}</td>
            <td>${article.updateDate}</td>
            <td>
              <a href="detail?id=${article.id}">${article.title}</a>
            </td>
            <td>${article.extra__writerName}</td>
          </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
      <div class="btn-group mt-[10px]">
        <a href="write" class="btn btn-primary">게시물 작성</a>
      </div>

      <div class="page flex justify-center mt-[15px]">
        <c:set var="cPage" value="${page}" />
        <c:set var="totalPage" value="${totalPage}" />

        <c:choose>
          <c:when test="${cPage > 1}">
            <button onclick="location.href='list?page=1'" class="btn">◀</button>
          </c:when>
          <c:when test="${cPage == 1}">
            <button onclick="location.href='list?page=1'" class="btn" disabled>◀</button>
          </c:when>
        </c:choose>

        <c:set var="pageMenuSize" value="5" />
        <c:set var="startPage" value="${cPage - pageMenuSize > 1 ? cPage - pageMenuSize : 1}" />
        <c:set var="endPage" value="${cPage + 10 < totalPage ? cPage + 10 : totalPage}" />

        <c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
          <c:set var="aClassStr" value="${cPage == i ? 'btn-active' : ''}" />
          <a class="btn ${aClassStr}" href="list?page=${i}">${i}</a>
        </c:forEach>

        <c:choose>
          <c:when test="${cPage < totalPage}">
            <button onclick="location.href='list?page=${totalPage}'" class="btn">▶</button>
          </c:when>
          <c:when test="${cPage == totalPage}">
            <button onclick="location.href='list?page=${totalPage}'" class="btn" disabled>▶</button>
          </c:when>
        </c:choose>
      </div>
    </div>
  </section>

<%@ include file="../part/foot.jspf"%>