package com.example.todo.repository;

import com.example.todo.dto.ToDoRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.entity.ToDo;
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
public class ToDoRepositoryImpl implements ToDoRepository{

    private final JdbcTemplate jdbcTemplate;

    public ToDoRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long saveMemo(ToDo toDo) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");

        Map<String, Object> paprameters = new HashMap<>();
        paprameters.put("name", toDo.getName());
        paprameters.put("contents", toDo.getContents());
        paprameters.put("password", toDo.getPassword());
        paprameters.put("date", toDo.getDate());
        paprameters.put("updateDate", toDo.getUpdateDate());

        Number id = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(paprameters));

        return id.longValue();
    }

    @Override
    public List<ToDoResponseDto> viewAll(ToDoRequestDto dto) { // 공백 수정

        String sql = "SELECT * FROM todo WHERE (DATE(updateDate) = ? OR ? IS NULL) AND (name = ? OR ? IS NULL)";

        return jdbcTemplate.query(sql, toDoResponseDtoRowMapper(), dto.getDate(), dto.getDate(), dto.getName(), dto.getName());
    }

    @Override
    public Optional<ToDo> viewId(Long id) {

        List<ToDo> result = jdbcTemplate.query("select * from todo where id = ?", toDoRowMapper(), id);

        return result.stream().findAny();
    }

    @Override
    public int update(String contents, String name, LocalDateTime now, Long id) {

        return jdbcTemplate.update("UPDATE todo SET contents = ?, name = ?, updateDate = ? WHERE id = ?"
                , contents, name, now, id);
    }

    @Override
    public int checkPassword(Long id, String password) {

        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM todo WHERE id = ? AND password = ?"
                , Integer.class, id, password);
    }

    @Override
    public int delete(Long id) {

        return jdbcTemplate.update("DELETE FROM todo WHERE id = ?", id);
    }

    private RowMapper<ToDoResponseDto> toDoResponseDtoRowMapper() {
        return new RowMapper<ToDoResponseDto>() {
            @Override
            public ToDoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ToDoResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("contents"),
                        rs.getDate("updateDate").toLocalDate()
                );
            }
        };
    }

    private RowMapper<ToDo> toDoRowMapper() {
        return new RowMapper<ToDo>() {
            @Override
            public ToDo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ToDo(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("contents"),
                        rs.getString("password"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime()
                );
            }
        };
    }
}