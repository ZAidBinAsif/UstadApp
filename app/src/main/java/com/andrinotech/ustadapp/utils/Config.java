package com.andrinotech.ustadapp.utils;

/**
 * Created by ZaidAs on 3/1/2018.
 */

public class Config {

    public final static String BASE_URL = "http://192.168.42.71/appserver/public/api/";

    public class Api {
        public final static String SignIn = BASE_URL + "ustad/login?email={email}&password={password}";
        public final static String REGISTER = BASE_URL + "ustad/register?name={name}&username={username}&email={email}&password={password}";
        public final static String EDIT_PROFIEL = BASE_URL + "ustad/edit-profile";
        public final static String FORGOT_PASSWORD = BASE_URL + "ustad/send-code?email={email}";
        public final static String FORGOT_RESET_CODE = BASE_URL + "ustad/verify-code?email={email}&code={code}";
        public final static String CHANGE_PASSWORD = BASE_URL + "ustad/new-password?email={email}&password={password}";
        public final static String CHANGESTATUS = BASE_URL + "ustad/change-status";
        public final static String getAllPostsUstad = BASE_URL+"post/getAllPostsUstad";
        public final static String getPostById = BASE_URL+"post/getPostById";

        public final static String Edit_PASSWORD = BASE_URL + "ustad/edit-password?email={email}&password={password}";
        public final static String UPLOAD_IMAGE = BASE_URL + "ustad/upload-image";
        public final static String ADD_POST =BASE_URL+"post/make-post";
        public final static String ADD_LIKE =BASE_URL+"post/like";
        public final static String UNLIKE_POST =BASE_URL+"post/unlike";
        public final static String ADD_COMMENT=BASE_URL+"post/postComment";
        public final static String GET_ALL_COMMENTS =BASE_URL+"post/getPostComments";
        public final static String GetAllPOSt =BASE_URL+"post/get-all-posts";

    }
}
