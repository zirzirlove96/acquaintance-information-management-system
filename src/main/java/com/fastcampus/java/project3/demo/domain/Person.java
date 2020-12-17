package com.fastcampus.java.project3.demo.domain;


import com.fastcampus.java.project3.demo.dto.PersonDto;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data //hashCode, Getter,Setter,ToString etc..
@Where(clause = "deleted=false") // JPA:where 를 사용하면 모든 (Repository)쿼리문에서 delete 변수를 가질 수 있게 한다.
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull //@RequiredArgsConstructor에 의해 만들어진 생성자에 꼭 들어가게 만든다.
    @Column(nullable = false)
    @NotEmpty
    private String name;

    //NotEmpty, Column은 지정을 해야 null값이 들어가지 않는다.
    /*@NonNull
    @Min(1)//최소값을 지켜야 하므로
    private int age;*/

    private String hobby;

    @NonNull
    @Column(nullable = false)
    @NotEmpty
    private String bloodType;

    private String address;

    @Embedded //Birthday를 객체화했으므로
    private Birthday birthday;

    private String job;

    @ToString.Exclude
    private String phoneNumber;

    //직접 DB에 접근하여 삭제를 하면 다시 복구할 방법이 없기 때문에
    //삭제할 것인지를 체크하는 boolean타입의 변수를 설정해 준다.
    @ColumnDefault("0")//0은 false 1은 true
    private boolean deleted;


    //@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}) //각각의 객체가 하나씩 mapping
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Block block;


    public void set(PersonDto personDto){
        if(!StringUtils.isEmpty(personDto.getName())){
            this.setName(personDto.getName());
        }//string문자열이 있는 경우 set해준다.
        if(!StringUtils.isEmpty(personDto.getAddress())){
            this.setAddress(personDto.getAddress());
        }
        if(!StringUtils.isEmpty(personDto.getBloodType())){
            this.setBloodType(personDto.getBloodType());
        }
        if(!StringUtils.isEmpty(personDto.getHobby())){
            this.setHobby(personDto.getHobby());
        }
        if(!StringUtils.isEmpty(personDto.getJob())){
            this.setJob(personDto.getJob());
        }
        if(!StringUtils.isEmpty(personDto.getPhoneNumber())){
            this.setPhoneNumber(personDto.getPhoneNumber());
        }
    }

    //생일을 통해 나이를 구해보자
    //생일을 통해 구할 수 있으므로 따로 나이라는 변수를 갖추지 않아도 된다.
    public Integer getAge() {
        if(this.birthday != null) {
            return LocalDate.now().getYear() - this.birthday.getYear() + 1;
        }
        else{
            return null;
        }//생일이 안들어간 경우 나이를 유추할 수 없을 때 null
    }

    //오늘 생일인 사람을 위해 생일이면 true, 아니면 false
    public boolean isBirthdayToday() {
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYear(),this.birthday.getMonth(),this.birthday.getDay()));
    }

}
