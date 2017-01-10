package com.example.zhanyang.retrofitdemo2;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zhanyang.retrofitdemo2.dragger.component.DaggerMainActivityComponent;
import com.example.zhanyang.retrofitdemo2.dragger.module.MainActivityModule;
import com.example.zhanyang.retrofitdemo2.presenter.MainActivityPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    MainActivityPresenter  mainActivityPresenter;

//    @InjectView(R.id.btn_upload_multi)
    Button btn_upload_multi;

//    @InjectView(R.id.btn_upload_single)
    Button btn_upload_single;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_upload_multi=(Button) findViewById(R.id.btn_upload_multi);
        btn_upload_single=(Button) findViewById(R.id.btn_upload_single);
//        ButterKnife.inject(this);
        DaggerMainActivityComponent daggerMainActivityComponent=
                (DaggerMainActivityComponent)DaggerMainActivityComponent.builder().mainActivityModule(new MainActivityModule(this)).build();
        daggerMainActivityComponent.in(this);

        final List<File> fileList=new ArrayList<File>();

//        String path = "/mnt/手机U盘";
        //test1.txt
        final String file1Path= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"3.jpg";
        File file=new File(file1Path);
        fileList.add(file);

        String file2Path= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"test2.txt";
        File file2=new File(file2Path);
        fileList.add(file2);

        String file3Path= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"test3.txt";
        File file3=new File(file3Path);
        fileList.add(file3);
        btn_upload_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //上传多个文件
//                mainActivityPresenter.uploadFilesWithMultiPart(fileList);
                mainActivityPresenter.uploadFilesWithRequestBody(fileList);
            }
        });


        btn_upload_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传单个文件
                mainActivityPresenter.uploadSingleFile(file1Path);
            }
        });
    }
}
