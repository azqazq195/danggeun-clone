# PasswordConfig

## BCryptPasswordEncoder

BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 인코딩 방식 중 하나로, BCrypt 해싱 알고리즘을 사용합니다.

## 특징

- 솔트(Salt) 사용: 각각의 비밀번호에 대해 고유한 솔트 값을 사용하여 해싱합니다. 동일한 비밀번호라도 솔트 값이 다르면 다른 해시 값이 생성됩니다. 이로 인해 레인보우 테이블 공격을 방어할 수 있습니다.

- 계산 복잡성: BCrypt는 CPU 집중적인 작업으로 설계되었으며, 공격자가 빠르게 해시 값을 추측하거나 계산하는 것을 방해합니다. 스트레치 팩터(strength factor)를 설정하여 해시 계산의 복잡성을
  조절할
  수 있으며, 기본값은 10입니다.

- 동일한 입력, 다른 출력: 동일한 비밀번호에 대해 다른 솔트 값을 사용하면 다른 해시 값이 생성됩니다. 이로 인해 사용자 간에 같은 비밀번호를 사용하더라도 해시 값이 달라집니다.

- 저장 형식: 생성된 해시 값은 60자로 구성되며, 알고리즘 정보, 스트레치 팩터, 솔트 값, 실제 해시 값이 함께 저장됩니다.

- Spring Security 호환성: Spring Security와 원활하게 통합되어 사용할 수 있으며, 다른 인코딩 방식으로 쉽게 전환할 수 있습니다.

## 요약

- BCryptPasswordEncoder는 비밀번호의 안전한 저장과 관리를 위해 설계된 Spring Security의 비밀번호 인코더이다.
- 솔트, 계산 복잡성 조절, 동일 입력에 대한 다른 출력 등의 특징이 있다.

## 저장 프로세스

1. 솔트 생성
    - 무작위 문자열인 솔트를 생성한다.
2. 비밀번호 해싱
    - 비밀번호와 생성한 솔트를 결합하여 해시 함수를 통과 시킨다.
3. 해시 값 저장
    - 데이터 베이스에 저장

## 검증 프로세스

1. 해시 값 분석
    - 해시 값은 알고리즘 정보, 스트레치 팩터, 솔트 값, 실제 해시 값으로 구성된다.
    - 저장된 해시 값에서 솔트 값을 추출한다.
2. 솔트 사용 해싱
    - 사용자가 입력한 비밀번호와 추출한 솔트 값을 사용하여 새로운 해시 값을 계산한다.
3. 해시 값 비교
    - 새로 계산한 해시 값과 저장된 해시 값이 동일한지 비교한다.

## 설정

빈 등록:

```kotlin
@Bean
fun passwordEncoder() = BCryptPasswordEncoder()
```

비밀번호 인코딩:

```kotlin
val encodedPassword = passwordEncoder.encode(rawPassword)
```

비밀번호 검증:

```kotlin
val isMatch = passwordEncoder.matches(rawPassword, encodedPassword)
```

## 추가

### 저장된 해시 값에 정보들이 다 들어있는데 복원은 불가능 한가?

- 단방향 해시 함수는 복호화가 불가능
- 대신 동일한 password 값이면 동일한 digest를 얻을 수 있음
- 이 때문에 bcrypt 는 salt와 key stretching을 사용한다.
    - Salting: 입력한 비밀번호에 임의의 문자열을 합친다.
    - KeyStretching: Salting 및 해싱을 여러번 반복하게 한다. (해커의 무작위 대입 대비)

### 참조

https://st-lab.tistory.com/100
