package com.wwf.opensourcechina.cachemanager;

import android.text.TextUtils;

import com.wwf.opensourcechina.utils.GsonUtil;

import java.util.List;

/**
 * 作者: old样
 * 描述:
 * 上海传智播客android黑马程序员
 **/

public class JsonCacheManager {

        private  JsonCacheManager() {

            }

            private static JsonCacheManager clss = new JsonCacheManager();

            public  static JsonCacheManager getInstance() {
                return clss;
            }


    //用户传地址返回一个数据
    public<T> T requestDataGet(String url, Class<T> clss) {
        //1请求网络数据
       /* NetManager netManager = new NetManager();*/
        String content = NetManager.getInstance().dataGet(url);//返回请求的json
        //2验证数据
        if (TextUtils.isEmpty(content)) {
            //空,去缓存拿数据
           // FileManager fileManager = new FileManager();
            content = FileManager.getInstance().getData(url);
        } else {
            //有,保存数据到缓存中
            //FileManager fileManager = new FileManager();
            FileManager.getInstance().saveData(url,content);
        }

        //3.有数据解析,没有数据直接返回
        if (TextUtils.isEmpty(content)) {
            //没有数据
            return null;
        } else {
           return GsonUtil.parseJsonToBean(content, clss);
        }

    }

    public<T> List<T> requestListData(String url, Class<T> clss) {
        //1请求网络数据
       /* NetManager netManager = new NetManager();*/
        String content = NetManager.getInstance().dataGet(url);//返回请求的json
        //2验证数据
        if (TextUtils.isEmpty(content)) {
            //空,去缓存拿数据
            // FileManager fileManager = new FileManager();
            content = FileManager.getInstance().getData(url);
        } else {
            //有,保存数据到缓存中
            //FileManager fileManager = new FileManager();
            FileManager.getInstance().saveData(url,content);
        }

        //3.有数据解析,没有数据直接返回
        if (TextUtils.isEmpty(content)) {
            //没有数据
            return null;
        } else {
            return GsonUtil.fromJsonArray(content, clss);
        }
    }
}
