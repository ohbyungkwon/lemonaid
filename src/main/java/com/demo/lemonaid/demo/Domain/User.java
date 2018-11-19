package com.demo.lemonaid.demo.Domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "email",length = 20)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "personal_id")
    private String personal_id;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "tel")
    private String tel;

    @Column(name = "addr")
    private String addr;

    @Column(name = "user_type",length = 1, nullable = false)
    private String userType;

    @Column(name="reg_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate = new Date();
//
//    @OneToMany(cascade= CascadeType.ALL, targetEntity = ResultMulti.class)
//    @JoinColumn(name = "user_id")
//    private Collection<ChoiceSingle> user_single;
//
//    @OneToMany(cascade= CascadeType.ALL, targetEntity = ResultSingle.class)
//    @JoinColumn(name = "user_id")
//    private Collection<ChoiceMulti> user_multi;
//
//    @OneToMany(cascade= CascadeType.ALL, targetEntity = Write.class)
//    @JoinColumn(name = "user_id")
//    private Collection<Write> user_write;
}
