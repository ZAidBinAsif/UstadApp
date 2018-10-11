package com.andrinotech.ustadapp.network;


import com.andrinotech.ustadapp.data.UserManager;
import com.andrinotech.ustadapp.network.interfaces.IRequest;
import com.andrinotech.ustadapp.ui.ChangePassword.ChangePasswordApiModel;
import com.andrinotech.ustadapp.ui.Post.AddPostApiModel;
import com.andrinotech.ustadapp.ui.Post.AddPostResponseModel;
import com.andrinotech.ustadapp.ui.Post.AllPostResponseModel;
import com.andrinotech.ustadapp.ui.Post.CommentResponseModel;
import com.andrinotech.ustadapp.ui.Post.LikePostResponseModel;
import com.andrinotech.ustadapp.ui.forgotPassword.ForgotPasswordApiModel;
import com.andrinotech.ustadapp.ui.login.AuthTocken;
import com.andrinotech.ustadapp.ui.login.MetaData;
import com.andrinotech.ustadapp.ui.profile.EditprofileApidModel;
import com.andrinotech.ustadapp.ui.register.RegisterUserApidModel;
import com.andrinotech.ustadapp.utils.Config;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import io.reactivex.Single;

public class RequestHandler implements IRequest {
    @Override
    public Single<MetaData> signIn(AuthTocken authTocken) {
        return Rx2AndroidNetworking.post(Config.Api.SignIn)
                .addBodyParameter("email", authTocken.getEmail())
                .addBodyParameter("password", authTocken.getPass())
                .addBodyParameter("fcmKey", UserManager.getFcmKey())
                .build()
                .getObjectSingle(MetaData.class);
    }


    @Override
    public Single<MetaData> getForgotPassword(ForgotPasswordApiModel forgotPasswordApiModel) {
        return Rx2AndroidNetworking.post(Config.Api.FORGOT_PASSWORD)
                .addBodyParameter("email", forgotPasswordApiModel.getEmail())
                .build()
                .getObjectSingle(MetaData.class);
    }

    @Override
    public Single<MetaData> senResetCode(ForgotPasswordApiModel forgotPasswordApiModel) {
        return Rx2AndroidNetworking.post(Config.Api.FORGOT_RESET_CODE)
                .addBodyParameter("email", forgotPasswordApiModel.getEmail())
                .addBodyParameter("code", forgotPasswordApiModel.getCode())
                .build()
                .getObjectSingle(MetaData.class);
    }

    @Override
    public Single<MetaData> changepassword(ChangePasswordApiModel changePasswordApiModel) {
        return Rx2AndroidNetworking.post(Config.Api.CHANGE_PASSWORD)
                .addBodyParameter("email", changePasswordApiModel.getEmail())
                .addBodyParameter("password", changePasswordApiModel.getPassword())
                .build()
                .getObjectSingle(MetaData.class);
    }

    @Override
    public Single<MetaData> editPassword(String oldpassword, ChangePasswordApiModel changePasswordApiModel) {
        return Rx2AndroidNetworking.post(Config.Api.Edit_PASSWORD)
                .addBodyParameter("email", changePasswordApiModel.getEmail())
                .addBodyParameter("password", changePasswordApiModel.getPassword())
                .addBodyParameter("oldpassword", oldpassword)

                .build()
                .getObjectSingle(MetaData.class);
    }

    @Override
    public Single<MetaData> getRegisterUser(RegisterUserApidModel registerUserApidModel) {

        return Rx2AndroidNetworking.post(Config.Api.REGISTER)
                .addBodyParameter("name", registerUserApidModel.getName())
                .addBodyParameter("username", registerUserApidModel.getUsername())
                .addBodyParameter("email", registerUserApidModel.getEmail())
                .addBodyParameter("password", registerUserApidModel.getPassword())
                .addBodyParameter("fcmKey", UserManager.getFcmKey())

                .build()
                .getObjectSingle(MetaData.class);

    }

    @Override
    public Single<MetaData> Editprfofile(EditprofileApidModel registerUserApidModel) {
        Rx2ANRequest.PostRequestBuilder postRequestBuilder = new Rx2ANRequest.PostRequestBuilder(Config.Api.EDIT_PROFIEL);
        if (!registerUserApidModel.getUser().getEmail().equalsIgnoreCase(UserManager.getInstance().getMetaData().getUser().getEmail())) {
            postRequestBuilder.addBodyParameter("newEmail", registerUserApidModel.getUser().getEmail());
        }
        if (!registerUserApidModel.getUser().getUsername().equalsIgnoreCase(UserManager.getInstance().getMetaData().getUser().getUsername())) {
            postRequestBuilder.addBodyParameter("newUsername", registerUserApidModel.getUser().getUsername());
        }
        postRequestBuilder.addBodyParameter("name", registerUserApidModel.getUser().getName());
        postRequestBuilder.addBodyParameter("username", registerUserApidModel.getUser().getUsername());
        postRequestBuilder.addBodyParameter("email", registerUserApidModel.getUser().getEmail());
        postRequestBuilder.addBodyParameter("phone", registerUserApidModel.getUser().getPhone());
        postRequestBuilder.addBodyParameter("price", registerUserApidModel.getUser().getPrice());
        postRequestBuilder.addBodyParameter("skills", registerUserApidModel.getUser().getSkils());
        postRequestBuilder.addBodyParameter("info", registerUserApidModel.getUser().getInfo());
        postRequestBuilder.addBodyParameter("category", registerUserApidModel.getUser().getCategory());


        return postRequestBuilder.build().getObjectSingle(MetaData.class);
    }

    @Override
    public Single<AddPostResponseModel> AddPost(AddPostApiModel addPostApiModel) {
        Rx2ANRequest.PostRequestBuilder postRequestBuilder = new Rx2ANRequest.PostRequestBuilder(Config.Api.ADD_POST);
        postRequestBuilder.addBodyParameter("title", addPostApiModel.getPostModel().getTitle());
        postRequestBuilder.addBodyParameter("text", addPostApiModel.getPostModel().getText());
        postRequestBuilder.addBodyParameter("category", addPostApiModel.getPostModel().getCategory());
        postRequestBuilder.addBodyParameter("userId", addPostApiModel.getPostModel().getUserId());
        postRequestBuilder.addBodyParameter("type", addPostApiModel.getPostModel().getType());

        return postRequestBuilder.build().getObjectSingle(AddPostResponseModel.class);
    }

    @Override
    public Single<LikePostResponseModel> addlike(int id) {
        Rx2ANRequest.PostRequestBuilder postRequestBuilder = new Rx2ANRequest.PostRequestBuilder(Config.Api.ADD_LIKE);
        postRequestBuilder.addBodyParameter("postId", String.valueOf(id));
        postRequestBuilder.addBodyParameter("userId", UserManager.getInstance().getMetaData().getUser().getId());
        postRequestBuilder.addBodyParameter("userType", "ustad");
        return postRequestBuilder.build().getObjectSingle(LikePostResponseModel.class);
    }


    @Override
    public Single<LikePostResponseModel> posUnlike(int id) {
        Rx2ANRequest.PostRequestBuilder postRequestBuilder = new Rx2ANRequest.PostRequestBuilder(Config.Api.UNLIKE_POST);
        postRequestBuilder.addBodyParameter("postId", String.valueOf(id));
        postRequestBuilder.addBodyParameter("userId", UserManager.getInstance().getMetaData().getUser().getId());
        postRequestBuilder.addBodyParameter("userType", "ustad");
        return postRequestBuilder.build().getObjectSingle(LikePostResponseModel.class);
    }

    @Override
    public Single<MetaData> ChangeStatus(String status) {
        Rx2ANRequest.PostRequestBuilder postRequestBuilder = new Rx2ANRequest.PostRequestBuilder(Config.Api.CHANGESTATUS);
        postRequestBuilder.addBodyParameter("status", status);
        postRequestBuilder.addBodyParameter("email", UserManager.getInstance().getMetaData().getUser().getEmail());

        postRequestBuilder.build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

            }

            @Override
            public void onError(ANError anError) {

            }
        });
        return null;
    }

    @Override
    public Single<AllPostResponseModel> getAllPosts(AddPostApiModel addPostApiModel) {
        return null;
    }


    @Override
    public Single<AllPostResponseModel> getPostOfUstad(int id) {

        Rx2ANRequest.PostRequestBuilder postRequestBuilder = new Rx2ANRequest.PostRequestBuilder(Config.Api.getAllPostsUstad);
        postRequestBuilder.addBodyParameter("userId", UserManager.getInstance().getMetaData().getUser().getId());
        postRequestBuilder.addBodyParameter("userType", "student");
        postRequestBuilder.addBodyParameter("ustadId", String.valueOf(id));

        return postRequestBuilder.build().getObjectSingle(AllPostResponseModel.class);

    }

    @Override
    public Single<AllPostResponseModel> getAllPosts() {

        Rx2ANRequest.PostRequestBuilder postRequestBuilder = new Rx2ANRequest.PostRequestBuilder(Config.Api.GetAllPOSt);
        postRequestBuilder.addBodyParameter("userId", UserManager.getInstance().getMetaData().getUser().getId());
        postRequestBuilder.addBodyParameter("userType", "ustad");

        return postRequestBuilder.build().getObjectSingle(AllPostResponseModel.class);

    }

    @Override
    public Single<CommentResponseModel> getAllPostComment(int id) {

        Rx2ANRequest.PostRequestBuilder postRequestBuilder = new Rx2ANRequest.PostRequestBuilder(Config.Api.GET_ALL_COMMENTS);
        postRequestBuilder.addBodyParameter("postId", String.valueOf(id));
        return postRequestBuilder.build().getObjectSingle(CommentResponseModel.class);

    }

    @Override
    public Single<CommentResponseModel> addComment(int id, String text) {
        Rx2ANRequest.PostRequestBuilder postRequestBuilder = new Rx2ANRequest.PostRequestBuilder(Config.Api.ADD_COMMENT);
        postRequestBuilder.addBodyParameter("postId", String.valueOf(id));
        postRequestBuilder.addBodyParameter("userId", UserManager.getInstance().getMetaData().getUser().getId());
        postRequestBuilder.addBodyParameter("userType", "ustad");
        postRequestBuilder.addBodyParameter("text", text);
        return postRequestBuilder.build().getObjectSingle(CommentResponseModel.class);
    }
}
