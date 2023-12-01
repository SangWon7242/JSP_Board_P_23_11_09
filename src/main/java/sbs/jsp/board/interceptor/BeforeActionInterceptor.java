package sbs.jsp.board.interceptor;

import sbs.jsp.board.Rq;
import sbs.jsp.board.dto.Member;

public class BeforeActionInterceptor extends Interceptor {
  @Override
  public boolean runBeforeAction(Rq rq) {
    if (rq.getSessionAttr("loginedMember") != null) {
      rq.setLogined(true);
      rq.setLoginedMember(rq.getSessionAttr("loginedMember"));
      rq.setLoginedMemberId(rq.getLoginedMember().getId());
    }

    rq.setAttr("rq", rq);

    return true;
  }
}
