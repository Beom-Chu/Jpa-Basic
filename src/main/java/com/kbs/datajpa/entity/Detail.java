package com.kbs.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Detail {

    @Id @GeneratedValue
    @Column(name = "detailId")
    private Long id;
    private String name;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headerId")
    private Header header;

    public Detail(String name, String type, Header header) {
        this.name = name;
        this.type = type;
        this.header = header;
    }
}
