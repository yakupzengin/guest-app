package com.project.questapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "post")
@Data
public class Post {

    @Id
    Long id;
    @ManyToOne(fetch =  FetchType.LAZY) // Don't directly the data.
    @JoinColumn(name = "user_id" , nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // if a user deleted, remove all the post from that user.
    @JsonIgnore // Do not appear in JSON data
    User user;
    String title;

    @Lob
    @Column(columnDefinition = "text")
    String text;

}
