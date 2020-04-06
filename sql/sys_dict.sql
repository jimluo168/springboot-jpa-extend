INSERT INTO sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1100', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'DRIVE_AGE_TYPE_CHILD', '1', '类型一', '1', '类型一', 'Integer', '1', '100');
UPDATE sys_dict` SET `code` = 'EMPLOYMENT_TYPE', `remark` = '从业类型', `text` = '从业类型' WHERE (`id` = '100');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1101', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'DRIVE_AGE_TYPE_CHILD', '2', '类型二', '2', '类型二', 'Integer', '2', '100');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1102', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'DRIVE_AGE_TYPE_CHILD', '3', '类型三', '2', '类型三', 'Integer', '3', '100');


INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`)
VALUES ('101', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'STATION_TYPE', '3', '场站类型', '1', '场站类型', 'Integer');

INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1200', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'STATION_TYPE_CHILD', '3', '类型一', '2', '类型三', 'Integer', '1', '101');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1201', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'STATION_TYPE_CHILD', '3', '类型二', '2', '类型三', 'Integer', '2', '101');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1202', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'STATION_TYPE_CHILD', '3', '类型三', '2', '类型三', 'Integer', '3', '101');




INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`)
VALUES ('102', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'FUEL_TYPE', '3', '燃料类型', '1', '燃料类型', 'Integer');

INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1300', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'FUEL_TYPE_CHILD', '1', '石油', '2', '石油', 'Integer', '1', '102');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1301', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'FUEL_TYPE_CHILD', '2', '气', '2', '气', 'Integer', '2', '102');


-- 上下行
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`)
VALUES ('103', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'UP_DOWN_TYPE', '4', '上下行', '1', '上下行', 'Integer');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1400', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'FUEL_TYPE_CHILD', '1', '上', '2', '上', 'Integer', '1', '103');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1401', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'FUEL_TYPE_CHILD', '2', '下', '2', '下', 'Integer', '2', '103');


-- 投诉建议类型
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`)
VALUES ('104', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'SUGGEST_TYPE', '5', '投诉建议类型', '1', '投诉建议类型', 'Integer');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1500', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'SUGGEST_TYPE_CHILD', '1', '服务', '2', '服务', 'Integer', '1', '104');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1501', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'SUGGEST_TYPE_CHILD', '2', '安全', '2', '安全', 'Integer', '2', '104');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1501', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'SUGGEST_TYPE_CHILD', '2', 'B2C', '2', 'B2C', 'Integer', '3', '104');

-- 文章类型
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`)
VALUES ('105', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'ARTICLE_TYPE', '6', '文章类型', '1', '文章类型', 'Integer');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('10501', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'ARTICLE_TYPE_CHILD', '1', '政策', '2', '政策', 'Integer', '1', '105');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('10502', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'ARTICLE_TYPE_CHILD', '2', '规范标准', '2', '规范标准', 'Integer', '2', '105');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('10503', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'ARTICLE_TYPE_CHILD', '2', '公告', '2', '公告', 'Integer', '3', '105');

-- EMERGENCY_LEVEL 应急事件等级
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`)
VALUES ('160', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'EMERGENCY_LEVEL', '160', '应急事件等级', '1', '应急事件等级', 'Integer');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('16001', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'EMERGENCY_LEVEL_CHILD', '1', '一级', '1', '一级', 'Integer', '1', '160');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('16002', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'EMERGENCY_LEVEL_CHILD', '2', '二级', '1', '一级', 'Integer', '2', '160');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('16003', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'EMERGENCY_LEVEL_CHILD', '3', '三级', '1', '一级', 'Integer', '3', '160');

-- EMERGENCY_PREPLAN_TYPE 预案分类
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`)
VALUES ('161', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'EMERGENCY_PREPLAN_TYPE', '160', '预案分类', '1', '预案分类', 'Integer');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('16101', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'EMERGENCY_PREPLAN_TYPE_CHILD', '1', '分类一', '1', '分类一', 'Integer', '1', '161');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('16102', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'EMERGENCY_PREPLAN_TYPE_CHILD', '2', '分类二', '1', '分类二', 'Integer', '2', '161');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('16103', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'EMERGENCY_PREPLAN_TYPE_CHILD', '3', '分类三', '1', '分类三', 'Integer', '3', '161');
