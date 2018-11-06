package com.demo.lemonaid.demo.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @Getter @Setter
    private long id;

    @Column(name = "email",length = 20)
    @Getter @Setter
    private String email;

    @Column(name = "password", length = 100)
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
    private String addr;

    //1:수퍼관리자, 2:관리자, 3:사용자
    @Column(name = "user_type",length = 1, nullable = false)
    private String userType;

    @Column(name="reg_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate = new Date();

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
