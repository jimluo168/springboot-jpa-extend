# 行业管理系统后端代码(IndustryManagement-Backend)

![avatar](关系图.png)

## 1. 开发环境

1. JDK 1.8+(包含1.8)

2. Maven 3.3+

3. 安装插件Lombok
- IDEA: https://projectlombok.org/setup/intellij
- Eclipse: https://projectlombok.org/setup/eclipse

### 1.1. 代码注视

File->Preferences->Editor->File and Code Templates->File Header

```java
/**
 * TODO(类的简要说明)
 * @author 这里填写开发者
 * @date ${DATE}
 */
```

### 1.2. 开发调试
访问swagger-ui管理界面 http://youhostname:8080/swagger-ui.html

对自己编写的接口进行开发调试测试

## 2. 系统模块命名

|模块名称|英文|Java包命名|
|---|---|---|
|公共模块|common|com.bms.common|
|系统管理模块|sys|com.bms.sys|
|公交行业管理系统|industry|com.bms.industry|
|公交综合运行监测与应急响应系统|monitor|com.bms.monitor|
|公交统计决策分析系统|statis|com.bms.statis|

## 3. 错误码规范

|模块|错误码范围|
|---|------|
|common|1000-9999|
|sys|10000-19999|
|industry|20000-29999|
|monitor|30000-39999|
|statis|40000-49999|

## 4. 权限编码规范

### 4.1. 编码规范
功能名称+下划线(_)+操作名称

|操作名称|命名|
|---|---|
|列表=查询|list|
|新增|create|
|编辑|edit|
|删除|delete|
|详情|details|
|禁用/启用|status|

例如：用户管理

|操作名称|命名|
|---|---|
|用户管理-列表=查询|user_list|
|用户管理-新增|user_create|
|用户管理-编辑|user_dit|
|用户管理-删除|user_delete|
|用户管理-详情|user_details|
|用户管理-禁用/启用|user_status|
|用户管理-重制密码|user_reset_passwd|

### 4.2. 代码规范
```java
    @RequiresPermissions("user_create")
    @PostMapping("")
    public Result<User> create(@RequestBody User user) {
        return ok(userService.insert(user));
    }

    @RequiresPermissions("user_list")
    @GetMapping("/list")
    public Result<PageList<User>> list(PageRequest pageRequest, String keyword) {
        return ok(userService.page(pageRequest, keyword));
    }

    @RequiresPermissions("user_details")
    @GetMapping("/{id}")
    public Result<User> details(@PathVariable Long id) {
        User user = userService.findById(id);
        return ok(user);
    }

    @RequiresPermissions("user_edit")
    @PutMapping("/{id}")
    public Result<User> edit(@PathVariable Long id, @RequestBody User updateBody) {
        User user = userService.updateById(id, updateBody);
        return ok(user);
    }

    @RequiresPermissions("user_delete")
    @DeleteMapping("/{id}")
    public Result<Long> delete(@PathVariable Long id) {
        User user = userService.deleteById(id);
        return ok(user.getId());
    }
```


## 5. API接口

## 6. OSS文件管理

### 6.1. OSS文件-上传

`header的Content-Type必须为multipart/form-data`

```
/images  图片
/docs    文档
/html    html内容
/videos  视频

```

```yaml
@post: /oss/:path

@header:
	Authorization:登录令牌(必须)	
	Content-Type:multipart/form-data

@params:
  path:string:上传文件时所使用的文件目录 参考[常用目录规则]

@payload:
  name:string:文件名
  file:file:上传的文件

@return:
  code:int:操作状态
    - 200:操作成功
    - 500:未知错误
    - 1003:文件内容无效
  data:object:返回存储文件的信息
    mimetype:string:文件类型
    filename:string:上传到OSS的文件路径
    originalname:string:原始文件名
    size:long:文件大小
    sha1:string:文件的SHA
    md5:string:文件的MD5
  success:bool:是否成功
  msg:string:操作提示

```


### 6.2. OSS文件-文本内容

`header的Content-Type必须为application/x-www-form-urlencoded`

```
@post: /oss/:path

@header:
	Authorization:登录令牌(必须)	
	Content-Type:application/x-www-form-urlencoded

@params:
    path:string:上传文件时所使用的文件目录 参考[常用目录规则]
    

@payload:
    suffix:string:文本内容的后缀名 .html .jpg .png
    text:string:文本内容

@return:
    code:int:操作状态
        - 200:操作成功
        - 500:未知错误
        - 31000:不支持的http消息头
    data:object:返回存储文件的信息
        mimetype:string:文件类型
            - text:文本
        filename:string:上传到OSS的文件路径
        size:long:文本长度
        sha1:string:文本的SHA
        md5:string:文本的MD5
    success:bool:是否成功
    msg:string:操作提示

```


### 6.3. OSS文件-下载

```yaml
@get: /oss/:path

@header:
	Authorization:登录令牌(必须)	

@params:
  path:string:post提交时的filename

@return:
    - 200:对应的文件流
    - 404:文件不存在壮体啊
```

### 6.4. OSS文件-删除

```yaml
@delete: /oss/:path

@header:
	Authorization:登录令牌(必须)	

@params:
  path:string:post提交时的filename

@return:
  code:int:操作状态
      - 200:操作成功
      - 500:未知错误
      - 404:文件不存在
  success:bool:是否成功
  msg:string:操作提示
```

## 7. 用户管理

### 7.1. 用户登录

```yaml
@post: /sys/login

@header:
  X-User-Agent:手机信息(必须)

@payload:
  account:string:账号
  passwd:string:密码

@return:
  code:int:操作码
    - 10000:密码错误
    - 10001:账号不存在
    - 1004:用户已禁用
  success:bool:是否成功
  msg:string:操作提示

```

### 7.2. 用户注销

```yaml
@post: /sys/logout

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@return:
  code:int:操作码
    - 1002:Session会话无效或过期，请重新登录
    - 10000:密码错误
    - 10001:账号不存在
    - 1004:用户已禁用
  success:bool:是否成功
  msg:string:操作提示

```

### 7.3. 获取我的个人信息

```yaml
@get: /sys/my/profiles


@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌
@return:
  code:int:操作码
    - 1002:Session会话无效或过期，请重新登录
    - 10000:密码错误
    - 10001:账号不存在
    - 1004:用户已禁用
  data:object:我的个人信息
    id:long:用户ID
    account:string:账户
    organization:object:机构信息
      id:long:机构ID
      name:string:名称
      level:string:级别
      province:string:省
      city:string:市
      county:string:区/县
      address:string:详细地址
      business_license:string:营业执照(url)
      business_scope:string:经营范围
      operate_route:string:运营路线
      principal:string:负责人
      contact:string:联系方式
      remark:string:备注
    real_name:string:用户名
    remark:string:备注
    role:object:角色信息
      id:long:角色ID
      name:string:角色名称
      remark:string:描述
    status:int:用户状态
      - 0:禁用
      - 1:启用
  success:bool:是否成功
  msg:string:操作提示
```


### 7.4. 用户管理-列表

```yaml
@get: /sys/users/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  account:string:用户名
  real_name:string:真实姓名
  organization:string:企业名称
  status:int:状态
    - 0:禁用
    - 1:启用
@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:用户列表信息
      id:long:用户ID
      account:string:账户
      organization:object:机构信息
        id:long:机构ID
        name:string:名称
        level:string:级别
        province:string:省
        city:string:市
        county:string:区/县
        address:string:详细地址
        business_license:string:营业执照(url)
        business_scope:string:经营范围
        operate_route:string:运营路线
        principal:string:负责人
        contact:string:联系方式
        remark:string:备注
      real_name:string:用户名
      remark:string:备注
      role:object:角色信息
        id:long:角色ID
        name:string:角色名称
        remark:string:描述
      status:int:用户状态
        - 0:禁用
        - 1:启用
  success:bool:是否成功
  msg:string:操作提示
```

### 7.5. 用户管理-详情

```yaml
@get: /sys/users/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:用户ID
    account:string:账户
    organization:object:机构信息
      id:long:机构ID
      name:string:名称
      level:string:级别
      province:string:省
      city:string:市
      county:string:区/县
      address:string:详细地址
      business_license:string:营业执照(url)
      business_scope:string:经营范围
      operate_route:string:运营路线
      principal:string:负责人
      contact:string:联系方式
      remark:string:备注
    real_name:string:用户名
    remark:string:备注
    role:object:角色信息
      id:long:角色ID
      name:string:角色名称
      remark:string:描述
    status:int:用户状态
      - 0:禁用
      - 1:启用
  success:bool:是否成功
  msg:string:操作提示
```

### 7.6. 用户管理-新增

```yaml
@post: /sys/users

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  account:string:账户
  passwd:string:密码
  organization:object:机构信息
    id:long:机构id
  real_name:string:用户名
  remark:string:备注
  role:object:角色信息
    id:long:角色id
  status:int:用户状态
    - 0:禁用
    - 1:启用
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:用户ID
  success:bool:是否成功
  msg:string:操作提示
```

### 7.7. 用户管理-修改

```yaml
@put: /sys/users/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id

@payload:
  organization:object:机构信息
    id:long:机构id
  real_name:string:用户名
  remark:string:备注
  role:object:角色信息
    id:long:角色id
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:用户ID
    account:string:账户
    organization:object:机构信息
    real_name:string:用户名
    remark:string:备注
    role:object:角色信息
    status:int:用户状态
      - 0:禁用
      - 1:启用
  success:bool:是否成功
  msg:string:操作提示
```

### 7.8. 用户管理-禁用/启用

```yaml
@put: /sys/users/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id
  status:int:用户状态
    - 0:禁用
    - 1:启用
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:用户ID
    account:string:账户
    organization:object:机构信息
    real_name:string:用户名
    remark:string:备注
    role:object:角色信息
    status:int:用户状态
      - 0:禁用
      - 1:启用
  success:bool:是否成功
  msg:string:操作提示
```
### 7.9. 用户管理-删除

```yaml
@delete: /sys/users/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:用户ID
  success:bool:是否成功
  msg:string:操作提示
```

### 7.10. 用户管理-重置密码

```yaml
@post: /sys/users/:id/resetpasswd

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户ID

@return:
  code:int:操作码
  data:object:用户信息
    id:long:用户ID
  success:bool:是否成功
  msg:string:操作提示
```

## 8. 公交企业管理

### 8.1. 公交企业管理-列表

```yaml
@get: /sys/organizations/list?page=:page&size=:size&name=:name&status=:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  name:string:机构名称
  level:int:机构级别
  status:int:状态
    - 1:待审核 
    - 2:通过审核 
    - 3:未通过审核

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:机构列表信息
      id:long:机构ID
      name:string:名称
      level:string:级别
      province:string:省
      city:string:市
      county:string:区/县
      address:string:详细地址
      business_license:string:营业执照(url)
      business_scope:string:经营范围
      company_no:string:公司编号
      scale:string:企业规模
      sales_volume:int:销售额
      staff_number:int:员工人数
      vehicle_number:int:车辆数
      station_number:int:车站数
      operate_route:string:运营路线
      principal:string:负责人
      contact:string:联系方式
      remark:string:备注
      status:int:状态(1:待审核 2:通过审核 3:未通过审核)
      parent:object:上级公司信息
        id:long:ID
        name:string:名称
      car_team_list:arry<object>:车队信息
        id:long:ID
        oid:string:旧系统ID
        name:string:车队名称
        address:string:地址
        telephone:string:电话
        principal:string:负责人
        num:string:车队编号
        create_date:date:创建时间
        status:int:状态
      reason:string:理由
      audit_list:array<object>:审核记录
        id:long:审核ID
        reason:string:原因
        create_date:date:创建时间
        create_user:long:创建用户
        last_upd_date:date:最后修改时间
        last_upd_user:long:最后修改人

  success:bool:是否成功
  msg:string:操作提示
```

### 8.2. 公交企业管理-新增

```yaml
@post: /sys/organizations

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:名称
  level:string:级别
  province:string:省
  city:string:市
  county:string:区/县
  address:string:详细地址
  business_license:string:营业执照(url)
  business_scope:string:经营范围
  operate_route:string:运营路线
  principal:string:负责人
  contact:string:联系方式
  remark:string:备注

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:机构ID
  success:bool:是否成功
  msg:string:操作提示
```

### 8.3. 公交企业管理-编辑

```yaml
@put: /sys/organizations/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:机构ID

@payload:
  name:string:名称
  level:string:级别
  province:string:省
  city:string:市
  county:string:区/县
  address:string:详细地址
  business_license:string:营业执照(url)
  business_scope:string:经营范围
  operate_route:string:运营路线
  principal:string:负责人
  contact:string:联系方式
  remark:string:备注

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:机构ID
  success:bool:是否成功
  msg:string:操作提示
```

### 8.4. 公交企业管理-审核

```yaml
@post: /sys/organizations/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:机构ID
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```

### 8.5. 公交企业管理-详情

```yaml
@get: /sys/organizations/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:机构ID

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:机构ID
    name:string:名称
    level:string:级别
    province:string:省
    city:string:市
    county:string:区/县
    address:string:详细地址
    business_license:string:营业执照(url)
    business_scope:string:经营范围
    operate_route:string:运营路线
    principal:string:负责人
    contact:string:联系方式
    remark:string:备注
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
    reason:string:理由
  success:bool:是否成功
  msg:string:操作提示
```

### 8.6. 公交企业管理-删除

```yaml
@delete: /sys/organizations/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:机构ID

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:机构ID
  success:bool:是否成功
  msg:string:操作提示
```

### 8.7. 公交企业管理-导出

```yaml
@get: /sys/organizations/export?name=:name&status=:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  name:string:机构名称
  status:int:状态
    - 1:待审核
    - 2:通过审核
    - 3:未通过审核

@return:
  code:int:操作码
    - 10002:导出数据出错
  success:bool:是否成功
  msg:string:操作提示
```

### 8.8. 公交企业管理-导入

```yaml
@post: /sys/organizations/import

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:文件名
  file:file:导入的文件

@return:
  code:int:操作码
    - 10003:导入数据出错
  success:bool:是否成功
  msg:string:操作提示
```

## 9. 菜单管理

### 9.1. 菜单管理-我的菜单

```yaml
@get: /sys/menus/my

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌


@return:
  code:int:操作码
  data:array<object>:菜单信息
    name:string:名称
    path:string:路径
    icon:string:图标
    index:int:顺序
    type:int:类型(1=菜单 2=按钮 3=tab页选项卡)
    has_tabs:int:标记该菜单下是否有tabs选项卡(1:有 0:无)
    children:array<object>:子菜单
      name:string:名称
      path:string:路径
      parent:string:父菜单的path
      index:int:顺序
      type:int:类型(1=菜单 2=按钮 3=tab页选项卡)
      has_tabs:int:标记该菜单下是否有tabs选项卡(1:有 0:无)
  success:bool:是否成功
  msg:string:操作提示
```

### 9.2. 菜单管理-全部菜单

```yaml
@get: /sys/menus/all

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@return:
  code:int:操作码
  data:array<object>:菜单信息
    name:string:名称
    path:string:路径
    icon:string:图标
    index:int:顺序
    type:int:类型(1=菜单 2=按钮 3=tab页选项卡)
    has_tabs:int:标记该菜单下是否有tabs选项卡(1:有 0:无)
    children:array<object>:子菜单
      name:string:名称
      path:string:路径
      parent:string:父菜单的path
      index:int:顺序
      type:int:类型(1=菜单 2=按钮 3=tab页选项卡)
      has_tabs:int:标记该菜单下是否有tabs选项卡(1:有 0:无)
  success:bool:是否成功
  msg:string:操作提示
```

### 9.3. 菜单管理-菜单下所有的tabs页签

```yaml
@get: /sys/menus/:id/alltabs

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:菜单ID

@return:
  code:int:操作码
  data:array<object>:菜单信息
    name:string:名称
    path:string:路径
    icon:string:图标
    index:int:顺序
    type:int:类型(1=菜单 2=按钮 3=tab页选项卡)
  success:bool:是否成功
  msg:string:操作提示
```

### 9.4. 菜单管理-菜单下我拥有的tabs页签

```yaml
@get: /sys/menus/:id/mytabs

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:菜单ID

@return:
  code:int:操作码
  data:array<object>:tab页签信息
    name:string:名称
    path:string:路径
    icon:string:图标
    index:int:顺序
    type:int:类型(1=菜单 2=按钮 3=tab页选项卡)
  success:bool:是否成功
  msg:string:操作提示
```


## 10. 角色管理

### 10.1. 角色管理-列表

```yaml
@get: /sys/roles/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  name:string:角色名
  remark:string:角色描述

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:机构列表信息
      id:long:角色ID
      name:string:角色名称
      remark:string:描述
      user_list:array<object>:用户信息
        id:long:用户ID
  success:bool:是否成功
  msg:string:操作提示
```
### 10.2. 角色-详情

```yaml
@get: /sys/roles/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:角色ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:角色ID
    name:string:角色名称
    remark:string:描述
  success:bool:是否成功
  msg:string:操作提示
```

### 10.3. 角色管理-新增

```yaml
@post: /sys/roles

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:角色名称
  remark:string:描述
  menu_list:array<object>:菜单信息
    id:long:选择的菜单ID
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:角色ID
  success:bool:是否成功
  msg:string:操作提示
```

### 10.4. 角色管理-编辑

```yaml
@put: /sys/roles/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:菜单ID

@payload:
  name:string:角色名称
  remark:string:描述
  menu_list:array<object>:菜单信息
    id:long:选择的菜单ID
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:角色ID
    name:string:角色名称
    remark:string:描述
  success:bool:是否成功
  msg:string:操作提示
```


### 10.5. 角色管理-删除

```yaml
@delete: /sys/roles/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:角色ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:角色ID
  success:bool:是否成功
  msg:string:操作提示
```

## 11. 日志管理

### 11.1. 日志管理-列表

```yaml
@get: /sys/oplogs/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  account:string:用户名
  real_name:string:真实姓名
  org_name:string:机构名称
  module:string:模块
  func_name:string:操作名称
  params:string:参数
  ip:string:IP
  begin:date:开始时间
  end:date:结束时间

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:日志列表信息
      id:long:机构ID
      account:string:用户名
      real_name:string:真实姓名
      org_name:string:机构名称
      module:string:模块
      func_name:string:操作名称
      params:string:参数
      ip:string:IP
      create_date:date:操作时间

  success:bool:是否成功
  msg:string:操作提示
```

## 12. 从业人员管理

### 12.1. 从业人员管理-列表

```yaml
@get: /industry/practitioners/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  name:string:姓名
  gender:string:性别
    - M:男
    - F:女
    - N:未知
  cert_no:string:资格证号
  id_number:string:高价证号
  organization:string:企业名称
@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:人员列表信息
      id:long:人员ID
      name:string:人员姓名
      gender:string:性别
        - M:男
        - F:女
        - N:未知
      age:int:年龄
      driving_age:int:架龄
      cert_no:string:资格证号
      id_number:string:身份证号
      phone:string:联系号码
      email:string:电子邮箱
      address:string:通讯地址
      organization:object:机构信息
        id:long:机构ID
        name:string:名称
        level:string:级别
        province:string:省
        city:string:市
        county:string:区/县
        address:string:详细地址
        business_license:string:营业执照(url)
        business_scope:string:经营范围
        operate_route:string:运营路线
        principal:string:负责人
        contact:string:联系方式
        remark:string:备注
      car_team:object:车队
        id:long:ID
        name:string:名称
      staff_number:string:员工工号
      card_number:string:卡号
      type:string:从业类型
      bus_route:object:线路
        id:long:线路id
        name:string:线路名
      remark:string:备注
      status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 12.2. 从业人员管理-详情

```yaml
@get: /industry/practitioners/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:人员ID
    name:string:人员姓名
    gender:string:性别
      - M:男
      - F:女
      - N:未知
    age:int:年龄
    driving_age:int:架龄
    cert_no:string:资格证号
    id_number:string:身份证号
    phone:string:联系号码
    email:string:电子邮箱
    address:string:通讯地址
    organization:object:机构信息
      id:long:机构ID
      name:string:名称
      level:string:级别
      province:string:省
      city:string:市
      county:string:区/县
      address:string:详细地址
      business_license:string:营业执照(url)
      business_scope:string:经营范围
      operate_route:string:运营路线
      principal:string:负责人
      contact:string:联系方式
      remark:string:备注
    car_team:object:车队
        id:long:ID
        name:string:名称
    staff_number:string:员工工号
    card_number:string:卡号
    type:string:从业类型
    bus_route:object:线路
      id:long:线路id
      name:string:线路名
    remark:string:备注
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 12.3. 从业人员管理-新增

```yaml
@post: /industry/practitioners

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:人员姓名
  gender:string:性别
    - M:男
    - F:女
    - N:未知
  age:int:年龄
  driving_age:int:架龄
  cert_no:string:资格证号
  id_number:string:身份证号
  phone:string:联系号码
  email:string:电子邮箱
  address:string:通讯地址
  organization:object:机构信息
    id:long:机构ID
  car_team:object:车队
    id:long:ID
  staff_number:string:员工工号
  card_number:string:卡号
  type:string:从业类型
  bus_route:object:线路
    id:long:线路id
  remark:string:备注
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:人员ID
  success:bool:是否成功
  msg:string:操作提示
```

### 12.4. 从业人员管理-修改

```yaml
@put: /industry/practitioners/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id

@payload:
  name:string:人员姓名
  gender:string:性别
    - M:男
    - F:女
    - N:未知
  age:int:年龄
  driving_age:int:架龄
  cert_no:string:资格证号
  id_number:string:身份证号
  phone:string:联系号码
  address:string:通讯地址
  organization:object:机构信息
    id:long:机构id
  car_team:object:车队
    id:long:ID
  staff_number:string:员工工号
  card_number:string:卡号
  type:string:从业类型
  car_team:object:车队
    id:long:ID
  remark:string:备注

@return:
  code:int:操作码
  data:object:返回信息
    id:long:人员ID
    name:string:人员姓名
    gender:string:性别
      - M:男
      - F:女
      - N:未知
    age:int:年龄
    driving_age:int:架龄
    cert_no:string:资格证号
    id_number:string:身份证号
    phone:string:联系号码
    email:string:电子邮箱
    address:string:通讯地址
    organization:object:机构信息
      id:long:机构ID
      name:string:名称
      level:string:级别
      province:string:省
      city:string:市
      county:string:区/县
      address:string:详细地址
      business_license:string:营业执照(url)
      business_scope:string:经营范围
      operate_route:string:运营路线
      principal:string:负责人
      contact:string:联系方式
      remark:string:备注
    car_team:object:车队
      id:long:ID
      name:string:名称
    staff_number:string:员工工号
    card_number:string:卡号
    type:string:从业类型
    bus_route:object:线路
      id:long:线路id
      name:string:线路名
    remark:string:备注
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 12.5. 从业人员管理-删除

```yaml
@delete: /industry/practitioners/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:人员ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:人员ID
  success:bool:是否成功
  msg:string:操作提示
```

### 12.6. 从业人员管理-导出

```yaml
@get: /industry/practitioners/export?name=:name&status=:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  name:string:姓名
  gender:string:性别
    - M:男
    - F:女
    - N:未知
  cert_no:string:资格证号
  id_number:string:高价证号
  organization:string:企业名称

@return:
  code:int:操作码
    - 10002:导出数据出错
  success:bool:是否成功
  msg:string:操作提示
```

### 12.7. 从业人员管理-导入

```yaml
@post: /industry/practitioners/import

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:文件名
  file:file:导入的文件

@return:
  code:int:操作码
    - 10003:导入数据出错
  success:bool:是否成功
  msg:string:操作提示
```

### 12.8. 从业人员管理-审核

```yaml
@post: /industry/practitioners/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```


## 13. 场站管理

### 13.1. 场站管理-列表

```yaml
@get: /industry/busterminals/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  name:string:名称
  code:string:编号
  type:string:类型
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:场站列表信息
      id:long:人员ID
      name:string:名称
      code:string:编码
      type:string:类型
      area:float:面积
      address:string:地址
      longitude:float:经度
      latitude:float:纬度
      parking_number:int:停车数
      photos:string:现场照片
      organization:object:机构信息
        id:long:机构ID
        name:string:名称
        level:string:级别
        province:string:省
        city:string:市
        county:string:区/县
        address:string:详细地址
        business_license:string:营业执照(url)
        business_scope:string:经营范围
        operate_route:string:运营路线
        principal:string:负责人
        contact:string:联系方式
        remark:string:备注
      remark:string:备注
      status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 13.2. 场站管理-详情

```yaml
@get: /industry/busterminals/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:人员ID
    name:string:名称
    code:string:编码
    type:string:类型
    area:float:面积
    address:string:地址
    longitude:float:经度
    latitude:float:纬度
    parking_number:int:停车数
    photos:string:现场照片
    organization:object:机构信息
      id:long:机构ID
      name:string:名称
      level:string:级别
      province:string:省
      city:string:市
      county:string:区/县
      address:string:详细地址
      business_license:string:营业执照(url)
      business_scope:string:经营范围
      operate_route:string:运营路线
      principal:string:负责人
      contact:string:联系方式
      remark:string:备注
    remark:string:备注
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 13.3. 场站管理-新增

```yaml
@post: /industry/busterminals

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:名称
  code:string:编码
  type:string:类型
  area:float:面积
  address:string:地址
  longitude:float:经度
  latitude:float:纬度
  parking_number:int:停车数
  photos:string:现场照片（逗号分隔）
  organization:object:机构信息
    id:long:机构ID
  remark:string:备注
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:场站ID
  success:bool:是否成功
  msg:string:操作提示
```

### 13.4. 场站管理-修改

```yaml
@put: /industry/busterminals/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:场站id

@payload:
  name:string:名称
  code:string:编码
  type:string:类型
  area:float:面积
  address:string:地址
  longitude:float:经度
  latitude:float:纬度
  parking_number:int:停车数
  photos:string:现场照片（逗号分隔）
  organization:object:机构信息
    id:long:机构ID
  remark:string:备注
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:人员ID
    name:string:名称
    code:string:编码
    type:string:类型
    area:float:面积
    address:string:地址
    longitude:float:经度
    latitude:float:纬度
    parking_number:int:停车数
    photos:string:现场照片
    organization:object:机构信息
      id:long:机构ID
      name:string:名称
      level:string:级别
      province:string:省
      city:string:市
      county:string:区/县
      address:string:详细地址
      business_license:string:营业执照(url)
      business_scope:string:经营范围
      operate_route:string:运营路线
      principal:string:负责人
      contact:string:联系方式
      remark:string:备注
    remark:string:备注
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 13.5. 场站管理-删除

```yaml
@delete: /industry/busterminals/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:场站ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:场站ID
  success:bool:是否成功
  msg:string:操作提示
```
### 13.6. 场站管理-导出

```yaml
@get: /industry/busterminals/export?name=:name&status=:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  name:string:名称
  code:string:编号
  type:string:类型

@return:
  code:int:操作码
    - 10002:导出数据出错
  success:bool:是否成功
  msg:string:操作提示
```

### 13.7. 场站管理-导入

```yaml
@post: /industry/busterminals/import

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:文件名
  file:file:导入的文件

@return:
  code:int:操作码
    - 10003:导入数据出错
  success:bool:是否成功
  msg:string:操作提示
```


### 13.8. 场站管理-审核

```yaml
@post: /industry/busterminals/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```

## 14. 公交车辆管理

### 14.1. 公交车辆管理-列表

```yaml
@get: /industry/vehicles/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  lic_no:string:车牌号
  vin:string:VIN码
  fuel_type:int:燃料类型
  card_time:date:上牌时间
  status:int:状态
    - 1:待审核 
    - 2:通过审核 
    - 3:未通过审核

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:公交车辆列表信息
      id:long:ID
      lic_no:string:车牌号
      vin:string:VIN码
      length:float:车长
      width:float:车宽
      height:float:车高
      fuel_type:int:燃料类型
      -- veh_type:int:车辆型号 改为string
      <font color=red>添加字段-开始</font>
      veh_type:string:车辆型号 改为String 必填
      load_num:int:载客数量
      factory_name:string:制造商
      product_date:date:生产日期
      register_date:date:投产日期
      purpose:string:用途
      car_help:int:是否助力(1=是  0=否)
      engine:int:前后置发动机(1=前  2=后)
      air_condition:int:是否空调(1=是  0=否)
      code:string:车辆编号
      <font color=red>添加字段-结束</font>
      card_time:date:上牌时间
      sim:string:SIM卡号
      terminal_no:string:车载终端编号
      organization:object:所属企业
        id:long:ID
        name:string:名称
      car_team:object:车队
        id:long:ID
        name:string:名称
      -- route:string:路线 删掉此字段
      bus_route:object:公交路线信息
        id:long:ID
        name:string:名称
      seat_num:int:座位数
      remark:string:备注
      status:int:状态(1:待审核 2:通过审核 3:未通过审核)
      reason:string:理由
      audit_list:array<object>:审核记录
        id:long:审核ID
        reason:string:原因
        create_date:date:创建时间
        create_user:long:创建用户
        last_upd_date:date:最后修改时间
        last_upd_user:long:最后修改人

  success:bool:是否成功
  msg:string:操作提示
```

### 14.2. 公交车辆管理-新增

```yaml
@post: /industry/vehicles

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  lic_no:string:车牌号
  vin:string:VIN码
  length:float:车长
  width:float:车宽
  height:float:车高
  fuel_type:int:燃料类型
  -- veh_type:int:车辆型号 改为string
  <font color=red>添加字段-开始</font>
  veh_type:string:车辆型号 改为String 必填
  load_num:int:载客数量
  factory_name:string:制造商
  product_date:date:生产日期
  register_date:date:投产日期
  purpose:string:用途
  car_help:int:是否助力(1=是  0=否)
  engine:int:前后置发动机(1=前  2=后)
  air_condition:int:是否空调(1=是  0=否)
  code:string:车辆编号
  <font color=red>添加字段-结束</font>
  card_time:date:上牌时间
  sim:string:SIM卡号
  terminal_no:string:车载终端编号
  organization:object:所属企业
    id:long:ID
    name:string:名称
  car_team:object:车队
    id:long:ID
    name:string:名称
  -- route:string:路线 删掉此字段
  bus_route:object:公交路线信息
    id:long:ID
    name:string:名称
  seat_num:int:座位数
  remark:string:备注
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 14.3. 公交车辆管理-编辑

```yaml
@put: /industry/vehicles/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@payload:
  lic_no:string:车牌号
  vin:string:VIN码
  length:float:车长
  width:float:车宽
  height:float:车高
  fuel_type:int:燃料类型
  -- veh_type:int:车辆型号 改为string
  <font color=red>添加字段-开始</font>
  veh_type:string:车辆型号 改为String 必填
  load_num:int:载客数量
  factory_name:string:制造商
  product_date:date:生产日期
  register_date:date:投产日期
  purpose:string:用途
  car_help:int:是否助力(1=是  0=否)
  engine:int:前后置发动机(1=前  2=后)
  air_condition:int:是否空调(1=是  0=否)
  code:string:车辆编号
  <font color=red>添加字段-结束</font>
  card_time:date:上牌时间
  sim:string:SIM卡号
  terminal_no:string:车载终端编号
  organization:object:所属企业
    id:long:ID
    name:string:名称
  car_team:object:车队
    id:long:ID
    name:string:名称
  -- route:string:路线 删掉此字段
  bus_route:object:公交路线信息
    id:long:ID
    name:string:名称
  seat_num:int:座位数
  remark:string:备注
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 14.4. 公交车辆管理-审核

```yaml
@post: /industry/vehicles/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```

### 14.5. 公交车辆管理-详情

```yaml
@get: /industry/vehicles/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
    lic_no:string:车牌号
    vin:string:VIN码
    length:float:车长
    width:float:车宽
    height:float:车高
    fuel_type:int:燃料类型
    -- veh_type:int:车辆型号 改为string
    <font color=red>添加字段-开始</font>
    veh_type:string:车辆型号 改为String 必填
    load_num:int:载客数量
    factory_name:string:制造商
    product_date:date:生产日期
    register_date:date:投产日期
    purpose:string:用途
    car_help:int:是否助力(1=是  0=否)
    engine:int:前后置发动机(1=前  2=后)
    air_condition:int:是否空调(1=是  0=否)
    code:string:车辆编号
    <font color=red>添加字段-结束</font>
    card_time:date:上牌时间
    sim:string:SIM卡号
    terminal_no:string:车载终端编号
    organization:object:所属企业
      id:long:ID
      name:string:名称
    car_team:object:车队
      id:long:ID
      name:string:名称
    -- route:string:路线 删掉此字段
    bus_route:object:公交路线信息
      id:long:ID
      name:string:名称
    seat_num:int:座位数
    remark:string:备注
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
    reason:string:理由
    audit_list:array<object>:审核记录
      id:long:审核ID
      reason:string:原因
      create_date:date:创建时间
      create_user:long:创建用户
      last_upd_date:date:最后修改时间
      last_upd_user:long:最后修改人
  success:bool:是否成功
  msg:string:操作提示
```

### 14.6. 公交车辆管理-删除

```yaml
@delete: /industry/vehicles/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 14.7. 公交车辆管理-导出

```yaml
@get: /industry/vehicles/export?name=:name&status=:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  name:string:机构名称
  status:int:状态
    - 1:待审核
    - 2:通过审核
    - 3:未通过审核

@return:
  code:int:操作码
    - 10002:导出数据出错
  success:bool:是否成功
  msg:string:操作提示
```

### 14.8. 公交车辆管理-导入

```yaml
@post: /industry/vehicles/import

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:文件名
  file:file:导入的文件

@return:
  code:int:操作码
    - 10003:导入数据出错
  success:bool:是否成功
  msg:string:操作提示
```

## 15. 公交路线管理


### 15.1. 公交路线管理-列表

```yaml
@get: /industry/busroutes/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  name:string:名称
  mileage:string:里程
  organization.name:string:所属企业
  price:float:票价
  way_sites:string:途经站点
  status:int:状态
    - 1:待审核 
    - 2:通过审核 
    - 3:未通过审核

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:公交车辆列表信息
      id:long:ID
      name:string:名称
      code:string:编号
      <font color=red>添加字段-开始</font>
      type:int:线路类型(字典表中获取)
      <font color=red>添加字段-结束</font>
      price:float:票价
      mileage:string:里程
      start_site:string:首发站
      end_site:string:终点站
      way_sites:string:途经站点
      startTime:date:首班时间
      lastTime:date:首班时间
      remark:string:备注
      status:int:状态(1:待审核 2:通过审核 3:未通过审核)
      organization:object:所属企业
        id:long:ID
        name:string:名称
      car_team:object:车队
        id:long:ID
        name:string:名称
      reason:string:理由
      audit_list:array<object>:审核记录
        id:long:审核ID
        reason:string:原因
        create_date:date:创建时间
        create_user:long:创建用户
        last_upd_date:date:最后修改时间
        last_upd_user:long:最后修改人

  success:bool:是否成功
  msg:string:操作提示
```

### 15.2. 公交路线管理-新增

```yaml
@post: /industry/busroutes

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:名称
  code:string:编号
  price:float:票价
  mileage:string:里程
  start_site:string:首发站
  end_site:string:终点站
  way_sites:string:途经站点
  startTime:date:首班时间
  lastTime:date:首班时间
  remark:string:备注
  organization:object:所属企业
    id:long:ID
    name:string:名称
  car_team:object:车队
    id:long:ID
    name:string:名称

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 15.3. 公交路线管理-编辑

```yaml
@put: /industry/busroutes/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@payload:
  name:string:名称
  code:string:编号
  price:float:票价
  mileage:string:里程
  start_site:string:首发站
  end_site:string:终点站
  way_sites:string:途经站点
  startTime:date:首班时间
  lastTime:date:首班时间
  remark:string:备注
  organization:object:所属企业
    id:long:ID
    name:string:名称
  car_team:object:车队
    id:long:ID
    name:string:名称

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 15.4. 公交路线管理-审核

```yaml
@post: /industry/busroutes/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```

### 15.5. 公交路线管理-详情

```yaml
@get: /industry/busroutes/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
    name:string:名称
    code:string:编号
    price:float:票价
    mileage:string:里程
    start_site:string:首发站
    end_site:string:终点站
    way_sites:string:途经站点
    startTime:date:首班时间
    lastTime:date:首班时间
    remark:string:备注
    organization:object:所属企业
      id:long:ID
      name:string:名称
    car_team:object:车队
      id:long:ID
      name:string:名称
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
    reason:string:理由
    audit_list:array<object>:审核记录
      id:long:审核ID
      reason:string:原因
      create_date:date:创建时间
      create_user:long:创建用户
      last_upd_date:date:最后修改时间
      last_upd_user:long:最后修改人
  success:bool:是否成功
  msg:string:操作提示
```

### 15.6. 公交路线管理-删除

```yaml
@delete: /industry/busroutes/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 15.7. 公交路线管理-导出

```yaml
@get: /industry/busroutes/export?name=:name&status=:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  name:string:机构名称
  status:int:状态
    - 1:待审核
    - 2:通过审核
    - 3:未通过审核

@return:
  code:int:操作码
    - 10002:导出数据出错
  success:bool:是否成功
  msg:string:操作提示
```

### 15.8. 公交路线管理-导入

```yaml
@post: /industry/busroutes/import

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:文件名
  file:file:导入的文件

@return:
  code:int:操作码
    - 10003:导入数据出错
  success:bool:是否成功
  msg:string:操作提示
```


## 16. 公交车队管理

### 16.1. 公交车队管理-列表

```yaml
@get: /industry/busteams/list?page=:page&size=:size&name=:name&status=:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  name:string:机构名称
  status:int:状态
    - 1:待审核
    - 2:通过审核
    - 3:未通过审核

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:车队列表信息
      id:long:ID
      oid:string:旧系统ID
      name:string:车队名称
      ocid:string:旧系统公司ID
      address:string:地址
      telephone:string:电话
      principal:string:负责人
      num:string:车队编号
      create_date:date:创建时间
      status:int:状态

  success:bool:是否成功
  msg:string:操作提示
```


## 17. 字典管理

### 17.1. 字典管理-根据编码获取字典值集合

```yaml
@get: /sys/dict/codes/:code

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  code:string:类型编码

@return:
  code:int:操作码
  data:array<object>:类型列表信息
    id:long:ID
    code:string:编码
    type:string:数据类型
    value:string:数据值
    text:string:数据文本描述
    index:int:排序顺序
    remark:string:备注
    status:int:状态(0=禁用 1=正常)
    parent:object:字典类型父信息
      id:long:ID
    children:array<object>:子类型信息
      id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```


### 17.2. 字典管理-获取所有字典类型

```yaml
@get: /sys/dict/all

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌


@return:
  code:int:操作码
  data:array<object>:类型列表信息
    id:long:ID
    code:string:编码
    type:string:数据类型
    value:string:数据值
    text:string:数据文本描述
    index:int:排序顺序
    remark:string:备注
    status:int:状态(0=禁用 1=正常)
    parent:object:字典类型父信息
      id:long:ID
    children:array<object>:子类型信息
      id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

## 18. 字典表类型说明

```yaml
# VEH_TYPE:int:车辆类型 删除 不需要
EMPLOYMENT_TYPE:int:从业人员类型
FUEL_TYPE:int:燃料类型
STATION_TYPE:int:场站类型
UP_DOWN_TYPE:int:上下行
SUGGEST_TYPE:int:投诉建议类型
ARTICLE_TYPE:int:文章类型
VIOLATION_TYPE:int:违规行为=违规类型
VIOLATION_NATURE_TYPE:int:事件性质
VIOLATION_SEVERITY_TYPE:int:严重程度
ROUTE_TYPE:int:线路类型

EMERGENCY_PREPLAN_TYPE:int:预案分类
EMERGENCY_LEVEL:int:应急事件等级

KNOWLEDGE_BASE_TYPE:int:知识库类型
KNOWLEDGE_BASE_INDUSTRY:int:知识库行业
EXPERT_INDUSTRY:int:专家所属行业
EXPERT_LEVEL:int:专家级别
EXPERT_FIELD:int:领域

```

## 19. 模版配置说明
```yaml
网上数据申报模版: docs/20200330/online_data_declare.xlsx

```

## 20. 公交站点管理

### 20.1. 公交站点管理-列表

```yaml
@get: /industry/bussites/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  name:string:名称
  code:string:编号
  province:string:省
  city:string:市
  county:string:县
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:场站列表信息
      id:long:站点ID
      route:object:线路
        id:long:ID
        name:string:名称
        code:string:编号
        price:float:票价
        mileage:string:里程      start_site:string:首发站
        end_site:string:终点站
        way_sites:string:途经站点
        startTime:date:首班时间
        lastTime:date:首班时间
        remark:string:备注
        status:int:状态(1:待审核 2:通过审核 3:未通过审核)
        organization:object:所属企业
          id:long:ID
          name:string:名称
        car_team:object:车队
          id:long:ID
          name:string:名称
        reason:string:理由
        audit_list:array<object>:审核记录
          id:long:审核ID
          reason:string:原因
          create_date:date:创建时间
          create_user:long:创建用户
          last_upd_date:date:最后修改时间
          last_upd_user:long:最后修改人
      index:int:排序顺序
      up_down:int:上下行
      name:string:站点名称
      code:string:站点编号
      province:string:省      
      city:string:市
      county:string:区/县
      address:sting:详细地址
      longitude:float:经度
      latitude:float:纬度
      gps_angle:float:GPS夹角
      radius:float:半径
      photos:string:现场照片 以英文 , 号隔开
      remark:string:备注
      status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 20.2. 公交站点管理-详情

```yaml
@get: /industry/bussites/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:站点ID
    route:object:线路
      id:long:ID
      name:string:名称
      code:string:编号
      price:float:票价
      mileage:string:里程      start_site:string:首发站
      end_site:string:终点站
      way_sites:string:途经站点
      startTime:date:首班时间
      lastTime:date:首班时间
      remark:string:备注
      status:int:状态(1:待审核 2:通过审核 3:未通过审核)
      organization:object:所属企业
        id:long:ID
        name:string:名称
      car_team:object:车队
        id:long:ID
        name:string:名称
      reason:string:理由
      audit_list:array<object>:审核记录
        id:long:审核ID
        reason:string:原因
        create_date:date:创建时间
        create_user:long:创建用户
        last_upd_date:date:最后修改时间
        last_upd_user:long:最后修改人
    index:int:排序顺序
    up_down:int:上下行
    name:string:站点名称
    code:string:站点编号
    province:string:省      
    city:string:市
    county:string:区/县
    address:sting:详细地址
    longitude:float:经度
    latitude:float:纬度
    gps_angle:float:GPS夹角
    radius:float:半径
    photos:string:现场照片 以英文 , 号隔开
    remark:string:备注
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 20.3. 公交站点管理-新增

```yaml
@post: /industry/bussites

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  route:object:线路
    id:long:ID
  index:int:排序顺序
  up_down:int:上下行
  name:string:站点名称
  code:string:站点编号
  province:string:省      
  city:string:市
  county:string:区/县
  address:sting:详细地址
  longitude:float:经度
  latitude:float:纬度
  gps_angle:float:GPS夹角
  radius:float:半径
  photos:string:现场照片 以英文 , 号隔开
  remark:string:备注
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:站点ID
  success:bool:是否成功
  msg:string:操作提示
```

### 20.4. 公交站点管理-修改

```yaml
@put: /industry/bussites/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:站点ID

@payload:
  name:string:名称
  code:string:编码
  type:string:类型
  area:float:面积
  address:string:地址
  longitude:float:经度
  latitude:float:纬度
  parking_number:int:停车数
  photos:string:现场照片（逗号分隔）
  organization:object:机构信息
    id:long:机构ID
  remark:string:备注
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:站点ID
    route:object:线路
      id:long:ID
      name:string:名称
      code:string:编号
      price:float:票价
      mileage:string:里程      start_site:string:首发站
      end_site:string:终点站
      way_sites:string:途经站点
      startTime:date:首班时间
      lastTime:date:首班时间
      remark:string:备注
      status:int:状态(1:待审核 2:通过审核 3:未通过审核)
      organization:object:所属企业
        id:long:ID
        name:string:名称
      car_team:object:车队
        id:long:ID
        name:string:名称
      reason:string:理由
      audit_list:array<object>:审核记录
        id:long:审核ID
        reason:string:原因
        create_date:date:创建时间
        create_user:long:创建用户
        last_upd_date:date:最后修改时间
        last_upd_user:long:最后修改人
    index:int:排序顺序
    up_down:int:上下行
    name:string:站点名称
    code:string:站点编号
    province:string:省      
    city:string:市
    county:string:区/县
    address:sting:详细地址
    longitude:float:经度
    latitude:float:纬度
    gps_angle:float:GPS夹角
    radius:float:半径
    photos:string:现场照片 以英文 , 号隔开
    remark:string:备注
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 20.5. 公交站点管理-删除

```yaml
@delete: /industry/bussites/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:站点ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:场站ID
  success:bool:是否成功
  msg:string:操作提示
```
### 20.6. 公交站点管理-导出

```yaml
@get: /industry/bussites/export

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  name:string:名称
  code:string:编号
  province:string:省
  city:string:市
  county:string:县
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@return:
  code:int:操作码
    - 10002:导出数据出错
  success:bool:是否成功
  msg:string:操作提示
```

### 20.7. 公交站点管理-导入

```yaml
@post: /industry/busterminals/import

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:文件名
  file:file:导入的文件

@return:
  code:int:操作码
    - 10003:导入数据出错
  success:bool:是否成功
  msg:string:操作提示
```


### 20.8. 公交站点管理-审核

```yaml
@post: /industry/bussites/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```
## 21. 行政管理

### 21.1. 行政管理-列表

```yaml
@get: /industry/notices/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  title:string:标题
  type:int:文章类型(字典表)
  begin:date:开始时间
  end:date:结束时间

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:场站列表信息
      id:long:ID
      title:string:标题
      author:string:作者
      content:string:内容 存放OSS /html/yyyMMdd/xxx.html
      attachs:string:附件 多个以英文 , 号隔开.
      type:int:文章类型(字典表)
      create_date:date:发布时间
  success:bool:是否成功
  msg:string:操作提示
```

### 21.2. 行政管理-详情

```yaml
@get: /industry/notices/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    title:string:标题
    author:string:作者
    content:string:内容 存放OSS /html/yyyMMdd/xxx.html
    attachs:string:附件 多个以英文 , 号隔开.
    type:int:文章类型(字典表)
    create_date:date:发布时间
  success:bool:是否成功
  msg:string:操作提示
```

### 21.3. 行政管理-新增

```yaml
@post: /industry/notices

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  title:string:标题
  author:string:作者
  content:string:内容 存放OSS /html/yyyMMdd/xxx.html
  mileage:string:里程      start_site:string:首发站
  attachs:string:附件 多个以英文 , 号隔开.
  type:int:文章类型(字典表)
  create_date:date:发布时间
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:行政ID
  success:bool:是否成功
  msg:string:操作提示
```

### 21.4. 行政管理-修改

```yaml
@put: /industry/notices/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:站点ID

@payload:
  title:string:标题
  author:string:作者
  content:string:内容 存放OSS /html/yyyMMdd/xxx.html
  attachs:string:附件 多个以英文 , 号隔开.
  type:int:文章类型(字典表)
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    title:string:标题
    author:string:作者
    content:string:内容 存放OSS /html/yyyMMdd/xxx.html
    attachs:string:附件 多个以英文 , 号隔开.
    type:int:文章类型(字典表)
    create_date:date:发布时间
  success:bool:是否成功
  msg:string:操作提示
```

### 21.5. 行政管理-删除

```yaml
@delete: /industry/notices/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:站点ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:行政ID
  success:bool:是否成功
  msg:string:操作提示
```

## 22. 投诉建议管理

### 22.1. 投诉建议管理-列表

```yaml
@get: /industry/suggests/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  type:string:类型
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  content:string:内容
  begin:date:开始时间
  end:date:结束时间

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:场站列表信息
      id:long:ID
      type:string:类型
      content:string:内容
      time:date:发生时间
      promoter:string:投诉人
      photos:string:照片 多个用英文 , 号隔开.
      auditor:object:审核人
        id:long:用户id
        real_name:string:审核人姓名
      reason:string:审核理由-意见
      audit_time:date:审核时间
      remark:string:备注
      status:int:(1:待审核 2:通过审核 3:未通过审核)
  msg:string:操作提示
```

### 22.2. 投诉建议管理-详情

```yaml
@get: /industry/suggests/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    type:string:类型
    content:string:内容
    time:date:发生时间
    promoter:string:投诉人
    photos:string:照片 多个用英文 , 号隔开.
    auditor:object:审核人
      id:long:用户id
      real_name:string:审核人姓名
    reason:string:审核理由-意见
    audit_time:date:审核时间
    remark:string:备注
    status:int:(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 22.3. 投诉建议管理-新增

```yaml
@post: /industry/suggests

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  type:string:类型
  content:string:内容
  time:date:发生时间
  promoter:string:投诉人
  photos:string:照片 多个用英文 , 号隔开.
  remark:string:备注
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:行政ID
  success:bool:是否成功
  msg:string:操作提示
```

### 22.4. 投诉建议管理-修改

```yaml
@put: /industry/suggests/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:站点ID

@payload:
  type:string:类型
  content:string:内容
  time:date:发生时间
  promoter:string:投诉人
  photos:string:照片 多个用英文 , 号隔开.
  remark:string:备注
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    type:string:类型
    content:string:内容
    time:date:发生时间
    promoter:string:投诉人
    photos:string:照片 多个用英文 , 号隔开.
    auditor:object:审核人
      id:long:用户id
      real_name:string:审核人姓名
    reason:string:审核理由-意见
    audit_time:date:审核时间
    remark:string:备注
    status:int:(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 22.5. 投诉建议管理-删除

```yaml
@delete: /industry/suggests/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:站点ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示2
```
### 22.6. 投诉建议管理-审核

```yaml
@post: /industry/suggests/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```


## 23. 违规信息管理

### 23.1. 违规信息管理-列表

```yaml
@get: /industry/busviolations/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  vehicle.licNo:string:车牌号
  practitioner.name:string:司机姓名
  busRoute.name:string:线路
  veh_type:string:车辆型号
  organization.name:string:企业名称
  type:int:违规行为
  begin:date:开始时间
  end:date:结束时间
  status:int:状态(1:处理中 2:已处理)


@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:违规信息列表信息
      id:long:ID
      practitioner:object:司机信息
        id:long:ID
        name:string:司机姓名
        organization:object:企业信息
          id:long:ID
          name:string:企业名称
        car_team:object:车队信息
          id:long:ID
          oid:string:旧系统ID
          name:string:车队名称
        bus_route:object:线路信息
          id:long:ID
          name:string:线路名称
      vehicle:object:车辆信息
        id:long:ID
        lic_no:string:车牌号
        veh_type:string:车辆型号
        code:string:车辆编号
        organization:object:企业信息
          id:long:ID
          name:string:企业名称
        car_team:object:车队信息
          id:long:ID
          oid:string:旧系统ID
          name:string:车队名称
        bus_route:object:线路信息
          id:long:ID
          name:string:线路名称
      veh_type:string:车辆型号(车辆信息的veh_type)
      veh_code:string:车辆编号(车辆信息的code)
      bus_route:object:线路信息
        id:long:ID
        name:string:线路名称
      organization:object:企业信息
        id:long:ID
        name:string:企业名称
      car_team:object:车队信息
        id:long:ID
        oid:string:旧系统ID
        name:string:车队名称
      longitude:float:经度
      latitude:float:纬度
      type:int:违规类型-行为(字典表 VIOLATION_TYPE)
      event_nature:int:事件性质(字典表 VIOLATION_NATURE_TYPE)
      severity:int:严重程度(字典表 VIOLATION_SEVERITY_TYPE)
      place:string:违规地点
      description:string:违规描述
      photos:string:取证照片
      videos:string:取证视频
      time:date:发生时间
      deal_opinion:string:处理意见
      deal_photos:strign:处理照片
      transactor:object:处理人信息
        id:long:处理人ID
        real_name:string:真实姓名
      attachs:string:附件 json字符串 格式参看:attachs:string:附件说明
      status:int:状态(1:处理中 2:已处理)
  success:bool:是否成功
  msg:string:操作提示
```

#### 23.1.1. attachs:string:附件说明

```json
[{
  "mimetype": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
  "filename": "/html/20200325/8eb73eac323849e6a9150ffbe35cf67d.xlsx",
  "originalname": "20200316.xlsx",
  "size": "3857",
  "sha1": "27ebcf0f1f645ca3b00c7f63254439191d44418b",
  "md5": "8eb73eac323849e6a9150ffbe35cf67d"
},{
  "mimetype": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
  "filename": "/html/20200325/8eb73eac323849e6a9150ffbe35cf67d.xlsx",
  "originalname": "20200325.xlsx",
  "size": "3857",
  "sha1": "27ebcf0f1f645ca3b00c7f63254439191d44418b",
  "md5": "8eb73eac323849e6a9150ffbe35cf67d"
}]
```

### 23.2. 违规信息管理-新增

```yaml
@post: /industry/busviolations

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  practitioner:object:司机信息
    id:long:ID
    name:string:司机姓名
  vehicle:object:车辆信息
    id:long:ID
    lic_no:string:车牌号
    veh_type:string:车辆型号
    code:string:车辆编号
  veh_type:string:车辆型号(车辆信息的veh_type)
  veh_code:string:车辆编号(车辆信息的code)
  bus_route:object:线路信息
    id:long:ID
    name:string:线路名称
  organization:object:企业信息
    id:long:ID
    name:string:企业名称
  car_team:object:车队信息
    id:long:ID
    oid:string:旧系统ID
    name:string:车队名称
  longitude:float:经度
  latitude:float:纬度
  type:int:违规类型-行为(字典表 VIOLATION_TYPE)
  event_nature:int:事件性质(字典表 VIOLATION_NATURE_TYPE)
  severity:int:严重程度(字典表 VIOLATION_SEVERITY_TYPE)
  place:string:违规地点
  description:string:违规描述
  photos:string:取证照片
  videos:string:取证视频
  time:date:发生时间
  deal_opinion:string:处理意见
  deal_photos:strign:处理照片
  transactor:object:处理人信息
    id:long:处理人ID
    real_name:string:真实姓名
  attachs:string:附件 json字符串 格式参看:attachs:string:附件说明
@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```


### 23.3. 违规信息管理-处理

```yaml
@post: /industry/busviolations/:id/deals

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@payload:
  deal_opinion:string:处理意见
  deal_photos:string:处理照片 多个以英文 , 号隔开
  transactor:object:处理人信息
    id:long:处理人ID
    real_name:string:真实姓名
  attachs:string:附件 json字符串 格式参看:attachs:string:附件说明
  
@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```

### 23.4. 违规信息管理-详情

```yaml
@get: /industry/busviolations/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
    practitioner:object:司机信息
      id:long:ID
      name:string:司机姓名
      organization:object:企业信息
        id:long:ID
        name:string:企业名称
      car_team:object:车队信息
        id:long:ID
        oid:string:旧系统ID
        name:string:车队名称
      bus_route:object:线路信息
        id:long:ID
        name:string:线路名称
    vehicle:object:车辆信息
      id:long:ID
      lic_no:string:车牌号
      veh_type:string:车辆型号
      code:string:车辆编号
      organization:object:企业信息
        id:long:ID
        name:string:企业名称
      car_team:object:车队信息
        id:long:ID
        oid:string:旧系统ID
        name:string:车队名称
      bus_route:object:线路信息
        id:long:ID
        name:string:线路名称
    veh_type:string:车辆型号(车辆信息的veh_type)
    veh_code:string:车辆编号(车辆信息的code)
    bus_route:object:线路信息
      id:long:ID
      name:string:线路名称
    organization:object:企业信息
      id:long:ID
      name:string:企业名称
    car_team:object:车队信息
      id:long:ID
      oid:string:旧系统ID
      name:string:车队名称
    longitude:float:经度
    latitude:float:纬度
    type:int:违规类型-行为(字典表 VIOLATION_TYPE)
    event_nature:int:事件性质(字典表 VIOLATION_NATURE_TYPE)
    severity:int:严重程度(字典表 VIOLATION_SEVERITY_TYPE)
    place:string:违规地点
    description:string:违规描述
    photos:string:取证照片
    videos:string:取证视频
    time:date:发生时间
    deal_opinion:string:处理意见
    deal_photos:strign:处理照片
    transactor:object:处理人信息
      id:long:处理人ID
      real_name:string:真实姓名
    attachs:string:附件 json字符串 格式参看:attachs:string:附件说明
    status:int:状态(1:处理中 2:已处理)
  success:bool:是否成功
  msg:string:操作提示
```

### 23.5. 违规信息管理-删除

```yaml
@delete: /industry/busviolations/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回违规信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 23.6. 违规信息管理-导出

```yaml
@get: /industry/busviolations/export

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  vehicle.licNo:string:车牌号
  practitioner.name:string:司机姓名
  busRoute.name:string:线路
  veh_type:string:车辆型号
  organization.name:string:企业名称
  type:int:违规行为
  begin:date:开始时间
  end:date:结束时间

@return:
  code:int:操作码
    - 10002:导出数据出错
  success:bool:是否成功
  msg:string:操作提示
```

### 23.7. 违规信息管理-导入

```yaml
@post: /industry/busviolations/import

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:文件名
  file:file:导入的文件

@return:
  code:int:操作码
    - 10003:导入数据出错
    - 10004:导入的数据格式不正确
  success:bool:是否成功
  msg:string:操作提示
```

## 24. 事件统计分析

### 24.1. 公司违规信息统计
```yaml
@get:/industry/busviolationstats/companies

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  begin:date:开始时间
  end:date:结束时间

@return:
  code:int:操作码
  data:array<object>:规信息统计
    company:object:公司名
    num:int:违规量
  success:bool:是否成功
  msg:string:操作提示
```

### 24.2. 司机违规信息排行
```yaml
@get:/industry/busviolationstats/drivers

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  begin:date:开始时间
  end:date:结束时间

@return:
  code:int:操作码
  data:array<object>:
    driver:string:司机名
    company:string:公司名
    num:int:违规量
  success:bool:是否成功
  msg:string:操作提示
```


### 24.3. 违规类型

```yaml
@get: /industry/busviolationstats/types?begin=:begin&end=:end

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  begin:date:开始日期
  end:date:结束日期

@return:
  code:int:操作码
  data:array<object>:统计信息
    type:int:违规类型
    text:string:违规类型文字说明
    num:int:违规数量
  success:bool:是否成功
  msg:string:操作提示  
```

### 24.4. 全部违规行为统计(周、月、年)-周

```yaml
@get: /industry/busviolationstats/weeks

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@return:
  code:int:操作码
  data:object:统计信息
    legend_data:array<string>:类型数组
    series:array<object>:序列
      name:string:违规类型
      data:array<int>:违规数量
  success:bool:是否成功
  msg:string:操作提示  

```

### 24.5. 全部违规行为统计(周、月、年)-月

```yaml
@get: /industry/busviolationstats/months

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@return:
  code:int:操作码
  data:object:统计信息
    legend_data:array<string>:类型数组
    series:array<object>:序列
      name:string:违规类型
      data:array<int>:违规数量
  success:bool:是否成功
  msg:string:操作提示  

```

### 24.6. 全部违规行为统计(周、月、年)-年

```yaml
@get: /industry/busviolationstats/years

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@return:
  code:int:操作码
  data:object:统计信息
    legend_data:array<string>:类型数组
    series:array<object>:序列
      name:string:违规类型
      data:array<int>:违规数量
  success:bool:是否成功
  msg:string:操作提示
```


## 25. 燃油消耗管理-统计数据表

### 25.1. 总排炭量统计

```yaml
@get: /industry/onlinedatadeclares/stats/carbons

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@return:
  code:int:操作码
  data:object:统计信息
    gas_quantity:float:汽油数量(换算为排碳量)
    diesel_oil_quantity:float:柴油数量(换算为排碳量)
    natural_gas_quantity:float:天然气数量(换算为排碳量)
    electric_quantity:float:电能数量(换算为排碳量)
  success:bool:是否成功
  msg:string:操作提示
```

### 25.2. 节能减排数据

```yaml
@get: /industry/onlinedatadeclares/stats/cutemissions?begin=:begin&end=:end

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  begin:date:开始日期
  end:date:结束日期

@return:
  code:int:操作码
  data:object:统计信息
    gas_quantity:float:汽油数量(换算为排碳量)
    diesel_oil_quantity:float:柴油数量(换算为排碳量)
    natural_gas_quantity:float:天然气数量(换算为排碳量)
    electric_quantity:float:电能数量(换算为排碳量)
  success:bool:是否成功
  msg:string:操作提示
```

```
二氧化碳排行量（千克）等于：
1. 汽油数乘以2.7
2. 耗电量乘以0.997
3. 柴油数乘以3.115
4. 天然气数（立方米）乘以1.96

总排量参考蓝湖计算方式.

```
### 25.3. 能源趋势对比

```yaml
@get: /industry/onlinedatadeclares/stats/energycomparisons

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

params:
  organization.id:long:公司ID
  bus_route.id:long:线路ID
  category:int:类型(1:年 2:季 3:月 4:周)

@return:
  code:int:操作码
  data:object:统计信息
    legend_data:array<string>:类型数组
    series:array<object>:序列
      name:string:能耗类别
      data:array<int>:能耗数量
  success:bool:是否成功
  msg:string:操作提示
```

### 25.4. 统计查询

```yaml
@get: /industry/onlinedatadeclares/stats/querystatis

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  org_name:string:公司名
  team_id:long:车队id
  route_id:long:线路id
  veh_code：string:车辆编号
  begin:date:开始日期
  end:date:结束日期

@return:
  code:int:操作码
  data:object:返回信息
    list:attay<object>:查询信息
      org_name:string:公司名
      team_name:string:所属车队
      oute_name:string:线路
      veh_code:string:车辆编号;
      gas_quantity:float:汽油数量
      gas_price:float:汽油单价
      gas_balance:float:汽油金额
      diesel_oil_quantity:float:柴油数量
      diesel_oil_price:float:柴油单价
      diesel_oil_balance:float:柴油金额
      natural_gas_quantity:float:天然气数量
      natural_gas_price:float:天然气单价
      natural_gas_balance:float:天然气金额
      electric_quantity:float:电能数量
      electric_price:float:电能单价
      electric_balance:float:电能金额
      total_balance:float:总金额
    total:object:总计
      gas_quantity_total:float:汽油总数量
      gas_balance_total:float:汽油总金额
      diesel_oil_quantity_total:float:柴油总数量
      diesel_oil_balance_total:float:柴油总金额
      natural_gas_quantity_total:float:天然气总数量
      natural_gas_balance_total:float:天然气总金额
      electric_quantity_total:float:电能总数量
      electric_balance_total:float:电能总金额
      all_balance_total:float:总金额
  success:bool:是否成功
  msg:string:操作提示
```

## 26. 网上数据申报管理

### 26.1. 网上数据申报管理-列表

```yaml
@get: /industry/datadeclares/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  organization.name:string:公司名称
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  begin:date:开始时间
  end:date:结束时间

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:申报列表信息
      id:long:ID
      organization:object:公司信息
        id:long:ID
        name:string:名称
      org_name:string:公司名
      declarer:string:申报人
      start_time:date:申报时间-开始
      end_time:date:申报时间-结束
      reason:string:理由
      status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 26.2. 网上数据申报管理-详情

```yaml
@get: /industry/datadeclares/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    organization:object:公司信息
      id:long:ID
      name:string:名称
    org_name:string:公司名
    declarer:string:申报人
    start_time:date:申报时间-开始
    end_time:date:申报时间-结束
    reason:string:理由
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
    item_list:array<object>:明细列表 
      declare:object:申报信息
        id:long:申报id
      organization:object:公司信息
        id:long:公司信息
        name:string:公司名
      org_name:string:机构名
      car_team:object:车队信息
        id:long:车队id
        name:string:车队名
      team_name:string:车队名
      bus_route:object:线路信息
        id:long:线路id
        name:string:线路名
      team_name:string:车队名 
      veh_vode:string:车辆编号
      gas_quantity:float:汽油数量
      gas_price:float:汽油单价
      gas_balance:float:汽油金额
      diesel_oil_quantity:float:柴油数量
      diesel_oil_price:float:柴油单价
      diesel_oil_balance:float:柴油金额
      natural_gas_quantity:float:天然气数量
      natural_gas_price:float:天然气单价
      natural_gas_balance:float:天然气金额
      electric_quantity:float:电能数量
      electric_price:float:电能单价
      electric_balance:float:电能金额
  success:bool:是否成功
  msg:string:操作提示
```

### 26.3. 网上数据申报管理-新增

```yaml
@post: /industry/datadeclares

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  declarer:string:申报人
  start_time:date:申报时间-开始
  end_time:date:申报时间-结束
  file:string:申报明细的文件路径(oss返回的路径)

  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:申报ID
  success:bool:是否成功
  msg:string:操作提示
```

### 26.4. 网上数据申报管理-修改

```yaml
@put: /industry/datadeclares/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:站点ID

@payload:
  organization:object:公司信息
    id:long:ID
  org_name:string:公司名
  declarer:string:申报人
  start_time:date:申报时间-开始
  end_time:date:申报时间-结束
  file:file:申报明细的文件
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    organization:object:公司信息
      id:long:ID
      name:string:名称
    org_name:string:公司名
    declarer:string:申报人
    start_time:date:申报时间-开始
    end_time:date:申报时间-结束
    reason:string:理由
    status:int:状态(1:待审核 2:通过审核 3:未通过审核)
  success:bool:是否成功
  msg:string:操作提示
```

### 26.5. 网上数据申报管理-删除

```yaml
@delete: /industry/datadeclares/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:申报ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:申报ID
  success:bool:是否成功
  msg:string:操作提示
```

### 26.6. 网上数据申报管理-审核

```yaml
@post: /industry/datadeclares/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID
  status:int:状态(1:待审核 2:通过审核 3:未通过审核)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```
## 27. 专家知识库管理

### 27.1. 专家知识库管理-列表

```yaml
@get: /monitor/knowledgebases/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  title:string:标题
  type:int:类型
  begin:date:开始时间
  end:date:结束时间

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:知识库信息列表
      id:long:ID
      title:string:标题
      author:string:作者
      content:string:正文 存放OSS /html/yyyMMdd/xxx.html.
      attachs:string:附件 多个以英文 , 号隔开.
      type:int:类型
      industry:int:行业
      create_date:date:创建时间
  success:bool:是否成功
  msg:string:操作提示
```

### 27.2. 专家知识库管理-详情

```yaml
@get: /monitor/knowledgebases/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:知识库id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    title:string:标题
    author:string:作者
    content:string:正文 存放OSS /html/yyyMMdd/xxx.html.
    attachs:string:附件 多个以英文 , 号隔开.
    type:int:类型
    industry:int:行业
    create_date:date:创建时间
  success:bool:是否成功
  msg:string:操作提示
```

### 27.3. 专家知识库管理-新增

```yaml
@post: /monitor/knowledgebases

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  title:string:标题
  author:string:作者
  content:string:正文 存放OSS /html/yyyMMdd/xxx.html.
  attachs:string:附件 多个以英文 , 号隔开.
  type:int:类型
  industry:int:行业

  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:知识库ID
  success:bool:是否成功
  msg:string:操作提示
```

### 27.4. 专家知识库管理-修改

```yaml
@put: /monitor/knowledgebases/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:站点ID

@payload:
  title:string:标题
  author:string:作者
  content:string:正文 存放OSS /html/yyyMMdd/xxx.html.
  attachs:string:附件 多个以英文 , 号隔开.
  type:int:类型
  industry:int:行业
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    title:string:标题
    author:string:作者
    content:string:正文 存放OSS /html/yyyMMdd/xxx.html.
    attachs:string:附件 多个以英文 , 号隔开.
    type:int:类型
    industry:int:行业
    create_date:date:创建时间
  success:bool:是否成功
  msg:string:操作提示
```

### 27.5. 专家知识库管理-删除

```yaml
@delete: /monitor/knowledgebases/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:知识库ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:知识库ID
  success:bool:是否成功
  msg:string:操作提示
```

## 28. 救援资源管理-人员

### 28.1. 救援资源管理-人员-列表-公司下拉框

```yaml
@get: /monitor/rescuerescuers/orgname/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  org_name:string:公司名称

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-人员列表信息
      org_name:string:所属单位名称
  success:bool:是否成功
  msg:string:操作提示
```

### 28.2. 救援资源管理-人员-列表-职位下拉框

```yaml
@get: /monitor/rescuerescuers/position/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  position:string:职位

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-人员列表信息
      position:string:职位
  success:bool:是否成功
  msg:string:操作提示
```


### 28.3. 救援资源管理-人员-列表

```yaml
@get: /monitor/rescuerescuers/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  org_name:string:公司名称
  position:string:职位
  status:int:状态(1:待审核 2:空闲 3:未通过 4:执行)

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-人员列表信息
      id:long:ID
      name:string:姓名
      gender:string:性别(M:男 F:女 N:未知)
      staff_no:string:员工工号
      phone:string:联系电话
      org_name:string:所属单位名称
      department:string:所属部门
      position:string:职位
      org_address:string:单位地址
      service_area:string:服务区域
      remark:string:备注
      reason:string:审核理由
      status:int:状态(1:待审核 2:空闲 3:未通过 4:执行)
  success:bool:是否成功
  msg:string:操作提示
```

### 28.4. 救援资源管理-人员管理-新增

```yaml
@post: /monitor/rescuerescuers

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:姓名
  gender:string:性别(M:男 F:女 N:未知)
  staff_no:string:员工工号
  phone:string:联系电话
  org_name:string:所属单位名称
  department:string:所属部门
  position:string:职位
  org_address:string:单位地址
  service_area:string:服务区域
  remark:string:备注

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```



### 28.5. 救援资源管理-人员管理-新增

```yaml
@put: /monitor/rescuerescuers/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@payload:
  name:string:姓名
  gender:string:性别(M:男 F:女 N:未知)
  staff_no:string:员工工号
  phone:string:联系电话
  org_name:string:所属单位名称
  department:string:所属部门
  position:string:职位
  org_address:string:单位地址
  service_area:string:服务区域
  remark:string:备注

@return:
  code:int:操作码
  data:object:返回机构信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```


### 28.6. 救援资源管理-人员-详情

```yaml
@get: /monitor/rescuerescuers/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回救援资源管理-人员信息
    id:long:ID
    name:string:姓名
    gender:string:性别(M:男 F:女 N:未知)
    staff_no:string:员工工号
    phone:string:联系电话
    org_name:string:所属单位名称
    department:string:所属部门
    position:string:职位
    org_address:string:单位地址
    service_area:string:服务区域
    remark:string:备注
    reason:string:审核理由
    status:int:状态(1:待审核 2:空闲 3:未通过 4:执行)
  success:bool:是否成功
  msg:string:操作提示
```

### 28.7. 救援资源管理-人员-删除

```yaml
@delete: /monitor/rescuerescuers/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回救援资源管理-人员
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 28.8. 救援资源管理-人员-审核

```yaml
@post: /monitor/rescuerescuers/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:机构ID
  status:int:状态(1:待审核 2:空闲=通过 3:未通过 4:执行)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```

## 29. 救援资源管理-车辆

### 29.1. 救援资源管理-车辆-列表-公司下拉框

```yaml
@get: /monitor/rescuevehicles/orgname/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  org_name:string:公司名称

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-车辆列表信息
      org_name:string:所属单位名称
  success:bool:是否成功
  msg:string:操作提示
```

### 29.2. 救援资源管理-车辆-列表-线路下拉框

```yaml
@get: /monitor/rescuevehicles/routename/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  route_name:string:线路名称

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-车辆列表信息
      route_name:string:线路名称
  success:bool:是否成功
  msg:string:操作提示
```


### 29.3. 救援资源管理-车辆-列表

```yaml
@get: /monitor/rescuevehicles/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  org_name:string:公司名称
  position:string:职位

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-车辆列表信息
      id:long:ID
      code:string:车辆编号
      lic_no:string:车牌号
      veh_type:string:车辆类型
      driver:string:驾驶员
      driver_phone:string:驾驶员电话
      org_name:string:所属单位
      route_name:string:线路名称
      remark:string:备注
      reason:string:审核理由
      status:int:状态(1:待审核 2:空闲 3:未通过 4:执行)
  success:bool:是否成功
  msg:string:操作提示
```

### 29.4. 救援资源管理-车辆-新增

```yaml
@post: /monitor/rescuevehicles

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  code:string:车辆编号
  lic_no:string:车牌号
  veh_type:string:车辆类型
  driver:string:驾驶员
  driver_phone:string:驾驶员电话
  org_name:string:所属单位
  route_name:string:线路名称
  remark:string:备注

@return:
  code:int:操作码
  data:object:返回救援资源管理-车辆信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```



### 29.5. 救援资源管理-车辆-编辑

```yaml
@put: /monitor/rescuevehicles/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@payload:
  code:string:车辆编号
  lic_no:string:车牌号
  veh_type:string:车辆类型
  driver:string:驾驶员
  driver_phone:string:驾驶员电话
  org_name:string:所属单位
  route_name:string:线路名称
  remark:string:备注

@return:
  code:int:操作码
  data:object:返回救援资源管理-车辆信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```


### 29.6. 救援资源管理-车辆-详情

```yaml
@get: /monitor/rescuevehicles/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回救援资源管理-车辆信息
    id:long:ID
    code:string:车辆编号
    lic_no:string:车牌号
    veh_type:string:车辆类型
    driver:string:驾驶员
    driver_phone:string:驾驶员电话
    org_name:string:所属单位
    route_name:string:线路名称
    remark:string:备注
    reason:string:审核理由
    status:int:状态(1:待审核 2:空闲 3:未通过 4:执行)
  success:bool:是否成功
  msg:string:操作提示
```

### 29.7. 救援资源管理-车辆-删除

```yaml
@delete: /monitor/rescuevehicles/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回救援资源管理-车辆
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 29.8. 救援资源管理-车辆-审核

```yaml
@post: /monitor/rescuevehicles/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:救援资源管理-车辆ID
  status:int:状态(1:待审核 2:空闲=通过 3:未通过 4:执行)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```


## 30. 救援资源管理-物资

### 30.1. 救援资源管理-物资-列表-公司下拉框

```yaml
@get: /monitor/rescuematerials/orgname/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  org_name:string:公司名称

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-物资列表信息
      org_name:string:所属单位名称
  success:bool:是否成功
  msg:string:操作提示
```

### 30.2. 救援资源管理-物资-列表-物资编号下拉框

```yaml
@get: /monitor/rescuematerials/code/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  code:string:物资编号

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-物资列表信息
      code:string:物资编号
  success:bool:是否成功
  msg:string:操作提示
```

### 30.3. 救援资源管理-物资-列表-物资类型下拉框

```yaml
@get: /monitor/rescuematerials/type/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  type:string:物资类型

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-物资列表信息
      type:string:物资类型
  success:bool:是否成功
  msg:string:操作提示
```

### 30.4. 救援资源管理-物资-列表-物资来源下拉框

```yaml
@get: /monitor/rescuematerials/type/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  origin:string:物资来源

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-物资列表信息
      origin:string:物资来源
  success:bool:是否成功
  msg:string:操作提示
```

### 30.5. 救援资源管理-物资-列表

```yaml
@get: /monitor/rescuematerials/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  org_name:string:公司名称
  position:string:职位

@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:救援资源管理-物资列表信息
      id:long:ID
      code:string:物资编号
      name:string:物资名称
      type:string:物资类型
      spec:string:规格
      unit:string:计量单位
      quantity:float:数量
      origin:string:物资来源
      parameter:string:参数
      purpose:string:用途
      store_place:strign:存放场所 地点
      price:float:单价
      total_price:float:总价
      producer:string:生产商
      useful_life:string:使用年限
      production_date:date:出厂日期
      purchase_date:date:购买日期
      maintenance_interval:string:定期保修间隔
      principal:string:负责人
      phone:string:联系方式
      org_name:string:所属单位
      remark:string:备注
      reason:string:审核理由
      status:int:状态(1:待审核 2:正常=通过 3:未通过 4:报废)
  success:bool:是否成功
  msg:string:操作提示
```

### 30.6. 救援资源管理-物资-新增

```yaml
@post: /monitor/rescuematerials

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  code:string:物资编号
  name:string:物资名称
  type:string:物资类型
  spec:string:规格
  unit:string:计量单位
  quantity:float:数量
  origin:string:物资来源
  parameter:string:参数
  purpose:string:用途
  store_place:strign:存放场所 地点
  price:float:单价
  total_price:float:总价
  producer:string:生产商
  useful_life:string:使用年限
  production_date:date:出厂日期
  purchase_date:date:购买日期
  maintenance_interval:string:定期保修间隔
  principal:string:负责人
  phone:string:联系方式
  org_name:string:所属单位
  remark:string:备注

@return:
  code:int:操作码
  data:object:返回救援资源管理-物资信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 30.7. 救援资源管理-物资-编辑

```yaml
@put: /monitor/rescuematerials/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@payload:
  code:string:物资编号
  name:string:物资名称
  type:string:物资类型
  spec:string:规格
  unit:string:计量单位
  quantity:float:数量
  origin:string:物资来源
  parameter:string:参数
  purpose:string:用途
  store_place:strign:存放场所 地点
  price:float:单价
  total_price:float:总价
  producer:string:生产商
  useful_life:string:使用年限
  production_date:date:出厂日期
  purchase_date:date:购买日期
  maintenance_interval:string:定期保修间隔
  principal:string:负责人
  phone:string:联系方式
  org_name:string:所属单位
  remark:string:备注

@return:
  code:int:操作码
  data:object:返回救援资源管理-物资信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```


### 30.8. 救援资源管理-物资-详情

```yaml
@get: /monitor/rescuematerials/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回救援资源管理-物资信息
    id:long:ID
    code:string:物资编号
    name:string:物资名称
    type:string:物资类型
    spec:string:规格
    unit:string:计量单位
    quantity:float:数量
    origin:string:物资来源
    parameter:string:参数
    purpose:string:用途
    store_place:strign:存放场所 地点
    price:float:单价
    total_price:float:总价
    producer:string:生产商
    useful_life:string:使用年限
    production_date:date:出厂日期
    purchase_date:date:购买日期
    maintenance_interval:string:定期保修间隔
    principal:string:负责人
    phone:string:联系方式
    org_name:string:所属单位
    remark:string:备注
    reason:string:审核理由
    status:int:状态(1:待审核 2:正常=通过 3:未通过 4:报废)
  success:bool:是否成功
  msg:string:操作提示
```

### 30.9. 救援资源管理-物资-删除

```yaml
@delete: /monitor/rescuematerials/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回救援资源管理-物资
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 30.10. 救援资源管理-物资-审核

```yaml
@post: /monitor/rescuematerials/:id/status/:status

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:救援资源管理-物资ID
  status:int:状态(1:待审核 2:空闲=通过 3:未通过 4:执行)

@payload:
  reason:string:理由

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```
### 30.11. 救援资源管理-物资-报废

```yaml
@post: /monitor/rescuematerials/:id/scraps

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:救援资源管理-物资ID

@return:
  code:int:操作码
  success:bool:是否成功
  msg:string:操作提示
```
## 31. 专家库管理

### 31.1. 专家库管理-列表

```yaml
@get: /monitor/experts/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  name:string:姓名
  field:int:专家领域
  level:int:专家等级

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:知识库信息列表
      id:long:ID
      photo:头像
      name:姓名
      gender:性别
      nation:string:民族
      birth:string:出生日期
      native_place:string:籍贯
      id_number:string:身份证
      political_affiliation:string:政治面貌
      college:string:毕业学校
      major:string:专业
      qualifications:string:最高学历
      mobile_phone:string:移动电话
      office_phone:string:办工电话
      fax:string:传真
      email:string:电子邮箱
      home_address:string:家庭住址
      industry:string:所在行业
      field:int:专家领域
      level:int:专家级别
      workplace:string:工作单位
      title:string:职称
      work_age:int:工龄
      office_address:string:单位地址
      major_des:string:专业描述
      resume:string:工作简历
  success:bool:是否成功
  msg:string:操作提示
```

### 21.2. 专家库管理-详情

```yaml
@get: /monitor/experts/:id
@params:
  id:long:专家库id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    photo:头像
    name:姓名
    gender:性别
    nation:string:民族
    birth:string:出生日期
    native_place:string:籍贯
    id_number:string:身份证
    political_affiliation:string:政治面貌
    college:string:毕业学校
    major:string:专业
    qualifications:string:最高学历
    mobile_phone:string:移动电话
    office_phone:string:办工电话
    fax:string:传真
    email:string:电子邮箱
    home_address:string:家庭住址
    industry:string:所在行业
    field:int:专家领域
    level:int:专家级别
    workplace:string:工作单位
    title:string:职称
    work_age:int:工龄
    office_address:string:单位地址
    major_des:string:专业描述
    resume:string:工作简历
  success:bool:是否成功
  msg:string:操作提示
```
### 31.3. 专家库管理-新增

```yaml
@post: /monitor/experts

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  photo:头像
  name:姓名
  gender:性别
  nation:string:民族
  birth:string:出生日期
  native_place:string:籍贯
  id_number:string:身份证
  political_affiliation:string:政治面貌
  college:string:毕业学校
  major:string:专业
  qualifications:string:最高学历
  mobile_phone:string:移动电话
  office_phone:string:办工电话
  fax:string:传真
  email:string:电子邮箱
  home_address:string:家庭住址
  industry:string:所在行业
  field:int:专家领域
  level:int:专家级别
  workplace:string:工作单位
  title:string:职称
  work_age:int:工龄
  office_address:string:单位地址
  major_des:string:专业描述
  resume:string:工作简历

@return:
  code:int:操作码
  data:object:返回信息
    id:long:专家ID
  success:bool:是否成功
  msg:string:操作提示
### 31.4. 专家库管理-修改

```yaml
@put: /monitor/experts/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌
@params:
  id:long:专家ID

@payload:
  id:long:ID
  photo:头像
  name:姓名
  gender:性别
  nation:string:民族
  birth:string:出生日期
  native_place:string:籍贯
  id_number:string:身份证
  political_affiliation:string:政治面貌
  college:string:毕业学校
  major:string:专业
  qualifications:string:最高学历
  mobile_phone:string:移动电话
  office_phone:string:办工电话
  fax:string:传真
  email:string:电子邮箱
  home_address:string:家庭住址
  industry:string:所在行业
  field:int:专家领域
  level:int:专家级别
  workplace:string:工作单位
  title:string:职称
  work_age:int:工龄
  office_address:string:单位地址
  major_des:string:专业描述
  resume:string:工作简历
 
@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    photo:头像
    name:姓名
    gender:性别
    nation:string:民族
    birth:string:出生日期
    native_place:string:籍贯
    id_number:string:身份证
    political_affiliation:string:政治面貌
    college:string:毕业学校
    major:string:专业
    qualifications:string:最高学历
    mobile_phone:string:移动电话
    office_phone:string:办工电话
    fax:string:传真
    email:string:电子邮箱
    home_address:string:家庭住址
    industry:string:所在行业
    field:int:专家领域
    level:int:专家级别
    workplace:string:工作单位
    title:string:职称
    work_age:int:工龄
    office_address:string:单位地址
    major_des:string:专业描述
    resume:string:工作简历
  success:bool:是否成功
  msg:string:操作提示
### 31.5. 专家库管理-删除

```yaml
@delete: /monitor/experts/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:专家ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:专家ID
  success:bool:是否成功
  msg:string:操作提示
```
## 32. 应急响应处理

### 32.1. 应急响应处理-列表

```yaml
@get: /monitor/emergencyresponses/list?page=:page&size=:size

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  status_list:int:状态(1:待处理 5:处理中 10:待评估 15:已评估) 多个 带多个参数即可 如 待处理和处理中两个状态 status_list=1&status_list=5
  generate_case:int:是否生成案例(0:未生成 1:已生成)
  
@return:
  code:int:操作码
  data:object:分页信息
    count:int:分页总大小
    list:array<object>:应急响应处理列表信息
      id:long:ID
      name:string:事件名称
      start_time:date:事发时间=开始时间
      place:string:事件地点
      description:string:事件描述
      preplan_name:string:预案名称
      preplan_code:string:预案编号
      preplan_type:int:预案分类
      level:int:事件等级
      rescue_plan:string:救援方案 url /html/yyyyMMdd/xxx.html
      attachs:string:附件 多个以英文 , 号隔开 /docs/yyyyMMdd/xxx.文件后缀名
      rescue_company_name:string:救援单位名称
      rescue_start_point:string:救援起点 json结构 { keyword: '贵阳市人民政府',city:'贵阳' }
      rescue_end_point:string:救援终点 json结构 { keyword: '贵阳北站',city:'贵阳' }
      remark:string:备注
      photos:string:执行照片 多个以英文 , 号隔开 /images/yyyyMMdd/xxx.照片后缀名
      videos:string:执行视频 多个以英文 , 号隔开 /videos/yyyyMMdd/xxx.视频后缀名
      effect:string:执行效果 /html/yyyyMMdd/xxx.html
      end_time:date:结束时间
      status:int:状态(1:待处理 5:处理中 10:待评估 15:已评估)
      generate_case:int:是否生成案例(0:未生成 1:已生成)
      info_score:int:信息采集准确度评估
      info_remark:string:信息采集准确度评估 补充
      effect_score:int:信息采集准确度评估
      effect_remark:string:信息采集准确度评估 补充
      dispatch_score:int:信息采集准确度评估
      dispatch_remark:string:信息采集准确度评估 补充
      material_score:int:信息采集准确度评估
      material_remark:string:信息采集准确度评估 补充
      evaluate_remark:string:评估时的备注
      group_leader_list:array<object>:组长信息
        id:long:ID
        name:string:姓名
        gender:string:性别(M:男 F:女 N:未知)
        staff_no:string:员工工号
        phone:string:联系电话
        org_name:string:所属单位名称
        department:string:所属部门
        position:string:职位
        org_address:string:单位地址
        service_area:string:服务区域
        remark:string:备注
      rescuer_list:array<object>:人员信息
        id:long:ID
        name:string:姓名
        gender:string:性别(M:男 F:女 N:未知)
        staff_no:string:员工工号
        phone:string:联系电话
        org_name:string:所属单位名称
        department:string:所属部门
        position:string:职位
        org_address:string:单位地址
        service_area:string:服务区域
        remark:string:备注
      rescue_material_list:array<object>:应急物资信息
        id:long:ID
        code:string:物资编号
        name:string:物资名称
        type:string:物资类型
        spec:string:规格
        unit:string:计量单位
        quantity:float:数量
        origin:string:物资来源
        parameter:string:参数
        purpose:string:用途
        store_place:strign:存放场所 地点
        price:float:单价
        total_price:float:总价
        producer:string:生产商
        useful_life:string:使用年限
        production_date:date:出厂日期
        purchase_date:date:购买日期
        maintenance_interval:string:定期保修间隔
        principal:string:负责人
        phone:string:联系方式
        org_name:string:所属单位
        remark:string:备注
      rescue_vehicle_list:array<object>:救援车辆信息
        id:long:ID
        code:string:车辆编号
        lic_no:string:车牌号
        veh_type:string:车辆类型
        driver:string:驾驶员
        driver_phone:string:驾驶员电话
        org_name:string:所属单位
        route_name:string:线路名称
        remark:string:备注
  success:bool:是否成功
  msg:string:操作提示
```

### 32.2. 应急响应处理-新增

```yaml
@post: /monitor/emergencyresponses

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:事件名称
  start_time:date:事发时间=开始时间
  place:string:事件地点
  description:string:事件描述
  preplan_name:string:预案名称
  preplan_code:string:预案编号
  preplan_type:int:预案分类
  level:int:事件等级
  rescue_plan:string:救援方案 url /html/yyyyMMdd/xxx.html
  attachs:string:附件 多个以英文 , 号隔开 /docs/yyyyMMdd/xxx.文件后缀名
  rescue_company_name:string:救援单位名称
  rescue_start_point:string:救援起点 json结构 { keyword: '贵阳市人民政府',city:'贵阳' }
  rescue_end_point:string:救援终点 json结构 { keyword: '贵阳北站',city:'贵阳' }
  remark:string:备注
  group_leader_list:array<object>:组长信息
    id:long:ID
  rescuer_list:array<object>:人员信息
    id:long:ID
  rescue_material_list:array<object>:应急物资信息
    id:long:ID
  rescue_vehicle_list:array<object>:救援车辆信息
    id:long:ID

@return:
  code:int:操作码
  data:object:返回应急响应处理信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 32.3. 应急响应处理-编辑

```yaml
@put: /monitor/emergencyresponses/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@payload:
  name:string:事件名称
  start_time:date:事发时间=开始时间
  place:string:事件地点
  description:string:事件描述
  preplan_name:string:预案名称
  preplan_code:string:预案编号
  preplan_type:int:预案分类
  level:int:事件等级
  rescue_plan:string:救援方案 url /html/yyyyMMdd/xxx.html
  attachs:string:附件 多个以英文 , 号隔开 /docs/yyyyMMdd/xxx.文件后缀名
  rescue_company_name:string:救援单位名称
  rescue_start_point:string:救援起点 json结构 { keyword: '贵阳市人民政府',city:'贵阳' }
  rescue_end_point:string:救援终点 json结构 { keyword: '贵阳北站',city:'贵阳' }
  remark:string:备注
  group_leader_list:array<object>:组长信息
    id:long:ID
  rescuer_list:array<object>:人员信息
    id:long:ID
  rescue_material_list:array<object>:应急物资信息
    id:long:ID
  rescue_vehicle_list:array<object>:救援车辆信息
    id:long:ID

@return:
  code:int:操作码
  data:object:返回应急响应处理信息
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 32.4. 应急响应处理-详情

```yaml
@get: /monitor/emergencyresponses/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回应急响应处理信息
    id:long:ID
    name:string:事件名称
    start_time:date:事发时间=开始时间
    place:string:事件地点
    description:string:事件描述
    preplan_name:string:预案名称
    preplan_code:string:预案编号
    preplan_type:int:预案分类
    level:int:事件等级
    rescue_plan:string:救援方案 url /html/yyyyMMdd/xxx.html
    attachs:string:附件 多个以英文 , 号隔开 /docs/yyyyMMdd/xxx.文件后缀名
    rescue_company_name:string:救援单位名称
    rescue_start_point:string:救援起点 json结构 { keyword: '贵阳市人民政府',city:'贵阳' }
    rescue_end_point:string:救援终点 json结构 { keyword: '贵阳北站',city:'贵阳' }
    remark:string:备注
    photos:string:执行照片 多个以英文 , 号隔开 /images/yyyyMMdd/xxx.照片后缀名
    videos:string:执行视频 多个以英文 , 号隔开 /videos/yyyyMMdd/xxx.视频后缀名
    effect:string:执行效果 /html/yyyyMMdd/xxx.html
    end_time:date:结束时间
    status:int:状态(1:待处理 5:处理中 10:待评估 15:已评估)
    generate_case:int:是否生成案例(0:未生成 1:已生成)
    info_score:int:信息采集准确度评估
    info_remark:string:信息采集准确度评估 补充
    effect_score:int:信息采集准确度评估
    effect_remark:string:信息采集准确度评估 补充
    dispatch_score:int:信息采集准确度评估
    dispatch_remark:string:信息采集准确度评估 补充
    material_score:int:信息采集准确度评估
    material_remark:string:信息采集准确度评估 补充
    evaluate_remark:string:评估时的备注
    group_leader_list:array<object>:组长信息
      id:long:ID
      name:string:姓名
      gender:string:性别(M:男 F:女 N:未知)
      staff_no:string:员工工号
      phone:string:联系电话
      org_name:string:所属单位名称
      department:string:所属部门
      position:string:职位
      org_address:string:单位地址
      service_area:string:服务区域
      remark:string:备注
    rescuer_list:array<object>:人员信息
      id:long:ID
      name:string:姓名
      gender:string:性别(M:男 F:女 N:未知)
      staff_no:string:员工工号
      phone:string:联系电话
      org_name:string:所属单位名称
      department:string:所属部门
      position:string:职位
      org_address:string:单位地址
      service_area:string:服务区域
      remark:string:备注
    rescue_material_list:array<object>:应急物资信息
      id:long:ID
      code:string:物资编号
      name:string:物资名称
      type:string:物资类型
      spec:string:规格
      unit:string:计量单位
      quantity:float:数量
      origin:string:物资来源
      parameter:string:参数
      purpose:string:用途
      store_place:strign:存放场所 地点
      price:float:单价
      total_price:float:总价
      producer:string:生产商
      useful_life:string:使用年限
      production_date:date:出厂日期
      purchase_date:date:购买日期
      maintenance_interval:string:定期保修间隔
      principal:string:负责人
      phone:string:联系方式
      org_name:string:所属单位
      remark:string:备注
    rescue_vehicle_list:array<object>:救援车辆信息
      id:long:ID
      code:string:车辆编号
      lic_no:string:车牌号
      veh_type:string:车辆类型
      driver:string:驾驶员
      driver_phone:string:驾驶员电话
      org_name:string:所属单位
      route_name:string:线路名称
      remark:string:备注
  success:bool:是否成功
  msg:string:操作提示
```

### 32.5. 应急响应处理-删除

```yaml
@delete: /monitor/emergencyresponses/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回应急响应处理
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```
### 32.6. 应急响应处理-跟进-完结事件

```yaml
@put: /monitor/emergencyresponses/follows/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@payload:
  photos:string:执行照片 多个以英文 , 号隔开 /images/yyyyMMdd/xxx.照片后缀名
  videos:string:执行视频 多个以英文 , 号隔开 /videos/yyyyMMdd/xxx.视频后缀名
  effect:string:执行效果 /html/yyyyMMdd/xxx.html
  end_time:date:结束时间

@return:
  code:int:操作码
  data:object:返回应急响应处理
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```
### 32.7. 应急响应处理-评估

```yaml
@put: /monitor/emergencyresponses/evaluates/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@payload:
  info_score:int:信息采集准确度评估
  info_remark:string:信息采集准确度评估 补充
  effect_score:int:信息采集准确度评估
  effect_remark:string:信息采集准确度评估 补充
  dispatch_score:int:信息采集准确度评估
  dispatch_remark:string:信息采集准确度评估 补充
  material_score:int:信息采集准确度评估
  material_remark:string:信息采集准确度评估 补充
  evaluate_remark:string:评估时的备注

@return:
  code:int:操作码
  data:object:返回应急响应处理
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```

### 32.8. 应急响应处理-案例生成

```yaml
@post: /monitor/emergencyresponses/generatecases/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:ID

@return:
  code:int:操作码
  data:object:返回应急响应处理
    id:long:ID
  success:bool:是否成功
  msg:string:操作提示
```
## 33. 应急信息发布

### 33.1. 应急信息发布-列表

```yaml
@get: /monitor/inforeleases/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  title:string:标题
  type:int:类型
  begin:date:开始时间
  end:date:结束时间

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:知识库信息列表
      id:long:ID
      name:string:事件名称
      type:int:事件分类
      level:int:事件级别
      content:string:发布内容
      channel:int:发布途径
  success:bool:是否成功
  msg:string:操作提示
```

### 33.2. 应急信息发布-详情

```yaml
@get: /monitor/inforeleases/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:知识库id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    name:string:事件名称
    type:int:事件分类
    level:int:事件级别
    content:string:发布内容
    channel:int:发布途径
  success:bool:是否成功
  msg:string:操作提示
```

### 33.3. 应急信息发布-新增

```yaml
@post: /monitor/inforeleases

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:事件名称
  type:int:事件分类
  level:int:事件级别
  content:string:发布内容
  channel:int:发布途径

  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:信息ID
  success:bool:是否成功
  msg:string:操作提示
```

### 33.4. 应急信息发布-修改

```yaml
@put: /monitor/inforeleases/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:站点ID

@payload:
  name:string:事件名称
  type:int:事件分类
  level:int:事件级别
  content:string:发布内容
  channel:int:发布途径
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:ID
    name:string:事件名称
    type:int:事件分类
    level:int:事件级别
    content:string:发布内容
    channel:int:发布途径
  success:bool:是否成功
  msg:string:操作提示
```

### 33.5. 应急信息发布-删除

```yaml
@delete: /monitor/inforeleases/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:信息ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:信息ID
  success:bool:是否成功
  msg:string:操作提示
```
## 34. 应急预案管理

### 34.1. 应急预案管理-列表

```yaml
@get: /monitor/emergencypreplans/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  rescue_company_name:string:单位
  preplan_type:int:预案类型
  level:int:等级

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:知识库信息列表
      id:long:应急预案ID
      name:string:名称
      code:string:编号
      preplan_type:int:预案分类
      level:int:事件等级
      rescuePlan:int:救援方案 url /html/yyyyMMdd/xxx.html
      attachs:string:附件 多个以英文 , 号隔开 /docs/yyyyMMdd/xxx.文件后缀名;
      rescue_company_name:string:救援单位名称;
      rescue_start_point:string:救援起点 json结构 { keyword: '贵阳市人民政府',city:'贵阳' };
      rescue_end_point:string:救援终点 json结构 { keyword: '贵阳北站',city:'贵阳'  };
      rescueDesc:string:救援详情
      remark:string:备注
      group_leader_list:array<object>:组长
        id:long:ID
        name:string:姓名
        org_name:string:所属部门
        phone:string:联系电话
      rescuer_list:array<object>:人员
        id:long:ID
        name:string:姓名
        org_name:string:所属部门
        phone:string:联系电话
      rescue_material_list:array<object>:应急物资
        id:long:ID
        name:string:物资名
        type:int:物资类型
      rescue_vehicle_list:array<object>:救援车辆
        id:long:ID
        lic_no:string:车牌号
        org_name:string:所属单位
        driver:string:驾驶员
        driver_phone:string:驾驶员电话
  success:bool:是否成功
  msg:string:操作提示
```

### 34.2. 应急预案管理-详情

```yaml
@get: /monitor/emergencypreplans/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:知识库id

@return:
  code:int:操作码
  data:object:返回信息
    id:long:应急预案ID
    name:string:名称
    code:string:编号
    preplan_type:int:预案分类
    level:int:事件等级
    rescuePlan:int:救援方案 url /html/yyyyMMdd/xxx.html
    attachs:string:附件 多个以英文 , 号隔开 /docs/yyyyMMdd/xxx.文件后缀名;
    rescue_company_name:string:救援单位名称;
    rescue_start_point:string:救援起点 json结构 { keyword: '贵阳市人民政府',city:'贵阳' };
    rescue_end_point:string:救援终点 json结构 { keyword: '贵阳北站',city:'贵阳'  };
    rescueDesc:string:救援详情
    remark:string:备注
    group_leader_list:array<object>:组长
      id:long:ID
      name:string:姓名
      org_name:string:所属部门
      phone:string:联系电话
    rescuer_list:array<object>:人员
      id:long:ID
      name:string:姓名
      org_name:string:所属部门
      phone:string:联系电话
    rescue_material_list:array<object>:应急物资
      id:long:ID
      name:string:物资名
      type:int:物资类型
    rescue_vehicle_list:array<object>:救援车辆
      id:long:ID
      lic_no:string:车牌号
      org_name:string:所属单位
      driver:string:驾驶员
      driver_phone:string:驾驶员电话
  success:bool:是否成功
  msg:string:操作提示
```

### 34.3. 应急预案管理-新增

```yaml
@post: /monitor/emergencypreplans

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:名称
  code:string:编号
  preplan_type:int:预案分类
  level:int:事件等级
  rescuePlan:int:救援方案 url /html/yyyyMMdd/xxx.html
  attachs:string:附件 多个以英文 , 号隔开 /docs/yyyyMMdd/xxx.文件后缀名;
  rescue_company_name:string:救援单位名称;
  rescue_start_point:string:救援起点 json结构 { keyword: '贵阳市人民政府',city:'贵阳' };
  rescue_end_point:string:救援终点 json结构 { keyword: '贵阳北站',city:'贵阳'  };
  rescueDesc:string:救援详情
  remark:string:备注
  group_leader_list:array<object>:组长
    id:long:ID
  rescuer_list:array<object>:人员
    id:long:ID
  rescue_material_list:array<object>:应急物资
    id:long:ID
  rescue_vehicle_list:array<object>:救援车辆
    id:long:ID
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:预案ID
  success:bool:是否成功
  msg:string:操作提示
```

### 34.4. 应急预案管理-修改

```yaml
@put: /monitor/emergencypreplans/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:站点ID

@payload:
  name:string:名称
  code:string:编号
  preplan_type:int:预案分类
  level:int:事件等级
  rescuePlan:int:救援方案 url /html/yyyyMMdd/xxx.html
  attachs:string:附件 多个以英文 , 号隔开 /docs/yyyyMMdd/xxx.文件后缀名;
  rescue_company_name:string:救援单位名称;
  rescue_start_point:string:救援起点 json结构 { keyword: '贵阳市人民政府',city:'贵阳' };
  rescue_end_point:string:救援终点 json结构 { keyword: '贵阳北站',city:'贵阳'  };
  rescueDesc:string:救援详情
  remark:string:备注
  group_leader_list:array<object>:组长
    id:long:ID
  rescuer_list:array<object>:人员
    id:long:ID
  rescue_material_list:array<object>:应急物资
    id:long:ID
  rescue_vehicle_list:array<object>:救援车辆
    id:long:ID
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:应急预案ID
    name:string:名称
    code:string:编号
    preplan_type:int:预案分类
    level:int:事件等级
    rescuePlan:int:救援方案 url /html/yyyyMMdd/xxx.html
    attachs:string:附件 多个以英文 , 号隔开 /docs/yyyyMMdd/xxx.文件后缀名;
    rescue_company_name:string:救援单位名称;
    rescue_start_point:string:救援起点 json结构 { keyword: '贵阳市人民政府',city:'贵阳' };
    rescue_end_point:string:救援终点 json结构 { keyword: '贵阳北站',city:'贵阳'  };
    rescueDesc:string:救援详情
    remark:string:备注
    group_leader_list:array<object>:组长
      id:long:ID
      name:string:姓名
      org_name:string:所属部门
      phone:string:联系电话
    rescuer_list:array<object>:人员
      id:long:ID
      name:string:姓名
      org_name:string:所属部门
      phone:string:联系电话
    rescue_material_list:array<object>:应急物资
      id:long:ID
      name:string:物资名
      type:int:物资类型
    rescue_vehicle_list:array<object>:救援车辆
      id:long:ID
      lic_no:string:车牌号
      org_name:string:所属单位
      driver:string:驾驶员
      driver_phone:string:驾驶员电话
  success:bool:是否成功
  msg:string:操作提示
```

### 34.5. 应急预案管理-删除

```yaml
@delete: /monitor/emergencypreplans/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:信息ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:预案ID
  success:bool:是否成功
  msg:string:操作提示
```