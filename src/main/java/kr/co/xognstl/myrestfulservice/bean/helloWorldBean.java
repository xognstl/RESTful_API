package kr.co.xognstl.myrestfulservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // 생성자 자동 추가
public class helloWorldBean {

    private String message;

//    public helloWorldBean(String message) {
//        this.message = message;
//    }
}
