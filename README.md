- 자주 쓰는 단축어
  - Shift + Art + 화살표 위아래 : 줄바꾸기
  - function + D : 한줄 복사
  - function + L : 코드 정렬

<hr>

- thyleaf 문법을 쓰기 위한 코드
  - controller에서 회원가입 등 페이지 요청 메서드 안에 매개변수에 Model을 담고, model.addAttribute("키값", 담을 DTO())를 해줘야함.
  - document 위에 html 아래 코드로 바꾸기
  - html lang="en" xmlns:th="http://www.thymeleaf.org"
  - form 태그 옆에 th:object="${controlelr에서 정한 model 키}"
  - input 태그 안에 name="" 대신 th:field="*{dto 필드 이름}"
  - 위 문법을 사용하면 id와 name을 자동으로 만들어줌.

<hr>

- 앞으로는 DTO를 분리하면서 써야함 (회원가입용, 로그인용, 업데이트용 등)

<hr>

notnull 같은 기능을 js대신 쓸 수 있게 해주는 dependency 추가
build.gradle에 implementation 'org.springframework.boot:spring-boot-starter-validation' <br>

1. DTO에서 @NotBlank 줄 곳에 붙이기
2. Controller에서 정보를 받는 곳(@Model 앞)에 @Validated 붙이기, 매개변수로 BindingResult 추가 변수이름은 bindingResult
3. 원래 리턴 위에 if(bindingResult.hasErrors()) 내용 추가
4. if의 역할 -> 에러가 있을 시 if문 작동
5. 정보를 받는 html 부분에 <p th:if="${#fields.hasErrors('dto필드 이름')}" th:errors="*{dto 필드 이름}"></p> 추가
6. 메세지를 지정한 NotBlank -> 지정한 메세지 출력
7. 메세지를 지정안한 NotBlank -> 공백일 수 없습니다 출력