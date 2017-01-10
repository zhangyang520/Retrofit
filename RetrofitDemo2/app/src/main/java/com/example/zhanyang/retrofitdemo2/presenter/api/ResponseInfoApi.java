package com.example.zhanyang.retrofitdemo2.presenter.api;

import com.example.zhanyang.retrofitdemo2.modle.net.ResponseInfo;
import com.example.zhanyang.retrofitdemo2.utils.Constants;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by zhangyang on 2017/1/7 22:33.
 * version 1
 */

public interface ResponseInfoApi {
    /**
     * 进行单文件上传
     * @param descritpion
     * @param file
     * @return
     */
    @Multipart
    @POST(Constants.UPLOAD)
    Call<ResponseInfo> upload(@Part("description") RequestBody descritpion, @Part MultipartBody.Part file);

    /**
     * 通过多个MultipartBody.Part集合的方式上传多个文件
     * @param parts
     * @return
     */
    @Multipart
    @POST(Constants.UPLOAD)
    Call<ResponseInfo> uploadFilesWithParts(@Part List<MultipartBody.Part> parts);

    /**
     * 通过RequestBody进行上传多个文件
     * @param multipartBody
     * @return
     */
    @POST(Constants.UPLOAD)
    Call<ResponseInfo> uploadFilesWithRequestBody(@Body MultipartBody multipartBody);
}


