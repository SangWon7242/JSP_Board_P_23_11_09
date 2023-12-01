package sbs.jsp.board.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sbs.jsp.board.Rq;
import sbs.jsp.board.container.Container;
import sbs.jsp.board.controller.Controller;
import sbs.jsp.board.util.MysqlUtil;

import java.io.IOException;

@WebServlet("/usr/*")
public class DispatcherServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    Rq rq = new Rq(req, resp);

    if(rq.isInvalid()) {
      rq.println("올바른 요청이 아닙니다.");
      return;
    }

    if(runInterceptor(rq) == true) {
      return;
    }

    Controller controller = null;

    switch (rq.getControllerTypeName()) {
      case "usr"
          -> {
        switch (rq.getControllerName()) {
          case "home" -> controller = Container.usrHomeController;
          case "article" -> controller = Container.usrArticleController;
          case "member" -> controller = Container.usrMemberController;
        }
      }
    }

    if(controller != null) {
      controller.performAction(rq);
      MysqlUtil.closeConnection();
    }
  }

  private boolean runInterceptor(Rq rq) {
    if(Container.beforeActionInterceptor.runBeforeAction(rq)) {
      return false;
    }

    if(Container.needLoginInterceptor.runBeforeAction(rq)) {
      return false;
    }

    if(Container.needLogoutInterceptor.runBeforeAction(rq)) {
      return false;
    }

    return true;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
