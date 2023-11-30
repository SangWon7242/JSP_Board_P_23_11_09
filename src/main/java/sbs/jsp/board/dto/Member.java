package sbs.jsp.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@AllArgsConstructor
@ToString
public class Member {
  private int id;
  private String regDate;
  private String updateDate;
  private String loginId;
  private String loginPw;
  private String name;
  private String email;

  public Member(Map<String, Object> selectRow) {
    this.id = (int) selectRow.get("id");
    this.regDate = (String) selectRow.get("regDate");
    this.updateDate = (String) selectRow.get("updateDate");
    this.loginId = (String) selectRow.get("loginId");
    this.loginPw = (String) selectRow.get("loginPw");
    this.name = (String) selectRow.get("name");
    this.email = (String) selectRow.get("email");
  }
}
