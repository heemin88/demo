package com.backend.back.domain.board;

import com.backend.back.domain.comment.Comment;
import com.backend.back.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Comment> commentList=new ArrayList<>();

    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone = "Asia/Seoul")
    private LocalDate created_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone = "Asia/Seoul")
    private LocalDate modified_date;
    private Integer view_count;


    @Enumerated(EnumType.STRING)
    private BoardType status; // Question,

    public Board(User user, String title, String description, LocalDate created_date, LocalDate modified_date, Integer view_count) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.created_date = created_date;
        this.modified_date = modified_date;
        this.view_count = view_count;
    }

    public void modify(String title,String description) {
        this.title=title;
        this.description=description;
        this.modified_date=LocalDate.now();
    }
}
