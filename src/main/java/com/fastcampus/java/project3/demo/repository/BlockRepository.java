package com.fastcampus.java.project3.demo.repository;

import com.fastcampus.java.project3.demo.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
}
