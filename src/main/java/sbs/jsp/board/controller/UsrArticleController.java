package sbs.jsp.board.controller;

import sbs.jsp.board.Rq;
import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;

import java.util.List;
import java.util.Map;

public class UsrArticleController {
  private Rq rq;

  public UsrArticleController(Rq rq) {
    this.rq = rq;
  }

  public void actionList() {
    int page = rq.getIntParam("page", 1);
    int itemInAPage = 20;
    int limitFrom = (page - 1) * itemInAPage;

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*)");
    sql.append("FROM article");

    int totalCount = MysqlUtil.selectRowIntValue(sql);
    int totalPage = (int)Math.ceil((double) totalCount / itemInAPage);

    sql = new SecSql();
    sql.append("SELECT A.*, M.name AS writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("ORDER BY A.id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

    System.out.println("articleListMap : " + articleListMap);

    rq.setAttr("articleListMap", articleListMap);
    rq.setAttr("page", page);
    rq.setAttr("totalPage", totalPage);

    rq.jsp("article/list");
  }
}
