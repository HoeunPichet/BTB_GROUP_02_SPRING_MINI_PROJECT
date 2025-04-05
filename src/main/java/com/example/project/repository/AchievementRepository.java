package com.example.project.repository;

import com.example.project.model.entity.Achievement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AchievementRepository {

    @Results(id = "achievementMapper", value = {
            @Result(property = "achievementId", column = "achievement_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "badge", column = "badge"),
            @Result(property = "xpRequired", column = "xp_required")
    })
    @Select("""
        select * from achievements;
    """)
    List<Achievement> findAllAchievement(Integer page, Integer size);
}
