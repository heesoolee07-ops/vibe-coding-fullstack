package com.example.vibeapp.post;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        PostResponseDto post = postService.findByNo(no);
        model.addAttribute("post", post);
        return "post/post_detail";
    }

    @GetMapping("/posts/new")
    public String createForm(Model model) {
        model.addAttribute("post", new PostCreateDto());
        return "post/post_new_form";
    }

    @PostMapping("/posts/add")
    public String create(@Valid @ModelAttribute("post") PostCreateDto postCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/post_new_form";
        }
        postService.save(postCreateDto);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{no}/edit")
    public String updateForm(@PathVariable("no") Long no, Model model) {
        PostResponseDto post = postService.findByNo(no);
        model.addAttribute("post", post);
        return "post/post_edit_form";
    }

    @PostMapping("/posts/{no}/save")
    public String update(@PathVariable("no") Long no, @Valid @ModelAttribute("post") PostUpdateDto postUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/post_edit_form";
        }
        postService.update(no, postUpdateDto);
        return "redirect:/posts/" + no;
    }

    @GetMapping("/posts/{no}/delete")
    public String delete(@PathVariable("no") Long no) {
        postService.delete(no);
        return "redirect:/posts";
    }
}
