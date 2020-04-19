# SpringBoot JPA Demo

SpringBoot 2.2.5.RELEASE

Spring Data JPA

并且扩展了Spring Data JPA复杂SQL查询，模仿MyBatis的Mapper，解决Spring Data JPA复杂条件的SQL的难题.


## 1. 开发环境

1. JDK 1.8+(包含1.8)

2. Maven 3.3+

3. 安装插件Lombok
- IDEA: https://projectlombok.org/setup/intellij
- Eclipse: https://projectlombok.org/setup/eclipse

### 1.1. 代码注视

- IDEA: File->Preferences->Editor->File and Code Templates->File Header

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
|公共模块|core|com.springboot.jpa.demo.core|
|系统管理模块|sys|com.springboot.jpa.demo.sys|
|模块1|module1|com.springboot.jpa.demo.module1|
|模块2|module2|com.springboot.jpa.demo.module2|
|模块3|module3|com.springboot.jpa.demo.module3|

## 3. 错误码规范

|模块|错误码范围|
|---|------|
|common|1000-9999|
|sys|10000-19999|
|module1|20000-29999|
|module2|30000-39999|
|module3|40000-49999|

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
具体参考 `com.springboot.jpa.demo.sys.controller.UserController`
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

```yaml
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
    id:long:菜单ID
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
    id:long:菜单ID
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
      module:string:模块
      func_name:string:操作名称
      params:string:参数
      ip:string:IP
      create_date:date:操作时间

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
MENU_TYPE:int:菜单类型

```