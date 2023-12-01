package sbs.jsp.board.interceptor;

import sbs.jsp.board.Rq;

public class NeedLogoutInterceptor extends Interceptor {
  @Override
  public boolean runBeforeAction(Rq rq) {
    switch (rq.getActionPath()) {
      case "/usr/member/join":
      case "/usr/member/doJoin":
      case "/usr/member/login":
      case "/usr/member/doLogin":
        return false;
    }

    if(rq.isLogined()) {
      rq.historyBack("로그아웃 후 이용해주세요.");
      return true;
    }

    return true;
  }
}
