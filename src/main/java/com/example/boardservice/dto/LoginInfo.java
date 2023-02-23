package com.example.boardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor // 생성자 초기화
@ToString
public class LoginInfo {
    private int userId;
    private String email;
    private String name;
}
