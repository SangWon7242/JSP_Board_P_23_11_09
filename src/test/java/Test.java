import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;

import java.util.List;
import java.util.Map;

public class Test {
  public static void main(String[] args) {
    MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "jspboard");

    MysqlUtil.setDevMode(true);

    List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(new SecSql().append("SELECT * FROM article"));
    System.out.println("articleListMap : " + articleListMap);

    Map<String, Object> articleMap = MysqlUtil.selectRow(new SecSql().append("SELECT * FROM article WHERE id = ?", 1));
    System.out.println("articleMap : " + articleMap);

    int id = MysqlUtil.selectRowIntValue(new SecSql().append("SELECT id FROM article WHERE id = ?", 1));
    System.out.println("articleId : " + id);

    boolean idIsExists = MysqlUtil.selectRowBooleanValue(new SecSql().append("SELECT COUNT(*) FROM article WHERE id = ?", 4));
    System.out.println("idIsExists : " + idIsExists);

    String newTitle = "새 제목";
    String newContent = "새 내용";
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", newTitle);
    sql.append(", content = ?", newContent);

    MysqlUtil.insert(sql);

    MysqlUtil.closeConnection();
  }
}
