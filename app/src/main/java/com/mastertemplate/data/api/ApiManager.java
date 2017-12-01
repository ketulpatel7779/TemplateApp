package com.mastertemplate.data.api;

import android.webkit.MimeTypeMap;

import com.mastertemplate.BuildConfig;
import com.mastertemplate.callback.OnResponse;
import com.mastertemplate.utils.CommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mastertemplate.utils.AppConstants.BASE_URL;


/**
 * This class contains necessary methods to make network requests
 */

public class ApiManager {
    /**
     * This method used to get @{@link ApiRepository} instance
     *
     * @return @{@link ApiRepository}
     */
    public static ApiRepository getApiRepository() {
        Retrofit retrofit = ApiManager.getRetrofitInstance();
        ApiRepository service = retrofit.create(ApiRepository.class);
        return service;
    }

    /**
     * This method returns default @{@link Retrofit} instance
     *
     * @return @{@link Retrofit}
     */
    private static Retrofit getRetrofitInstance() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.IS_DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            // add your other interceptors …
            httpClient.addInterceptor(logging);
        }
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    /**
     * This method will make network request and invoke @{@link OnResponse} listener on response
     *
     * @param onSuccessResponse listener to be invoked on response
     * @param call              object returned by calling any method inside ApiManager.getApiRepository()
     */
    public static <T extends Object> void executeRequest(final OnResponse onSuccessResponse, Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                try {
                    /*call onSuccess method of listener*/
                    onSuccessResponse.onSuccess(response.body());
                } catch (Exception e) {
                    /*parsing error call onError method of listener*/
                    onSuccessResponse.onError(e.getCause(), "Parsing error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                /*network error call onError method of listener*/
                onSuccessResponse.onError(t, "Webservice call failed");
            }
        });
    }

    /**
     * This method use to convert file object to  @{@link MultipartBody}
     *
     * @param fileMap map of parameter name and file to upload
     */

    public static List<MultipartBody.Part> prepareFilePart(HashMap<String, File> fileMap) {
        List<MultipartBody.Part> finalFileMap = new ArrayList<>();

            for (String paramName : fileMap.keySet()) {
                String mimeType = getMimeType(fileMap.get(paramName).getPath());
                CommonUtils.log(""+fileMap.get(paramName).getTotalSpace() + " is exist = "+mimeType);
                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse(mimeType),
                                fileMap.get(paramName)
                        );
                finalFileMap.add(MultipartBody.Part.createFormData(paramName, fileMap.get(paramName).getName(), requestFile));

            }

        // MultipartBody.Part is used to send also the actual file name
        return finalFileMap;
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}