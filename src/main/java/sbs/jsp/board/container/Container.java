package sbs.jsp.board.container;

import sbs.jsp.board.controller.UsrArticleController;
import sbs.jsp.board.controller.UsrHomeController;
import sbs.jsp.board.controller.UsrMemberController;
import sbs.jsp.board.interceptor.BeforeActionInterceptor;
import sbs.jsp.board.interceptor.NeedLoginInterceptor;
import sbs.jsp.board.interceptor.NeedLogoutInterceptor;
import sbs.jsp.board.repository.ArticleRepository;
import sbs.jsp.board.repository.MemberRepository;
import sbs.jsp.board.service.ArticleService;
import sbs.jsp.board.service.MemberService;

public class Container {
  public static BeforeActionInterceptor beforeActionInterceptor;
  public static NeedLoginInterceptor needLoginInterceptor;
  public static NeedLogoutInterceptor needLogoutInterceptor;

  public static MemberRepository memberRepository;
  public static ArticleRepository articleRepository;

  public static MemberService memberService;
  public static ArticleService articleService;

  public static UsrHomeController usrHomeController;
  public static UsrMemberController usrMemberController;
  public static UsrArticleController usrArticleController;

  public static void init() {
    beforeActionInterceptor = new BeforeActionInterceptor();
    needLoginInterceptor = new NeedLoginInterceptor();
    needLogoutInterceptor = new NeedLogoutInterceptor();

    memberRepository = new MemberRepository();
    articleRepository = new ArticleRepository();

    memberService = new MemberService();
    articleService = new ArticleService();

    usrHomeController = new UsrHomeController();
    usrMemberController = new UsrMemberController();
    usrArticleController = new UsrArticleController();
  }
}
