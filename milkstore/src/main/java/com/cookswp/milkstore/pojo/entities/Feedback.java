package com.cookswp.milkstore.pojo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_of_review")
    private int feedbackID;

    @Column(name = "order_id", nullable = false)
    private String orderID;

    @Column(name = "user_id", nullable = false)
    private int userID;

    @Column(name = "feedback_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "feedback_time", nullable = false)
    private LocalDateTime feedbackTime;

    @Column(name = "feedback_status")
    private boolean status;

    @Column(name = "feedback_rating", nullable = false)
    private int rating;
}
