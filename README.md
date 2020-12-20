# acquaintance-information-management-system
Spring Boot를 기반으로 프로그래밍 된 지인관리시스템

## Features
> Lombok - Getter, Setter, Constructor, Data, EqualsAndHashCode    
> JPA - Query Method   
> Mapping - Get,Post,Put,Delete   
> Refactoring - Controller, Repository, Service Refactoring..  
```
Controller : return타입이 없는 경우 assertThat or assertTrue 사용 / 리턴 타입이 있는 경우 andExpect를 사용해 준다.
             json 타입으로 Serializer를 해준다.(LocalDate 값을 입력 받기 위해)
Repository : sql파일에 입력된 값들을 활용하여 test를 한다.
Service : Mock이라는 라이브러리를 사용하여 세세하게, 빠르게 테스트를 한다.
```
