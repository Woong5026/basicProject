package com.sparta.basicproject.dto;

import com.sparta.basicproject.model.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardRequestDto {
    private String username;
    private String contents;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardRequestDto(Board board) {
        this.username = board.getUsername();
        this.contents = board.getContents();
        this.title = board.getTitle();
        this.modifiedAt = board.getModifiedAt();
        this.createdAt = board.getCreatedAt();

    }

}
