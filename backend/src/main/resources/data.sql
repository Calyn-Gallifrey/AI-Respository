INSERT INTO user(id, name, role, branch, avatar_url) VALUES
  (1, 'Allen Wong', 'Adviser', 'Hong Kong Branch (Life Insurance)', 'https://i.pravatar.cc/150?img=5')
ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO business_card(id, user_id, card_type, title, created_at, updated_at) VALUES
  (1, 1, 'standard', 'Standard', NOW(), NOW()),
  (2, 1, 'elite', 'Elite', NOW(), NOW())
ON DUPLICATE KEY UPDATE title=VALUES(title), updated_at=NOW();

INSERT INTO business_card_setting(card_id, setting_key, is_visible, updated_at)
SELECT 1, key_name, CASE WHEN idx < 5 THEN 1 ELSE 0 END, NOW() FROM (
  SELECT 0 idx, 'tenure' key_name UNION ALL
  SELECT 1, 'professional_title' UNION ALL
  SELECT 2, 'service_introduction' UNION ALL
  SELECT 3, 'honor_medals' UNION ALL
  SELECT 4, 'customer_comments' UNION ALL
  SELECT 5, 'total_customers' UNION ALL
  SELECT 6, 'total_claims' UNION ALL
  SELECT 7, 'policies_in_force' UNION ALL
  SELECT 8, 'total_coverage' UNION ALL
  SELECT 9, 'premium_services' UNION ALL
  SELECT 10, 'company_profile' UNION ALL
  SELECT 11, 'agent_highlights'
) t WHERE NOT EXISTS (SELECT 1 FROM business_card_setting b WHERE b.card_id=1 AND b.setting_key=t.key_name);

INSERT INTO business_card_setting(card_id, setting_key, is_visible, updated_at)
SELECT 2, key_name, CASE WHEN idx < 8 THEN 1 ELSE 0 END, NOW() FROM (
  SELECT 0 idx, 'tenure' key_name UNION ALL
  SELECT 1, 'professional_title' UNION ALL
  SELECT 2, 'service_introduction' UNION ALL
  SELECT 3, 'honor_medals' UNION ALL
  SELECT 4, 'customer_comments' UNION ALL
  SELECT 5, 'total_customers' UNION ALL
  SELECT 6, 'total_claims' UNION ALL
  SELECT 7, 'policies_in_force' UNION ALL
  SELECT 8, 'total_coverage' UNION ALL
  SELECT 9, 'premium_services' UNION ALL
  SELECT 10, 'company_profile' UNION ALL
  SELECT 11, 'agent_highlights'
) t WHERE NOT EXISTS (SELECT 1 FROM business_card_setting b WHERE b.card_id=2 AND b.setting_key=t.key_name);
