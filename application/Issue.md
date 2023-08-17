# 이슈 모음

## @SpringBootTest

- `@SpringBootTest`은 `@Transactional`을 포함하지 않는다.
- `KotestConfig`를 작성하였더라도 저장된 entity를 rollback 하지 않는다. 