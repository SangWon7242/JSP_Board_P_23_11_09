<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="sbs.jsp.board.dto.Article" %>

<%
  Article article = (Article) request.getAttribute("article");
%>

<%@ include file="../part/head.jspf"%>

<section class="article-detail-wrap">
  <div class="con mx-auto w-[1100px]">
    <h1 class="badge badge-neutral my-[10px]">게시물 상세보기</h1>
    <div class="article-detail-box border rounded-xl h-[600px] p-[20px] flex flex-col">
      <div class="article-datail__head border-b">
        <div class="title h-[80px] flex items-center">
          <h1 class="text-[2rem]">
            <%= article.getTitle() %>
          </h1>
        </div>
        <div class="write-info">
          <div class="writer-name font-bold badge badge-ghost !p-3">
            <span>작성자 : </span>
            &nbsp;
            <span><%= article.getExtra__writerName() %></span>
          </div>
          <div class="write-date flex gap-x-3 ml-[3px] my-3">
            <div class="write-date__regdate badge badge-primary badge-outline">
              <span>작성날짜 :</span>
              &nbsp;
              <span><%= article.getRegDate() %></span>
            </div>
            <div class="write-date__updateDate badge badge-secondary badge-outline">
              <span>수정날짜 :</span>
              &nbsp;
              <span><%= article.getUpdateDate() %></span>
            </div>
          </div>
        </div>
      </div>
      <div class="article-datail__body flex-grow">
        <div class="content-box text-[2rem] mt-[20px]">
          <%= article.getContent() %>
        </div>
      </div>
    </div>

    <div class="btn-group mt-[10px]">
      <a href="list" class="btn">리스트</a>
      <c:if test="${article.extra__actorCanDelete}">
        <a href="doDelete?id=${param.id}" class="btn btn-primary">삭제</a>&nbsp;
      </c:if>
      <c:if test="${article.extra__actorCanModify}">
        <a href="modify?id=${param.id}" class="btn btn-secondary">수정</a>
      </c:if>
    </div>
  </div>
</section>
<%@ include file="../part/foot.jspf"%>
