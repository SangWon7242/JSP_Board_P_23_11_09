package sbs.jsp.board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import sbs.jsp.board.dto.Member;
import sbs.jsp.board.util.Ut;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Rq {
  @Getter
  private final HttpServletRequest req;
  @Getter
  private final HttpServletResponse resp;
  @Getter
  private boolean isInvalid = false;

  @Getter
  private boolean isLogined = false;

  @Getter
  private int loginedMemberId = 0;

  @Getter
  private Member logineMember = null;

  @Getter
  private String controllerTypeName;
  @Getter
  private String controllerName;
  @Getter
  private String actionMethodName;

  public Rq(HttpServletRequest req, HttpServletResponse resp) {
    this.req = req;
    this.resp = resp;

    try {
      req.setCharacterEncoding("UTF-8"); // 들어오는 데이터를 UTF-8로 해석하겠다.
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    resp.setCharacterEncoding("UTF-8"); // 완성되는 HTML 인코딩을 UTF-8로 하겠다.
    resp.setContentType("text/html; charset-ut-8"); // 브라우저에게 우리가 만든 결과물이 UTF-8이다 라고 알리는 의미


    String requestUri = req.getRequestURI();
    String[] requestUriBits = requestUri.split("/");
    // /usr/article/list
    // [0]/[1]/[2]/[3]

    int minBitsCount = 4;

    if (requestUriBits.length < minBitsCount) {
      isInvalid = true;
      return;
    }

    int controllerTypeNameIndex = 1;
    int controllerNameIndex = 2;
    int controllerMethodNameIndex = 3;

    controllerTypeName = requestUriBits[controllerTypeNameIndex];
    controllerName = requestUriBits[controllerNameIndex];
    actionMethodName = requestUriBits[controllerMethodNameIndex];
  }

  public int getIntParam(String paramName, int defaultValue) {
    String value = req.getParameter(paramName);

    if(value == null) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(value);
    }
    catch (NullPointerException e) {
      return defaultValue;
    }
  }

  public String getParam(String paramName, String defaultValue) {
    String value = req.getParameter(paramName);

    if (value == null) {
      return defaultValue;
    }

    return value;
  }

  public void print(String str) {
    try {
      resp.getWriter().append(str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void println(String str) {
    print(str + "\n");
  }

  public void printf(String format, Object... args) {
    print(Ut.f(format, args));
  }

  public void historyBack(String msg) {
    println("<script>");
    printf("alert('%s');\n", msg);
    printf("history.back();", msg);
    println("</script>");
  }

  public void replace(String msg, String redirectUri) {
    println("<script>");
    printf("alert('%s');\n", msg);
    printf("location.replace('%s');", redirectUri);
    println("</script>");
  }

  public Object getAttr(String name) {
    return req.getAttribute(name);
  }

  public void setAttr(String name, Object value) {
    req.setAttribute(name, value);
  }

  public void jsp(String jspPath) {
    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/" + jspPath + ".jsp");
    try {
      requestDispatcher.forward(req, resp);
    } catch (ServletException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public HttpSession getSession() {
    HttpSession session = req.getSession();
    return session;
  }

  public void setSessionAttr(String attrName, Object attValue) {
    req.getSession().setAttribute(attrName, attValue);
  }

  public void removeSessionAttr(String attrName) {
    req.getSession().removeAttribute(attrName);
  }
}
