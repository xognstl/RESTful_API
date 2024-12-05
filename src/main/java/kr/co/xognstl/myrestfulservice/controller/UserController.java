package kr.co.xognstl.myrestfulservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.xognstl.myrestfulservice.bean.User;
import kr.co.xognstl.myrestfulservice.dao.UserDaoService;
import kr.co.xognstl.myrestfulservice.exception.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name = "user-controller", description = "일반 사용자 서비스를 위한 컨트롤러")  // 클래스에 대한 설명
public class UserController {

    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {  // 모든 유저 조회
        return service.findAll();
    }

    @Operation(summary = "사용자 정보 조회 API", description = "사용자 ID를 이용해서 사용자 상세 정보 조회를 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD request!"),
            @ApiResponse(responseCode = "404", description = "USER NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
        }
    )
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(
            @Parameter(description = "사용자 ID", required = true, example = "1") @PathVariable int id) {    // 특정 유저 조회

        /* HTTP Status Code 제어를 위한 Exception Handling
        * user id 존재하지 않을 때도 200ok 반환
        * throw new UserNotFoundException 라는 예외 생성
        * */
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel entityModel = EntityModel.of(user);

        //링크 작업 추가
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users")); // all-users -> http://localhost:8088/users
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {    // 유저 등록 (POST 방식, json 으로 user 데이터를 전달해야함. 브라우저 말고 postman 으로 테스트
        User saveUser = service.save(user);

        /* HTTP Status Code 제어
         생성이 되고 성공이 되면 201 create 상태를 반환 하고 싶을때 void => ResponseEntity<User> 로 변경 하고
         ServletUriComponentsBuilder 를 사용 한다.
         response 에 header에 location 키 값에 사용자 생성 후 어떻게 새로운 사용자의 상세 보기를 할 수 있는지 주소 값 확인 가능
        * */

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()//현재 request 정보로부터 uri 생성(/users)
                .path("/{id}") // 경로
                .buildAndExpand(saveUser.getId()) // id 값에 들어갈 거
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {  // 유저 삭제
        User deleteUser = service.deleteById(id);

        if (deleteUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return ResponseEntity.noContent().build();
    }
}
