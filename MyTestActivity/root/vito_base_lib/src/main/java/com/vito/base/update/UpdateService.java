package com.vito.base.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.vito.base.R;
import com.vito.base.jsonbean.UpdateBean;
import com.vito.base.jsonbean.VitoJsonTemplateBean;
import com.vito.base.utils.ToastShow;
import com.vito.base.utils.network.httplibslc.JsonLoaderCallBack;
import com.vito.base.utils.network.httplibslc.RequestVo;
import com.vito.base.utils.network.jsonpaser.JsonLoader;

import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;

public class UpdateService extends Service implements Handler.Callback {

    private static final int SHOW_UPDATE_DIALOG = 1000;
    private static final int DISMISS_PROGRESS_DG = 1001;
    private static final int DOWNLOAD_PROGRESS = 1002;
    private static final int DOWNLOAD_END = 1003;
    private static final int SHOW_NO_MEMORY = 1004;
    private static final int TIME_OVER = 1005;
    private static final int MSG_DOWNLOAD_ERROR_TIMEOUT = 1006;
    private static final int DOWNLOAD_FAILED = 1007;
    private static final int DOWNLOADING = 1009;
    private static final int HADUPDATESUCCESSED = 1010;
    private static final int DOWNLOAD_PERIOD = 60 * 1000;
    private static final HandlerThread sUpdateThread = new HandlerThread("launcher-update") {{
        start();
    }};
    private static final String TAG = UpdateService.class.getName();
    private static final Handler handlerUpdate = new Handler(
            sUpdateThread.getLooper());

    // 实例化自定义的Binder类
    private final IBinder mBinder = new LocalBinder();
    public int mFileSize = 0;
    public int downloadProgress = 0;
    public int mTag = 0;
    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            return (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0);
        }
    };
    private IDialog mDialog = null;
    private ProgressDialog mPbarDialog = null;
    private Handler mHandler = null;
    private UpdateBean mUpdateInfo = null;
    private Activity mActivity = null;
    private String version;
    private boolean isRunnableRunning = false;


    JsonLoaderCallBack mJsonLoaderCallBack = new JsonLoaderCallBack() {
        @Override
        public void onJsonDataGetSuccess(Object re_data, int reqcode) {
            isRunnableRunning = false;
            VitoJsonTemplateBean<UpdateBean> bean = (VitoJsonTemplateBean) re_data;
            if (bean.getCode() != 0) {
                return;
            }
            if (bean.getCode() == 0 && mTag == 1 && bean.getData().getVersionCode() == null) {
                Message mess = mHandler.obtainMessage();
                mess.what = HADUPDATESUCCESSED;
                mHandler.sendMessage(mess);
                isRunnableRunning = false;
                return;
            }
            mUpdateInfo = bean.getData();
            Log.d(TAG, "mUpdateInfo.version=" + mUpdateInfo.getVersionCode());
            Log.d(TAG, "version=" + version);
            Log.d(TAG, "mUpdateInfo.download_url=" + mUpdateInfo.getDownloadUrl());
            if (mUpdateInfo != null
                    && mUpdateInfo.getDownloadUrl() != null) {
                Log.d(TAG, "enter if");
                while (true) {
                    if (mActivity == null) {
                        try {
                            Thread.sleep(3 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
                Log.d(TAG, "getAvailableInternalMemorySize :" + getAvailableInternalMemorySize());
                Log.d(TAG, "mFileSize :" + mFileSize);
                if (getAvailableInternalMemorySize() < mFileSize) {
                    Message mess = mHandler.obtainMessage();
                    mess.what = SHOW_NO_MEMORY;
                    mHandler.sendMessage(mess);
                } else {
                    Message mess = mHandler.obtainMessage();
                    mess.what = SHOW_UPDATE_DIALOG;
                    mess.arg1 = mFileSize;
                    mess.obj = mUpdateInfo;
                    mHandler.sendMessage(mess);
                }
            }
        }

        @Override
        public void onJsonDataGetFailed(int re_code, String re_info, int reqcode) {
            isRunnableRunning = false;
            Log.d(TAG, "update failed : " + re_info);
        }
    };

    public UpdateService() {
        mHandler = new Handler(this);
    }

    /**
     * 获取当前/data分区剩余空间
     */
    static public long getAvailableInternalMemorySize() {

        File path = Environment.getDataDirectory();

        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSize();

        long availableBlocks = stat.getAvailableBlocks();

        return availableBlocks * blockSize;

    }

    public void startUpdate(int tag) {
        if (handlerUpdate.hasMessages(1) || isRunnableRunning) {
            ToastShow.toastShow("后台正在下载，请稍等！", ToastShow.LENGTH_SHORT);
            return;
        }
        mTag = tag;
        version = getVersion();
        JsonLoader loader = new JsonLoader(this, mJsonLoaderCallBack);
        RequestVo requestVo = new RequestVo();
        requestVo.requestDataMap = new HashMap<>();
        requestVo.requestDataMap.put("sysType", UpdateConfig.getInstance().getSysType());
        requestVo.requestDataMap.put("apkType", "android");
        requestVo.requestDataMap.put("versionCode", String.valueOf(version));
        requestVo.requestUrl = UpdateConfig.getInstance().getGetUpdateURL();

        loader.load(requestVo, null, new TypeReference<VitoJsonTemplateBean<UpdateBean>>() {
        }, JsonLoader.MethodType.MethodType_Get, 0);
        isRunnableRunning = true;

        Message msg = Message.obtain(handlerUpdate);
        msg.what = 1;
        handlerUpdate.sendMessage(msg);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void setActivity(Activity act) {
        mActivity = act;
    }

    public void showUpdateDialog(String title, String tip, String content,
                                 final int showButtons) {

        mDialog = new IDialog(mActivity);
        mDialog.setIcon(R.drawable.update_dialog_icon);
        mDialog.setTitleView(title);
        mDialog.setOnKeyListener(keylistener);
        String text = "-欢迎使用最新版本，更新如下-" + "\n"
                + "\n" + content;
        String message1 = text.replace("<br />", " \n");
        String message = message1.replace("<br>", "");
        mDialog.setMessage(message);

        mDialog.setPositiveButton("立刻升级", new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDialog.dismiss();
                if (getAvailableInternalMemorySize() < mFileSize) {
                    Message mess = mHandler.obtainMessage();
                    mess.what = SHOW_NO_MEMORY;
                    mHandler.sendMessage(mess);
                } else {
                    Message message = mHandler.obtainMessage();
                    message.what = TIME_OVER;
                    mHandler.sendMessage(message);
                }
            }
        });
        if (mUpdateInfo.getIsForce().equals(UpdateBean.NORMAL_UPDATE)) {
            mDialog.setNegativeButton("暂不升级", new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        }
        mDialog.show();
    }

    public void updateDialog() {
        String newVer = mUpdateInfo.getVersionCode();
        String apkURL = UpdateConfig.getInstance().getBaseUrl() + mUpdateInfo.getDownloadUrl();
        showDownloadDialog(apkURL, String.valueOf(newVer));
    }

    private void showDownloadDialog(String apkUrl, String apkVersion) {
        downloadApk(apkUrl, apkVersion);
    }

    private void downloadPage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri url = Uri.parse("http://app.qq.com/#id=detail&appid=1104787567");
        intent.setData(url);
        this.startActivity(intent);
    }

    public void downloadApk(String apkUrl, String apkVersion) {
        String apkName = "new_swcity.apk";
        String apkPath = getFilesDir() + "/" + apkName;

        try {
            File apkFile = new File(apkPath);
            if (apkFile.exists()) {
                apkFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        start_update_thread(apkUrl, apkPath, apkName);
    }

    private void start_update_thread(final String apkUrl, final String apkPath,
                                     final String apkName) {
        Log.d(TAG, "apkUrl : " + apkUrl);
        Runnable runable = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                isRunnableRunning = true;

                try {
                    int lProgress = 0;
                    int total = 0;
                    int s_apk_total = 0;
                    InputStream is = null;
                    FileOutputStream fos = null;
                    HttpURLConnection conn;
                    URL url = new URL(apkUrl);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(DOWNLOAD_PERIOD);
                    conn.setReadTimeout(DOWNLOAD_PERIOD * 2);
                    s_apk_total = conn.getContentLength();
                    if (s_apk_total == -1) {
                        s_apk_total = mFileSize;
                    }
                    is = conn.getInputStream();
                    fos = openFileOutput(apkName, Context.MODE_WORLD_READABLE);
                    byte[] buffer = new byte[1024];
                    int len = is.read(buffer);
                    while (len > 0) {
                        total += len;
                        downloadProgress = (Double.valueOf((total * 1.0
                                / s_apk_total * 100))).intValue();
                        if (downloadProgress != lProgress) {
                            lProgress = downloadProgress;
                            Message mess = mHandler.obtainMessage();
                            mess.what = DOWNLOAD_PROGRESS;
                            mess.obj = downloadProgress;
                            mHandler.sendMessage(mess);
                        }

                        fos.write(buffer, 0, len);
                        len = is.read(buffer);
                    }

                    fos.flush();
                    is.close();
                    fos.close();
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    Message mess = mHandler.obtainMessage();
                    mess.what = MSG_DOWNLOAD_ERROR_TIMEOUT;
                    mHandler.sendMessage(mess);
                    isRunnableRunning = false;
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    Message mess = mHandler.obtainMessage();
                    mess.what = DISMISS_PROGRESS_DG;
                    mHandler.sendMessage(mess);
                }
                if (downloadProgress == 100) {
                    Message messend = mHandler.obtainMessage();
                    messend.what = DOWNLOAD_END;
                    messend.obj = apkPath;
                    mHandler.sendMessage(messend);
                    //	installApk(apkPath);
                } else {
                    Message messend = mHandler.obtainMessage();
                    messend.what = DOWNLOAD_FAILED;
                    mHandler.sendMessage(messend);
                }
                isRunnableRunning = false;
            }
        };
        Message msg = Message.obtain(handlerUpdate, runable);
        msg.what = 1;
        handlerUpdate.sendMessage(msg);
    }

    private void installApk(String apkPath) {

        File file = new File(apkPath);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    public void showProgressDialog() {

        mPbarDialog = new ProgressDialog(mActivity);
        mPbarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mPbarDialog.setMessage("下载中...");
        mPbarDialog.setCancelable(false);
        mPbarDialog.setMax(100);
        mPbarDialog.show();
        mPbarDialog.setOnKeyListener(keylistener);
    }

    @Override
    public boolean handleMessage(Message msg) {
        // TODO Auto-generated method stub

        switch (msg.what) {
            case SHOW_NO_MEMORY:
                ToastShow.toastShow(R.string.no_memory, ToastShow.LENGTH_SHORT);
                break;
            case DOWNLOADING:
                ToastShow.toastShow("新版本正在下载中", ToastShow.LENGTH_SHORT);
                break;
            case SHOW_UPDATE_DIALOG:
                String title = mUpdateInfo.getIsForce().equals(UpdateBean.FORCE_UPDATE) ? UpdateService.this.getString(R.string.force_update_title) : UpdateService.this.getString(R.string.manual_update_title);
                UpdateService.this.showUpdateDialog(
                        title,
                        UpdateService.this.getString(R.string.manual_update_tip),
                        mUpdateInfo.getRemark(), 1);

                break;
            case TIME_OVER:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                updateDialog();
                showProgressDialog();
                break;
            case DOWNLOAD_PROGRESS:
                mPbarDialog.setProgress((Integer) msg.obj);
                Log.d(TAG, "downloadProgress = " + downloadProgress);
                break;
            case DOWNLOAD_END:
                // show“桌面已更新”Toast
                if (mPbarDialog != null)
                    mPbarDialog.dismiss();
                String installApkPath = (String) msg.obj;
                // install Apk
                Log.d(TAG, "installApkPath->" + installApkPath);
                installApk(installApkPath);
                stopSelf();
                break;
            case MSG_DOWNLOAD_ERROR_TIMEOUT:
                if (mPbarDialog != null)
                    mPbarDialog.dismiss();
                ToastShow.toastShow(R.string.update_failed, ToastShow.LENGTH_SHORT);
                break;
            case DOWNLOAD_FAILED:
                if (mPbarDialog != null)
                    mPbarDialog.dismiss();
                ToastShow.toastShow(R.string.update_failed, ToastShow.LENGTH_SHORT);

                break;
            case HADUPDATESUCCESSED:
                ToastShow.toastShow(R.string.version_newest, ToastShow.LENGTH_SHORT);
            default:
                break;
        }

        return true;
    }

    /**
     * 自定义的Binder类，这个是一个内部类，所以可以知道其外围类的对象，通过这个类，让Activity知道其Service的对象
     */
    public class LocalBinder extends Binder {
        public UpdateService getService() {
            // 返回Activity所关联的Service对象，这样在Activity里，就可调用Service里的一些公用方法和公用属性
            return UpdateService.this;
        }
    }
}
