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
| birthYear | 出生年 | 是 | int | | | 1990 |
| birthMonth | 出生月 | 是 | int | 1-12 | | 8 |
| birthDay | 出生日 | 是 | int | 1-31 | | 15 |
| birthHour | 出生时辰 | 是 | int | 1-12 | | 3 |
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
| message | 状态信息 | 是 | string | | | 操作成功 |
| data | 响应数据 | 是 | object | | | |
| data>>solarDate | 阳历日期 | 是 | string | | | 1990年8月15日 |
| data>>lunarDate | 农历日期 | 是 | string | | | 1990年6月25日 |
| data>>birthHour | 出生时辰 | 是 | int | 1-12 | | 3 |
| data>>gender | 性别 | 是 | string | 男,女 | | 男 |
| data>>age | 年龄 | 是 | int | | | 0 |
| data>>palaces | 十二宫位 | 是 | array | | | |
| data>>palaces>>name | 宫位名 | 是 | string | | | 命宫 |
| data>>palaces>>index | 宫位序号 | 是 | int | 0-11 | | 0 |
| data>>palaces>>branch | 地支 | 是 | string | | | 午 |
| data>>palaces>>stars | 星耀列表 | 是 | array | | | |
| data>>palaces>>stars>>name | 星耀名 | 是 | string | | | 太阳 |
| data>>palaces>>stars>>position | 位置 | 是 | int | 0-11 | | 0 |
| data>>palaces>>stars>>branch | 地支 | 是 | string | | | 午 |
| data>>palaces>>mutagens | 四化 | 是 | array | | | ["化禄","化科"] |
| data>>stars | 星耀列表 | 是 | array | | | |
| data>>yearStem | 年干 | 是 | string | | | 庚 |
| data>>soul | 命主 | 是 | string | | | 紫微 |
| data>>body | 身主 | 是 | string | | | 破军 |
| data>>fiveElements | 五行 | 是 | string | | | 木 |

**测试案例**
```bash
curl -X POST 'http://localhost:8080/api/v1/astrolabe/get' \
-H 'Content-Type: application/json' \
-d '{
    "birthYear": 1994,
    "birthMonth": 12,
    "birthDay": 8,
    "birthHour": 5,
    "gender": 1,
    "isLunar": false
}'
```

**响应示例**
```json
{
    "code": 200,
    "message": "操作成功",
    "data": {
        "solarDate": "1994年12月8日",
        "lunarDate": "1994年11月6日",
        "birthHour": 5,
        "gender": "男",
        "age": 0,
        "palaces": [
            {
                "name": "命宫",
                "heavenlyStem": "辛",
                "index": 0,
                "branch": "未",
                "stars": [
                    {
                        "name": "紫微",
                        "position": 0,
                        "branch": "未"
                    },
                    {
                        "name": "破军",
                        "position": 0,
                        "branch": "未"
                    }
                ],
                "mutagens": [],
                "body": false,
                "ming": true
            }
            // ... 其他宫位数据 ...
        ],
        "stars": [
            {
                "name": "紫微",
                "position": 0,
                "branch": "未"
            },
            {
                "name": "破军",
                "position": 0,
                "branch": "未"
            }
            // ... 其他星耀数据 ...
        ],
        "yearStem": "甲",
        "soul": "紫微",
        "body": "破军",
        "fiveElements": "木"
    }
}
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
| solarDate | 阳历日期 | 是 | string | | | 1990年8月15日 |
| lunarDate | 农历日期 | 是 | string | | | 1990年6月25日 |
| birthHour | 出生时辰 | 是 | int | 1-12 | | 3 |
| gender | 性别 | 是 | string | 男,女 | | 男 |
| palaces | 十二宫位 | 是 | array | | | |
| stars | 星耀列表 | 是 | array | | | |
| yearStem | 年干 | 是 | string | | | 庚 |

**响应内容**

**返回结果**
>成功 (200)
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| code | 状态码 | 是 | int | | | 200 |
| message | 状态信息 | 是 | string | | | 操作成功 |
| data | 响应数据 | 是 | object | | | |
| data>>palaceRelations | 宫位关系 | 是 | object | | | |
| data>>palaceRelations>>punishments | 相刑关系 | 是 | object | | | |
| data>>palaceRelations>>harmonies | 相合关系 | 是 | object | | | |
| data>>palaceRelations>>trineFormations | 三合关系 | 是 | object | | | |
| data>>starCombinations | 星耀组合 | 是 | object | | | |
| data>>starCombinations>>trineAndOpposition | 三方四正 | 是 | object | | | |
| data>>starCombinations>>convergence | 会合 | 是 | object | | | |
| data>>mutagenRelations | 四化关系 | 是 | object | | | |
| data>>mutagenRelations>>mutagenRelations | 四化关系 | 是 | object | | | |
| data>>mutagenRelations>>starConflicts | 星耀冲突 | 是 | object | | | |
| data>>majorPatterns | 主要格局 | 是 | array | | | ["经商有道","学术优秀"] |

**测试案例**
```bash
curl -X POST 'http://localhost:8080/api/v1/astrolabe/interpret' \
-H 'Content-Type: application/json' \
-d '{
    "solarDate": "1990年8月15日",
    "lunarDate": "1990年6月25日",
    "birthHour": 3,
    "gender": "男",
    "age": 0,
    "palaces": [
        {
            "name": "命宫",
            "index": 0,
            "branch": "午",
            "stars": [
                {
                    "name": "太阳",
                    "position": 0,
                    "branch": "午"
                }
            ],
            "mutagens": [],
            "body": false,
            "ming": true
        }
        // ... 其他宫位数据 ...
    ],
    "stars": [
        {
            "name": "太阳",
            "position": 0,
            "branch": "午"
        }
        // ... 其他星耀数据 ...
    ],
    "yearStem": "庚"
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

## 4. 婚姻感情分析

### 基本信息

**API Path**
/api/v1/astrolabe/analyze/marriage

**请求协议**
HTTP

**请求方法**
POST

**请求体**
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| astrolabe | 命盘数据 | 是 | object | | | |
| age | 当前年龄 | 是 | int | | | 25 |

**响应内容**
```json
{
    "code": 200,
    "message": "操作成功",
    "data": {
        "score": 85,
        "analysis": {
            "marriagePalace": "夫妻宫分析",
            "romanceStars": "桃花星分析",
            "timing": "婚姻时机",
            "suggestions": "感情建议"
        }
    }
}
```

## 5. 事业财运分析

### 基本信息

**API Path**
/api/v1/astrolabe/analyze/career-wealth

**请求协议**
HTTP

**请求方法**
POST

**请求体**
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| astrolabe | 命盘数据 | 是 | object | | | |
| age | 当前年龄 | 是 | int | | | 25 |

**响应内容**
```json
{
    "code": 200,
    "message": "操作成功",
    "data": {
        "career": {
            "score": 80,
            "analysis": "事业分析",
            "suggestions": "事业建议"
        },
        "wealth": {
            "score": 75,
            "analysis": "财运分析",
            "suggestions": "理财建议"
        }
    }
}
```

## 6. 健康状况分析

### 基本信息

**API Path**
/api/v1/astrolabe/analyze/health

**请求协议**
HTTP

**请求方法**
POST

**请求体**
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| astrolabe | 命盘数据 | 是 | object | | | |
| age | 当前年龄 | 是 | int | | | 25 |

**响应内容**
```json
{
    "code": 200,
    "message": "操作成功",
    "data": {
        "score": 90,
        "analysis": {
            "constitution": "体质分析",
            "potentialIssues": "潜在问题",
            "suggestions": "健康建议"
        }
    }
}
```

## 7. 学业考试分析

### 基本信息

**API Path**
/api/v1/astrolabe/analyze/study

**请求协议**
HTTP

**请求方法**
POST

**请求体**
Json
Object

| 参数名 | 说明 | 必填 | 类型 | 值可能性 | 限制 | 示例 |
|--------|------|------|------|-----------|------|------|
| astrolabe | 命盘数据 | 是 | object | | | |
| age | 当前年龄 | 是 | int | | | 25 |

**响应内容**
```json
{
    "code": 200,
    "message": "操作成功",
    "data": {
        "score": 85,
        "analysis": {
            "studyAbility": "学习能力",
            "subjects": "擅长科目",
            "suggestions": "学习建议"
        }
    }
}
```

## 错误码说明

### 系统错误
- 200: 成功
- 400: 请求参数错误
- 401: 未授权
- 403: 禁止访问
- 500: 服务器内部错误

### 业务错误
- 1001: 生日参数无效
- 1002: 命盘生成失败
- 1003: 分析计算异常
- 1004: 数据不完整
- 1005: 年龄参数无效
- 1006: 分析类型不支持

## 使用说明
1. 所有请求需要包含header: `Content-Type: application/json`
2. 日期格式统一使用: `YYYY-MM-DD`
3. 时间采用24小时制
4. 分数范围: 0-100
5. 建议先调用获取命盘接口,再进行其他分析
6. 分析接口的age参数需要与实际年龄相符 