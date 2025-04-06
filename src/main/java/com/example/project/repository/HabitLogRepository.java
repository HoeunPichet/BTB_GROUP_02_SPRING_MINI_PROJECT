package com.example.project.repository;

import com.example.project.model.dto.request.HabitLogRequest;
import com.example.project.model.entity.AppUserRegister;
import com.example.project.model.entity.HabitLog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.simpleframework.xml.Path;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitLogRepository {

    @Select("""
            SELECT * FROM 
            habit_logs hl
            JOIN habits h ON hl.habit_id = h.habit_id
            WHERE
                h.habit_id = #{habitId}::UUID 
                offset (#{page}-1)* #{size} limit #{size}
            """)
    @Results(id = "habitLogMapper", value = {
            @Result(property = "habitLogId", column = "habit_log_id", javaType = UUID.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "logDate", column = "log_date"),
            @Result(property = "xpEarned", column = "xp_earned"),
            @Result(property = "habit", column = "habit_id",
                one = @One (select = "com.example.project.repository.HabitRepository.getHabitById")
            ),
    })
    List<HabitLog> getAllHabitLogsByHabitId(Integer page, Integer size, UUID habitId);

    @Select("""
        INSERT INTO habit_logs(status, habit_id, xp_earned)
        VALUES(
            #{habitLog.status},
            #{habitLog.habitId}::UUID,
            CASE
                WHEN #{habitLog.status} = 'COMPLETED' THEN 10
                ELSE 0
            END
        )
        RETURNING *;
    """)
    @ResultMap("habitLogMapper")
    HabitLog createHabitLog(@Param("habitLog") HabitLogRequest habitLogRequest);
}
