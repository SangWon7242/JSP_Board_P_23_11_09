package sbs.jsp.board.service;

import sbs.jsp.board.dto.Member;
import sbs.jsp.board.dto.ResultData;
import sbs.jsp.board.repository.MemberRepository;
import sbs.jsp.board.util.Ut;

public class MemberService {
  private MemberRepository memberRepository;

  public MemberService() {
    memberRepository = new MemberRepository();
  }

  public ResultData join(String loginId, String loginPw, String name, String email) {
    Member oldMember = getMemberByLoginId(loginId);

    if(oldMember != null) {
      return ResultData.from("F-1", Ut.f("%s(은)는 이미 가입된 로그인 아이디입니다.", loginId));
    }

    oldMember = getMemberByNameAndEmail(name, email);

    if(oldMember != null) {
      return ResultData.from("F-2", Ut.f("%s(은)는 이미 가입된 이메일입니다.", email));
    }

    int id = memberRepository.join(loginId, loginPw, name, email);

    return ResultData.from("S-1", "회원 가입이 완료되었습니다.", "id", id);
  }

  private Member getMemberByNameAndEmail(String name, String email) {
    return memberRepository.getMemberByNameAndEmail(name, email);
  }

  private Member getMemberByLoginId(String loginId) {
    return memberRepository.getMemberByLoginId(loginId);
  }
}
