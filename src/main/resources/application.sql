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
    level INT DEFAULT 0,
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

INSERT INTO achievements (achievement_id, title, description, badge, xp_required) VALUES
(DEFAULT, 'First Habit Completed', 'Awarded when a user completes their first habit.', 'first_habit_badge.png', 50),
(DEFAULT, '7-Day Streak', 'Awarded for completing a habit for 7 consecutive days.', '7_day_streak_badge.png', 100),
(DEFAULT, '30-Day Streak', 'Awarded for completing a habit for 30 consecutive days.', '30_day_streak_badge.png', 200),
(DEFAULT, 'Habit Master', 'Awarded for completing 10 different habits.', 'habit_master_badge.png', 500),
(DEFAULT, 'Perfect Month', 'Awarded for completing a habit every day in a given month.', 'perfect_month_badge.png', 300),
(DEFAULT, 'XP Novice', 'Awarded for earning your first 100 XP.', 'xp_novice_badge.png', 100),
(DEFAULT, 'XP Champion', 'Awarded for earning 500 XP in total.', 'xp_champion_badge.png', 500),
(DEFAULT, 'XP Overlord', 'Awarded for earning 5000 XP in total.', 'xp_overlord_badge.png', 5000),
(DEFAULT, '7-Day Streak Achievement', 'Awarded when a user completes a habit for 7 consecutive days.', '7_day_streak_achievement.png', 50),
(DEFAULT, 'Level 10 Reached', 'Awarded when a user reaches level 10.', 'level_10_badge.png', 1000);
