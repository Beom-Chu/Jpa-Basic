package com.kbs.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Header {

    @Id @GeneratedValue
    @Column(name = "headerId")
    private Long id;
    private String name;
    private int height;

    /* Json 변환에서 제외 */
//    @JsonIgnore
    @OneToMany(mappedBy = "header")
    private List<Detail> detail = new ArrayList<>();

    public Header(String name, int height) {
        this.name = name;
        this.height = height;
    }
}
