package com.fastcampus.java.project3.demo.domain;


import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull //@RequiredArgsConstructor에 의해 만들어진 생성자에 꼭 들어가게 만든다.
    @Column(nullable = false)
    @NotEmpty
    private String name;

    //NotEmpty, Column은 지정을 해야 null값이 들어가지 않는다.
    @NonNull
    @Min(1)//최소값을 지켜야 하므로
    private int age;

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


    //@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}) //각각의 객체가 하나씩 mapping
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Block block;



}
