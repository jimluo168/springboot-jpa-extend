use bms_db;
SELECT * FROM menus;
select * from role_menus;

-- 用户管理-按钮
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('100', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '434272516287107072', '1', 'user_list','用户管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('101', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '434272516287107072', '2', 'user_create','用户管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('102', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '434272516287107072', '3', 'user_edit','用户管理-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('103', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '禁用/启用', '2', '434272516287107072', '4', 'user_status','用户管理-禁用/启用');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('104', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '重制密码', '2', '434272516287107072', '5', 'user_resetpasswd','用户管理-重制密码');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('105', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '434272516287107072', '6', 'user_details','用户管理-详情');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('106', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '434272516287107072', '7', 'user_delete','用户管理-删除');

-- 角色管理-按钮
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('110', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '434272518099046400', '1', 'role_list','角色管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('111', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '434272518099046400', '2', 'role_create','角色管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('112', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '434272518099046400', '3', 'role_edit','角色管理-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('113', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '434272518099046400', '4', 'role_delete','角色管理-删除');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('114', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '434272518099046400', '5', 'role_details','角色管理-详情');

-- 日志管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `path`,`remark`)
VALUES ('1000', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '日志管理', '1', '434272512889720832', '3', 'oplog','日志管理');

INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('120', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '1000', '1', 'oplog_list','日志管理-查询');

-- 公交企业管理

INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('130', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '434272522435956736', '1', 'organization_list','公交企业管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('131', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '434272522435956736', '2', 'organization_create','公交企业管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('132', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '434272522435956736', '3', 'organization_edit','公交企业管理-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('133', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '434272522435956736', '4', 'organization_delete','公交企业管理-删除');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('134', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '434272522435956736', '5', 'organization_details','公交企业管理-详情');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('135', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '审核/拒绝', '2', '434272522435956736', '6', 'organization_audit','公交企业管理-审核/拒绝');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('136', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '导入', '2', '434272522435956736', '7', 'organization_import','公交企业管理-导入');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('137', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '导出', '2', '434272522435956736', '8', 'organization_export','公交企业管理-导出');


-- 从业人员管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('140', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '434272524021403648', '1', 'employee_list','从业人员管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('141', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '434272524021403648', '2', 'employee_create','从业人员管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('142', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '434272524021403648', '3', 'employee_edit','从业人员管理-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('143', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '434272524021403648', '4', 'employee_delete','从业人员管理-删除');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('144', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '434272524021403648', '5', 'employee_details','从业人员管理-详情');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('145', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '审核/拒绝', '2', '434272524021403648', '6', 'employee_audit','从业人员管理-审核/拒绝');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('146', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '导入', '2', '434272524021403648', '7', 'practitioner_import','从业人员管理-导入');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('147', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '导出', '2', '434272524021403648', '8', 'practitioner_export','从业人员管理-导出');



-- 公交车辆管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('150', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '434272525602656256', '1', 'bus_vehicle_list','公交车辆管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('151', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '434272525602656256', '2', 'bus_vehicle_create','公交车辆管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('152', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '434272525602656256', '3', 'bus_vehicle_edit','公交车辆管理-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('153', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '434272525602656256', '4', 'bus_vehicle_delete','公交车辆管理-删除');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('154', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '434272525602656256', '5', 'bus_vehicle_details','公交车辆管理-详情');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('155', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '审核/拒绝', '2', '434272525602656256', '6', 'bus_vehicle_audit','公交车辆管理-审核/拒绝');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('156', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '导入', '2', '434272530384162816', '7', 'bus_vehicle_import','公交车辆管理-导入');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('157', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '导出', '2', '434272530384162816', '8', 'bus_vehicle_export','公交车辆管理-导出');



-- 公交路线管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('160', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '434272527175520256', '1', 'bus_rote_list','公交路线管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('161', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '434272527175520256', '2', 'bus_rote_create','公交路线管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('162', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '434272527175520256', '3', 'bus_rote_edit','公交路线管理-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('163', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '434272527175520256', '4', 'bus_rote_delete','公交路线管理-删除');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('164', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '434272527175520256', '5', 'bus_rote_details','公交路线管理-详情');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('165', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '审核/拒绝', '2', '434272527175520256', '6', 'bus_rote_audit','公交路线管理-审核/拒绝');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('166', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '导入', '2', '434272530384162816', '7', 'bus_route_import','公交路线管理-导入');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('167', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '导出', '2', '434272530384162816', '8', 'bus_route_export','公交路线管理-导出');


-- 公交场站管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('170', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '434272528769355776', '1', 'bus_terminal_list','公交场站管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('171', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '434272528769355776', '2', 'bus_terminal_create','公交场站管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('172', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '434272528769355776', '3', 'bus_terminal_edit','公交场站管理-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('173', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '434272528769355776', '4', 'bus_terminal_delete','公交场站管理-删除');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('174', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '434272528769355776', '5', 'bus_terminal_details','公交场站管理-详情');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('175', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '审核/拒绝', '2', '434272528769355776', '6', 'bus_terminal_audit','公交场站管理-审核/拒绝');

-- 公交站点管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('180', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '434272530384162816', '1', 'bus_site_list','公交站点管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('181', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '434272530384162816', '2', 'bus_site_create','公交站点管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('182', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '434272530384162816', '3', 'bus_site_edit','公交站点管理-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('183', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '434272530384162816', '4', 'bus_site_delete','公交站点管理-删除');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('184', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '434272530384162816', '5', 'bus_site_details','公交站点管理-详情');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('185', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '审核/拒绝', '2', '434272530384162816', '6', 'bus_site_audit','公交站点管理-审核/拒绝');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('186', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '导入', '2', '434272530384162816', '7', 'bus_site_import','公交站点管理-导入');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('187', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '导出', '2', '434272530384162816', '8', 'bus_site_export','公交站点管理-导出');



-- 行政信息管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `path`, `type`, `index`)
VALUES ('1100', '2020-03-13 08:43:57.504000', '0', '2020-03-13 08:44:00.072000', '行政信息管理', 'adminfo', '1', '4');

INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `path`,`remark`)
VALUES ('1110', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '行政信息发布', '1', '1100', '1', 'notice','行政信息发布');



INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `path`,`remark`)
VALUES ('1120', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '投诉建议', '1', '1100', '2', 'suggest','投诉建议');

INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1121', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '1120', '1', 'suggest_list','投诉建议-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1122', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '1120', '2', 'suggest_create','投诉建议-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1123', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '1120', '3', 'suggest_edit','投诉建议-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1124', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '1120', '4', 'suggest_delete','投诉建议-删除');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1125', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '1120', '4', 'suggest_details','投诉建议-详情');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1126', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '审核', '2', '1120', '4', 'suggest_audit','投诉建议-审核');


-- 服务稽查管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `path`, `type`, `index`)
VALUES ('1200', '2020-03-13 08:43:57.504000', '0', '2020-03-13 08:44:00.072000', '服务稽查管理', 'service_audit', '1', '4');
-- 违规信息管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `path`,`remark`)
VALUES ('1210', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '违规信息管理', '1', '1200', '1', 'violation','违规信息管理');

INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1211', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '1210', '1', 'violation_list','违规信息管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1212', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '1210', '2', 'violation_create','违规信息管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1214', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '1210', '4', 'violation_delete','违规信息管理-删除');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1215', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '1210', '4', 'violation_details','违规信息管理-详情');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1216', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '处理', '2', '1210', '5', 'violation_deal','违规信息管理-处理');

-- 事件统计分析
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `path`,`remark`)
VALUES ('1220', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '事件统计分析', '1', '1200', '2', 'bus_event_stats','事件统计分析');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1221', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查看', '2', '1220', '1', 'bus_event_stats_list','违规信息管理-查看');


-- 燃油油耗管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `path`, `type`, `index`)
VALUES ('1300', '2020-03-13 08:43:57.504000', '0', '2020-03-13 08:44:00.072000', '燃油消耗管理', 'fuel_management', '1', '5');
-- 网上数据申报
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `path`,`remark`)
VALUES ('1310', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '网上数据申报', '1', '1300', '1', 'online_data_declare','网上数据申报');

INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1311', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '1310', '1', 'online_data_declare_list','网上数据申报-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1312', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '1310', '2', 'online_data_declare_create','网上数据申报-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1313', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '1310', '3', 'online_data_declare_edit','网上数据申报-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1314', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '1310', '4', 'online_data_declare_delete','网上数据申报-删除');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1315', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '详情', '2', '1310', '4', 'online_data_declare_details','网上数据申报-详情');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1316', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '审核', '2', '1310', '4', 'online_data_declare_audit','网上数据申报-审核');

-- 查询统计分析
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `path`,`remark`)
VALUES ('1410', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询统计分析', '1', '1300', '2', 'query_statis','查询统计分析');

INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1411', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '1410', '1', 'query_statis_list','查询统计分析-查询');

-- 统计数据表
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `path`,`remark`)
VALUES ('1510', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '统计数据表', '1', '1300', '3', 'statis_data','统计数据表');

INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('1511', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '1510', '1', 'statis_data_list','统计数据表-查询');
