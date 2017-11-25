package com.wwf.opensourcechina.cachemanager;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者: old样
 * 描述:统一网络管理类
 * 上海传智播客android黑马程序员
 **/

public class NetManager {

    private  NetManager() {

        }

        private static NetManager clss = new NetManager();

        public  static NetManager getInstance() {
            return clss;
        }


    //get请求数据
    public String dataGet(String url) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            Request reqeust = new Request.Builder()
                    .get()
                    .url(url)//请求的地址
                    .build();
            Response response = okHttpClient.newCall(reqeust).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            //返回错误
            return null;
        }
    }

}
