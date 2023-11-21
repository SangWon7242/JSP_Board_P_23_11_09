package sbs.jsp.board.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sbs.jsp.board.Rq;
import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;

import java.io.IOException;
import java.util.Map;

@WebServlet("/usr/member/doLogin")
public class UsrMemberDoLoginServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "jspboard");
    MysqlUtil.setDevMode(true);

    Rq rq = new Rq(req, resp);

    String loginId = rq.getParam("loginId", "");
    String loginPw = rq.getParam("loginPw", "");

    SecSql sql = new SecSql();
    sql.append("SELECT*");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    Map<String, Object> memberRow = MysqlUtil.selectRow(sql);

    if(memberRow.isEmpty()) {
      rq.appendBody("""
          <script>
            alert('로그인 아이디를 잘 못 입력하셨습니다.');
            history.back();
          </script>
          """);
    }

    if(((String) memberRow.get("loginPw")).equals(loginPw) == false) {
      rq.appendBody("""
          <script>
            alert('로그인 비번을 잘 못 입력하셨습니다.');
            history.back();
          </script>
          """);
    }
    
    // 세션에다 현재 로그인한 정보를 담기 위해서
    HttpSession session = req.getSession();
    session.setAttribute("loginedMemberId", memberRow.get("id"));

    rq.appendBody("""
          <script>
            alert('로그인 되었습니다.');
            location.replace('/home/main');
          </script>
          """);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
