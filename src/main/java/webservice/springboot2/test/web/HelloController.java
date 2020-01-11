package webservice.springboot2.test.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webservice.springboot2.test.web.dto.HelloResponseDto;

@RestController
public class HelloController {
    @GetMapping("/hello")   // localhost:8080/hello로 접속하면 hello가 출력된다
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}
