package com.demo.lemonaid.demo.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @Getter @Setter
    private int id;

    @Column(name = "email")
    @Getter @Setter
    private String email;

    @Column(name = "password")
    @Getter @Setter
    private String password;

    @Column(name = "name")
    @Getter @Setter
    private String name;

    @Column(name = "personal_id")
    @Getter @Setter
    private int personal_id;

    @Column(name = "gender", nullable = false)
    @Getter @Setter
    private boolean gender;

    @Column(name = "tel")
    @Getter @Setter
    private int tel;

    @Column(name = "addr")
    @Getter @Setter
    private int addr;

    @OneToMany(cascade= CascadeType.ALL, targetEntity = ResultMulti.class)
    @JoinColumn(name = "user_id")
    private Collection<ChoiceSingle> user_single;

    @OneToMany(cascade= CascadeType.ALL, targetEntity = ResultSingle.class)
    @JoinColumn(name = "user_id")
    private Collection<ChoiceSingle> user_multi;

    @OneToMany(cascade= CascadeType.ALL, targetEntity = Write.class)
    @JoinColumn(name = "user_id")
    private Collection<ChoiceSingle> user_write;
}
