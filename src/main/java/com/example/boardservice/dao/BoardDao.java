package com.example.boardservice.dao;

import com.example.boardservice.dto.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class BoardDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations insertBoard;

//   생성자 주입, 스프링이 자동으로 DataSource의 구현체 HicariCP Bean을 주입한다.
    public BoardDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertBoard = new SimpleJdbcInsert(dataSource)
                .withTableName("board")
                .usingGeneratedKeyColumns("board_id"); //자동으로 증가되는 id설정
    }

    @Transactional
    public void addBoard(int userId, String title, String content) {
//      Board DTO에 값을 넣는다
        Board board = new Board();
        board.setUserId(userId);
        board.setTitle(title);
        board.setContent(content); 
        board.setRegdate(LocalDateTime.now()); //LocalDateTime.now() 현재시간
//      DTO의 값을 파라미터 값으로 넣는다.
        SqlParameterSource params = new BeanPropertySqlParameterSource(board);
        insertBoard.execute(params);
    }
}
