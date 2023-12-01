package sbs.jsp.board.container;

import sbs.jsp.board.controller.UsrArticleController;
import sbs.jsp.board.controller.UsrHomeController;
import sbs.jsp.board.controller.UsrMemberController;
import sbs.jsp.board.repository.ArticleRepository;
import sbs.jsp.board.repository.MemberRepository;
import sbs.jsp.board.service.ArticleService;
import sbs.jsp.board.service.MemberService;

public class Container {
  public static MemberRepository memberRepository;
  public static ArticleRepository articleRepository;

  public static MemberService memberService;
  public static ArticleService articleService;

  public static UsrHomeController usrHomeController;
  public static UsrMemberController usrMemberController;
  public static UsrArticleController usrArticleController;

  public static void init() {
    memberRepository = new MemberRepository();
    articleRepository = new ArticleRepository();

    memberService = new MemberService();
    articleService = new ArticleService();

    usrHomeController = new UsrHomeController();
    usrMemberController = new UsrMemberController();
    usrArticleController = new UsrArticleController();
  }
}
