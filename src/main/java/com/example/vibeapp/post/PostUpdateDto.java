package com.example.vibeapp.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record PostUpdateDto(
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 가능합니다.")
    String title,

    String content,

    String tags
) {
    public PostUpdateDto() {
        this(null, null, null);
    }

    public void updateEntity(Post post) {
        post.setTitle(this.title);
        post.setContent(this.content);
        post.setUpdatedAt(LocalDateTime.now());
    }
}
