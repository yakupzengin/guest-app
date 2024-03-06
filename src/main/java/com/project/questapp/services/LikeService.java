package com.project.questapp.services;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.LikeRepository;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if (userId.isPresent() && postId.isPresent()){
            list = likeRepository.findByUserIdAndPostId(userId,postId);
        } else if (userId.isPresent()){
            list =  likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()){
            list =  likeRepository.findByPostId(postId.get());
        }else {
            list = likeRepository.findAll();
        }
        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest request) {
        Post post = postService.getOnePostById(request.getPostId());
        User user = userService.getOneUserById(request.getUserId());

        if (post != null && user != null){
            Like toSaveLike =    new Like();
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
