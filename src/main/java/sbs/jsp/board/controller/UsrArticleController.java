package sbs.jsp.board.controller;

import jakarta.servlet.http.HttpSession;
import sbs.jsp.board.Rq;
import sbs.jsp.board.dto.Article;
import sbs.jsp.board.dto.ResultData;
import sbs.jsp.board.service.ArticleService;
import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;

import java.util.List;
import java.util.Map;

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

    List<Map<String, Object>> articleRows = articleService.getArticleRows(page);

    rq.setAttr("articleRows", articleRows);
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
      rq.historyBack("해당 게시물은 없는 게시물입니다.");
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
      rq.print("""
          <script>
            alert('잘못 된 요청입니다.');
            history.back();
          </script>
          """);
      return;
    }

    SecSql sql = new SecSql();
    sql.append("SELECT A.*");
    sql.append("FROM article AS A");
    sql.append("WHERE A.id = ?", id);

    Map<String, Object> articleRow = MysqlUtil.selectRow(sql);

    rq.setAttr("articleRow", articleRow);

    rq.jsp("article/modify");
  }

  public void actionModify(Rq rq) {
    int id = rq.getIntParam("id", 0);
    String title = rq.getParam("title", "");
    String content = rq.getParam("content", "");

    if (title.length() == 0) {
      rq.print("""
          <script>
            alert('제목을 입력해주세요');
            history.back();
          </script>
          """);
    }

    if (content.length() == 0) {
      rq.print("""
          <script>
            alert('내용을 입력해주세요');
            history.back();
          </script>
          """);
    }

    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", content = ?", content);
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);

    rq.print("""
          <script>
            alert('%d번 게시물이 수정되었습니다.');
            location.replace('detail?id=%d');
          </script>
          """.formatted(id, id));
  }

  public void actionDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      rq.print("<script>alert('잘못 된 요청입니다.'); history.back(); </script>");
      return;
    }

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*)");
    sql.append("FROM article AS A");
    sql.append("WHERE A.id = ?", id);

    boolean articleIsExists = MysqlUtil.selectRowBooleanValue(sql);

    if(articleIsExists == false) {
      rq.print("""
          <script>
            alert('해당 게시물은 없는 게시물입니다.');
            location.replace('list');
          </script>
          """);
      return;
    }

    sql = new SecSql();
    sql.append("DELETE");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    MysqlUtil.delete(sql);

    rq.print("""
        <script>
          alert('%d번 글이 삭제되었습니다.');
          location.replace('list');
        </script>
        """.formatted(id));
  }
}
