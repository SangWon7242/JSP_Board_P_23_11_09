package sbs.jsp.board.interceptor;

import sbs.jsp.board.Rq;

public class NeedLogoutInterceptor extends Interceptor {
  @Override
  public boolean runBeforeAction(Rq rq) {
//    switch (rq.getActionPath()) {
//        return true;
//    }

    if(rq.isLogined()) {
      rq.historyBack("로그아웃 후 이용해주세요.");
      return false;
    }

    return true;
  }
}
