package com.fastcampus.java.project3.demo.service;


import com.fastcampus.java.project3.demo.domain.Birthday;
import com.fastcampus.java.project3.demo.domain.Person;

import com.fastcampus.java.project3.demo.dto.PersonDto;
import com.fastcampus.java.project3.demo.exception.PersonNotFoundException;
import com.fastcampus.java.project3.demo.exception.RenameIsNotPermittedException;
import com.fastcampus.java.project3.demo.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//ExtendWith는 //Runwith를 대체하고 springbootTest대신 사용한다.
@ExtendWith(MockitoExtension.class)
@Transactional
class PersonServiceTest {

    //테스트의 주체가 되는 클래스
    //Autowired의 역할
    @InjectMocks
    private PersonService personService;

    //실제 빈이 아닌 가짜 빈을 만들어 대체한다.
    //PersonService에 주입시켜 준다.
    @Mock
    private PersonRepository personRepository;

    private PersonDto modifyPersonDto() {
        PersonDto personDto = PersonDto.of("martin","programming", "Seoul",LocalDate.now(),"programmer","010-1111-2222");
        return personDto;
    }


    //Mock테스트는 가짜 객체를 사용하여 테스트를 한다. Mock을 사용하면 빠르게, 디테일 하게 사용할 수 있기 때문에 사용한다.
    @Test
    void getPeopleByName() {

        //빈 껍데기인 Mock을 사용하여 검사를 해주기 때문에 DB에서 가져오는 것이 아닌
        //직접 입력해주어 테스트를 진행해야 한다.
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));
        //when...thenReturn의 역할은 Mock의 어떤 메소드와 파라미터가 매핑되는 경우, 결과값에 대해서 지정해줄 수 있음

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

    @Test
    void getPerson() {
        //if문과 같이 repository에서 가져온 값이 "martin"과 같다면
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("martin");
    }

    @Test
    void getPersonIsNull() {
        //1번이 없다면
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);

        //null로 리턴하는지 확인
        assertThat(person).isNull();
    }

    @Test
    void putPerson() {
        //PersonDto personDto = PersonDto.of("martin","programming", "Seoul",LocalDate.now(),"programmer","010-1111-2222");

        //여기에서는 mock을 사용하지 않았는데도 성공하였다.
        //그 이유는 mock은 exception or return 값이 없는 경우에는 활용하지 않아도 된다.
        //해당 메서드는 성공적으로 save되었다.
        //검증을 하지 않은 이유는 ? 실제 db에 저장하는 것이 아닌 mock이라는 가상 DB에 저장하는 것이므로 DB를 사용하여 확인할 수 없다.
        personService.save(modifyPersonDto());

        //mock에서의 확인 방법 verify
        verify(personRepository,times(1)).save(argThat(new PersonWillBeInserted()));
    }

    private static class PersonWillBeInserted implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals(person.getHobby(), "programming")
                    && equals(person.getAddress(), "Seoul")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(), "programmer")
                    && equals(person.getPhoneNumber(), "010-1111-2222");
        }

        //equals를 통해 어느 부분이 nullpointException이 발생했는지 알 수 있다.
        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }
    }

    /**modify id와 객체가 매개변수인 경우**/
    @Test
    void modifyIfPersonNotFound() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());
        //1번 person이 null값인 경우

        assertThrows(PersonNotFoundException.class,()->personService.modify(1L,modifyPersonDto()));

    }

    @Test
    void modifyIfNameIsDifferent() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("torny")));

        //assertThrows에서 RuntimeException이 발생하므로 통과한다.
        assertThrows(RenameIsNotPermittedException.class,()->personService.modify(1L,modifyPersonDto()));
    }

    @Test
    void modify() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,modifyPersonDto());

        //검증하는 단계 Person객체만 들어가면 통과한다.
        //verify(personRepository,times(1)).save(ArgumentMatchers.any(Person.class));

        //dto로 줬던 값이 모두 들어 있는 검증툴이므로 이것을 사용해 줘야 한다.
        verify(personRepository,times(1)).save(argThat(new IsPersonWillbeUpdated()));
    }

    
    //테스트를 통과할 수 잇는 객체를 custom해서 만들어 놓은 객체
    private static class IsPersonWillbeUpdated implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals(person.getHobby(), "programming")
                    && equals(person.getAddress(), "Seoul")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(), "programmer")
                    && equals(person.getPhoneNumber(), "010-1111-2222");
        }

        //equals를 통해 어느 부분이 nullpointException이 발생했는지 알 수 있다.
        private boolean equals(Object actual, Object expected) {
            return expected.equals(actual);
        }
    }

    /**이름에 대한 변경**/
    @Test
    void modifyByNamePersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        //어떤 이름값을 주더라도 오류가 발생한다. empty이르모
        assertThrows(PersonNotFoundException.class,()->personService.modify(1L,"daniel"));
    }

    @Test
    void modifyByName() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,"daniel");

        //ArgumentMatcher로 등록한 값이 맞는지 검증하는 단계
        verify(personRepository).save(argThat(new IsNameWillBeUpdated()));
    }

    private static class IsNameWillBeUpdated implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("daniel");
        }
    }

    /**삭제 대한 테스트 메서드**/
    @Test
    void deleteByPersonNotFound() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class,()->personService.delete(1L));
    }

    @Test
    void delete() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.delete(1L);

        //person이 삭제되었는지 확인
        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeDeleted()));
    }

    private static class IsPersonWillBeDeleted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.isDeleted();//isDeleted가 boolean타입이므로
        }
    }
}