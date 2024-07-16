//package com.cookswp.milkstore.service.post;
//
//import com.cookswp.milkstore.exception.AppException;
//import com.cookswp.milkstore.exception.ErrorCode;
//import com.cookswp.milkstore.pojo.dtos.PostModel.PostDTO;
//import com.cookswp.milkstore.pojo.entities.Post;
//import com.cookswp.milkstore.repository.post.PostRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class PostServiceTest {
//
//    @Mock
//    private PostRepository postRepository;
//
//    @InjectMocks
//    private PostService postService;
//
//    private Post post;
//
//    private PostDTO postDTO;
//
//    @BeforeEach
//    public void setUp() {
//        post = Post.builder()
//                .title("Post Title")
//                .content("Post Content")
//                .build();
//        postDTO = PostDTO.builder()
//                .title("PostDTO Title")
//                .content("PostDTO Content")
//                .build();
//    }
//
//    @Test
//    void testCreatePost_Success() {
//        //Returns true meaning that title not exists in the database
//        when(postRepository.titleMustBeUnique(postDTO.getTitle())).thenReturn(true);
//        //Save any Post class then return post
//        when(postRepository.save(any(Post.class))).thenReturn(post);
//
//        Post createdPost = postService.createPost(postDTO);
//
//        assertNotNull(createdPost);
//        assertEquals(post.getTitle(), createdPost.getTitle());
//        assertEquals(post.getContent(), createdPost.getContent());
//    }
//
//    @Test
//    void testCreatePost_With_Exists_Title() {
//        //Returns false meaning that title already exists in the database
//        when(postRepository.titleMustBeUnique(postDTO.getTitle())).thenReturn(false);
//
//        AppException exception = assertThrows(AppException.class, () -> {
//            postService.createPost(postDTO);
//        });
//
//        assertEquals(ErrorCode.POST_TITLE_EXISTS, exception.getErrorCode());
//    }
//
//    @Test
//    void testCreatePost_With_Title_Error() {
//        String title = "";
//        PostDTO postDTO = PostDTO.builder().title(title).content("Post Content").build();
//
//        AppException exception = assertThrows(AppException.class, () -> {
//            postService.createPost(postDTO);
//        });
//
//        if (postDTO.getTitle() == null || postDTO.getTitle().isBlank() || postDTO.getTitle().isEmpty()) {
//            assertEquals(ErrorCode.POST_TITLE_ERROR, exception.getErrorCode());
//        }
//    }
//
//    @Test
//    void testCreatePost_With_Content_Error() {
//        String content = "";
//        PostDTO postDTO = PostDTO.builder().title("Post Title").content(content).build();
//
//        AppException exception = assertThrows(AppException.class, () -> {
//            postService.createPost(postDTO);
//        });
//
//        if (postDTO.getContent() == null || postDTO.getContent().isBlank() || postDTO.getContent().isEmpty()) {
//            assertEquals(ErrorCode.POST_CONTENT_ERROR, exception.getErrorCode());
//        }
//    }
//
//    //-------------------------------------------------------------
//
//    @Test
//    void testUpdatePost_Success() {
//        int postID = 1;
//        ////Returns true meaning that title not exists in the database
//        when(postRepository.titleMustBeUnique(postDTO.getTitle())).thenReturn(true);
//        //assuming the PostID = 1 is exist in the database then return post
//        when(postRepository.findByIDAndVisibility(postID)).thenReturn(post);
//        //Save any Post class with the Post
//        when(postRepository.save(any(Post.class))).thenReturn(post);
//
//        //Update service
//        Post updatePost = postService.updatePost(postID, postDTO);
//
//        assertNotNull(updatePost);
//        assertEquals(updatePost.getTitle(), post.getTitle());
//        assertEquals(updatePost.getTitle(), post.getTitle());
//    }
//
//    //ED-64
//    @Test
//    void testUpdatePost_PostID_NotExist() {
//        int postID = 1;
//        //return null when postID not exist
//        when(postRepository.findByIDAndVisibility(postID)).thenReturn(null);
//
//        //exception
//        AppException exception = assertThrows(AppException.class, () -> {
//            postService.updatePost(postID, postDTO);
//        });
//
//        assertEquals(ErrorCode.POST_ID_NOT_FOUND, exception.getErrorCode());
//    }
//
//    @Test
//    void testUpdatePost_With_Title_Error() {
//        int postID = 1;
//        String title = "";
//        //return post with the postID = 1
//        when(postRepository.findByIDAndVisibility(postID)).thenReturn(post);
//        //
//        PostDTO postDTO = PostDTO.builder()
//                .title(title)
//                .content("content")
//                .build();
//
//        AppException exception = assertThrows(AppException.class, () -> {
//            postService.updatePost(postID, postDTO);
//        });
//
//        if (postDTO.getTitle() == null || postDTO.getTitle().isBlank() || postDTO.getTitle().isEmpty()) {
//            assertEquals(ErrorCode.POST_TITLE_ERROR, exception.getErrorCode());
//        }
//    }
//
//    @Test
//    void testUpdatePost_With_Content_Error() {
//        int postID = 1;
//        String content = "";
//        //assuming the post is existing with ID = 1
//        when(postRepository.findByIDAndVisibility(postID)).thenReturn(post);
//        //
//        PostDTO postDTO = PostDTO.builder().title("title").content(content).build();
//
//        AppException exception = assertThrows(AppException.class, () -> {
//            postService.updatePost(postID, postDTO);
//        });
//
//        if (postDTO.getContent() == null || postDTO.getContent().isBlank() || postDTO.getContent().isEmpty()) {
//            assertEquals(ErrorCode.POST_CONTENT_ERROR, exception.getErrorCode());
//        }
//    }
//
//    @Test
//    void testUpdatePost_With_Exists_Title() {
//        int postID = 1;
//        //Returns false meaning that title already exists in the database
//        when(postRepository.titleMustBeUnique(postDTO.getTitle())).thenReturn(false);
//        when(postRepository.findByIDAndVisibility(postID)).thenReturn(post);
//
//        AppException exception = assertThrows(AppException.class, () -> {
//            postService.updatePost(postID, postDTO);
//        });
//        //
//        assertEquals(ErrorCode.POST_TITLE_EXISTS, exception.getErrorCode());
//    }
//
//
//}