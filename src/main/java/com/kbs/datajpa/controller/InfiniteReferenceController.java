package com.kbs.datajpa.controller;

import com.kbs.datajpa.entity.Detail;
import com.kbs.datajpa.entity.Header;
import com.kbs.datajpa.repository.DetailRepository;
import com.kbs.datajpa.repository.HeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RestController
public class InfiniteReferenceController {

    @Autowired
    private HeaderRepository headerRepository;

    @Autowired
    private DetailRepository detailRepository;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void setData() {

        Header header1 = headerRepository.save(new Header("header1", 10));
        Header header2 = headerRepository.save(new Header("header2", 20));

        Detail detail1 = detailRepository.save(new Detail("detName1", "type1", header1));
        Detail detail2 = detailRepository.save(new Detail("detName2","type2",header1));
        Detail detail3 = detailRepository.save(new Detail("detName3","type3",header2));

//        detail1.setHeader(header1);
//        detail2.setHeader(header1);
//        detail3.setHeader(header2);

    }

    @GetMapping("/infinite-ref")
    public ResponseEntity<Header> getHeader() {
        System.out.println("[[[[InfiniteReferenceController.getHeader");

        Header header = headerRepository.findById(1L).get();

        System.out.println("[[[header = " + header);
        System.out.println("[[[detail = " + header.getDetail());

//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(header);
        return ResponseEntity.ok(header);
    }
}
