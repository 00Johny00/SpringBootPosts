package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private LocalDateTime created;

    @OneToMany(cascade = CascadeType.REMOVE) // oznaczenie relacji baz do siebie
    @JoinColumn( name = "postId", updatable = false, insertable = false) //Oznaczenie klucza z zewnętrznej bazy
    private List<Comment> comment; //Lazy ()Jackson serializuje do JSON problem n+1
}
