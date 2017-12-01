package com.mastertemplate.data.api;

import com.mastertemplate.data.api.model.ForgotPasswordResponse;
import com.mastertemplate.data.api.model.LoginResponse;
import com.mastertemplate.data.api.model.RegisterResponse;
import com.mastertemplate.data.api.model.UploadResponse;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
    * Interface contains all the webservices signatures
   */
public interface ApiRepository {

    /**
     * Retrofit get annotation with our URL
     * And our method that will return us details.
    */

    /**
     * make POST login request
     * @param map map containing email,password as parameters
     */
    @POST("login.php")
    Call<LoginResponse> logInUser(@QueryMap HashMap<String,String> map);

    /**
     * make POST register user request
     * @param map map containing email,password as parameters
     */
    @POST("user_registration.php")
    Call<RegisterResponse> registerUser(@QueryMap HashMap<String,String> map);

    /**
     * make POST forgot password request
     * @param map map containing email as parameter
     */
    @POST("forgot_password.php")
    Call<ForgotPasswordResponse> forgotPassword(@QueryMap HashMap<String,String> map);

    /**
     * make POST upload image request
     * @param file containing image file to upload
     * @param partMap containing as parameters
     */
    @Multipart
    @POST("live_updates.php")
    Call<UploadResponse> uploadFile(@Part List<MultipartBody.Part> file,
                                    @QueryMap HashMap<String, String> partMap);

}