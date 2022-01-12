package com.kbs.datajpa.entity;

import com.kbs.datajpa.repository.DetailRepository;
import com.kbs.datajpa.repository.HeaderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class InfiniteRefTest {

    @Autowired
    private HeaderRepository headerRepository;

    @Autowired
    private DetailRepository detailRepository;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    public void before(){
        Header header1 = headerRepository.save(new Header("header1", 10));
        Header header2 = headerRepository.save(new Header("header2", 20));

        Detail detail1 = detailRepository.save(new Detail("detName1", "type1", header1));
        Detail detail2 = detailRepository.save(new Detail("detName2","type2",header1));
        Detail detail3 = detailRepository.save(new Detail("detName3","type3",header2));
    }

    @Test
    public void test(){
        System.out.println("InfiniteRefTest.test");

        Header header = headerRepository.findById(1L).get();

        System.out.println("[[[size " + header.getDetail().size());

        System.out.println("[[[header = " + header);
        System.out.println("[[[detail = " + header.getDetail());

    }
}