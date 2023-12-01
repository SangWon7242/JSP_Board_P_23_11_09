package sbs.jsp.board.interceptor;

import sbs.jsp.board.Rq;
import sbs.jsp.board.dto.Member;

public class BeforeActionInterceptor extends Interceptor {
  @Override
  public boolean runBeforeAction(Rq rq) {
    // 모든 요청을 들어가기 전에 무조건 해야 하는 일 시작
    boolean isLogined = false;
    int loginedMemberId = 0;
    Member loginedMember = null;

    if (rq.getSessionAttr("loginedMember") != null) {
      loginedMember = rq.getSessionAttr("loginedMember");
      loginedMemberId = loginedMember.getId();
      isLogined = true;
    }

    rq.setAttr("isLogined", isLogined); // 로그인 여부
    rq.setAttr("loginedMemberId", loginedMemberId);
    rq.setAttr("member", loginedMember);
    // 모든 요청을 들어가기 전에 무조건 해야 하는 일 끝

    return true;
  }
}
