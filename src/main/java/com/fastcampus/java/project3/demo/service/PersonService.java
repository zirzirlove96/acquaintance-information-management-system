package com.fastcampus.java.project3.demo.service;

import com.fastcampus.java.project3.demo.domain.Block;
import com.fastcampus.java.project3.demo.domain.Person;
import com.fastcampus.java.project3.demo.repository.BlockRepository;
import com.fastcampus.java.project3.demo.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service //service lombok을 꼭 써줘야 한다.
@Slf4j //Log
public class PersonService {

    //DI로 인해 자동으로 연결시켜 준다. @Autowired 한 필드는 꼭 그에 맞는 lombok annotation을 가지고 있어야 한다.
    //Repository에 @Autowired를 했다면 @Repository가 되어 있어야 함.
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BlockRepository blockRepository;


    /*
    public List<Person> getPersonExcludeBlocks() {
        List<Person> people = personRepository.findAll();

        //List<Block> blocks = blockRepository.findAll();

        //stream을 사용하고 map을 이용하여 blockNames에 이름을 가져온다.
        //List<String> blockNames = blocks.stream().map(Block::getName).collect(Collectors.toList());

        //filter는 어떤 조건에 맞는 것만 추출한다.
        //person 이름중에서 block된 이름을 제외한 나머지를 반환
        //return people.stream().filter(person->!blockNames.contains(person.getName())).collect(Collectors.toList());

        //block데이터가 없는 사람들만 가져온다 차단되지 않은 사람들만 가져온다.
        //return people.stream().filter(person->person.getBlock()==null).collect(Collectors.toList());

        List<Block> blocks = blockRepository.findAll();

        //stream을 사용하고 map을 이용하여 blockNames에 이름을 가져온다.
        List<String> blockNames = blocks.stream().map(Block::getName).collect(Collectors.toList());

        //filter는 어떤 조건에 맞는 것만 추출한다.
        //person 이름중에서 block된 이름을 제외한 나머지를 반환
        return people.stream().filter(person->!blockNames.contains(person.getName())).collect(Collectors.toList());

    }*/

    public List<Person> getPersonExcludeBlocks() {
        return personRepository.findByBlockIsNull();
    }

    @Transactional
    public Person getPerson(Long id){
        //Optional<Person> person = personRepository.findById(id);
        //JAva 7에서 제공하는 Option의 orElse를 사용
        //get을 하는 데 없으면 null값 리턴
        Person person = personRepository.findById(id).orElse(null);
        log.info("person : {}",person);

        return person;
    }


    public List<Person> getPeopleByName(String name) {
        //List<Person> result = personRepository.findAll();
        //이렇게 하면 메모리 과부하가 걸릴 가능성이 높으므로 JPA Repository에 find~라는 추상메서드를 만들어 준다.
        //return result.stream().filter(person->person.getName().equals(name)).collect(Collectors.toList());
        return personRepository.findByName(name);
    }
}
