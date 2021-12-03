package com.example.demo.services;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //Lombok nie trzeba AutoWire
public class PostService {

    public static final int PAGE_SIZE = 20;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<Post> getPosts(int page, Sort.Direction sort){ //Stworszenie listy o obiektach Post

        return postRepository.findAllPosts(
                PageRequest.of(0, PAGE_SIZE,
                        Sort.by(sort, "id"))
        );

    }

    public Post getSinglePost(long id) {
        return postRepository.findById(id)
                .orElseThrow(); //Jeśli nie znajdzie to wyrzuć wyjątek
    }

    public List<Post> getPostsWithComments(int page, Sort.Direction sort) {

       List<Post> allPost =  postRepository.findAllPosts(
               PageRequest.of(0, PAGE_SIZE,
               Sort.by(sort, "id"))
       );
        List<Long> ids = allPost.stream()
                .map(Post::getId)
                .collect(Collectors.toList());
       List<Comment> comments = commentRepository.findAllByPostIdIn(ids);
       allPost.forEach(post -> post.setComment(extractComments(comments, post.getId())));
   return allPost;
    }

    private List<Comment> extractComments(List<Comment> comments, long id) {
   return  comments.stream()
           .filter(comment -> comment.getPostId() == id)
           .collect(Collectors.toList());
    }

    public Post addPost(Post post) {
    return postRepository.save(post);
    }

    @Transactional
    public Post editPost(Post post) {
        Post postEdited = postRepository.findById(post.getId()).orElseThrow();
        postEdited.setTitle(post.getTitle());
        postEdited.setContent(post.getContent());
        return postEdited;  //postRepository.save(post); - Hibernate widzi edycję nie ma potrzeby wypisywać w ten sposób
    }

    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
