package com.example.project.repository;

import com.example.project.model.entity.AppUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AppUserRepository {

    @Select("""
            SELECT * from app_users
            WHERE email = #{identifier} OR username = #{identifier}
            """)
    @Results(id = "userMapper", value = {
            @Result(property = "appUserId", column = "app_user_id"),
            @Result(property = "profileImageUrl", column = "profile_image"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    AppUser getUserByIdentifier(String identifier);
}
