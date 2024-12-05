package kr.co.xognstl.myrestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import kr.co.xognstl.myrestfulservice.bean.AdminUser;
import kr.co.xognstl.myrestfulservice.bean.AdminUserV2;
import kr.co.xognstl.myrestfulservice.bean.User;
import kr.co.xognstl.myrestfulservice.dao.UserDaoService;
import kr.co.xognstl.myrestfulservice.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

//    @GetMapping("/v1/users/{id}") // URI
//    @GetMapping(value = "/users/{id}", params = "version=1")  // parameter
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")   // header
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUser4Admin(@PathVariable int id) {    // 특정 유저 조회
        User user = service.findOne(id);
        AdminUser adminUser = new AdminUser(); // 기본 생성자로 객체생성, JSON을 객체로 변환할 때 기본 생성자를 호출하여 객체를 생성하고, 그 후에 필드를 채웁니다.

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

//    @GetMapping("/v2/users/{id}")
//    @GetMapping(value = "/users/{id}", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUser4AdminV2(@PathVariable int id) {    // 특정 유저 조회
        User user = service.findOne(id);
        AdminUserV2 adminUser = new AdminUserV2(); // 기본 생성자로 객체생성, JSON을 객체로 변환할 때 기본 생성자를 호출하여 객체를 생성하고, 그 후에 필드를 채웁니다.

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("VIP");
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers4Admin() {    // 전체 유저 조회
        List<User> users = service.findAll();

        List<AdminUser> adminUsers = new ArrayList<>();

        AdminUser adminUser = null; // 객체 매번생성 ㄴㄴ
        for (User user : users) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }

}
