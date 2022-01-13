package com.kbs.datajpa;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class SortTest {

    List<Book> list;

    @BeforeEach
    public void setData(){
        System.out.println("[[[SortTest.setData");
        list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(new Book((int) (Math.random()*10000), "book"+i));
        }
    }

    @Test
    public void test1(){

        Long st1 = System.currentTimeMillis();
        List<Book> sorted1 = list.stream().sorted(Comparator.comparingLong(Book::getNo).reversed()).collect(Collectors.toList());
        System.out.println("[[test1 : "+ sorted1.size() +" : " +(System.currentTimeMillis() - st1));
    }

    @Test
    public void test2(){

        Long st2 = System.currentTimeMillis();
        list.sort(Comparator.comparingLong(Book::getNo).reversed());
        System.out.println("[[test1 : "+ list.size() +" : "+(System.currentTimeMillis() - st2));
    }

    public class Book {
        Integer no;
        String name;

        public Book() {}

        public Book(Integer no, String name) {
            this.no = no;
            this.name = name;
        }

        public Integer getNo() {
            return no;
        }

        public void setNo(Integer no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
