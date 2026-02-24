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

    public List<PostListDto> findAll() {
        return postRepository.findAll().stream()
                .map(PostListDto::from)
                .toList();
    }

    public PostResponseDto findByNo(Long no) {
        Post post = postRepository.findByNo(no);
        return post != null ? PostResponseDto.from(post) : null;
    }

    public void save(PostCreateDto createDto) {
        postRepository.save(createDto.toEntity());
    }

    public void update(Long no, PostUpdateDto updateDto) {
        Post post = postRepository.findByNo(no);
        if (post != null) {
            updateDto.updateEntity(post);
            postRepository.save(post);
        }
    }

    public void delete(Long no) {
        postRepository.deleteByNo(no);
    }

    public List<PostListDto> findPaginated(int page, int size) {
        List<Post> allPosts = postRepository.findAll();
        int fromIndex = (page - 1) * size;
        if (fromIndex >= allPosts.size()) {
            return java.util.Collections.emptyList();
        }
        int toIndex = Math.min(fromIndex + size, allPosts.size());
        return allPosts.subList(fromIndex, toIndex).stream()
                .map(PostListDto::from)
                .toList();
    }

    public int countTotalPages(int size) {
        List<Post> allPosts = postRepository.findAll();
        if (allPosts.isEmpty()) return 1;
        return (int) Math.ceil((double) allPosts.size() / size);
    }
}
