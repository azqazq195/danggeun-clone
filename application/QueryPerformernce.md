# 쿼리 성능 개선

100만개의 Post를

```http request
POST /auth/sign-up HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 175
Host: localhost:8080

{
  "email" : "moseoh@danggeun.com",
  "password" : "password",
  "nickname" : "nickname",
  "phone" : "01012345678",
  "profileUrl" : "url",
  "regionIds" : [ 1, 2 ]
}
```

<details>
<summary>평균 12.62 s</summary>

- 13.47s
- 12.08s
- 12.65s
- 12.31s

</details>
