package com.example.project.repository;

import com.example.project.model.dto.request.ProfileRequest;
import com.example.project.model.dto.request.RegisterRequest;
import com.example.project.model.entity.AppUser;
import com.example.project.model.entity.AppUserRegister;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.UUID;

@Mapper
public interface AppUserRepository {

    @Select("""
            SELECT * from app_users
            WHERE email = #{identifier} OR username = #{identifier}
            LIMIT 1
            """)
    @Results(id = "userMapper", value = {
            @Result(property = "appUserId", column = "app_user_id", javaType = UUID.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "profileImageUrl", column = "profile_image"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    AppUser getUserByIdentifier(String identifier);

    @Select("""
            SELECT * from app_users
            WHERE email = #{email}
            """)
    @ResultMap("userMapper")
    AppUser getUserByEmail(String email);

    @Select("""
            SELECT * from app_users
            WHERE app_user_id = #{appUserId}::UUID
            """)
    @ResultMap("userMapper")
    AppUser getUserById(UUID appUserId);


    @Select("""
            INSERT INTO app_users(username, email, password, profile_image)
            VALUES(#{user.username}, #{user.email}, #{user.password}, #{user.profileImageUrl})
            RETURNING *
            """)
    @ResultMap("userMapper")
    AppUser registerUser(@Param("user") RegisterRequest registerRequest);

    @Update("""
            UPDATE app_users
            SET is_verified = true
            WHERE email = #{email}
            """)
    void verifyEmailWithOpt(String email);


    @Select("""
            UPDATE app_users
            SET level = ((xp+10)/100),
            xp = (xp+10)
            WHERE app_user_id = #{appUserId}::UUID
            RETURNING xp
            """)
    AppUser updateUserXpById(UUID appUserId);


    @Select("""
            UPDATE app_users
            SET username = #{user.username},
            profile_image = #{user.profileImageUrl}
            WHERE app_user_id = #{appUserId}::UUID
            RETURNING *
            """)
    @ResultMap("userMapper")
    AppUser updateUserProfile(UUID appUserId, @Param("user") ProfileRequest profileRequest);


    @Delete("""
            DELETE FROM app_users
            WHERE app_user_id = #{appUserId}::UUID
            """)
    void deleteUser(UUID appUserId);
}
