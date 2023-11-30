package sbs.jsp.board.controller;

import jakarta.servlet.http.HttpSession;
import sbs.jsp.board.Rq;
import sbs.jsp.board.dto.Article;
import sbs.jsp.board.dto.ResultData;
import sbs.jsp.board.service.ArticleService;
import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;
import sbs.jsp.board.util.Ut;

import java.util.List;

public class UsrArticleController extends Controller {
  private ArticleService articleService;

  public UsrArticleController() {
    articleService = new ArticleService();
  }

  @Override
  public void performAction(Rq rq) {
    switch (rq.getActionMethodName()) {
      case "list" -> showList(rq);
      case "detail" -> showDetail(rq);
      case "write" -> showWrite(rq);
      case "doWrite" -> actionWrite(rq);
      case "modify" -> showModify(rq);
      case "doModify" -> actionModify(rq);
      case "doDelete" -> actionDelete(rq);
      default -> rq.println("존재하지 않는 페이지 입니다.");
    }
  }

  public void showList(Rq rq) {
    int page = rq.getIntParam("page", 1);

    int totalPage = articleService.getForPrintListTotalPage();

    List<Article> articles = articleService.getForPrintArticles(page);

    rq.setAttr("articles", articles);
    rq.setAttr("page", page);
    rq.setAttr("totalPage", totalPage);

    rq.jsp("article/list");
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      rq.historyBack("잘못 된 요청입니다.");
      return;
    }

    Article article = articleService.getForPrintArticleById(id);

    if(article == null) {
      rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
      return;
    }

    rq.setAttr("article", article);

    rq.jsp("article/detail");
  }

  public void showWrite(Rq rq) {
    rq.jsp("article/write");
  }

  public void actionWrite(Rq rq) {
    String title = rq.getParam("title", "");
    String content = rq.getParam("content", "");
    String redirectUri = rq.getParam("redirectUri", "../article/list");

    if (title.length() == 0) {
      rq.historyBack("제목을 입력해주세요.");
      return;
    }

    if (content.length() == 0) {
      rq.historyBack("내용을 입력해주세요");
      return;
    }

    HttpSession session = rq.getSession();

    if(session.getAttribute("loginedMemberId") == null) {
      rq.replace("로그인 후 이용해주세요.", "../member/login");
      return;
    }

    int loginedMemberId = (int)session.getAttribute("loginedMemberId");

    ResultData writeRd = articleService.write(loginedMemberId, title, content);
    int id = (int) writeRd.getBody().get("id");

    redirectUri = redirectUri.replace("[NEW_ID]", id + "");

    // System.out.println("redirectUri : " + redirectUri);
    // ../article/detail?id=507

    rq.replace(writeRd.getMsg(), redirectUri);
  }

  public void showModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      rq.historyBack("잘못 된 요청입니다.");
      return;
    }

    HttpSession session = rq.getSession();

    if(session.getAttribute("loginedMemberId") == null) {
      rq.replace("로그인 후 이용해주세요.", "../member/login");
      return;
    }

    int loginedMemberId = (int)session.getAttribute("loginedMemberId");

    Article article = articleService.getForPrintArticleById(id);

    if(article == null) {
      rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);

    if(actorCanModifyRd.isFail()) {
      rq.historyBack(actorCanModifyRd.getMsg());
      return;
    }

    rq.setAttr("article", article);

    rq.jsp("article/modify");
  }

  public void actionModify(Rq rq) {
    int id = rq.getIntParam("id", 0);
    String title = rq.getParam("title", "");
    String content = rq.getParam("content", "");
    String redirectUri = rq.getParam("redirectUri", Ut.f("../article/detail?id=%d", id));

    if (title.length() == 0) {
      rq.historyBack("제목을 입력해주세요");
      return;
    }

    if (content.length() == 0) {
      rq.historyBack("내용을 입력해주세요");
      return;
    }

    if(id == 0) {
      rq.historyBack("잘못 된 요청입니다.");
      return;
    }

    Article article = articleService.getForPrintArticleById(id);

    HttpSession session = rq.getSession();

    if(session.getAttribute("loginedMemberId") == null) {
      rq.replace("로그인 후 이용해주세요.", "../member/login");
      return;
    }

    int loginedMemberId = (int)session.getAttribute("loginedMemberId");

    ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);

    if(actorCanModifyRd.isFail()) {
      rq.historyBack(actorCanModifyRd.getMsg());
      return;
    }

    ResultData modifyRd = articleService.modify(id, title, content);

    rq.replace(modifyRd.getMsg(), redirectUri);
  }

  public void actionDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);
    String redirectUri = rq.getParam("redirectUri", "../article/list");

    if(id == 0) {
      rq.historyBack("잘못 된 요청입니다.");
      return;
    }

    Article article = articleService.getForPrintArticleById(id);

    HttpSession session = rq.getSession();

    if(session.getAttribute("loginedMemberId") == null) {
      rq.replace("로그인 후 이용해주세요.", "../member/login");
      return;
    }

    int loginedMemberId = (int)session.getAttribute("loginedMemberId");

    ResultData actorCanDeleteRd = articleService.actorCanDelete(loginedMemberId, article);

    if(actorCanDeleteRd.isFail()) {
      rq.historyBack(actorCanDeleteRd.getMsg());
      return;
    }

    if(article == null) {
      rq.historyBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    ResultData deleteRd = articleService.delete(id);

    rq.replace(deleteRd.getMsg(), redirectUri);
  }
}
