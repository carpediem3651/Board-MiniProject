package com.example.boardservice.controller;

import com.example.boardservice.dto.LoginInfo;
import com.example.boardservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    // 게시물 목록 보여주기
    // http://localhost:8080/
    // list를 리턴한다는 것은 classpath:/template/list.html을 사용한다는 것이다.
    @GetMapping("/")
    public String list(HttpSession session, Model model) {
        // 게시물 목록 읽어오기
        // 페이징 처리
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        model.addAttribute("loginInfo", loginInfo); // 템플릿에게 loinInfo값을 넘긴다.
        return "list";
    }

//    http:localhost:8080/board?id=(?) id에 값이 들어오면 id=값 형태로 된다.
    @GetMapping("/board")
    public String board(@RequestParam("id") int id) {
        System.out.println("id :" + id);

        //id에 해당하는 게시물을 읽어온다.
        //id에 해당하는 게시물의 조회수가 1증가한다.
        return "board";
    }

    // 삭제한다. 관리자는 모든 글을 삭제할 수 있다.
    // 수정한다.

    @GetMapping("/writeForm")
    public String writeForm(HttpSession session, Model model) {
        // 로그인한 사람만 글을 쓴다.
        // 세션에서 로그인한 정보를 읽어들인다. 로그인 하지 않았다면, 리스트 보기로 자동이동시킨다.
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo"); // 세션에서 로그인 정보를 가져온다
        if(loginInfo == null) { // 세션에 로그인 정보가 없으면 즉, 로그인 정보가 틀릴경우 /loginform으로 redirect한다.
            return "redirect:/loginForm";
        }

        model.addAttribute("loginInfo", loginInfo); // 모델에 로그인 정보를 담아 'wirteForm'으로 보낸다.
        return "writeForm";
    }

    @PostMapping("/write")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session
    ) {
        // 로그인한 사람만 글을 쓴다.
        // 세션에서 로그인한 정보를 읽어들인다. 로그인 하지 않았다면, 리스트 보기로 자동이동시킨다.
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo"); // 세션에서 로그인 정보를 가져온다
        if(loginInfo == null) { // 세션에 로그인 정보가 없으면 즉, 로그인 정보가 틀릴경우 /loginform으로 redirect한다.
            return "redirect:/loginForm";
        }

        System.out.println("title:"+title);
        System.out.println("content:"+content);
        //로그인 한 회원 정보 + 제목, 내용을 저장한다.
        boardService.addBoard(loginInfo.getUserId(), title, content);
        //리스트 보기로 리다이렉트한다.
        return "redirect:/";
    }
}
