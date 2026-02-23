package com.example.vibeapp.service;

import com.example.vibeapp.entity.Post;
import com.example.vibeapp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostByNo(Long no) {
        return postRepository.findByNo(no);
    }

    public void createPost(Post post) {
        postRepository.save(post);
    }
}
