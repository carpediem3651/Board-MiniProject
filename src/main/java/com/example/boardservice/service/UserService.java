package com.example.boardservice.service;

import com.example.boardservice.dao.UserDao;
import com.example.boardservice.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor //lombok이 final 필드를 초기화하는 생성자를 자동으로 생성한다. 생성자 생성을 생략시킴
public class UserService {
//  dao 생성자 주입
//  생성자 주입: Spring이 UserService를 Bean으로 생성할 때 생성자를 이용해 생성하는데, 이때 UserDao Bean이 있는지 보고
//  Bean이 있으면 주입한다.
    private final UserDao userDao;
//    public UserService(UserDao userDao) {
//        this.userDao = userDao;
//    }

    //회원정보 저장
    // 1.값을 집어넣고 2.autoincreament를 구하고 3.권한을 부여하는 작업을 한번에 진행한다.
    // 서비스에는 @Transactional을 붙여 하나의 트랜잭션으로 처리한다.
    @Transactional
    public User addUser(String name, String email, String password) {
        User user = userDao.addUser(email, name, password);
        userDao.mappingUserRole(user.getUserId());
        return user;
    }

//    회원정보 가져오기
    @Transactional
    public User getUser(String email) {
        return userDao.getUser(email);
    }
}
