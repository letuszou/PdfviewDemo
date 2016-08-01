package com.example.pdfviewdemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.joanzapata.pdfview.PDFView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

public class MainActivity extends AppCompatActivity implements  OnPageChangeListener{
    public static final String Pdf_Url="http://bmob-cdn-5204.b0.upaiyun.com/2016/07/31/9b18e3c0400204d7808253a77b8e706a.pdf";
    private int pageNumber = 1;
    private PDFView pdfview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs
        setContentView(R.layout.activity_pdfview);
        pdfview = (PDFView) findViewById(R.id.pdfview);
        downloadPdf();
    }


    private void downloadPdf(){

        try {
            com.lidroid.xutils.HttpUtils http = new com.lidroid.xutils.HttpUtils();
            http.download(Pdf_Url,  Environment.getExternalStorageDirectory()+"/provisional.pdf", true, false, new RequestCallBack<File>() {
                @Override
                public void onStart() {
                }
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                }
                @Override
                public void onFailure(HttpException error, String msg) {
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    File file = new File(Environment.getExternalStorageDirectory()+"/provisional.pdf");
                    readPdf(file);
                }
            });
        } catch (Exception e) {
            Log.d("error", e.toString());
        }

    }

    private void readPdf(File file) {
        pdfview.fromFile(file)
                .defaultPage(pageNumber)
                .load();
    }




    //这个下载网络上的pdf到sd卡上的代码  然后进行本地读取  在失去焦点的时候删除下载的文件
//    删除代码
    @Override
    protected void onPause() {
        super.onPause();
        File file = new File(Environment.getExternalStorageDirectory()+"/provisional.pdf");
        if (file.exists()) {
            file.delete();
        }

    }


    //继承接口后可以继承的方法
    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//注意  诸位 如果从网上下载pdfview的类库 可能需要 修改类库的build.gradle里面的版本号啊

    //还有问题@我的呀

}