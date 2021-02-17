package com.devproject.usersystem.data.models;

import com.devproject.usersystem.data.models.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String country;
    private int age;
    private Gender gender;
    private String description;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "profiles_friendRequestsSent",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "friendRequest_id"))
    private List<Profile>friendRequestsSent;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "profiles_friendRequestsReceived",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "friendRequest_id"))
    private List<Profile>friendRequestsReceived;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ImageModel profilePicture;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "profiles_books",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "profiles_friends",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Profile>friends;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "profiles_songs",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> songs;
    @OneToOne
    private Song top1song;
    @OneToOne
    private Song top2song;
    @OneToOne
    private Song top3song;
    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<Comment>comments;
    @OneToOne(mappedBy = "profile")
    private User user;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name="profiles_suggestions",
            joinColumns = @JoinColumn(name="profile_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song>songSuggestions;



}
