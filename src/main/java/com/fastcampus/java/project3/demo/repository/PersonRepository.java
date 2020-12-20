package com.fastcampus.java.project3.demo.repository;

import com.fastcampus.java.project3.demo.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//CRUD
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByName(String name);
    //List<Person> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);//Between으로 조건문이 들어가게 된다.

    //월을 매개변수 삼아 사람을 찾는다.
    //@Query(value="select * from person where month = :month",nativeQuery = true)//Entity기반으로 query문을 수행
    @Query(value="select person from Person person where person.birthday.month = ?1")
    List<Person> findByMonthOfBirthday(int month);


    //nativeQuery문을 사용하여 query문을 내가 원하는 변수값으로 찾아볼 수 있다.
    //@Query(value="select * from person where month = :month and day = :day",nativeQuery = true)
    //List<Person> findByMonthofBirthday(@Param("month") int month, @Param("day") int day);


    //실제로 삭제된 데이터를 보기 위하여 쿼리문을 작성해 준다.
    @Query(value = "select * from Person person where person.deleted = true", nativeQuery = true)
    List<Person> findPeopleDeleted();
}
