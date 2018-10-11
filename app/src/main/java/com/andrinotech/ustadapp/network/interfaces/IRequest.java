package com.andrinotech.ustadapp.network.interfaces;

import com.andrinotech.ustadapp.ui.Post.AddPostApiModel;
import com.andrinotech.ustadapp.ui.Post.AddPostResponseModel;
import com.andrinotech.ustadapp.ui.Post.AllPostResponseModel;
import com.andrinotech.ustadapp.ui.Post.CommentResponseModel;
import com.andrinotech.ustadapp.ui.Post.LikePostResponseModel;
import com.andrinotech.ustadapp.ui.Post.OnePostResponseModel;
import com.andrinotech.ustadapp.ui.login.AuthTocken;
import com.andrinotech.ustadapp.ui.ChangePassword.ChangePasswordApiModel;
import com.andrinotech.ustadapp.ui.login.MetaData;
import com.andrinotech.ustadapp.ui.forgotPassword.ForgotPasswordApiModel;
import com.andrinotech.ustadapp.ui.profile.EditprofileApidModel;
import com.andrinotech.ustadapp.ui.register.RegisterUserApidModel;

import io.reactivex.Single;

public interface IRequest {

//    public Single<String> SignOut();

    public Single<MetaData> getForgotPassword(ForgotPasswordApiModel forgotPasswordApiModel);
    public Single<MetaData> senResetCode(ForgotPasswordApiModel forgotPasswordApiModel);
    public Single<MetaData> changepassword(ChangePasswordApiModel changePasswordApiModel);

    Single<MetaData> editPassword(String oldpassword, ChangePasswordApiModel changePasswordApiModel);

    //
    public Single<MetaData> getRegisterUser(RegisterUserApidModel registerUserApidModel);
    public Single<MetaData> Editprfofile(EditprofileApidModel registerUserApidModel);

    public Single<MetaData> signIn(AuthTocken authTocken);


    Single<AddPostResponseModel> AddPost(AddPostApiModel addPostApiModel);

    Single<LikePostResponseModel> addlike(int id);

    Single<CommentResponseModel> addComment(int id, String text);

    Single<LikePostResponseModel> posUnlike(int id);

    Single<MetaData> ChangeStatus(String status);

    Single<AllPostResponseModel> getAllPosts(AddPostApiModel addPostApiModel);

    Single<AllPostResponseModel> getPostOfUstad(int id);

    Single<OnePostResponseModel> getPostById(int id);

    Single<AllPostResponseModel> getAllPosts();

    Single<CommentResponseModel> getAllPostComment(int id);
}
