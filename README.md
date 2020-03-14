# 行业管理系统后端代码(IndustryManagement-Backend)



## 开发环境

1. JDK 1.8+(包含1.8)

2. Maven 3.3+

3. 安装插件Lombok
- IDEA: https://projectlombok.org/setup/intellij
- Eclipse: https://projectlombok.org/setup/eclipse

### 代码注视

File->Preferences->Editor->File and Code Templates->File Header

```java
/**
 * TODO(类的简要说明)
 * @author 这里填写开发者
 * @date ${DATE}
 */
```

## 系统模块命名

|模块名称|英文|Java包命名|
|---|---|---|
|公共模块|common|com.bms.common|
|系统管理模块|sys|com.bms.sys|
|公交行业管理系统|industry|com.bms.industry|
|公交综合运行监测与应急响应系统|monitor|com.bms.monitor|
|公交统计决策分析系统|statis|com.bms.statis|

## 错误码规范

|模块|错误码范围|
|---|------|
|common|1000-9999|
|sys|10000-19999|
|industry|20000-29999|
|monitor|30000-39999|
|statis|40000-49999|

## 权限编码规范

### 编码规范
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

### 代码规范
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


## API接口

### OSS文件-上传

`header的Content-Type必须为multipart/form-data`

```
/images  图片
/docs    文档
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

### OSS文件-下载

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

### OSS文件-删除

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


### 用户登录

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
  success:bool:是否成功
  msg:string:操作提示

```


### 公交企业管理-列表

```yaml
@get: /sys/organizations/list?page=:page&size=:size&name=:name&level=:level

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  name:string:机构名称
  level:int:机构级别

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
      operate_route:string:运营路线
      principal:string:负责人
      contact:string:联系方式
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

### 公交企业管理-新增

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

### 公交企业管理-编辑

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

### 公交企业管理-审核

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

### 公交企业管理-详情

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

### 公交企业管理-删除

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

### 菜单管理-我的菜单

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
    children:array<object>:子菜单
        name:string:名称
        path:string:路径
        parent:string:父菜单的path
        index:int:顺序
  success:bool:是否成功
  msg:string:操作提示
```

### 菜单管理-全部菜单

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
    type:int:类型(1=菜单 2=按钮)
    children:array<object>:子菜单
        name:string:名称
        path:string:路径
        parent:string:父菜单的path
        index:int:顺序
        type:int:类型(1=菜单 2=按钮)
  success:bool:是否成功
  msg:string:操作提示
```


### 角色-列表

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
### 角色-详情

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

### 角色管理-新增

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

### 角色管理-编辑

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


### 角色管理-删除

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

### 用户管理-列表

```yaml
@get: /sys/users/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  keyword:string:关键字

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:机构列表信息
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

### 用户管理-列表

```yaml
@get: /sys/users/list

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  page:int:页码
  size:int:页码大小
  keyword:string:关键字

@return:
  code:int:操作码
  data:object:返回信息
    count:int:分页总大小
    list:array<object>:机构列表信息
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

### 用户管理-详情

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
    real_name:string:用户名
    remark:string:备注
    role:object:角色信息
    status:int:用户状态
      - 0:禁用
      - 1:启用
  success:bool:是否成功
  msg:string:操作提示
```

### 用户管理-新增

```yaml
@post: /sys/users

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  account:string:账户
  passwd:string:密码
  organization:object:机构信息
    id:long:机构ID
  real_name:string:用户名
  remark:string:备注
  role:object:角色信息
    id:long:角色ID
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

### 用户管理-修改

```yaml
@put: /sys/users/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:用户id

@payload:
  account:string:账户
  organization:object:机构信息
  real_name:string:用户名
  remark:string:备注
  role:object:角色信息
  
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

### 用户管理-禁用/启用

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
### 用户管理-删除

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

### 用户管理-重置密码

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

### 日志管理-列表

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
  create_date:date:操作时间

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