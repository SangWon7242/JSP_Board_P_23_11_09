package sbs.jsp.board.repository;

import sbs.jsp.board.dto.Member;
import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;

public class MemberRepository {
  public Member getMemberByLoginId(String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT M.*");
    sql.append("FROM member AS M");
    sql.append("WHERE M.loginId = ?", loginId);

    return new Member(MysqlUtil.selectRow(sql));
  }

  public Member getMemberByNameAndEmail(String name, String email) {
    SecSql sql = new SecSql();
    sql.append("SELECT M.*");
    sql.append("FROM member AS M");
    sql.append("WHERE M.name = ?", name);
    sql.append("AND M.email = ?", email);
    sql.append("LIMIT 1");

    return new Member(MysqlUtil.selectRow(sql));
  }

  public int join(String loginId, String loginPw, String name, String email) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", name = ?", name);
    sql.append(", email = ?", email);

    int id = MysqlUtil.insert(sql);

    return id;
  }
}
