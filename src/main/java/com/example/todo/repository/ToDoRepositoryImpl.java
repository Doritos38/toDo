package com.example.todo.repository;

import com.example.todo.dto.AllPageRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.dto.ViewAllRequestDto;
import com.example.todo.entity.ToDo;
import com.example.todo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ToDoRepositoryImpl implements ToDoRepository {

    private final JdbcTemplate jdbcTemplate;

    public ToDoRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override       // 할 일 저장
    public Long saveToDo(ToDo toDo) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");

        Map<String, Object> paprameters = new HashMap<>();
        paprameters.put("name", toDo.getName());
        paprameters.put("contents", toDo.getContents());
        paprameters.put("password", toDo.getPassword());
        paprameters.put("date", toDo.getDate());
        paprameters.put("updateDate", toDo.getUpdateDate());
        paprameters.put("userId", toDo.getUserId());
        paprameters.put("deleted", toDo.isDeleted());

        Number id = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(paprameters));

        return id.longValue();
    }

    @Override       // 전체 보기
    public List<ToDoResponseDto> viewAll(ViewAllRequestDto dto) { // 공백 수정

        String sql = "SELECT t.*, u.email FROM todo t " +
                "LEFT JOIN user u ON t.userId = u.id " +
                "WHERE t.deleted = false AND (DATE(t.updateDate) = ? OR ? IS NULL) " +
                "AND (t.name = ? OR ? IS NULL) AND (t.userId = ? OR ? IS NULL) " +
                "ORDER BY updateDate DESC";

        return jdbcTemplate.query(sql, toDoResponseDtoRowMapper(),
                dto.getDate(), dto.getDate(), dto.getName(), dto.getName(), dto.getUserId(), dto.getUserId());
    }

    @Override       // 하나만 보기
    public Optional<ToDo> viewId(Long id) {

        List<ToDo> result = jdbcTemplate.query("select * from todo where id = ?", toDoRowMapper(), id);

        return result.stream().findAny();
    }

    @Override       // 할 일 수정
    public int update(String contents, String name, LocalDateTime now, Long id) {

        return jdbcTemplate.update("UPDATE todo SET contents = ?, name = ?, updateDate = ? WHERE id = ?"
                , contents, name, now, id);
    }

    @Override       // 비밀번호 확인
    public int checkPassword(Long id, String password) {

        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM todo WHERE id = ? AND password = ?"
                , Integer.class, id, password);
    }

    @Override       // 할 일 삭제
    public int delete(Long id) {

        return jdbcTemplate.update("UPDATE todo SET deleted = true WHERE id = ?", id);
    }

    @Override       // 유저 등록
    public Long regi(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> paprameters = new HashMap<>();
        paprameters.put("name", user.getName());
        paprameters.put("email", user.getEmail());
        paprameters.put("regiDate", user.getRegiDate());
        paprameters.put("updateDate", user.getUpdateDate());
        paprameters.put("deleted", user.isDeleted());

        Number id = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(paprameters));

        return id.longValue();
    }

    @Override       // 페이징 전체 보기
    public List<ToDoResponseDto> paging(AllPageRequestDto dto, int offSet) {

        String sql = "SELECT t.*, u.email FROM todo t " +
                "LEFT JOIN user u ON t.userId = u.id " +
                "WHERE t.deleted = false AND (DATE(t.updateDate) = ? OR ? IS NULL) " +
                "AND (t.name = ? OR ? IS NULL) AND (t.userId = ? OR ? IS NULL) " +
                "ORDER BY updateDate DESC LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, pageResponseDtoRowMapper(),
                dto.getDate(), dto.getDate(), dto.getName(), dto.getName(), dto.getUserId(), dto.getUserId(),
                dto.getSize(), offSet);
    }

    private RowMapper<ToDoResponseDto> pageResponseDtoRowMapper() {     // 페이징 SQL문 결과 DTO에 담기

        return new RowMapper<ToDoResponseDto>() {
            @Override
            public ToDoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ToDoResponseDto(
                        rs.getLong("t.id"),
                        rs.getLong("t.userId"),
                        rs.getString("t.name"),
                        rs.getString("u.email"),
                        rs.getString("t.contents"),
                        rs.getDate("t.updateDate").toLocalDate()
                );
            }
        };
    }

    @Override
    public int checkSize() {        //  전체 할 일 수 체크

        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TODO WHERE deleted = false", Integer.class);
    }

    @Override
    public boolean checkDelete(Long id) {       // 삭제 여부 체크

        return jdbcTemplate.queryForObject("SELECT deleted FROM TODO WHERE id = ?", Boolean.class, id);
    }


    private RowMapper<ToDoResponseDto> toDoResponseDtoRowMapper() {     // 할 일 목록 전체 출력 SQL문 결과 DTO에 담기
        return new RowMapper<ToDoResponseDto>() {
            @Override
            public ToDoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ToDoResponseDto(
                        rs.getLong("t.id"),
                        rs.getLong("t.userId"),
                        rs.getString("t.name"),
                        rs.getString("u.email"),
                        rs.getString("t.contents"),
                        rs.getDate("t.updateDate").toLocalDate()
                );
            }
        };
    }

    private RowMapper<ToDo> toDoRowMapper() {       //  할 일 출력 SQL문 결과 DTO에 담기
        return new RowMapper<ToDo>() {
            @Override
            public ToDo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ToDo(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("contents"),
                        rs.getString("password"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime(),
                        rs.getLong("userId")
                );
            }
        };
    }
}