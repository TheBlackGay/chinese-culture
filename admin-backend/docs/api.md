# 紫微斗数系统API文档

## 1. 获取命盘

### 基本信息

**API Path**
/api/v1/astrolabe/get

**请求协议**
HTTP

**请求方法**
POST

**相关人员**
负责人: 钟佳峰
创建人: 钟佳峰
最后编辑人: 钟佳峰

**请求体**
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| birthDateTime | 出生日期时间 | 是 | string | | yyyyMMddHHmmss | 19900101120000 |
| gender | 性别 | 是 | int | 1-男,2-女 | | 1 |
| isLunar | 是否农历 | 否 | boolean | | | false |

**响应内容**

**返回结果**
>成功 (200)
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| code | 状态码 | 是 | int | | | 200 |
| message | 状态信息 | 是 | string | | | success |
| data | 响应数据 | 是 | object | | | |
| data>>solarDate | 阳历日期 | 是 | string | | | 1990-01-01 |
| data>>lunarDate | 农历日期 | 是 | string | | | 己巳年十二月初五 |
| data>>gender | 性别 | 是 | int | 1-男,2-女 | | 1 |
| data>>age | 年龄 | 是 | int | | | 33 |
| data>>palaces | 十二宫位 | 是 | array | | | |
| data>>palaces>>name | 宫位名 | 是 | string | | | 命宫 |
| data>>palaces>>branch | 地支 | 是 | string | | | 寅 |
| data>>palaces>>stars | 星耀列表 | 是 | array | | | |
| data>>palaces>>stars>>name | 星耀名 | 是 | string | | | 紫微 |
| data>>palaces>>stars>>type | 星耀类型 | 是 | string | | | 主星 |
| data>>palaces>>stars>>brightness | 亮度 | 是 | int | 1-4 | | 1 |
| data>>palaces>>mutagens | 四化 | 是 | array | | | ["化科"] |
| data>>horoscopeData | 运限数据 | 是 | object | | | |
| data>>horoscopeData>>decadal | 大限 | 是 | object | | | |
| data>>horoscopeData>>decadal>>index | 序号 | 是 | int | | | 1 |
| data>>horoscopeData>>decadal>>name | 名称 | 是 | string | | | 戊寅 |
| data>>horoscopeData>>decadal>>stars | 星耀 | 是 | array | | | |
| data>>horoscopeData>>yearly | 流年 | 是 | object | | | |
| data>>horoscopeData>>monthly | 流月 | 是 | object | | | |
| data>>horoscopeData>>daily | 流日 | 是 | object | | | |
| data>>horoscopeData>>hourly | 流时 | 是 | object | | | |

**测试案例**
```bash
curl -X POST 'http://localhost:8080/api/v1/astrolabe/get' \
-H 'Content-Type: application/json' \
-d '{
    "birthDateTime": "19900101120000",
    "gender": 1,
    "isLunar": false
}'
```

## 2. 解析命盘

### 基本信息

**API Path**
/api/v1/astrolabe/interpret

**请求协议**
HTTP

**请求方法**
POST

**相关人员**
负责人: 钟佳峰
创建人: 钟佳峰
最后编辑人: 钟佳峰

**请求体**
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| astrolabe | 命盘数据 | 是 | object | | | |

**响应内容**

**返回结果**
>成功 (200)
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| code | 状态码 | 是 | int | | | 200 |
| message | 状态信息 | 是 | string | | | success |
| data | 响应数据 | 是 | object | | | |
| data>>palaceRelations | 宫位关系 | 是 | object | | | |
| data>>palaceRelations>>punishments | 冲克关系 | 是 | array | | | ["命宫冲克财帛宫"] |
| data>>palaceRelations>>harmonies | 合化关系 | 是 | array | | | ["命宫与官禄宫合化"] |
| data>>palaceRelations>>trineFormations | 三合关系 | 是 | array | | | ["命宫、财帛宫、官禄宫三合"] |
| data>>starCombinations | 星耀组合 | 是 | object | | | |
| data>>starCombinations>>trineAndOpposition | 三方四正 | 是 | array | | | ["紫微天府朱雀组合"] |
| data>>starCombinations>>convergence | 星耀会合 | 是 | array | | | ["紫微天府同宫"] |
| data>>mutagenRelations | 四化关系 | 是 | object | | | |
| data>>mutagenRelations>>mutagenRelations | 四化关系列表 | 是 | array | | | ["紫微化科与天府化权相会"] |
| data>>mutagenRelations>>starConflicts | 星耀冲突 | 是 | array | | | ["紫微与天府相冲"] |
| data>>majorPatterns | 主要格局 | 是 | array | | | ["紫微命格", "财帛格"] |

**测试案例**
```bash
curl -X POST 'http://localhost:8080/api/v1/astrolabe/interpret' \
-H 'Content-Type: application/json' \
-d '{
    "astrolabe": {
        "solarDate": "19900101",
        "lunarDate": "己巳年十二月初五",
        "gender": 1,
        "age": 33,
        "palaces": [
            {
                "name": "命宫",
                "branch": "寅",
                "stars": [
                    {
                        "name": "紫微",
                        "type": "主星",
                        "brightness": 1
                    }
                ],
                "mutagens": ["化科"]
            }
        ]
    }
}'
```

## 3. 批量获取运限

### 基本信息

**API Path**
/api/v1/astrolabe/horoscope/batch

**请求协议**
HTTP

**请求方法**
POST

**相关人员**
负责人: 钟佳峰
创建人: 钟佳峰
最后编辑人: 钟佳峰

**请求体**
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| astrolabe | 命盘数据 | 是 | object | | | |
| ages | 年龄列表 | 是 | array | | 1-100个年龄 | [25,26,27] |

**响应内容**

**返回结果**
>成功 (200)
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| code | 状态码 | 是 | int | | | 200 |
| message | 状态信息 | 是 | string | | | success |
| data | 响应数据 | 是 | object | | | |
| data>>25 | 25岁运限数据 | 是 | object | | | |
| data>>25>>decadal | 大限 | 是 | object | | | |
| data>>25>>yearly | 流年 | 是 | object | | | |
| data>>25>>monthly | 流月 | 是 | object | | | |
| data>>26 | 26岁运限数据 | 是 | object | | | |
| data>>27 | 27岁运限数据 | 是 | object | | | |

**测试案例**
```bash
curl -X POST 'http://localhost:8080/api/v1/astrolabe/horoscope/batch' \
-H 'Content-Type: application/json' \
-d '{
    "astrolabe": {
        "solarDate": "19900101",
        "lunarDate": "己巳年十二月初五",
        "gender": 1,
        "age": 33,
        "palaces": []
    },
    "ages": [25, 26, 27]
}'
```

## 4. 分析星耀组合

### 基本信息

**API Path**
/api/v1/astrolabe/star-combinations

**请求协议**
HTTP

**请求方法**
POST

**相关人员**
负责人: 钟佳峰
创建人: 钟佳峰
最后编辑人: 钟佳峰

**请求体**
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| astrolabe | 命盘数据 | 是 | object | | | |
| includeTrineAndOpposition | 是否包含三方四正 | 否 | boolean | | | true |
| includeConvergence | 是否包含星耀会合 | 否 | boolean | | | true |

**响应内容**

**返回结果**
>成功 (200)
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| code | 状态码 | 是 | int | | | 200 |
| message | 状态信息 | 是 | string | | | success |
| data | 响应数据 | 是 | object | | | |
| data>>trine | 三方组合 | 是 | array | | | ["紫微天府同梁组合"] |
| data>>opposition | 四正组合 | 是 | array | | | ["紫微天府对冲"] |
| data>>convergence | 会合组合 | 是 | array | | | ["紫微天府同宫"] |

**测试案例**
```bash
curl -X POST 'http://localhost:8080/api/v1/astrolabe/star-combinations' \
-H 'Content-Type: application/json' \
-d '{
    "astrolabe": {
        "solarDate": "19900101",
        "lunarDate": "己巳年十二月初五",
        "gender": 1,
        "age": 33,
        "palaces": []
    },
    "includeTrineAndOpposition": true,
    "includeConvergence": true
}'
```

## 5. 分析宫位关系

### 基本信息

**API Path**
/api/v1/astrolabe/palace-relations

**请求协议**
HTTP

**请求方法**
POST

**相关人员**
负责人: 钟佳峰
创建人: 钟佳峰
最后编辑人: 钟佳峰

**请求体**
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| astrolabe | 命盘数据 | 是 | object | | | |
| includePunishments | 是否包含冲克关系 | 否 | boolean | | | true |
| includeHarmonies | 是否包含合化关系 | 否 | boolean | | | true |
| includeTrineFormations | 是否包含三合关系 | 否 | boolean | | | true |

**响应内容**

**返回结果**
>成功 (200)
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| code | 状态码 | 是 | int | | | 200 |
| message | 状态信息 | 是 | string | | | success |
| data | 响应数据 | 是 | object | | | |
| data>>punishments | 冲克关系 | 是 | array | | | ["命宫冲克财帛宫"] |
| data>>harmonies | 合化关系 | 是 | array | | | ["命宫与官禄宫合化"] |
| data>>trineFormations | 三合关系 | 是 | array | | | ["命宫、财帛宫、官禄宫三合"] |

**测试案例**
```bash
curl -X POST 'http://localhost:8080/api/v1/astrolabe/palace-relations' \
-H 'Content-Type: application/json' \
-d '{
    "astrolabe": {
        "solarDate": "19900101",
        "lunarDate": "己巳年十二月初五",
        "gender": 1,
        "age": 33,
        "palaces": []
    },
    "includePunishments": true,
    "includeHarmonies": true,
    "includeTrineFormations": true
}'
```

## 错误码说明

### 系统级错误码
| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

### 业务级错误码
| 错误码 | 说明 |
|--------|------|
| 1001 | 命盘计算错误 |
| 1002 | 运限计算错误 |
| 1003 | 星耀组合分析错误 |
| 1004 | 宫位关系分析错误 |
| 1005 | 出生时间错误 |

## 注意事项

1. 所有POST请求必须设置Content-Type为application/json
2. 日期时间格式统一使用yyyyMMddHHmmss格式
3. 批量查询运限时,年龄列表大小限制在1-100之间
4. 建议使用HTTPS协议进行请求
5. 接口调用频率限制:
   - 单个IP每秒最多请求10次
   - 单个用户每天最多请求1000次 