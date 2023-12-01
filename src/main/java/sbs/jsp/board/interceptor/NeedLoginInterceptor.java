package sbs.jsp.board.interceptor;

import sbs.jsp.board.Rq;

public class NeedLoginInterceptor extends Interceptor {
  @Override
  public boolean runBeforeAction(Rq rq) {
    if(rq.getControllerTypeName().equals("usr") == false) {
      return true;
    }

    switch (rq.getActionPath()) {
      case "/usr/article/list":
      case "/usr/article/detail":
      case "/usr/home/main":
      case "/usr/member/login":
      case "/usr/member/doLogin":
      case "/usr/member/doLogout":
      case "/usr/member/join":
      case "/usr/member/doJoin":
        return true;
    }

    if(rq.isNotLogined()) {
      rq.replace("로그인 후 이용해주세요.", "../member/login");
      return false;
    }

    return true;
  }
}
