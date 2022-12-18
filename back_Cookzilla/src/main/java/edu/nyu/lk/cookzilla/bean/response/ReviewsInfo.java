package edu.nyu.lk.cookzilla.bean.response;

import edu.nyu.lk.cookzilla.bean.request.ReviewPostRequest;
import edu.nyu.lk.cookzilla.model.Reviews;

public class ReviewsInfo {

    private ReviewPostRequest reviewPostRequest;

    private String username;

    public ReviewPostRequest getReviewPostRequest() {
        return reviewPostRequest;
    }

    public void setReviewPostRequest(ReviewPostRequest reviewPostRequest) {
        this.reviewPostRequest = reviewPostRequest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
