package sbs.jsp.board.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/gugudan")
public class GugudanServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8"); // 들어오는 데이터를 UTF-8로 해석하겠다.
    resp.setCharacterEncoding("UTF-8"); // 완성되는 HTML 인코딩을 UTF-8로 하겠다.
    resp.setContentType("text/html; charset-ut-8"); // 브라우저에게 우리가 만든 결과물이 UTF-8이다 라고 알리는 의미

    int dan = Integer.parseInt(req.getParameter("dan"));
    int limit = Integer.parseInt(req.getParameter("limit"));

    resp.getWriter().append("<h1>%d단</h1>\n".formatted(dan));

    for(int i = 1; i <= limit; i++) {
      resp.getWriter().append("<div>%d * %d = %d</div>\n".formatted(dan, i, dan * i));
    }
  }
}
