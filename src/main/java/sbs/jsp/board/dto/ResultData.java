package sbs.jsp.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sbs.jsp.board.util.Ut;

import java.util.Map;

@NoArgsConstructor
@ToString
public class ResultData {
  @Getter
  private String resultCode; // 성공 실패를 담을 수 있는 resultCode
  @Getter
  private String msg; // 성공, 실패에 관련 된 msg
  @Getter
  private Map<String, Object> body;

  public boolean isSuccess() {
    return resultCode.startsWith("S-");
  }

  public boolean isFail() {
    return !isSuccess();
  }

  public static ResultData from(String resultCode, String msg, Object... bodyArgs) {
    ResultData rd = new ResultData();

    rd.resultCode = resultCode;
    rd.msg = msg;
    rd.body = Ut.mapOf(bodyArgs);

    return rd;
  }
}
