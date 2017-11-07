package ${packageName}.account;

public interface LoginResultCallBack {
    void LoginSuccess(String other_info);

    void LoginFail(String re);

    void PushDeviceTokenOk();

    void PushDeviceTokenFail();
}