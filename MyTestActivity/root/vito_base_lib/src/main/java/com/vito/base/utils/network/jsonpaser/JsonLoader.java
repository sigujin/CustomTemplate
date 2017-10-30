package com.vito.base.utils.network.jsonpaser;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.vito.base.utils.network.httplibslc.HttpRequest;
import com.vito.base.utils.network.httplibslc.JsonLoaderCallBack;
import com.vito.base.utils.network.httplibslc.RequestVo;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by lennon on 2016/2/24.
 */
public class JsonLoader {

    protected static final int BUFFER_SIZE = 32 * 1024; // 32 Kb
    private static final int HTTP_REQUEST_TIMEOUT = 10 * 1000; // 10 s
    public static HashSet<AsyncTask> sTaskSet = new HashSet();
    private Context mContext = null;
    //    private JsonLoaderCallBack mJsonLoaderCallBack = null;
    private WeakReference<JsonLoaderCallBack> mJsonLoaderCallBack;

    public void setIsAllHtml(boolean mIsAllHtml) {
        this.mIsAllHtml = mIsAllHtml;
    }

    boolean mIsAllHtml = false;

    public JsonLoader(Context context,
                      JsonLoaderCallBack in_JsonLoaderCallBack) {
        mContext = context;
        mJsonLoaderCallBack = new WeakReference<JsonLoaderCallBack>(in_JsonLoaderCallBack);
    }

    private static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    public void setJsonLoaderCallBack(JsonLoaderCallBack in_JsonLoaderCallBack) {
        mJsonLoaderCallBack = new WeakReference<JsonLoaderCallBack>(in_JsonLoaderCallBack);
    }

    private InputStream getStream(RequestVo requestVo, Class<?> mClazz, TypeReference refrence, String methodtype) throws IOException {
        switch (Scheme.ofUri(requestVo.requestUrl)) {
            case HTTP:
            case HTTPS:
                return loadStrFromServer(requestVo, methodtype);
            case FILE:
                return getStreamFromFile(requestVo.requestUrl);
            case CONTENT:
                return getStreamFromContent(requestVo.requestUrl);
            case ASSETS:
                return getStreamFromAssets(requestVo.requestUrl);
            case RAW:
                return getStreamFromRaw(requestVo.requestUrl);
            case UNKNOWN:
            default:
                throw new IOException("invalid Scheme uri = " + requestVo.requestUrl);
        }
    }

    /**
     * Retrieves {@link InputStream} of image by URI (image is located on the
     * local file system or SD card).
     *
     * @return {@link InputStream} of image
     * @throws IOException if some I/O error occurs reading from file system
     */
    protected InputStream getStreamFromFile(String reqeustURI) throws IOException {
        String filePath = Scheme.FILE.crop(reqeustURI);
        {
            BufferedInputStream imageStream = new BufferedInputStream(
                    new FileInputStream(filePath), BUFFER_SIZE);
            return imageStream;
        }
    }

    /**
     * Retrieves {@link InputStream} of json file by URI (file is accessed using
     * {@link ContentResolver}).
     *
     * @return {@link InputStream} of json file
     * @throws FileNotFoundException if the provided URI could not be opened
     */
    protected InputStream getStreamFromContent(String reqeustURI) throws FileNotFoundException {
        ContentResolver res = mContext.getContentResolver();
        Uri uri = Uri.parse(reqeustURI);
        return res.openInputStream(uri);
    }

    /**
     * Retrieves {@link InputStream} of image by URI (image is located in assets
     * of application).
     *
     * @param reqeustURI reqeustURI URI
     * @return {@link InputStream} of image
     * @throws IOException if some I/O error occurs file reading
     */
    protected InputStream getStreamFromAssets(String reqeustURI) throws IOException {
        String filePath = Scheme.ASSETS.crop(reqeustURI);
        return mContext.getAssets().open(filePath);
    }

    /**
     * Retrieves {@link InputStream} of image by URI (image is located in
     * drawable resources of application).
     *
     * @return {@link InputStream} of image
     */
    protected InputStream getStreamFromRaw(String reqeustURI) {
        String drawableIdString = Scheme.RAW.crop(reqeustURI);
        int drawableId = Integer.parseInt(drawableIdString);
        return mContext.getResources().openRawResource(drawableId);
    }

    /**
     * @param requestVo  资源路径(http:// ; assets://)
     * @param clazz      json解析后的类型
     * @param refrence   json解析后的类型 （ clazz、refrence其中任意一项为null）
     * @param methodtype 只针对http 其他请求为null  MethodType_Get,MethodType_Post
     * @param reqcode    请求index 用于区分请求
     */
    public void load(final RequestVo requestVo, final Class<?> clazz, final TypeReference refrence
            , MethodType methodtype, int reqcode) {
        if (requestVo.requestDataMap == null)
            requestVo.requestDataMap = new HashMap<String, String>();
        requestVo.requestDataMap.put("msgId", UUidUtils.getUUID());

        JsonLoaderAsyncTask asyncTask = new JsonLoaderAsyncTask(requestVo, clazz, refrence
                , methodtype, reqcode);
        asyncTask.execute(1000);
        sTaskSet.add(asyncTask);


    }

    public InputStream loadStrFromServer(RequestVo requestVo, String MethodType) throws UnsupportedEncodingException {
        String requestData = null;
        if (MethodType.equals("Post"))
            requestData = HttpRequest.getInstance().postRequest(requestVo);
        else if (MethodType.equals("Get"))
            requestData = HttpRequest.getInstance().getRequest(requestVo);

        InputStream in_withcode = null;
        if (requestData != null)
            in_withcode = new ByteArrayInputStream(requestData.getBytes("UTF-8"));
        return in_withcode;
    }

    /**
     * Represents supported schemes(protocols) of URI. Provides convenient
     * methods for work with schemes and URIs.
     */

    public static enum MethodType {
        MethodType_Get,
        MethodType_Post
    }

    public static enum JsonError {
        ENVIROMENT_ERROR(-1, "环境异常"), JSONPARSEERROR(-2,
                "Json数据解析错误"), JSONMAPPINGERROR(-3, "Json数据错误"), IOERROR(
                -4, "IO Error"), UNKNOWNERROR(-10, "请检查网络状态"), JSONEMPTYDATA(-11, "数据为空");

        private int code;
        private String msg;

        JsonError(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

    }

    /**
     * Represents supported schemes(protocols) of URI. Provides convenient
     * methods for work with schemes and URIs.
     */
    public enum Scheme {
        HTTP("http"), HTTPS("https"), FILE("file"), CONTENT("content"), ASSETS(
                "assets"), RAW("raw"), UNKNOWN("");

        private String scheme;
        private String uriPrefix;

        Scheme(String scheme) {
            this.scheme = scheme;
            uriPrefix = scheme + "://";
        }

        /**
         * Defines scheme of incoming URI
         *
         * @param uri URI for scheme detection
         * @return Scheme of incoming URI
         */
        public static Scheme ofUri(String uri) {
            if (uri != null) {
                for (Scheme s : values()) {
                    if (s.belongsTo(uri)) {
                        return s;
                    }
                }
            }
            return UNKNOWN;
        }

        private boolean belongsTo(String uri) {
            return uri.toLowerCase(Locale.US).startsWith(uriPrefix);
        }

        /**
         * Appends scheme to incoming path
         */
        public String wrap(String path) {
            return uriPrefix + path;
        }

        /**
         * Removed scheme part ("scheme://") from incoming URI
         */
        public String crop(String uri) {
            if (!belongsTo(uri)) {
                throw new IllegalArgumentException(String.format(
                        "URI [%1$s] doesn't have expected scheme [%2$s]", uri,
                        scheme));
            }
            return uri.substring(uriPrefix.length());
        }
    }

    public class JsonLoaderAsyncTask extends AsyncTask<Integer, Integer, String> {
        private int errcode = 0;
        private String errmsg = "";

        private int mMsgWhat = -1;
        private RequestVo mRequestVo;
        private Class<?> mClazz;
        private TypeReference mRefrence;
        private MethodType mMethodType;

        public JsonLoaderAsyncTask(RequestVo in_requestVo, final Class<?> in_mClazz, final TypeReference in_refrence
                , MethodType in_methodtype, int in_recode) {
            super();
            mMsgWhat = in_recode;
            mRequestVo = in_requestVo;
            mClazz = in_mClazz;
            mRefrence = in_refrence;
            mMethodType = in_methodtype;
        }


        /**
         * 这里的Integer参数对应AsyncTask中的第一个参数
         * 这里的String返回值对应AsyncTask的第三个参数
         * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
         * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
         */
        @Override
        protected String doInBackground(Integer... params) {
            String result = null;
            if (mContext == null
                    || mJsonLoaderCallBack.get() == null || (mClazz == null && mRefrence == null)) {
                errcode = JsonError.ENVIROMENT_ERROR.getCode();
                errmsg = JsonError.ENVIROMENT_ERROR.getMsg();
                return null;
            }

            String re = null;
            try {
                InputStream is = getStream(mRequestVo, mClazz, mRefrence,
                        mMethodType == MethodType.MethodType_Get ? "Get" :
                                mMethodType == MethodType.MethodType_Post ? "Post" : "Get");
                if (is == null) {
                    errcode = JsonError.UNKNOWNERROR.getCode();
                    errmsg = JsonError.UNKNOWNERROR.getMsg();
                    return null;
                }

                if (mIsAllHtml)
                    re = inputStream2String(is);
                else
                    re = Html.fromHtml(inputStream2String(is)).toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return re;
        }

        /**
         * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
         * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
         */
        @Override
        protected void onPostExecute(String result) {
            Log.i("ch", result + "result");
            if (result == null && errcode < 0) {
                if (mJsonLoaderCallBack.get() != null)
                    mJsonLoaderCallBack.get().onJsonDataGetFailed(errcode, errmsg, mMsgWhat);
                return;
            }
            try {
                ObjectMapper objectMapper = JsonUtils.createObjectMapper();
                objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);//临时解决转义字符问题
                Object readValue = null;
                readValue = mClazz != null ? objectMapper.readValue(result, mClazz) :
                        objectMapper.readValue(result, mRefrence);
                if (readValue != null && mJsonLoaderCallBack.get() != null)
                    mJsonLoaderCallBack.get().onJsonDataGetSuccess(readValue, mMsgWhat);
                else if (mJsonLoaderCallBack.get() != null)
                    mJsonLoaderCallBack.get().onJsonDataGetFailed(JsonError.JSONEMPTYDATA.getCode(),
                            JsonError.JSONEMPTYDATA.getMsg(), mMsgWhat);
            } catch (JsonParseException e) {
                e.printStackTrace();
                errcode = JsonError.JSONPARSEERROR.getCode();
                errmsg = JsonError.JSONPARSEERROR.getMsg();
                if (mJsonLoaderCallBack.get() != null)
                    mJsonLoaderCallBack.get().onJsonDataGetFailed(errcode, errmsg, mMsgWhat);
            } catch (JsonMappingException e) {
                e.printStackTrace();
                errcode = JsonError.JSONMAPPINGERROR.getCode();
                errmsg = JsonError.JSONMAPPINGERROR.getMsg();
                if (mJsonLoaderCallBack.get() != null)
                    mJsonLoaderCallBack.get().onJsonDataGetFailed(errcode, errmsg, mMsgWhat);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                errcode = JsonError.UNKNOWNERROR.getCode();
                errmsg = JsonError.UNKNOWNERROR.getMsg();
                if (mJsonLoaderCallBack.get() != null)
                    mJsonLoaderCallBack.get().onJsonDataGetFailed(errcode, errmsg, mMsgWhat);
            }
        }


        //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
        @Override
        protected void onPreExecute() {
//            textView.setText("开始执行异步线程");
        }


        /**
         * 这里的Intege参数对应AsyncTask中的第二个参数
         * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
         * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
        }

    }
}

