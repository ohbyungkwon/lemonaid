package com.demo.lemonaid.demo.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.annotation.DeclareAnnotation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "write")
@Data
public class Write {
    @Id
    @Setter
    @Getter
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "question_id", nullable = false)
    @Setter
    @Getter
    private int question_id;

    @Column(name = "conetent", nullable = false)
    @Setter
    @Getter
    private int content;

    @Column(name = "text", nullable = false)
    @Setter
    @Getter
    private int text;
}
