package sbs.jsp.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@AllArgsConstructor
@ToString
public class Article {
  private int id;
  private String regDate;
  private int memberId;
  private String updateDate;
  private String title;
  private String content;

  private String extra__writerName;
  private boolean extra__actorCanModify;
  private boolean extra__actorCanDelete;

  public Article(Map<String, Object> selectRow) {
    this.id = (int) selectRow.get("id");
    this.regDate = (String) selectRow.get("regDate");
    this.memberId = (int) selectRow.get("memberId");
    this.updateDate = (String) selectRow.get("updateDate");
    this.title = (String) selectRow.get("title");
    this.content = (String) selectRow.get("content");

    if(selectRow.get("extra__writerName") != null) {
      this.extra__writerName = (String) selectRow.get("extra__writerName");
    }
  }
}
