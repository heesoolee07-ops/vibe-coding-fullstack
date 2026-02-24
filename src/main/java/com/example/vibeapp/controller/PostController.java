package com.example.vibeapp.controller;

import com.example.vibeapp.entity.Post;
import com.example.vibeapp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String listPosts(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        int pageSize = 5;
        model.addAttribute("posts", postService.getPostsPaginated(page, pageSize));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postService.getTotalPages(pageSize));
        return "posts";
    }

    @GetMapping("/posts/{no}")
    public String getPostDetail(@PathVariable("no") Long no, Model model) {
        Post post = postService.getPostByNo(no);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @GetMapping("/posts/new")
    public String newPostForm() {
        return "post_new_form";
    }

    @PostMapping("/posts/add")
    public String addPost(@RequestParam("title") String title, @RequestParam("content") String content) {
        Post newPost = new Post(
                null, 
                title, 
                content, 
                LocalDateTime.now(), 
                null, 
                0
        );
        postService.createPost(newPost);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{no}/edit")
    public String editPostForm(@PathVariable("no") Long no, Model model) {
        Post post = postService.getPostByNo(no);
        model.addAttribute("post", post);
        return "post_edit_form";
    }

    @PostMapping("/posts/{no}/save")
    public String updatePost(@PathVariable("no") Long no, @RequestParam("title") String title, @RequestParam("content") String content) {
        postService.updatePost(no, title, content);
        return "redirect:/posts/" + no;
    }

    @GetMapping("/posts/{no}/delete")
    public String deletePost(@PathVariable("no") Long no) {
        postService.deletePost(no);
        return "redirect:/posts";
    }
}
