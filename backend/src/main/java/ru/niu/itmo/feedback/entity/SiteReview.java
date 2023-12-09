package ru.niu.itmo.feedback.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

/**
 * @author amifideles
 */
@Data
@Entity
@NoArgsConstructor
public class SiteReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "text")
    private String comment;
    private String contact;
}
