package com.example.zhanyang.retrofitdemo2.presenter;

import android.widget.Toast;

import com.example.zhanyang.retrofitdemo2.MainActivity;
import com.example.zhanyang.retrofitdemo2.modle.net.ResponseInfo;
import com.example.zhanyang.retrofitdemo2.presenter.api.ResponseInfoApi;
import com.example.zhanyang.retrofitdemo2.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangyang on 2017/1/7 22:30.
 * version 1
 */
public class MainActivityPresenter {
    private MainActivity mainActivity;
    private ResponseInfoApi responseInfoApi;

    int DEFAULT_TIMEOUT=30;
    public MainActivityPresenter(MainActivity mainActivity) {
        this.mainActivity=mainActivity;

        //进行创建ResponseApi 实例
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        responseInfoApi=new Retrofit.Builder().baseUrl(Constants.BASE_URL).//
                                     addConverterFactory(GsonConverterFactory.create()).//
                                            client(okHttpClient).build().create(ResponseInfoApi.class);
    }

    /**
     * 进行上传单个文件
     * @param fileName
     */
    public void uploadSingleFile(String fileName) {
        //进行创建文件
        File file=new File(fileName);
        //通过RequestBody进行构建MultipartBody.part application/otcet-stream
        RequestBody requestBody=RequestBody.create(MediaType.parse("imgage/jpg"),file);
        MultipartBody.Part body=MultipartBody.Part.createFormData("aFile",file.getName(),requestBody);
        //进行构建描述类型的RequestBody的description
        RequestBody description=RequestBody.create(MediaType.parse("multipart/form-data"),"this is a description");
        //通过responseInfoApi进行上传单个文件
        responseInfoApi.upload(description,body).enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                if(response.code()==200){
                    Toast.makeText(mainActivity,"uploadSuccess",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mainActivity,"uploadCode:"+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mainActivity,"upload failure "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 通过Multipart上传多个文件
     * @param fileList
     */
    public void uploadFilesWithMultiPart(List<File> fileList) {
        List<MultipartBody.Part> parts=filesToMultiPart(fileList);
         responseInfoApi.uploadFilesWithParts(parts).enqueue(new Callback<ResponseInfo>() {
             @Override
             public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                 if(response.code()==200){
                     Toast.makeText(mainActivity,"uploadSuccess",Toast.LENGTH_SHORT).show();
                 }else{
                     Toast.makeText(mainActivity,"uploadCode:"+response.code(),Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<ResponseInfo> call, Throwable t) {
                 t.printStackTrace();
                 Toast.makeText(mainActivity,"upload failure",Toast.LENGTH_SHORT).show();
             }
         });
    }

    /**
     * 通过reqeustBody进行上传文件集合
     * @param files
     */
    public void uploadFilesWithRequestBody(List<File> files) {
        MultipartBody multipartBody=filesToMultipartBody(files);
        responseInfoApi.uploadFilesWithRequestBody(multipartBody).enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                if(response.code()==200){
                    Toast.makeText(mainActivity,"uploadSuccess",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mainActivity,"uploadCode:"+response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mainActivity,"upload failure",Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 将fileList转换为MultipartBody
     * @param fileList
     * @return
     */
    public MultipartBody filesToMultipartBody(List<File> fileList) {
        //创建builder实体
        MultipartBody.Builder builder=new MultipartBody.Builder();
        //循环文件将requestBody放入到MultipartBody
        for (File file : fileList) {
            RequestBody requestBody=RequestBody.create(MediaType.parse("application/otcet-steam"),file);
            builder.addFormDataPart("aFile",file.getName(),requestBody);
        }

        //进行设置类型
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody=builder.build();
        return multipartBody;
    }

    /**
     * 进行将list<File>转换成MultiBody.Part</>
     * @param files
     * @return
     */
    public List<MultipartBody.Part> filesToMultiPart(List<File> files) {
        List<MultipartBody.Part> parts=new ArrayList<MultipartBody.Part>();
        for (File file : files) {
            RequestBody requestBody=RequestBody.create(MediaType.parse("applicaiton/otcet-stream"),file);
            MultipartBody.Part part=MultipartBody.Part.createFormData("aFile",file.getName(),requestBody);
            parts.add(part);
        }
        return parts;
    }
}
