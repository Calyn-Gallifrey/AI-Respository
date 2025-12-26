CREATE DATABASE IF NOT EXISTS demo_card CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE demo_card;

DROP TABLE IF EXISTS business_card_setting;
DROP TABLE IF EXISTS business_card;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id BIGINT PRIMARY KEY,
  name VARCHAR(50),
  role VARCHAR(50),
  branch VARCHAR(100),
  avatar_url VARCHAR(255)
);

CREATE TABLE business_card (
  id BIGINT PRIMARY KEY,
  user_id BIGINT,
  card_type VARCHAR(20),
  title VARCHAR(50),
  created_at DATETIME,
  updated_at DATETIME
);

CREATE TABLE business_card_setting (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  card_id BIGINT,
  setting_key VARCHAR(50),
  is_visible BOOLEAN,
  updated_at DATETIME
);
