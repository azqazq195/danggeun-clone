# Email Validation

### 이슈

Test 도중 `azqazq195@sss` 으로 테스트 하였을 때 통과가 된다.
Spring Validation 에서 기본제공 해주는 `@Email` Annotation 은 간단한 검사만 하는 것 같다.

### 해결

```java
public class AbstractEmailValidator<A extends Annotation> implements ConstraintValidator<A, CharSequence> {
    // ...
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }

        // cannot split email string at @ as it can be a part of quoted local part of email.
        // so we need to split at a position of last @ present in the string:
        String stringValue = value.toString();
        int splitPosition = stringValue.lastIndexOf('@');

        // need to check if
        if (splitPosition < 0) {
            return false;
        }

        String localPart = stringValue.substring(0, splitPosition);
        String domainPart = stringValue.substring(splitPosition + 1);

        if (!isValidEmailLocalPart(localPart)) {
            return false;
        }

        return DomainNameUtil.isValidEmailDomainAddress(domainPart);
    }
    // ...
}
```

`AbstractEmailValidator` 구현체를 살펴보면 첫째줄에 null 과 empty 를 허용하고 있다.\
Email 을 사용하지 않는 서비스를 위함인 것 같은데 여기서 해당 서비스에서는 Email 이 필수 값 이므로\
`@NotBlank` 어노테이션을 붙여 추가 검사를 진행한다.

또한 `@` 기준으로 분리된 앞쪽인 `localPart` 에서는 이메일에서 사용되지 않는 특수 기호등을 검사한다.

- `/*@domain.com`
- `😉@domain.com`

`domainPart` 검사인 `DomainNameUtil.isValidEmailDomainAddress(domainPart);`에 들어가보면\
여러가지 domainPart 검사를 하지만 일반적으로 쓰는 email domain 이 포함되지 않는다.

`@Email` 의 regexp 를 custom 으로 설정하여 해결하였다.\
`@field:Email(regexp = "^[\\w!#\$%&'*+/=?{|}~^-]+(?:\\.[\\w!#\$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}\$")`

참조:\
https://see-one.tistory.com/14
