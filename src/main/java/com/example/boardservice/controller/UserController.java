package com.example.boardservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    // 회원가입 화면 보여주기
    // http://localhost:8080/userRegForm
    // userRegForm 리턴한다는 것은 classpath:/template/userRegForm.html을 사용한다는 것이다.
    @GetMapping("/userRegForm")
    public String userRegForm() {
        return "userRegForm";
    }

    @PostMapping("/userReg")
    public String userReg(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        System.out.println("name : " + name);
        System.out.println("email : " + email);
        System.out.println("password : " + password); //@RequestParam 값이 잘 넘어 왔는지 확인

        //회원 정보를 저장한다.

        return "redirect:/welcome"; // http://localhost:8080/welcom으로 이동
    }

    // http://localhost:8080/welcome
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password)
    {
        // email에 해당하는 회원 정보를 읽어온 후
        // 아이디, 암호가 맞다면 세션에 회원정보를 저장한다.
        System.out.println("email :" + email);
        System.out.println("password: " + password);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        // 세션에서 회원정보를 삭제한다.
        return "redirect:/";
    }
}
