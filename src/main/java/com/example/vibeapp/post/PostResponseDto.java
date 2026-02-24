package com.example.vibeapp.post;

import java.time.LocalDateTime;

public class PostResponseDto {
    private Long no;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer views;

    public static PostResponseDto from(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.no = post.getNo();
        dto.title = post.getTitle();
        dto.content = post.getContent();
        dto.createdAt = post.getCreatedAt();
        dto.updatedAt = post.getUpdatedAt();
        dto.views = post.getViews();
        return dto;
    }

    // Getters
    public Long getNo() { return no; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Integer getViews() { return views; }
}
