package com.example.project.repository;

import com.example.project.model.entity.Achievement;
import org.apache.ibatis.annotations.*;
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


    @RequestMapping("achievementMapper")
    @Select("""
        select * from achievements where xp_required <= #{xp}
    """)
    List<Achievement> findAchievementxByXp(Integer xp);


    @Delete("""
        delete from app_user_achievements
        where app_user_id = #{userId}::UUID
    """)
    void deleteUserAchievement(UUID userId);

    @Insert("""
        insert into app_user_achievements(app_user_id, achievement_id)
        values(#{userId}::UUID, #{achievementId}::UUID)
    """)
    void addUserAchievement(UUID userId, UUID achievementId);
}
