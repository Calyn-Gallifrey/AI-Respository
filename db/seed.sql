USE demo_card;
INSERT INTO user(id, name, role, branch, avatar_url) VALUES
  (1, 'Allen Wong', 'Adviser', 'Hong Kong Branch (Life Insurance)', 'https://i.pravatar.cc/150?img=5');

INSERT INTO business_card(id, user_id, card_type, title, created_at, updated_at) VALUES
  (1, 1, 'standard', 'Standard', NOW(), NOW()),
  (2, 1, 'elite', 'Elite', NOW(), NOW());

SET @keys = 'tenure,professional_title,service_introduction,honor_medals,customer_comments,total_customers,total_claims,policies_in_force,total_coverage,premium_services,company_profile,agent_highlights';

INSERT INTO business_card_setting(card_id, setting_key, is_visible, updated_at)
SELECT 1, k.value, IF(idx < 5, 1, 0) as is_visible, NOW()
FROM (
  SELECT ROW_NUMBER() OVER () -1 as idx, SUBSTRING_INDEX(SUBSTRING_INDEX(@keys, ',', numbers.n), ',', -1) as value
  FROM (
    SELECT 1 n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12
  ) numbers
) k;

INSERT INTO business_card_setting(card_id, setting_key, is_visible, updated_at)
SELECT 2, k.value, IF(idx < 8, 1, 0) as is_visible, NOW()
FROM (
  SELECT ROW_NUMBER() OVER () -1 as idx, SUBSTRING_INDEX(SUBSTRING_INDEX(@keys, ',', numbers.n), ',', -1) as value
  FROM (
    SELECT 1 n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12
  ) numbers
) k;
