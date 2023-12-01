package sbs.jsp.board.controller;

import sbs.jsp.board.Rq;
import sbs.jsp.board.container.Container;
import sbs.jsp.board.dto.Member;
import sbs.jsp.board.dto.ResultData;
import sbs.jsp.board.service.MemberService;

public class UsrMemberController extends Controller {
  private MemberService memberService;
  public UsrMemberController() {
    memberService = Container.memberService;
  }

  @Override
  public void performAction(Rq rq) {
    switch (rq.getActionMethodName()) {
      case "join" -> showJoin(rq);
      case "doJoin" -> actionJoin(rq);
      case "login" -> showLogin(rq);
      case "doLogin" -> actionDoLoin(rq);
      case "logout" -> actionDoLogout(rq);
      default -> rq.println("존재하지 않는 페이지 입니다.");
    }
  }

  private void actionDoLogout(Rq rq) {
    rq.removeSessionAttr("loginedMemberId");
    rq.replace("로그아웃 되었습니다.", "../article/list");
  }

  private void actionDoLoin(Rq rq) {
    String loginId = rq.getParam("loginId", "");
    String loginPw = rq.getParam("loginPw", "");
    String redirectUri = rq.getParam("redirectUri", "../article/list");

    if(loginId.length() == 0) {
      rq.historyBack("loginId(을)를 입력해주세요.");
      return;
    }

    if(loginPw.length() == 0) {
      rq.historyBack("loginPw(을)를 입력해주세요.");
      return;
    }

    ResultData loginRd = memberService.login(loginId, loginPw);

    if(loginRd.isFail()) {
      rq.historyBack(loginRd.getMsg());
      return;
    }

    Member member = (Member) loginRd.getBody().get("member");

    rq.setSessionAttr("loginedMember", member);

    rq.replace(loginRd.getMsg(), redirectUri);
  }

  private void showLogin(Rq rq) {
    rq.jsp("member/login");
  }

  private void actionJoin(Rq rq) {
    String loginId = rq.getParam("loginId", "");
    String loginPw = rq.getParam("loginPw", "");
    String name = rq.getParam("name", "");
    String email = rq.getParam("email", "");
    String redirectUri = rq.getParam("redirectUri", "../article/list");

    if(loginId.length() == 0) {
      rq.historyBack("loginId(을)를 입력해주세요.");
      return;
    }

    if(loginPw.length() == 0) {
      rq.historyBack("loginPw(을)를 입력해주세요.");
      return;
    }

    if(name.length() == 0) {
      rq.historyBack("name(을)를 입력해주세요.");
      return;
    }

    if(email.length() == 0) {
      rq.historyBack("email(을)를 입력해주세요.");
      return;
    }

    ResultData joinRd = memberService.join(loginId, loginPw, name, email);

    rq.replace(joinRd.getMsg(), redirectUri);
  }

  private void showJoin(Rq rq) {
    rq.jsp("member/join");
  }

}
