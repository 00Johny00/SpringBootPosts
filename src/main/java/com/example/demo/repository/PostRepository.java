package com.example.demo.repository;

import com.example.demo.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    @Query("select p from Post p where title = ?1")
//    List<Post> = findAllByTitle(String title);
    @Query("Select p From Post p")
    List<Post> findAllPosts(Pageable page);

  //  List<Post> findAllByTitle(String title); // Tworzymy nowe metody przez dodawanie zwrotu
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query

}
