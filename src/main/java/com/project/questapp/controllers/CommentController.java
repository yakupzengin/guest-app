package com.project.questapp.controllers;

import com.project.questapp.entities.Comment;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    /*
        …/comments
        …/comments?postId={postId}
        …/comments?userId={userId}
        …/comments?postId={postId}&userId={userId}

     */
    @GetMapping()
    public List<Comment> getAllComment(@RequestParam Optional<Long> userId ,
                                       @RequestParam Optional<Long> postId  ){
        return commentService.getAllCommentsWithParam(userId,postId);
    }

    @PostMapping()
    public Comment createOneComment(@RequestBody CommentCreateRequest commentCreateRequest){
        return commentService.createOneComment(commentCreateRequest);
    }

    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneCommentById(commentId);
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable long commentId ,
                                    @RequestBody CommentUpdateRequest commentUpdateRequest){
        return commentService.updateCommentById(commentId,commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable long commentId){
            commentService.deleteOneCommentById(commentId);
        }
    }
