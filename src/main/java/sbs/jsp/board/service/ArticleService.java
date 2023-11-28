package sbs.jsp.board.service;

import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;

import java.util.List;
import java.util.Map;

public class ArticleService {

  public int getItemInAPage() {
    return 10;
  }
  public int getForPrintListTotalPage() {
    int itemInAPage = getItemInAPage();

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*)");
    sql.append("FROM article");

    int totalCount = MysqlUtil.selectRowIntValue(sql);
    int totalPage = (int)Math.ceil((double) totalCount / itemInAPage);

    return totalPage;
  }

  public List<Map<String, Object>> getForPrintArticleRows(int page) {
    int itemInAPage = getItemInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    SecSql sql = new SecSql();
    sql.append("SELECT A.*, M.name AS writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("ORDER BY A.id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleRows = MysqlUtil.selectRows(sql);

    return articleRows;
  }
}
