package sbs.jsp.board.interceptor;

import sbs.jsp.board.Rq;

public class NeedLoginInterceptor extends Interceptor {
  @Override
  public boolean runBeforeAction(Rq rq) {
    switch (rq.getActionPath()) {
      case "/usr/article/list":
      case "/usr/article/detail":
      case "/usr/home/main":
      case "/usr/member/login":
      case "/usr/member/doLogin":
        return true;
    }

    if(rq.isNotLogined()) {
      rq.historyBack("로그인 후 이용해주세요.");
      return false;
    }

    return true;
  }
}
