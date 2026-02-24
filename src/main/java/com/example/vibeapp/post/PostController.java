package com.example.vibeapp.post;

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
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        int pageSize = 5;
        model.addAttribute("posts", postService.findPaginated(page, pageSize));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postService.countTotalPages(pageSize));
        return "post/posts";
    }

    @GetMapping("/posts/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        Post post = postService.findByNo(no);
        model.addAttribute("post", post);
        return "post/post_detail";
    }

    @GetMapping("/posts/new")
    public String createForm() {
        return "post/post_new_form";
    }

    @PostMapping("/posts/add")
    public String create(@RequestParam("title") String title, @RequestParam("content") String content) {
        Post newPost = new Post(
                null, 
                title, 
                content, 
                LocalDateTime.now(), 
                null, 
                0
        );
        postService.save(newPost);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{no}/edit")
    public String updateForm(@PathVariable("no") Long no, Model model) {
        Post post = postService.findByNo(no);
        model.addAttribute("post", post);
        return "post/post_edit_form";
    }

    @PostMapping("/posts/{no}/save")
    public String update(@PathVariable("no") Long no, @RequestParam("title") String title, @RequestParam("content") String content) {
        postService.update(no, title, content);
        return "redirect:/posts/" + no;
    }

    @GetMapping("/posts/{no}/delete")
    public String delete(@PathVariable("no") Long no) {
        postService.delete(no);
        return "redirect:/posts";
    }
}
