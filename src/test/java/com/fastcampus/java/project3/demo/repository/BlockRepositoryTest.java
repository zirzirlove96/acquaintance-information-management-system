package com.fastcampus.java.project3.demo.repository;

import com.fastcampus.java.project3.demo.domain.Block;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class BlockRepositoryTest {

    @Autowired
    private BlockRepository blockRepository;


    @Test
    public void crud() {
        Block block = new Block();
        block.setName("martin");
        block.setReason("dd");
        block.setStartDate(LocalDate.of(1996,2,22));
        block.setEndDate(LocalDate.now());

        blockRepository.save(block);

        List<Block> blocks = blockRepository.findAll();

        //Test
        assertThat(blocks.size()).isEqualTo(3);
        //assertThat(blocks.get(0).getName()).isEqualTo("martin");
        //assertThat(blocks.get(1).getName()).isEqualTo("jiyoung");
        //assertThat(blocks.get(2).getName()).isEqualTo("lilly");
    }

}