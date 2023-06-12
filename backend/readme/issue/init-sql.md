# init sql

Spring Boot 에서는 서버 실행시 init sql 스크립트 실행을 지원한다.
기본 경로는 classpath: schema.sql, data.sql 이다.

하지만 schema.sql 은 작성하지 않고 jpa ddl-auto 를 사용하여 테이블은 만들고 data.sql 만 사용하여 seed data 를 생성하려고 하면, 테이블이 만들어지기 전에 data.sql을 수행하여
문제가 발생한다.

아래 설정을 통해 테이블 생성 후 data.sql 스크립트를 실행하도록 한다.
sql 스크립트는 한 줄로 작성해야 하며, 멀티 라인으로 작성할 수 있는 옵션이 존재하지만 적용되지 않았다.

```yaml
sql.init.mode: always
jpa:
  defer-datasource-initialization: true
```


