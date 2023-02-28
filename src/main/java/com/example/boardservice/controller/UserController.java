package com.example.boardservice.controller;

import com.example.boardservice.dto.LoginInfo;
import com.example.boardservice.dto.User;
import com.example.boardservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
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
        //회원 정보를 저장한다.
        userService.addUser(name,email,password);
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
            @RequestParam("password") String password,
            HttpSession httpSession // Spring이 자동으로 Session을 처리하는 HttpSession 객체 생성
    )

    {

        // email에 해당하는 회원 정보를 읽어온 후
        // 암호가 맞다면 세션에 회원정보를 저장한다.
        try {
            User user = userService.getUser(email);
            if(user.getPassword().equals(password)) {
                System.out.println("암호가 같습니다.");
                LoginInfo loginInfo = new LoginInfo(user.getUserId(), user.getEmail(), user.getName());

                //권한 정보를 읽어와서 loginInfo에 추가한다.
                List<String> roles =userService.getRoles(user.getUserId());
                loginInfo.setRoles(roles);

                httpSession.setAttribute("loginInfo", loginInfo); //로그인 정보를 Session 객체에 넣는다.
                System.out.println("세션에 로그인 정보가 저장되었습니다.");
            } else {
                throw new RuntimeException("암호가 일치하지 않음");
            }
        } catch (Exception ex) {
            return "redirect:/loginForm?error=true";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        // 세션에서 회원정보를 삭제한다.
        httpSession.removeAttribute("loginInfo");
        return "redirect:/";
    }
}
