package com.andrinotech.ustadapp.ui.Post;


import com.andrinotech.ustadapp.UstadApp;
import com.andrinotech.ustadapp.data.UserManager;
import com.andrinotech.ustadapp.ui.base.BaseViewModel;
import com.andrinotech.ustadapp.utils.NetworkUtils;
import com.andrinotech.ustadapp.utils.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PostViewModel extends BaseViewModel<PostCallback> {
    public enum validationEnum {
        title, descrtion, category
    }

    public boolean areFieldsValid(String title, String descriptoon, String category) {
        boolean isValid = true;
        if (StringUtils.isNullOrEmpty(title)) {
            getmCallback().ValidationError(validationEnum.title);
            isValid = false;
        }
        if (StringUtils.isNullOrEmpty(descriptoon)) {
            getmCallback().ValidationError(validationEnum.descrtion);
            isValid = false;
        }
        if (StringUtils.isNullOrEmpty(category)) {
            getmCallback().ValidationError(validationEnum.category);
            isValid = false;
        }

        return isValid;
    }



    public void addPost(String title, String descriptoon, String category) {
        if (areFieldsValid(title, descriptoon, category)) {
            AddPostApiModel addPostApiModel = new AddPostApiModel();
            PostModel postModel = new PostModel(title, descriptoon, "Text", category, UserManager.getInstance().getMetaData().getUser().getId(), "");
            addPostApiModel.setPostModel(postModel);
            getmCompositeDisposable().add(getmRequestHandler()
                    .AddPost(addPostApiModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AddPostResponseModel>() {
                        @Override
                        public void accept(AddPostResponseModel addPostResponseModel) throws Exception {
                            if (addPostResponseModel == null) {
                                getmCallback().ErrorOnAddPost("Not Available");
                                return;
                            } else if (addPostResponseModel.getError().getCode() == 302) {
                                getmCallback().ErrorOnAddPost(addPostResponseModel.getError().getMessage());
                                return;
                            }
                            getmCallback().SuccessFullyAdded();

                        }

                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (NetworkUtils.isNetworkConnected(UstadApp.getInstance().getApplicationContext())) {
                                getmCallback().ErrorOnAddPost("Check your internet connection");

                            } else {
                                getmCallback().ErrorOnAddPost("No Internet Connection");

                            }

                        }
                    }));
        }

    }

    public void likepost(int id, final int pos) {
        getmCompositeDisposable().add(getmRequestHandler()
                .addlike(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<LikePostResponseModel>() {
                    @Override
                    public void accept(LikePostResponseModel addPostResponseModel) throws Exception {
                        if (addPostResponseModel == null) {
                            getmCallback().ErrorOnAddPost("Not Available");
                            return;
                        } else if (addPostResponseModel.getError().getCode() == 302) {
                            getmCallback().ErrorOnAddPost(addPostResponseModel.getError().getMessage());
                            return;
                        }
                        getmCallback().likeAdded(pos, addPostResponseModel);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (NetworkUtils.isNetworkConnected(UstadApp.getInstance().getApplicationContext())) {
                            getmCallback().ErrorOnAddPost("Check your internet connection");

                        } else {
                            getmCallback().ErrorOnAddPost("No Internet Connection");

                        }

                    }
                }));

    }

    public void unlikepost(int id, final int pos) {
        getmCompositeDisposable().add(getmRequestHandler()
                .posUnlike(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<LikePostResponseModel>() {
                    @Override
                    public void accept(LikePostResponseModel addPostResponseModel) throws Exception {
                        if (addPostResponseModel == null) {
                            getmCallback().ErrorOnAddPost("Not Available");
                            return;
                        } else if (addPostResponseModel.getError().getCode() == 302) {
                            getmCallback().ErrorOnAddPost(addPostResponseModel.getError().getMessage());
                            return;
                        }
                        getmCallback().likeAdded(pos, addPostResponseModel);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (NetworkUtils.isNetworkConnected(UstadApp.getInstance().getApplicationContext())) {
                            getmCallback().ErrorOnAddPost("Check your internet connection");
                        } else {
                            getmCallback().ErrorOnAddPost("No Internet Connection");

                        }

                    }
                }));

    }

    public void getPosts() {
        getmCompositeDisposable().add(getmRequestHandler()
                .getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AllPostResponseModel>() {
                    @Override
                    public void accept(AllPostResponseModel getPostResponseModel) throws Exception {
                        if (getPostResponseModel == null) {
                            getmCallback().ErrorOnAddPost("Not Available");
                            return;
                        } else if (getPostResponseModel.getError().getCode() == 302) {
                            getmCallback().ErrorOnAddPost(getPostResponseModel.getError().getMessage());
                            return;
                        }
                        getmCallback().allposts(getPostResponseModel.getPostModel());

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (NetworkUtils.isNetworkConnected(UstadApp.getInstance().getApplicationContext())) {
                            getmCallback().ErrorOnAddPost("Check your internet connection");

                        } else {
                            getmCallback().ErrorOnAddPost("No Internet Connection");

                        }

                    }
                }));
    }

    public void getPostComment(int id) {
        getmCompositeDisposable().add(getmRequestHandler()
                .getAllPostComment(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CommentResponseModel>() {
                    @Override
                    public void accept(CommentResponseModel getPostResponseModel) throws Exception {
                        if (getPostResponseModel == null) {
                            getmCallback().ErrorOnAddPost("Not Available");
                            return;
                        } else if (getPostResponseModel.getError().getCode() == 302) {
                            getmCallback().ErrorOnAddPost(getPostResponseModel.getError().getMessage());
                            return;
                        }
                        getmCallback().allcomments(getPostResponseModel.getComments());

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (NetworkUtils.isNetworkConnected(UstadApp.getInstance().getApplicationContext())) {
                            getmCallback().ErrorOnAddPost("Check your internet connection");

                        } else {
                            getmCallback().ErrorOnAddPost("No Internet Connection");

                        }

                    }
                }));
    }
    public void addComment(String text, int id) {
        getmCompositeDisposable().add(getmRequestHandler()
                .addComment(id, text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CommentResponseModel>() {
                    @Override
                    public void accept(CommentResponseModel addPostResponseModel) throws Exception {
                        if (addPostResponseModel == null) {
                            getmCallback().ErrorOnAddPost("Not Available");
                            return;
                        } else if (addPostResponseModel.getError().getCode() == 302) {
                            getmCallback().ErrorOnAddPost(addPostResponseModel.getError().getMessage());
                            return;
                        }
                        getmCallback().allcomments(addPostResponseModel.getComments());

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (NetworkUtils.isNetworkConnected(UstadApp.getInstance().getApplicationContext())) {
                            getmCallback().ErrorOnAddPost("Check your internet connection");

                        } else {
                            getmCallback().ErrorOnAddPost("No Internet Connection");

                        }

                    }
                }));


    }
    public void getPostsofUstad(int id) {
        getmCompositeDisposable().add(getmRequestHandler()
                .getPostOfUstad(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AllPostResponseModel>() {
                    @Override
                    public void accept(AllPostResponseModel getPostResponseModel) throws Exception {
                        if (getPostResponseModel == null) {
                            getmCallback().ErrorOnAddPost("Not Available");
                            return;
                        } else if (getPostResponseModel.getError().getCode() == 302) {
                            getmCallback().ErrorOnAddPost(getPostResponseModel.getError().getMessage());
                            return;
                        }
                        getmCallback().allposts(getPostResponseModel.getPostModel());

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (NetworkUtils.isNetworkConnected(UstadApp.getInstance().getApplicationContext())) {
                            getmCallback().ErrorOnAddPost("Check your internet connection");

                        } else {
                            getmCallback().ErrorOnAddPost("No Internet Connection");

                        }

                    }
                }));
    }
}
