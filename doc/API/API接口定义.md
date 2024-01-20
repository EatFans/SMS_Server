# API 接口定义

## 协议规定
* 请求数据格式：JSON
* 响应数据格式：JSON
* 字符编码：UTF-8

*****

## 用户登录接口定义
**1、** 请求url： http://服务器地址/v1/login

**2、** 请求方式：POST

**3、** 请求体：
```
{
    "action": "userLogin",
    "key": "fdafdafdafdasfderqwrew"
    "data": {
        "name": "eatfan0921",
        "password": "123456",
        "token": "321321"
    }
}

```
**4、** 响应