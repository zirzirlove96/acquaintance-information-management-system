package com.fastcampus.java.project3.demo.domain;


import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embeddable;
import java.time.LocalDate;

import jdk.vm.ci.meta.Local;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Birthday {
    //int보다는 Integer를 사용하여 null값이 들어갈 수 있게 한다.
    private Integer year;
    private Integer month;
    private Integer day;

    private Birthday(LocalDate birthday){
        this.year=birthday.getYear();
        this.month=birthday.getMonthValue();
        this.day= birthday.getDayOfMonth();
    }

    //static을 이용하여 값을 저장
    public static Birthday of(LocalDate birthday){
        return new Birthday(birthday);
    }

}
