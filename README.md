# 行业管理系统后端代码(IndustryManagement-Backend)



## 开发环境

1. JDK 1.8+(包含1.8)

2. Maven 3.3+

3. 安装插件Lombok
- IDEA: https://projectlombok.org/setup/intellij
- Eclipse: https://projectlombok.org/setup/eclipse

## 系统模块命名

|模块名称|英文|Java包命名|
|---|---|---|
|公公模块|common|com.bms.common|
|系统管理模块|sys	com.bms.sys|
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


## API接口

### 用户登录

```yaml
@post: /sys/users/login

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
  data:object:返回机构信息
    id:long:机构ID
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

### 菜单管理-新增

```yaml
@post: /sys/menus

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@payload:
  name:string:菜单名称
  icon:string:图标
  path:string:路径
  parent:object:父菜单信息
    id:long:菜单ID
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:菜单ID
  success:bool:是否成功
  msg:string:操作提示
```

### 菜单管理-编辑

```yaml
@put: /sys/menus/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:菜单ID

@payload:
  name:string:菜单名称
  icon:string:图标
  path:string:路径
  parent:object:父菜单信息
    id:long:菜单ID
  
@return:
  code:int:操作码
  data:object:返回信息
    id:long:菜单ID
  success:bool:是否成功
  msg:string:操作提示
```


### 菜单管理-删除

```yaml
@delete: /sys/menus/:id

@header:
  X-User-Agent:手机信息(必须)
  Authorization:token令牌

@params:
  id:long:菜单ID

@return:
  code:int:操作码
  data:object:返回信息
    id:long:菜单ID
  success:bool:是否成功
  msg:string:操作提示
```