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

    String requestUri = req.getRequestURI();
    String[] requestUriBits = requestUri.split("/");
    // /usr/article/list
    // [0]/[1]/[2]/[3]

    int minBitsCount = 4;

    if (requestUriBits.length < minBitsCount) {
      rq.appendBody("""
            <script>
              alert('올바른 요청이 아닙니다.');
              location.replace('/home/main');
            </script>
          """);
    }

    int controllerTypeNameIndex = 1;
    int controllerNameIndex = 2;
    int controllerMethodNameIndex = 3;

    String controllerTypeName = requestUriBits[controllerTypeNameIndex];
    String controllerName = requestUriBits[controllerNameIndex];
    String actionMethodName = requestUriBits[controllerMethodNameIndex];

    System.out.printf("%s/%s/%s\n", controllerTypeName, controllerName, actionMethodName);

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

    if(controllerName.equals("home")) {
      UsrHomeController usrHomeController = new UsrHomeController(rq);

      switch (actionMethodName) {
        case "main" -> usrHomeController.showMain();
      }
    }

    if(controllerName.equals("article")) {
      UsrArticleController usrArticleController = new UsrArticleController(rq);

      switch (actionMethodName) {
        case "list" -> usrArticleController.showList();
        case "detail" -> usrArticleController.showDetail();
        case "write" -> usrArticleController.showWrite();
        case "doWrite" -> usrArticleController.actionWrite();
        case "modify" -> usrArticleController.showModify();
        case "doModify" -> usrArticleController.actionModify();
        case "doDelete" -> usrArticleController.actionDelete();
      }
    }

    MysqlUtil.closeConnection();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
