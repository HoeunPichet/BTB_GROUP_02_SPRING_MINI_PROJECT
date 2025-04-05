CREATE DATABASE mini_project_spring;

CREATE TABLE achievements (
    achievement_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(100) NOT NULL,
    description TEXT,
    badge VARCHAR(255),
    xp_required INT NOT NULL
);

CREATE TABLE app_users (
    app_user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    level INT DEFAULT 1,
    xp INT DEFAULT 0,
    profile_image VARCHAR(255),
    is_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE app_user_achievements (
    app_user_achievement_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    app_user_id UUID,
    achievement_id UUID,
    FOREIGN KEY (app_user_id) REFERENCES app_users(app_user_id) ON DELETE CASCADE,
    FOREIGN KEY (achievement_id) REFERENCES achievements(achievement_id) ON DELETE CASCADE
);

CREATE TABLE habits (
    habit_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(100) NOT NULL,
    description TEXT,
    frequency TEXT NOT NULL CHECK (frequency IN ('DAILY', 'WEEKLY', 'MONTHLY')),
    is_active BOOLEAN DEFAULT TRUE,
    app_user_id UUID,
    FOREIGN KEY (app_user_id) REFERENCES app_users(app_user_id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE habit_logs (
    habit_log_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    log_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status TEXT NOT NULL CHECK (status IN ('COMPLETED', 'MISSED')),
    xp_earned INT,
    habit_id UUID,
    FOREIGN KEY (habit_id) REFERENCES habits(habit_id) ON DELETE CASCADE
);