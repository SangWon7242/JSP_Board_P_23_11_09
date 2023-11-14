package sbs.jsp.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Article {
  private int id;
  private String regDate;
  private String updateDate;
  private String title;
  private String content;
}
