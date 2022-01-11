package com.kbs.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "detail_id", scope = Detail.class)
public class Header {

    @Id @GeneratedValue
    @Column(name = "header_id")
    private Long id;
    private String name;
    private int height;

    @OneToMany(mappedBy = "header")
    private List<Detail> detail = new ArrayList<>();

    public Header(String name, int height) {
        this.name = name;
        this.height = height;
    }
}
