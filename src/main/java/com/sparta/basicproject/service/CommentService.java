package com.sparta.basicproject.service;

import com.sparta.basicproject.dto.CommentRequestDto;
import com.sparta.basicproject.model.Board;
import com.sparta.basicproject.model.Comment;
import com.sparta.basicproject.repository.BoardRepository;
import com.sparta.basicproject.repository.CommentRepository;
import com.sparta.basicproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public void savecomment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {

        Comment comment = new Comment(requestDto);
        if (userDetails == null) {
            return;
        } else {
            comment.setUsername(userDetails.getUser().getUsername());
        }

        Board board = boardRepository.findById(requestDto.getPostId()).orElse(null);

        if (board != null) {
            board.addComment(commentRepository.save(comment));
            boardRepository.save(board);
        }

    }

    @Transactional
    public Boolean deletecomment(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElse(null);
        Board board = boardRepository.findById(comment.getPostid()).orElse(null);
        List<Comment> commentList = board.getCommentList();
        if (comment.getUsername().equals(userDetails.getUser().getUsername())) {
            commentList.remove(comment);
            commentRepository.delete(comment);
            return true;
        }else{
            return false;
        }
//        Comment comment1 = commentRepository.findById(id).orElse(null);
    }

    public Boolean editcomment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(requestDto.getPostId()).orElse(null);
        if (comment.getUsername().equals(userDetails.getUser().getUsername())) {
            comment.setComment(requestDto.getComment());
            commentRepository.save(comment);
            return true;
        } else {
            return false;
        }
    }
}
