package kr.co.xognstl.myrestfulservice.controller;

import kr.co.xognstl.myrestfulservice.bean.helloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /*
    * 주입이란 스프링 컨텍스트에 의해서 기동이 될때 해당하는 인스턴스를 미리 만들어놓고 (빈등록같은?) 메모리에 등록한다.
    * 미리 등록되어진 다른 스프링의 빈을 가지고 와서 현재 있는 클래스에서 사용할 수 있도록 객체를 생성하지 않더라도 참조할 수 있는 형태로 받아오는 것.
    * */

    // GET
    // URI - /hello-world
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world)
    @GetMapping(path = "/hello-world")
    public String helloworld() {
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public helloWorldBean helloworldBean() {
        return new helloWorldBean("Hello World!");  // 오브젝트 타입, 빈타입 부트에서는 자동으로 responsebody 변환 시켜줌. json으로 나옴.
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public helloWorldBean helloWorldBeanPathVariable(@PathVariable String name) {
        return new helloWorldBean(String.format("Hello World!, %s", name));
    }

    //Internationalization
    @GetMapping(path = "/hello-world-internationalized")
    public String helloworldInternalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {

        return messageSource.getMessage("greeting.message", null, locale);
    }
}
