package com.example.vibeapp.post;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findByNo(Long no) {
        return postRepository.findByNo(no);
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public void update(Long no, String title, String content) {
        Post post = findByNo(no);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
        }
    }

    public void delete(Long no) {
        postRepository.deleteByNo(no);
    }

    public List<Post> findPaginated(int page, int size) {
        List<Post> allPosts = findAll();
        int fromIndex = (page - 1) * size;
        if (fromIndex >= allPosts.size()) {
            return java.util.Collections.emptyList();
        }
        int toIndex = Math.min(fromIndex + size, allPosts.size());
        return allPosts.subList(fromIndex, toIndex);
    }

    public int countTotalPages(int size) {
        List<Post> allPosts = findAll();
        if (allPosts.isEmpty()) return 1;
        return (int) Math.ceil((double) allPosts.size() / size);
    }
}
