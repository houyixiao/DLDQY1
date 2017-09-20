package com.ts.dldqy.kuangjia.utils;

import android.os.Build;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okhttp工具类
 * 请求服务器框架
 */
public class OKHttpUtils {
    private static final String TAG = "TsOKHttpUtils";
    private static OkHttpClient mHttpClient;
    private static Request request;

    /**
     * 异步post
     */
    public static void doPostAsy(String url, Map<String, Object> map, Callback callback) {
        //设置超时无效
        mHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
//                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
//                .writeTimeout(10, TimeUnit.SECONDS).build();//设置写入超时时间
         .connectTimeout(30, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS).build();//设置写入超时时间

//        mHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(2000 , TimeUnit.MILLISECONDS).build();//设置超时时间

//        mHttpClient = new OkHttpClient();
        request = PostRequest(url, map);
        mHttpClient.newCall(request).enqueue(callback);
    }

    public static void doPostAsy1(String url, Map<String, Object> map, Callback callback) {
        //设置超时无效
        mHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
//                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
//                .writeTimeout(10, TimeUnit.SECONDS).build();//设置写入超时时间
                .connectTimeout(30, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS).build();//设置写入超时时间

//        mHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(2000 , TimeUnit.MILLISECONDS).build();//设置超时时间

//        mHttpClient = new OkHttpClient();
        request = PostRequest1(url, map);
        mHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 异步get
     */
    public static void doGetAsy(String url, Map<String, Object> map, Callback callback) {
        String newUrl = url;
        if (map != null) {
            int bz = 1;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (bz == 1) {
                    newUrl += "?" + entry.getKey() + "=" + entry.getValue().toString();
                    bz++;
                } else {
                    newUrl += "&" + entry.getKey() + "=" + entry.getValue().toString();
                }
            }
        }
        Log.d(TAG, "NetRegisterGet: newUrl " + newUrl);
        mHttpClient = new OkHttpClient();
        final Request request = addHeaders().url(newUrl).build();
        mHttpClient.newCall(request).enqueue(callback);
    }


    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private static Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
//                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0");
        return builder;
    }

    /**
     * 构造post参数
     */
    private static Request PostRequest1(String url, Map<String, Object> map) {
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }


    private static Request PostRequest(String url, Map<String, Object> map) {
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().addHeader("cookie",Hawk.get("session").toString()).url(url).post(requestBody).build();
    }


    /**
     * 下载文件异步（有进度）
     *
     * @param fileUrl     文件url
     * @param destFileDir 存储目标目录
     */
    public static void downLoadFile(String fileUrl, final String destFileDir, final DownloadCallBack mDownloadCallBack) {
        final String fileName = null;
//        fileName = MD5Util.encodeByMD5(fileUrl);//获取文件名
//        fileName = FileUtil.getFileName(fileUrl);//获取文件名
        Log.d(TAG, "downLoadFile() called with: fileUrl = [" + fileUrl + "], destFileDir = [" + destFileDir + "], mDownloadCallBack = [" + mDownloadCallBack + "]");

        File folderFile = new File(destFileDir);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        final File file = new File(destFileDir, fileName);
        if (file.exists()) {
            mDownloadCallBack.onSuccess();
            Log.d(TAG, "downLoadFile: file.exists ");
            return;
        }
        final Request request = new Request.Builder().url(fileUrl).build();
        final Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure " + e.getMessage());
                mDownloadCallBack.onFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    Log.e(TAG, "total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        Log.e(TAG, "current------>" + current);
                        mDownloadCallBack.onProgress(getPercent(total, current));
                    }
                    fos.flush();
                    Log.d(TAG, "onResponse: 下载成功");
                    mDownloadCallBack.onSuccess();
//                    successCallBack((T) file, callBack);
                } catch (IOException e) {
                    Log.e(TAG, "下载失败 " + e.toString());
                    mDownloadCallBack.onFail(e.getMessage());
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        });
    }

    /**
     * 上传文件（异步）
     *
     * @param map:上传文件的路径
     */
    public static void uploadFileAsy(String url, Map<String, Object> map, Callback callBack) {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS).build();//设置写入超时时间;
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //遍历map
        File fileSinger;
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey() == "file"||"file".equals(entry.getKey())) {
                    fileSinger = new File(entry.getValue().toString());
                    builder.addFormDataPart(entry.getKey(), entry.getValue().toString(), RequestBody.create(MediaType.parse("*/*"), fileSinger));
                } else {
                    builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                }
            }
        }
        //添加参数
//        builder.addFormDataPart(NetUrlInterface.URL_PARAM_REQUESTPARAMS, params);  final String params,
//        for (FilesBean file : files
//                ) {
//            File fileSinger = new File(file.getFile());
//            builder.addFormDataPart("file", fileSinger.getName(), RequestBody.create(MediaType.parse("*/*"), fileSinger));//		MEDIA_TYPE_PNG
//            builder.addFormDataPart("caseNum", file.getCaseNum());
//            builder.addFormDataPart("caseYear", file.getCaseYear());
//            builder.addFormDataPart("loginCode", file.getLoginCode());
//            builder.addFormDataPart("chkItem", file.getChkItem());
//            builder.addFormDataPart("fileType", file.getFileType());
//            builder.addFormDataPart("type", file.getType());
//            builder.addFormDataPart("address", file.getAddress());
//            builder.addFormDataPart("fkey", file.getFkey());
//
//        }
//        files.clear();
        MultipartBody requestBody = builder.build();
        // 构建请求
//        request = new Request.Builder().url(url)// 地址
//                .post(requestBody)// 添加请求体
//                .build();
//        if (Constants.cookie == null) {
//            request = new Request.Builder().url(url)// 地址
//                    .post(requestBody)// 添加请求体
//                    .build();
//        } else {
        request = new Request.Builder().addHeader("cookie",Hawk.get("session").toString()).url(url).post(requestBody).build();
//        }
        mHttpClient.newCall(request).enqueue(callBack);
    }

    /**
     * 上传头像（异步）
     *
     * @param files:上传文件的路径
     */
    public static void uploadheadAsy(String url, List<String> files, Callback callBack) {
        mHttpClient = new OkHttpClient();
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加参数
//        builder.addFormDataPart(NetUrlInterface.URL_PARAM_REQUESTPARAMS, params);  final String params,
//        for (FilesBean file : files
//                ) {
//            File fileSinger = new File(file.getFile());
//            builder.addFormDataPart("file", fileSinger.getName(), RequestBody.create(MediaType.parse("*/*"), fileSinger));//		MEDIA_TYPE_PNG
//            builder.addFormDataPart("loginCode", file.getLoginCode());
//            builder.addFormDataPart("fileType", file.getFileType());
//            builder.addFormDataPart("fkey", file.getFkey());
//        }
        files.clear();
        MultipartBody requestBody = builder.build();
        // 构建请求
        request = new Request.Builder().url(url)// 地址
                .post(requestBody)// 添加请求体
                .build();

        new OkHttpClient().newCall(request).enqueue(callBack);
    }

    /**
     * 下载回调
     */
    public interface DownloadCallBack {

        void onFail(String sErrorCause);

        void onSuccess();

        /**
         * 响应进度更新
         */
        void onProgress(String sProgress);
    }

    /**
     * 得到百分比
     */
    private static String getPercent(long total, long current) {
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(0);
        float baifen = (float) current / total;
        return nt.format(baifen);
    }

    /**
     * 图片与文字混合post异步请求
     */
    public static void doImagePostAsy(String url, List<String> files, Callback callback) {
        //设置超时无效
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS).build();//设置写入超时时间
        request = PostImgRequest(url, files);
        mHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 构造post参数
     */
    private static Request PostImgRequest(String url, List<String> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加参数
//        for (FileBean file : files
//                ) {
//            File fileSinger = new File(file.getFile());
//            builder.addFormDataPart("file", fileSinger.getName(), RequestBody.create(MediaType.parse("*/*"), fileSinger));//		MEDIA_TYPE_PNG
//            builder.addFormDataPart("fCode", file.getfCode());
//            builder.addFormDataPart("fName", file.getfName());
//            builder.addFormDataPart("fCreator", file.getfCreator());
//            builder.addFormDataPart("fMember", file.getfMember());
//            builder.addFormDataPart("fkey", file.getFkey());
//        }
        files.clear();
        MultipartBody requestBody = builder.build();

        return new Request.Builder().url(url).post(requestBody).build();
    }


}
