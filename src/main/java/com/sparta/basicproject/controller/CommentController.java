package com.sparta.basicproject.controller;

import com.sparta.basicproject.dto.CommentRequestDto;
import com.sparta.basicproject.model.User;
import com.sparta.basicproject.security.UserDetailsImpl;
import com.sparta.basicproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public void createBoard(@RequestBody CommentRequestDto requestDto,
                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.savecomment(requestDto, userDetails);

    }

    // 로그인한 회원이 게시글 조회
    @GetMapping("/api/checklogin")
    public Boolean getReply(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails == null){
            return true;
        }else return false;
    }

    // 게시글 id 로 댓글 조회
    @DeleteMapping("/api/comment/{id}")
    public Boolean deletecomment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       return commentService.deletecomment(id, userDetails);
    }

    @PutMapping("/api/updatecomment")
    public Boolean updatecomment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.editcomment(requestDto, userDetails);
    }

}
