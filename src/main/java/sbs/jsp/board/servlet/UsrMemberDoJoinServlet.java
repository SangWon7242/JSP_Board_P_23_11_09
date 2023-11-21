package sbs.jsp.board.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sbs.jsp.board.Rq;
import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;

import java.io.IOException;

@WebServlet("/usr/member/doJoin")
public class UsrMemberDoJoinServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "jspboard");
    MysqlUtil.setDevMode(true);

    Rq rq = new Rq(req, resp);

    String loginId = rq.getParam("loginId", "");
    String loginPw = rq.getParam("loginPw", "");
    String name = rq.getParam("name", "");
    String email = rq.getParam("email", "");

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    boolean isJoinAvailableLoginId = MysqlUtil.selectRowIntValue(sql) == 0;

    if(isJoinAvailableLoginId == false) {
      rq.appendBody("""
          <script>
            alert('%s(은)는 중복 된 로그인 아이디입니다.');
            history.back();
          </script>
          """.formatted(loginId));
    }

    sql = new SecSql();
    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM `member`");
    sql.append("WHERE email = ?", email);

    boolean isJoinAvailableEmail = MysqlUtil.selectRowIntValue(sql) == 0;

    if(isJoinAvailableEmail == false) {
      rq.appendBody("""
          <script>
            alert('%s(은)는 중복 된 이메일입니다.');
            history.back();
          </script>
          """.formatted(email));
    }

    sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", name = ?", name);
    sql.append(", email = ?", email);

    int id = MysqlUtil.insert(sql);
    rq.appendBody("""
          <script>
            alert('%d번 회원이 생성되었습니다.');
            location.replace('/home/main');
          </script>
          """.formatted(id, id));
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
