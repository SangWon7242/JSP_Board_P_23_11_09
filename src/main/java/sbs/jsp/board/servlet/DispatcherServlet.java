package sbs.jsp.board.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sbs.jsp.board.Rq;
import sbs.jsp.board.controller.UsrArticleController;
import sbs.jsp.board.controller.UsrHomeController;
import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;

import java.io.IOException;
import java.util.Map;

@WebServlet("/usr/*")
public class DispatcherServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "jspboard");
    MysqlUtil.setDevMode(true);

    Rq rq = new Rq(req, resp);

    // 모든 요청을 들어가기 전에 무조건 해야 하는 일 시작
    HttpSession session = req.getSession();

    boolean isLogined = false;
    int loginedMemberId = -1;
    Map<String, Object> loginedMemberRow = null;

    if (session.getAttribute("loginedMemberId") != null) {
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
    // 모든 요청을 들어가기 전에 무조건 해야 하는 일 끝

    switch (rq.getControllerTypeName()) {
      case "usr"
          -> {
        UsrHomeController usrHomeController = new UsrHomeController();
        UsrArticleController usrArticleController = new UsrArticleController();

        switch (rq.getControllerName()) {
          case "home" -> usrHomeController.performAction(rq);
          case "article" -> usrArticleController.performAction(rq);
        }
      }
    }

    MysqlUtil.closeConnection();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
