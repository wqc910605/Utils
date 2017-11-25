package com.wwf.opensourcechina.cachemanager;

import android.os.Environment;

import com.wwf.opensourcechina.application.OSCApplication;
import com.wwf.opensourcechina.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 作者: old样
 * 描述:文件读写管理类
 * 上海传智播客android黑马程序员
 **/

public class FileManager {

    private final String mSaveDir;

    private FileManager() {
        //创建目录
        mSaveDir = Environment.getExternalStorageDirectory().getPath() + File.separator + OSCApplication.getContext().getPackageName() + File.separator + "cache";

        File dirDile = new File(mSaveDir);
        if (!dirDile.exists()) {
            dirDile.mkdirs();
        }
    }

    private static FileManager clss = new FileManager();

    public static FileManager getInstance() {
        return clss;
    }

    // 保存数据
    public void saveData(String url, String content) {
        //TODO:后期实现
        //数据库:curd
        //sp:配置
        //网络,不考虑
        //文件:读写

        //java中多级目录需要手动创建
        //sd/包名/cache/xxx
        //File.separator跨平台分隔线
    /*    String saveDir = Environment.getExternalStorageDirectory().getPath() + File.separator + GooglePlay.context.getPackageName() + File.separator + "cache";
        //手动创建目录
        File dir = new File(saveDir);
        if (!dir.exists()) {
            //不存在,创建
            dir.mkdirs();//创建多级目录
        }*/

        //保存数据到文件中
        try {
            File saveFile = new File(mSaveDir, getMd5FileName(url));
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            fileOutputStream.write(content.getBytes());//写文件
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            //不需要处理
            ToastUtil.show("sd卡松动!!");
        }


    }

    //得到md5的url
    private String getMd5FileName(String url) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            byte[] digest = messageDigest.digest();
            for (int i = 0; i < digest.length; i++) {
                String toHexString = Integer.toHexString(digest[i]&0xFF);//取后两位有效值
                System.out.println(toHexString);
                stringBuffer.append(toHexString);
            }


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }

    //取数据
    public String getData(String url) {
        //TODO:后期实现
        //FIXME:后期加强
 /*       //创建目录
        String saveDir = Environment.getExternalStorageDirectory().getPath() + File.separator + GooglePlay.context.getPackageName() + File.separator + "cache";

        File dirDile = new File(saveDir);
        if (!dirDile.exists()) {
            dirDile.mkdirs();
        }*/


        StringBuffer stringBuffer = new StringBuffer();
        FileInputStream fileInputStream = null;
        try {
            File file = new File(mSaveDir, getMd5FileName(url));
            fileInputStream = new FileInputStream(file);

            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = fileInputStream.read(buffer)) != -1) {
                stringBuffer.append(new String(buffer, 0, len));
            }


        } catch (Exception e) {
            e.printStackTrace();
            //得到文件有异常要不要处理
            //这里一定要处理
            return null;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return stringBuffer.toString();
    }

}
