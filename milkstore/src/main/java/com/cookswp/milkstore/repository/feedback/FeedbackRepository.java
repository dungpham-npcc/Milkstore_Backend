package com.cookswp.milkstore.repository.feedback;

import com.cookswp.milkstore.pojo.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("SELECT f FROM Feedback f WHERE f.status = TRUE AND f.feedbackID = :feedbackID")
    Feedback findByFeedbackIDAndAndStatus(@Param("feedbackID") int feedbackID);

    @Query("SELECT f FROM Feedback f WHERE f.status = TRUE")
    List<Feedback> getAllFeedback();

    @Query("SELECT f FROM Feedback f WHERE f.status = TRUE and f.feedbackID = :feedbackID")
    Feedback findByFeedbackID(@Param("feedbackID") int feedbackID);

    @Query("SELECT f FROM Feedback f WHERE f.status = TRUE and f.orderID = :orderID")
    Feedback findByOrderID(@Param("orderID") String orderID);

}
