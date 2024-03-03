package com.project.questapp.services;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.LikeRepository;
import com.project.questapp.requests.LikeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private PostService postService;
    private UserService userService;
    public LikeService(LikeRepository likeRepository, PostService postService,UserService userService){
        this.likeRepository=likeRepository;
        this.postService = postService;
        this.userService = userService;
    }
    public List<Like> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()){
            return likeRepository.findByUserIdAndPostId(userId,postId);
        } else if (userId.isPresent()){
            return likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()){
            return likeRepository.findByPostId(postId.get());
        }
        return likeRepository.findAll();
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest request) {
        Post post = postService.getOnePostById(request.getPostId());
        User user = userService.getOneUserById(request.getUserId());

        if (post != null && user != null){
            Like toSaveLike = new Like();
            toSaveLike.setUser(user);
            toSaveLike.setPost(post);
            toSaveLike.setId(request.getId());
            return likeRepository.save(toSaveLike);
        }else
            return null;
    }

    public void deleteOneLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
