package com.andrinotech.ustadapp.ui.Post;


import com.andrinotech.ustadapp.ui.base.BaseCallBack;

import java.util.ArrayList;

public interface PostCallback extends BaseCallBack {
    public  void ValidationError(PostViewModel.validationEnum validationEnum);
    public void SuccessFullyAdded();
    public void likeAdded(int post,LikePostResponseModel likePostResponseModel);

    public void ErrorOnAddPost(String str);
    public void InternetError(String str);
public void allposts(ArrayList<PostModelResponse>postModelResponses);
    public void allcomments(ArrayList<Comment>postModelResponses);

}
