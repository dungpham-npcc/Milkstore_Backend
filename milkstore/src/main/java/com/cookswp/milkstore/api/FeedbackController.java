package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.FeedbackModel.FeedBackRequest;
import com.cookswp.milkstore.pojo.entities.Feedback;
import com.cookswp.milkstore.repository.feedback.FeedbackRepository;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.feedback.IFeedBackService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final IFeedBackService feedBackService;

    public FeedbackController(IFeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    @PostMapping
    public ResponseData<Feedback> addFeedback(@RequestBody FeedBackRequest feedback) {
        return new ResponseData<>(HttpStatus.OK.value(), "Add feedback", feedBackService.addFeedback(feedback));
    }

    @DeleteMapping("/{feedbackID}")
    public ResponseData<Feedback> deleteFeedback(@PathVariable int feedbackID){
        feedBackService.deleteFeedback(feedbackID);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete feedback", null);
    }

    @PutMapping("/{feedbackID}")
    public ResponseData<Feedback> updateFeedback(@PathVariable int feedbackID, @RequestBody FeedBackRequest feedback){
        return new ResponseData<>(HttpStatus.OK.value(), "Update feedback", feedBackService.updateFeedback(feedbackID, feedback));
    }

    @GetMapping("/")
    public ResponseData<List<Feedback>> getAllAFeedback(){
        return new ResponseData<>(HttpStatus.OK.value(), "List Feedback", feedBackService.getAllFeedback());
    }

    @GetMapping("/{feedbackID}")
    public ResponseData<Feedback> getFeedback(@PathVariable int feedbackID){
        return new ResponseData<>(HttpStatus.OK.value(), "Get feedback", feedBackService.getFeedbackByID(feedbackID));
    }
    
}
