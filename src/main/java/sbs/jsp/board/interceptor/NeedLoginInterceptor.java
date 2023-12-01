package sbs.jsp.board.interceptor;

import sbs.jsp.board.Rq;

public class NeedLoginInterceptor extends Interceptor {
  @Override
  public boolean runBeforeAction(Rq rq) {
    switch (rq.getActionPath()) {
      case "/usr/article/write":
      case "/usr/article/doWrite":
      case "/usr/article/modify":
      case "/usr/article/doModify":
      case "/usr/article/doDelete":
      case "/usr/member/doLogout":
        return false;
    }

    if(rq.isNotLogined()) {
      rq.historyBack("로그인 후 이용해주세요.");
      return true;
    }

    return true;
  }
}
