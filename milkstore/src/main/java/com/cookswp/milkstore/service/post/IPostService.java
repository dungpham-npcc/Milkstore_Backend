package com.cookswp.milkstore.service.post;

import com.cookswp.milkstore.pojo.dtos.PostModel.PostDTO;
import com.cookswp.milkstore.pojo.entities.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {

    //Get all post
    public List<Post> getAllPosts();

    //Get post by ID
    public Post getPostByID(int id);

    //Put post
    public Post updatePost(int ID, PostDTO post);

    //Create post
    public Post createPost(PostDTO post);

    public void deletePost(int id);
}
