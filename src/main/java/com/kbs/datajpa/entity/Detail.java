package com.kbs.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "header_id", scope = Header.class)
public class Detail {

    @Id @GeneratedValue
    @Column(name = "detail_id")
    private Long id;
    private String name;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id")
    private Header header;

    public Detail(String name, String type, Header header) {
        this.name = name;
        this.type = type;
        this.header = header;
    }
}
