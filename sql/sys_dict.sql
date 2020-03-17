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



INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`)
VALUES ('103', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'UP_DOWN_TYPE', '4', '上下行', '1', '上下行', 'Integer');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1400', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'FUEL_TYPE_CHILD', '1', '上', '2', '上', 'Integer', '1', '103');
INSERT INTO `sys_dict` (`id`, `create_date`, `create_user`, `is_delete`, `last_upd_date`, `last_upd_user`, `code`, `index`, `remark`, `status`, `text`, `type`, `value`, `parent_id`)
VALUES ('1401', '2020-03-16 08:09:45.954000', '434282783825858560', '0', '2020-03-16 08:09:45.954000', '434282783825858560', 'FUEL_TYPE_CHILD', '2', '下', '2', '下', 'Integer', '2', '103');
