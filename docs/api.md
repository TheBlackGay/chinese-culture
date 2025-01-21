# 紫微斗数系统API文档

## 1. 基础接口

### 1.1 获取命盘
GET /api/v1/astrolabe/get

**请求参数**
```json
{
    "birthYear": 1990,
    "birthMonth": 8,
    "birthDay": 15,
    "birthHour": 3,
    "gender": 1,
    "isLunar": false
}
```

**响应结果**
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "solarDate": "1990年8月15日",
        "lunarDate": "1990年6月25日",
        "birthHour": 3,
        "gender": "男",
        "age": 33,
        "palaces": [],
        "stars": [],
        "yearStem": "庚",
        "soul": "紫微",
        "body": "破军",
        "fiveElements": "木"
    }
}
```

## 2. 分析接口

### 2.1 流月流日运势
POST /api/v1/astrolabe/fortune/monthly-daily

**请求参数**
```json
{
    "astrolabe": {
        "solarDate": "1990年8月15日",
        "lunarDate": "1990年6月25日",
        "birthHour": 3,
        "gender": "男",
        "palaces": []
    },
    "month": 8,
    "day": 15
}
```

**响应结果**
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "monthly": {
            "flow": "寅",
            "palace": "命宫",
            "score": 85,
            "description": "本月运势极佳",
            "suggestion": "宜把握机会，积极进取"
        },
        "daily": {
            "flow": "卯",
            "palace": "财帛",
            "score": 75,
            "description": "今日运势良好",
            "suggestion": "宜理财投资，把握机会"
        }
    }
}
```

### 2.2 婚姻感情分析
POST /api/v1/astrolabe/analyze/marriage

**请求参数**
```json
{
    "astrolabe": {
        "solarDate": "1990年8月15日",
        "lunarDate": "1990年6月25日",
        "birthHour": 3,
        "gender": "男",
        "palaces": []
    },
    "age": 33
}
```

**响应结果**
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "marriagePalace": {
            "name": "夫妻",
            "stars": []
        },
        "strength": 85,
        "romance": {
            "score": 75,
            "description": "桃花运旺盛"
        },
        "timing": {
            "bestAge": 28,
            "suggestion": "建议把握机会"
        },
        "suggestion": "婚姻基础良好，注意维护感情"
    }
}
```

### 2.3 事业财运分析
POST /api/v1/astrolabe/analyze/career-wealth

**请求参数**
```json
{
    "astrolabe": {
        "solarDate": "1990年8月15日",
        "lunarDate": "1990年6月25日",
        "birthHour": 3,
        "gender": "男",
        "palaces": []
    },
    "age": 33
}
```

**响应结果**
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "career": {
            "score": 80,
            "suitableIndustries": ["管理", "金融"],
            "description": "事业运势极佳"
        },
        "wealth": {
            "score": 75,
            "sources": ["工作收入", "投资收益"],
            "description": "财运良好"
        },
        "timing": {
            "peakAge": 35,
            "suggestion": "正处于上升期"
        },
        "suggestion": "事业发展潜力大，建议把握机会"
    }
}
```

### 2.4 健康状况分析
POST /api/v1/astrolabe/analyze/health

**请求参数**
```json
{
    "astrolabe": {
        "solarDate": "1990年8月15日",
        "lunarDate": "1990年6月25日",
        "birthHour": 3,
        "gender": "男",
        "palaces": []
    },
    "age": 33
}
```

**响应结果**
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "score": 85,
        "potentialDiseases": ["神经系统", "消化系统"],
        "constitution": {
            "strengths": ["体质阳刚", "精力充沛"],
            "weaknesses": ["易有炎症"]
        },
        "suggestion": "体质较好，建议保持运动习惯"
    }
}
```

### 2.5 学业考试分析
POST /api/v1/astrolabe/analyze/study

**请求参数**
```json
{
    "astrolabe": {
        "solarDate": "1990年8月15日",
        "lunarDate": "1990年6月25日",
        "birthHour": 3,
        "gender": "男",
        "palaces": []
    },
    "age": 18
}
```

**响应结果**
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "score": 85,
        "strongSubjects": ["数学", "物理"],
        "characteristics": {
            "strengths": ["思维敏捷", "记忆力强"],
            "weaknesses": ["注意力易分散"]
        },
        "suggestion": "学习天赋优异，建议参加竞赛"
    }
}
```

## 3. 错误码说明

### 3.1 系统错误码
| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

### 3.2 业务错误码
| 错误码 | 说明 |
|--------|------|
| 1001 | 命盘计算错误 |
| 1002 | 运限计算错误 |
| 1003 | 星耀组合分析错误 |
| 1004 | 宫位关系分析错误 |
| 1005 | 出生时间错误 |
| 1006 | 流月流日计算错误 |
| 1007 | 婚姻分析错误 |
| 1008 | 事业财运分析错误 |
| 1009 | 健康分析错误 |
| 1010 | 学业分析错误 |

## 4. 注意事项

1. 所有POST请求必须设置Content-Type为application/json
2. 日期时间格式统一使用yyyy年MM月dd日格式
3. 性别参数：1-男，2-女
4. 时辰参数：1-12，对应子时到亥时
5. 年龄参数：0-100
6. API调用频率限制：
   - 单个IP每秒最多请求10次
   - 单个用户每天最多请求1000次 