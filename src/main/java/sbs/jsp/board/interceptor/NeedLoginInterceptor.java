package sbs.jsp.board.interceptor;

import sbs.jsp.board.Rq;

public class NeedLoginInterceptor extends Interceptor {
  @Override
  public boolean runBeforeAction(Rq rq) {
    return true;
  }
}
