package com.project.questapp.services;

import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.PostRepository;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository,UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts(Optional<Long> userId) {
        if (userId.isPresent())
            return postRepository.findByUserId(userId.get());
        return postRepository.findAll();
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user = userService.getOneUserById(newPostRequest.getUserId());
        if (user == null) {
            return null;
        }
        Post toSavePost = new Post();
        toSavePost.setId(newPostRequest.getId());
        toSavePost.setTitle(newPostRequest.getTitle());
        toSavePost.setText(newPostRequest.getText());
        toSavePost.setUser(user);

        return postRepository.save(toSavePost);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest updatePost) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()){
            Post toUpdatePost = post.get();
            toUpdatePost.setText(updatePost.getText());
            toUpdatePost.setTitle(updatePost.getTitle());
            postRepository.save(toUpdatePost);
            return toUpdatePost;
        }
        return null;
    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
