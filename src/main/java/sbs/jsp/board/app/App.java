package sbs.jsp.board.app;

import sbs.jsp.board.container.Container;
import sbs.jsp.board.util.MysqlUtil;

public class App {
  private static boolean isDevMode() {
    // 이 부분을 false로 바꾸면 production 모드 이다.
    return true;
  }

  public static void init() {
    // DB 세팅
    MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "jspboard");
    MysqlUtil.setDevMode(isDevMode());

    // 공용 객체 세팅
    Container.init();
  }
}
