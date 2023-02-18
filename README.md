# unionfly

## 需求
Spring Framework & mongodb 實作以下功能
## 功能
1. 實作一個登入REST API，當帳號密碼與DB的一致時，回傳JWT，不一致時，回傳錯誤訊息
2. 實作一個個人資料 REST API，當JWT可用時，回傳用戶的顯示名稱，不可用時，回傳錯誤訊息

----
## 結果
### 1. 登入
實作一個登入REST API，當帳號密碼與DB的一致時，回傳JWT，不一致時，回傳錯誤訊息
#### Request URL:
{{server_url}}/api/user/auth/login
#### Request Method:
POST
#### Request :
```
{
    "name":"barney",
    "password":"123456"
}
```

### 2. 個人資料
實作一個個人資料 REST API，當JWT可用時，回傳用戶的顯示名稱，不可用時，回傳錯誤訊息
#### Request URL:
{{server_url}}/api/user/name
#### Request Method:
GET

