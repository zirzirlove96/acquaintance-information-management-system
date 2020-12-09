package com.fastcampus.java.project3.demo.domain;


import lombok.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor //Service에서 인자없는 생성자를 사용하므로
@Data
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull //이름으로 차단을 해야하므로
    private String name;

    private String reason;

    private LocalDate startDate;

    private LocalDate endDate;

}
