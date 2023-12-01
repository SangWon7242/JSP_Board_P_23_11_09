package sbs.jsp.board.interceptor;

import sbs.jsp.board.Rq;

public abstract class Interceptor {
  abstract public boolean runBeforeAction(Rq rq);
}
