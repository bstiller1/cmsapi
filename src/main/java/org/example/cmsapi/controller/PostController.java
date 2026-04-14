package org.example.cmsapi.controller;

import org.example.cmsapi.model.Post;
import org.example.cmsapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    // POST - create a new post
    @PostMapping
    public Post createPost(@RequestBody Post post){
        return postRepository.save(post);
    }
    // GET - List all posts
    @GetMapping
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    // GET - List post by id
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        return postRepository.findById(id)
                .map(post -> ResponseEntity.ok().body(post))
                .orElse(ResponseEntity.notFound().build());
    }
    // PUT - Update a post by id
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails){
        return postRepository.findById(id).map(existingPost -> {
            existingPost.setTitle(postDetails.getTitle());
            existingPost.setContent(postDetails.getContent());
            existingPost.setAuthor(postDetails.getAuthor());
            Post updatedPost = postRepository.save(existingPost);
            return ResponseEntity.ok(updatedPost);
        }).orElse(ResponseEntity.notFound().build());
    }
    // DELETE - Remove a post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        return postRepository.findById(id).map(post ->{
            postRepository.delete(post);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
