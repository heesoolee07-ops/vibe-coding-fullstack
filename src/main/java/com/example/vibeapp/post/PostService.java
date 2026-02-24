package com.example.vibeapp.post;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    public PostService(PostRepository postRepository, PostTagRepository postTagRepository) {
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
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
        Post post = createDto.toEntity();
        postRepository.save(post);
        saveTags(post.getNo(), createDto.tags());
    }

    public void increaseViews(Long no) {
        postRepository.updateViews(no);
    }

    public List<PostTag> findTagsByPostNo(Long no) {
        return postTagRepository.findByPostNo(no);
    }

    public void update(Long no, PostUpdateDto updateDto) {
        Post post = postRepository.findByNo(no);
        if (post != null) {
            updateDto.updateEntity(post);
            postRepository.save(post);
            postTagRepository.deleteByPostNo(no);
            saveTags(no, updateDto.tags());
        }
    }

    public void delete(Long no) {
        postTagRepository.deleteByPostNo(no);
        postRepository.deleteByNo(no);
    }

    public List<PostListDto> findPaginated(int page, int size) {
        List<Post> allPosts = postRepository.findAll();
        int fromIndex = (page - 1) * size;
        if (fromIndex >= allPosts.size()) {
            return Collections.emptyList();
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

    private void saveTags(Long postNo, String tagsString) {
        if (tagsString == null || tagsString.isBlank()) return;
        Arrays.stream(tagsString.split(","))
                .map(String::trim)
                .filter(tag -> !tag.isEmpty())
                .forEach(tagName -> {
                    PostTag postTag = new PostTag(null, postNo, tagName);
                    postTagRepository.save(postTag);
                });
    }
}
