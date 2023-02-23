package com.example.boardservice.dao;

import com.example.boardservice.dto.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class UserDao {
//    JDBC
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insertUser;
    public UserDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("user_id"); //자동으로 증가되는 id설정
    }

    @Transactional //propagation.REQUIRED로 인해 Service(addUser) 부모 Transaction에  하나의 트랜잭션으로 묶인다.
    public User addUser(String email, String name, String password) {
//        insert into user(email, name, password, regdate) values (?,?,?,now());
//        SELECT LAST_INSERT_ID(); autoincreament값을 구함
//        Spring JDBC 프로그래밍
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRegdate(LocalDateTime.now());
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        Number number = insertUser.executeAndReturnKey(params);// insert를 실행하고 자동으로 생성된 id(1증가된 id)를 가져온다.
        int userId = number.intValue();
        user.setUserId(userId);
        return user;
    }

//    권한 정보 저장
    @Transactional //propagation.REQUIRED로 인해 Service(addUser) 부모 Transaction에  하나의 트랜잭션으로 묶인다.
    public void mappingUserRole(int userId) {
//      insert into user_role(user_id, role_id) values (?,1);
        String sql = "insert into user_role(user_id, role_id) values (:userId,1)";
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        jdbcTemplate.update(sql, params);
    }

    @Transactional
    public User getUser(String email) {
        String sql="select user_id, email, name, password, regdate from user where email = :email";
        SqlParameterSource params = new MapSqlParameterSource("email", email);
        RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
        User user = jdbcTemplate.queryForObject(sql, params, rowMapper);
        return user;
    }
}
