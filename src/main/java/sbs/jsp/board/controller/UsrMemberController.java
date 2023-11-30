package sbs.jsp.board.controller;

import sbs.jsp.board.Rq;
import sbs.jsp.board.dto.ResultData;
import sbs.jsp.board.service.MemberService;
import sbs.jsp.board.util.MysqlUtil;
import sbs.jsp.board.util.SecSql;

public class UsrMemberController extends Controller {
  private MemberService memberService;
  public UsrMemberController() {
    memberService = new MemberService();
  }

  @Override
  public void performAction(Rq rq) {
    switch (rq.getActionMethodName()) {
      case "join" -> showJoin(rq);
      case "doJoin" -> actionJoin(rq);
      case "login" -> showLogin(rq);
      case "doLogin" -> actionDoLoin(rq);
      default -> rq.println("존재하지 않는 페이지 입니다.");
    }
  }

  private void actionDoLoin(Rq rq) {
  }

  private void showLogin(Rq rq) {

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
