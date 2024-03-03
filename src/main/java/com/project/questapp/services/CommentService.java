package com.project.questapp.services;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()) {
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());

        } else if (userId.isPresent()) {
            return commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        }
        return commentRepository.findAll();

    }

    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest request) {
        Post post = postService.getOnePostById(request.getPostId());
        User user = userService.getOneUserById(request.getUserId());

        if (user != null && post != null) {
            Comment toSavecomment = new Comment();
            toSavecomment.setId(request.getId());
            toSavecomment.setPost(post);
            toSavecomment.setUser(user);
            toSavecomment.setText(request.getText());
            return commentRepository.save(toSavecomment);
        }else
            return null;
    }

    public Comment updateCommentById(long commentId, CommentUpdateRequest request) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()){
            Comment toUpdateComment = comment.get();
            toUpdateComment.setText(request.getText());
            return commentRepository.save(toUpdateComment);
        } else {
            return null;
        }
    }

    public void deleteOneCommentById(long commentId) {
        commentRepository.deleteById(commentId);
    }
}
