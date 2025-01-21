# 紫微斗数系统数据库设计

## 表结构设计

### 1. 用户表(t_user)

存储系统用户信息。

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | 20 | 否 | | 主键ID |
| username | varchar | 50 | 否 | | 用户名 |
| password | varchar | 100 | 否 | | 密码(加密) |
| nickname | varchar | 50 | 是 | | 昵称 |
| gender | tinyint | 4 | 是 | 0 | 性别(0-未知,1-男,2-女) |
| birth_date | datetime | | 是 | | 出生日期时间 |
| is_lunar | tinyint | 4 | 否 | 0 | 是否农历(0-否,1-是) |
| create_time | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_time | datetime | | 否 | CURRENT_TIMESTAMP | 更新时间 |
| status | tinyint | 4 | 否 | 1 | 状态(0-禁用,1-启用) |

### 2. 命盘表(t_astrolabe)

存储用户的命盘数据。

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | 20 | 否 | | 主键ID |
| user_id | bigint | 20 | 否 | | 用户ID |
| solar_date | date | | 否 | | 阳历日期 |
| lunar_date | varchar | 50 | 否 | | 农历日期 |
| birth_hour | int | 11 | 否 | | 出生时辰(1-12) |
| gender | tinyint | 4 | 否 | | 性别(1-男,2-女) |
| data | text | | 否 | | 命盘JSON数据 |
| create_time | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_time | datetime | | 否 | CURRENT_TIMESTAMP | 更新时间 |

### 3. 运限记录表(t_horoscope)

存储用户查询的运限记录。

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | 20 | 否 | | 主键ID |
| astrolabe_id | bigint | 20 | 否 | | 命盘ID |
| age | int | 11 | 否 | | 年龄 |
| data | text | | 否 | | 运限JSON数据 |
| create_time | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |

### 4. 星耀组合表(t_star_combination)

存储预定义的星耀组合规则。

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | 20 | 否 | | 主键ID |
| name | varchar | 100 | 否 | | 组合名称 |
| type | varchar | 50 | 否 | | 组合类型(三方四正/会合) |
| stars | varchar | 255 | 否 | | 星耀列表(逗号分隔) |
| description | varchar | 500 | 是 | | 组合说明 |
| create_time | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_time | datetime | | 否 | CURRENT_TIMESTAMP | 更新时间 |

### 5. 宫位关系表(t_palace_relation)

存储预定义的宫位关系规则。

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | bigint | 20 | 否 | | 主键ID |
| name | varchar | 100 | 否 | | 关系名称 |
| type | varchar | 50 | 否 | | 关系类型(冲克/合化/三合) |
| palaces | varchar | 255 | 否 | | 宫位列表(逗号分隔) |
| description | varchar | 500 | 是 | | 关系说明 |
| create_time | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_time | datetime | | 否 | CURRENT_TIMESTAMP | 更新时间 |

## 索引设计

### t_user
- 主键索引: `id`
- 唯一索引: `username`
- 普通索引: `birth_date`

### t_astrolabe
- 主键索引: `id`
- 外键索引: `user_id`
- 普通索引: `solar_date`, `lunar_date`

### t_horoscope
- 主键索引: `id`
- 外键索引: `astrolabe_id`
- 联合索引: `(astrolabe_id, age)`

### t_star_combination
- 主键索引: `id`
- 普通索引: `type`

### t_palace_relation
- 主键索引: `id`
- 普通索引: `type`

## 关联关系

1. 用户(t_user) 1:N 命盘(t_astrolabe)
2. 命盘(t_astrolabe) 1:N 运限记录(t_horoscope)

## 数据字典

### 性别(gender)
- 0: 未知
- 1: 男
- 2: 女

### 状态(status)
- 0: 禁用
- 1: 启用

### 是否农历(is_lunar)
- 0: 否
- 1: 是

### 组合类型(type in t_star_combination)
- trine: 三方
- opposition: 四正
- convergence: 会合

### 关系类型(type in t_palace_relation)
- punishment: 冲克
- harmony: 合化
- trine: 三合

## 注意事项

1. 所有表都应该使用InnoDB存储引擎
2. 字符集统一使用utf8mb4
3. 时间字段统一使用datetime类型
4. JSON数据存储在text字段中
5. 需要定期清理运限记录表的历史数据 