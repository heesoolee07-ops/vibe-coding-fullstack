package com.example.vibeapp.repository;

import com.example.vibeapp.entity.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();

    public PostRepository() {
        // Initialize with 10 sample data
        for (long i = 1; i <= 10; i++) {
            posts.add(new Post(
                i,
                "Sample Post Title " + i,
                "This is the content for sample post " + i,
                LocalDateTime.now().minusDays(11 - i),
                LocalDateTime.now().minusDays(11 - i),
                (int) (Math.random() * 100)
            ));
        }
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public Post findByNo(Long no) {
        return posts.stream()
                .filter(post -> post.getNo().equals(no))
                .findFirst()
                .orElse(null);
    }

    public void save(Post post) {
        if (post.getNo() != null) {
            Post existingPost = findByNo(post.getNo());
            if (existingPost != null) {
                int index = posts.indexOf(existingPost);
                posts.set(index, post);
                return;
            }
        }
        Long nextNo = posts.stream()
                .mapToLong(Post::getNo)
                .max()
                .orElse(0L) + 1;
        post.setNo(nextNo);
        posts.add(post);
    }
}
