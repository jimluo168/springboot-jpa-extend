use bms_db;
SELECT * FROM menus;
select * from role_menus;

-- 用户管理-按钮
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('100', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '434272516287107072', '1', 'user_list','用户管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('101', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '434272516287107072', '1', 'user_create','用户管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('102', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '434272516287107072', '1', 'user_edit','用户管理-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('103', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '禁用/启用', '2', '434272516287107072', '1', 'user_status','用户管理-禁用/启用');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('104', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '重制密码', '2', '434272516287107072', '1', 'user_resetpasswd','用户管理-重制密码');

-- 角色管理-按钮
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('110', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '查询', '2', '434272518099046400', '1', 'role_list','角色管理-查询');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('111', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '新增', '2', '434272518099046400', '2', 'role_create','角色管理-新增');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('112', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '编辑', '2', '434272518099046400', '3', 'role_edit','角色管理-编辑');
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
VALUES ('113', '2020-03-13 08:43:59.768000', '0', '2020-03-13 08:43:59.768000', '删除', '2', '434272518099046400', '4', 'role_delete','角色管理-删除');

-- 日志管理
INSERT INTO `menus` (`id`, `create_date`, `is_delete`, `last_upd_date`, `name`, `type`, `parent_id`, `index`, `permission_code`,`remark`)
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



-- role_menus 关系表

insert into role_menus(role_id,menu_id) values('434272239429488640','100');
insert into role_menus(role_id,menu_id) values('434272239429488640','101');
insert into role_menus(role_id,menu_id) values('434272239429488640','102');
insert into role_menus(role_id,menu_id) values('434272239429488640','103');
insert into role_menus(role_id,menu_id) values('434272239429488640','104');
insert into role_menus(role_id,menu_id) values('434272239429488640','110');
insert into role_menus(role_id,menu_id) values('434272239429488640','111');
insert into role_menus(role_id,menu_id) values('434272239429488640','112');
insert into role_menus(role_id,menu_id) values('434272239429488640','113');
insert into role_menus(role_id,menu_id) values('434272239429488640','120');
insert into role_menus(role_id,menu_id) values('434272239429488640','1000');
insert into role_menus(role_id,menu_id) values('434272239429488640','130');
insert into role_menus(role_id,menu_id) values('434272239429488640','131');
insert into role_menus(role_id,menu_id) values('434272239429488640','132');
insert into role_menus(role_id,menu_id) values('434272239429488640','133');
insert into role_menus(role_id,menu_id) values('434272239429488640','134');
insert into role_menus(role_id,menu_id) values('434272239429488640','135');
insert into role_menus(role_id,menu_id) values('434272239429488640','136');
insert into role_menus(role_id,menu_id) values('434272239429488640','137');


insert into role_menus(role_id,menu_id) values('434272239429488640','140');
insert into role_menus(role_id,menu_id) values('434272239429488640','141');
insert into role_menus(role_id,menu_id) values('434272239429488640','142');
insert into role_menus(role_id,menu_id) values('434272239429488640','143');
insert into role_menus(role_id,menu_id) values('434272239429488640','144');
insert into role_menus(role_id,menu_id) values('434272239429488640','145');
insert into role_menus(role_id,menu_id) values('434272239429488640','150');
insert into role_menus(role_id,menu_id) values('434272239429488640','151');
insert into role_menus(role_id,menu_id) values('434272239429488640','152');
insert into role_menus(role_id,menu_id) values('434272239429488640','153');
insert into role_menus(role_id,menu_id) values('434272239429488640','154');
insert into role_menus(role_id,menu_id) values('434272239429488640','155');
insert into role_menus(role_id,menu_id) values('434272239429488640','160');
insert into role_menus(role_id,menu_id) values('434272239429488640','161');
insert into role_menus(role_id,menu_id) values('434272239429488640','162');
insert into role_menus(role_id,menu_id) values('434272239429488640','163');
insert into role_menus(role_id,menu_id) values('434272239429488640','164');
insert into role_menus(role_id,menu_id) values('434272239429488640','165');
insert into role_menus(role_id,menu_id) values('434272239429488640','170');
insert into role_menus(role_id,menu_id) values('434272239429488640','171');
insert into role_menus(role_id,menu_id) values('434272239429488640','172');
insert into role_menus(role_id,menu_id) values('434272239429488640','173');
insert into role_menus(role_id,menu_id) values('434272239429488640','174');
insert into role_menus(role_id,menu_id) values('434272239429488640','175');
insert into role_menus(role_id,menu_id) values('434272239429488640','180');
insert into role_menus(role_id,menu_id) values('434272239429488640','181');
insert into role_menus(role_id,menu_id) values('434272239429488640','182');
insert into role_menus(role_id,menu_id) values('434272239429488640','183');
insert into role_menus(role_id,menu_id) values('434272239429488640','184');
insert into role_menus(role_id,menu_id) values('434272239429488640','185');

