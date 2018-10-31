package ua.logos.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name="author")
public class AuthorEntity extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column
    private String life;

}
