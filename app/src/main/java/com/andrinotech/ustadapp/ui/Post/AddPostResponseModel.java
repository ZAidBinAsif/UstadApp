
package com.andrinotech.ustadapp.ui.Post;

import com.andrinotech.ustadapp.helper.Error;
import com.andrinotech.ustadapp.ui.login.Ustad;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPostResponseModel {
    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @SerializedName("error")
    @Expose
    private Error error;


    @SerializedName("post")
    @Expose
    private PostModel  postModel;

    public PostModel getPostModel() {
        return postModel;
    }

    public void setPostModel(PostModel postModel) {
        this.postModel = postModel;
    }
}
