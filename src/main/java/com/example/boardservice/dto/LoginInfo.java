package com.example.boardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor // 생성자 초기화
public class LoginInfo {
    private int userId;
    private String email;
    private String name;
}
