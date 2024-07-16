package com.cookswp.milkstore.pojo.dtos.FeedbackModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedBackRequest {

    private int userID;

    private String orderID;

    private String description;

    private int rating;

}
