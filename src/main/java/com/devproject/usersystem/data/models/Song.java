package com.devproject.usersystem.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Song extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String artist;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private ImageModel image;
    private String songUrl;
    @ManyToMany(mappedBy = "songs",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<Profile>users;
    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},orphanRemoval = true)
    private List<Comment> comments;
    public List<Comment> getCommentsInOrder(){
        List<Comment>returnList = this.comments;
        Collections.reverse(returnList);
        return returnList;
    }
    public Long likes;
    public Long dislikes;

}
