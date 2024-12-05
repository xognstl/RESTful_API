package kr.co.xognstl.myrestfulservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 500 => 404 에러로 변경
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}



// trace 값같이 보안에 문제될 수 있는 게 보일 수 도 있다.