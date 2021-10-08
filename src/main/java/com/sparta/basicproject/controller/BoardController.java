package com.sparta.basicproject.controller;

import com.sparta.basicproject.model.Board;
import com.sparta.basicproject.repository.BoardRepository;
import com.sparta.basicproject.dto.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardRepository boardRepository;

    @PostMapping("/api/boards")
    public Board createBoard(@RequestBody BoardRequestDto requestDto){
        Board board = new Board(requestDto);
        return boardRepository.save(board);
    }
    @GetMapping("/api/boards")
    public List<Board> getBoard(){
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }


    @GetMapping("/api/boards/{id}")
    public Board datailBoard(@PathVariable Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );
        return board;
    }

}
