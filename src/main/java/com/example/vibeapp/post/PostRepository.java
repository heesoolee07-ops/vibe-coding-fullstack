package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PostRepository {
    List<Post> findAll();
    Post findByNo(Long no);
    void save(Post post);
    void deleteByNo(Long no);
}
