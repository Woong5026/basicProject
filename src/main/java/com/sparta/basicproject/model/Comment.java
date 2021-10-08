package com.sparta.basicproject.model;

import com.sparta.basicproject.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long postid;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comment;

    public Comment(Long postid, String username, String comment) {
        this.postid = postid;
        this.username = username;
        this.comment = comment;
    }
    public Comment(CommentRequestDto requestDto) {
        this.postid = requestDto.getPostId();
        this.comment = requestDto.getComment();
    }

}
