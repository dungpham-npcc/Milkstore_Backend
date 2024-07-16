package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.PostModel.PostDTO;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostStaffController {

    private final PostService postService;
    //create
    @PostMapping("/")
    public ResponseData<Post> createPost(@RequestBody @Valid PostDTO postRequest) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "Post created successfully", postService.createPost(postRequest));
    }
    //update
    @PatchMapping("/{ID}")
    public ResponseData<Post> updatePost(@PathVariable int ID, @RequestBody PostDTO postRequest) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Post updated successfully", postService.updatePost(ID, postRequest));
    }

    //retrieve
    @GetMapping("/")
    public ResponseData<List<Post>> getAllPost() {
        return new ResponseData<>(HttpStatus.OK.value(), "Post list", postService.getAllPosts());
    }

    @GetMapping("/{ID}")
    public ResponseData<Post> getPostById(@PathVariable int ID) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get post with ID: " + ID, postService.getPostByID(ID));
    }

    //Delete Post
    @PatchMapping("/{ID}/delete")
    public ResponseData<Post> deletePost(@PathVariable int ID) {
        postService.deletePost(ID);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete post with ID: " + ID, null);
    }

}
