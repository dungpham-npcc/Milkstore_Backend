package com.cookswp.milkstore.service.post;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.PostModel.PostDTO;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.repository.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAllVisibility();
        if (!posts.isEmpty()) {
            return posts;
        } else {
            throw new AppException(ErrorCode.ALL_POST_EMPTY);
        }
    }

    @Override
    public Post getPostByID(int id) {
        Post post = postRepository.findByIDAndVisibility(id);
        if (post != null) {
            return post;
        } else {
            throw new AppException(ErrorCode.POST_ID_NOT_FOUND);
        }
    }

    @Override
    public Post updatePost(int ID, PostDTO postRequest) {
        Post postEntity = postRepository.findByIDAndVisibility(ID);
        if (postEntity != null) {
            validateInputRequest(postRequest);
            postEntity.setUserID(1);
            postEntity.setContent(postRequest.getContent());
            postEntity.setTitle(postRequest.getTitle());
            postEntity.setDateCreated(new Date());
            postEntity.setVisibility(true);
            return postRepository.save(postEntity);
        } else {
            throw new AppException(ErrorCode.POST_ID_NOT_FOUND);
        }
    }

    @Override
    public Post createPost(PostDTO postRequest) {
        validateInputRequest(postRequest);
        Post post = Post.builder()
                .content(postRequest.getContent())
                .title(postRequest.getTitle())
                .dateCreated(new Date())
                .userComment("")
                .userID(6)
                .visibility(true)
                .build();
        return postRepository.save(post);
    }

    private void validateInputRequest(PostDTO postRequest) {
        if (postRequest.getTitle() == null || postRequest.getTitle().isBlank() || postRequest.getTitle().isEmpty()) {
            throw new AppException(ErrorCode.POST_TITLE_ERROR);
        }
        if (postRequest.getContent() == null || postRequest.getContent().isBlank() || postRequest.getContent().isEmpty()) {
            throw new AppException(ErrorCode.POST_CONTENT_ERROR);
        }
        if (!postRepository.titleMustBeUnique(postRequest.getTitle())) {
            throw new AppException(ErrorCode.POST_TITLE_EXISTS);
        }
        if (postRequest.getContent().equals("F*ck") || postRequest.getContent().equals("D*ck head")) {
            throw new AppException(ErrorCode.POST_CONTENT_OFFENSIVE_WORD);//Valid word
        }
    }

    @Override
    public void deletePost(int id) {
        Post postEntity = postRepository.findByIDAndVisibility(id);
        if (postEntity != null) {
            postEntity.setVisibility(false);
            postRepository.save(postEntity);
        } else {
            throw new AppException(ErrorCode.POST_ID_NOT_FOUND);
        }
    }
}
