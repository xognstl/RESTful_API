package kr.co.xognstl.myrestfulservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data   // getter, setter
@AllArgsConstructor // 생성자
@NoArgsConstructor  // 아무것도 가지고 있지 않은 default 생성자 만들어줌.
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}
