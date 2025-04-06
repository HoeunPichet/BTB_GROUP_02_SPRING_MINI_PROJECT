package com.example.project.repository;

import com.example.project.model.entity.Achievement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Mapper
public interface AchievementRepository {

    @Results(id = "achievementMapper", value = {
            @Result(property = "achievementId", column = "achievement_id", javaType = UUID.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "badge", column = "badge"),
            @Result(property = "xpRequired", column = "xp_required")
    })
    @Select("""
        select * from achievements offset (#{page}-1)* #{size} limit #{size};
    """)
    List<Achievement> findAllAchievement(Integer page, Integer size);

    @RequestMapping("achievementMapper")
    @Select("""
        SELECT * FROM achievements a INNER JOIN app_user_achievements uc ON a.achievement_id = uc.achievement_id WHERE uc.app_user_id = CAST(#{userId} AS uuid) LIMIT #{size} OFFSET (#{page} - 1) * #{size}
    """)
    List<Achievement> findAchievementByAppUser(UUID userId, Integer page, Integer size);
}
