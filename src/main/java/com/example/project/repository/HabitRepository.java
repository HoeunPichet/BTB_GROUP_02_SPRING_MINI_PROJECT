package com.example.project.repository;

import com.example.project.model.dto.request.HabitRequest;
import com.example.project.model.entity.AppUser;
import com.example.project.model.entity.AppUserRegister;
import com.example.project.model.entity.Habit;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitRepository {
    @Select("""
    select * from habits  where app_user_id=CAST(#{userId} AS uuid) offset (#{page}-1)* #{size} limit #{size}
""")
    @Results(id="habitMapper",value={
            @Result(property = "habitId",column = "habit_id",javaType = UUID.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "habitTitle",column = "title"),
            @Result(property = "appUser",column = "app_user_id",one=@One(select = "getUserById"))
    })
    List<Habit> getAllHabit(Integer page, Integer size,UUID userId);

    @Select("""
            SELECT app_user_id,username,email,level,xp,profile_image,is_verified,created_at from app_users
            WHERE app_user_id = #{appUserId}::UUID
            """)
    @Results(id = "userMapper", value = {
            @Result(property = "appUserId", column = "app_user_id", javaType = UUID.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "profileImageUrl", column = "profile_image"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    AppUserRegister getUserById(UUID appUserId);


    @Select("""
select * from habits where habit_id=CAST(#{Id} AS uuid);
""")
    @ResultMap("habitMapper")
    Habit getHabitById(UUID id);


    @Select("""
    insert into habits (title,description,frequency,app_user_id)
    values (#{habit.habitTitle},#{habit.description},#{habit.frequency},CAST(#{userId} AS uuid)) returning *
""")
    @ResultMap("habitMapper")
    Habit createHabit(@Param("habit") HabitRequest habit, UUID userId);


    @Select("""
    delete from habits where habit_id=CAST(#{Id} AS uuid) returning *
""")
    @ResultMap("habitMapper")
    Habit deleteHabit(UUID id);


    @Select("""
    update habits set title=#{habit.habitTitle},description=#{habit.description},frequency=#{habit.frequency} where habit_id=CAST(#{id} AS uuid) returning *
""")
    @ResultMap("habitMapper")
    Habit updateHabitById( UUID id,@Param("habit") HabitRequest habit);
}
