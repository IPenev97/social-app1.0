package com.devproject.usersystem.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor

public class Comment extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Song song;
    private String content;
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Profile author;
    private String date;
    private String time;
}
