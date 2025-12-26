USE demo_card;
CREATE TABLE IF NOT EXISTS user (
  id BIGINT PRIMARY KEY,
  name VARCHAR(50),
  role VARCHAR(50),
  branch VARCHAR(100),
  avatar_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS business_card (
  id BIGINT PRIMARY KEY,
  user_id BIGINT,
  card_type VARCHAR(20),
  title VARCHAR(50),
  created_at DATETIME,
  updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS business_card_setting (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  card_id BIGINT,
  setting_key VARCHAR(50),
  is_visible BOOLEAN,
  updated_at DATETIME
);
