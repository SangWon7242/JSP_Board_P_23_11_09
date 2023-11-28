package sbs.jsp.board.controller;

import jakarta.servlet.http.HttpSession;
import sbs.jsp.board.Rq;
import sbs.jsp.board.service.ArticleService;
import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;

import java.util.List;
import java.util.Map;

public class UsrArticleController {
  private Rq rq;
  private ArticleService articleService;

  public UsrArticleController(Rq rq) {
    this.rq = rq;
    articleService = new ArticleService();
  }

  public void showList() {
    int page = rq.getIntParam("page", 1);

    int totalPage = articleService.getForPrintListTotalPage();

    List<Map<String, Object>> articleRows = articleService.getForPrintArticleRows(page);

    rq.setAttr("articleRows", articleRows);
    rq.setAttr("page", page);
    rq.setAttr("totalPage", totalPage);

    rq.jsp("article/list");
  }

  public void showDetail() {
    HttpSession session = rq.getSession();

    boolean isLogined = false;
    int loginedMemberId = -1;
    Map<String, Object> loginedMemberRow = null;

    if(session.getAttribute("loginedMemberId") != null) {
      loginedMemberId = (int) session.getAttribute("loginedMemberId");
      isLogined = true;

      SecSql sql = new SecSql();
      sql.append("SELECT * FROM `member`");
      sql.append("WHERE id = ?", loginedMemberId);
      loginedMemberRow = MysqlUtil.selectRow(sql);
    }

    rq.setAttr("isLogined", isLogined); // 로그인 여부
    rq.setAttr("loginedMemberId", loginedMemberId);
    rq.setAttr("loginedMemberRow", loginedMemberRow);

    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      rq.appendBody("<script>alert('잘못 된 요청입니다.'); history.back(); </script>");
      return;
    }

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*)");
    sql.append("FROM article AS A");
    sql.append("WHERE A.id = ?", id);

    boolean articleIsExists = MysqlUtil.selectRowBooleanValue(sql);

    if(articleIsExists == false) {
      rq.appendBody("<script>alert('해당 게시물은 없는 게시물입니다.'); history.back(); </script>");
      return;
    }

    sql = new SecSql();
    sql.append("SELECT A.*, M.name AS writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("WHERE A.id = ?", id);
    sql.append("ORDER BY A.id DESC");

    Map<String, Object> articleRow = MysqlUtil.selectRow(sql);

    rq.setAttr("articleRow", articleRow);

    rq.jsp("article/detail");
  }

  public void showWrite() {
    rq.jsp("article/write");
  }

  public void actionWrite() {
    String title = rq.getParam("title", "");
    String content = rq.getParam("content", "");

    if (title.length() == 0) {
      rq.appendBody("""
          <script>
            alert('제목을 입력해주세요');
            history.back();
          </script>
          """);
    }

    if (content.length() == 0) {
      rq.appendBody("""
          <script>
            alert('내용을 입력해주세요');
            history.back();
          </script>
          """);
    }

    HttpSession session = rq.getSession();

    if(session.getAttribute("loginedMemberId") == null) {
      rq.appendBody("""
          <script>
            alert('로그인 후 이용해주세요.');
            location.replace('../member/login');
          </script>
          """);
    }

    int loginedMemberId = (int)session.getAttribute("loginedMemberId");

    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", content = ?", content);
    sql.append(", memberId = ?", loginedMemberId);

    int id = MysqlUtil.insert(sql);
    rq.appendBody("""
          <script>
            alert('%d번 게시물이 생성되었습니다.');
            location.replace('detail?id=%d');
          </script>
          """.formatted(id, id));
  }

  public void showModify() {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      rq.appendBody("""
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

  public void actionModify() {
    int id = rq.getIntParam("id", 0);
    String title = rq.getParam("title", "");
    String content = rq.getParam("content", "");

    if (title.length() == 0) {
      rq.appendBody("""
          <script>
            alert('제목을 입력해주세요');
            history.back();
          </script>
          """);
    }

    if (content.length() == 0) {
      rq.appendBody("""
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

    rq.appendBody("""
          <script>
            alert('%d번 게시물이 수정되었습니다.');
            location.replace('detail?id=%d');
          </script>
          """.formatted(id, id));
  }

  public void actionDelete() {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      rq.appendBody("<script>alert('잘못 된 요청입니다.'); history.back(); </script>");
      return;
    }

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*)");
    sql.append("FROM article AS A");
    sql.append("WHERE A.id = ?", id);

    boolean articleIsExists = MysqlUtil.selectRowBooleanValue(sql);

    if(articleIsExists == false) {
      rq.appendBody("""
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

    rq.appendBody("""
        <script>
          alert('%d번 글이 삭제되었습니다.');
          location.replace('list');
        </script>
        """.formatted(id));
  }
}
