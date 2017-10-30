package com.vito.myapplication.utils;

/**
 * Created by pc on 2016/11/22.
 */

public class Comments {

    /* test base url 外网地址*/
    public static final String BASE_URL = "http://123.138.87.158:60080/home.web";
    //    public static final String BASE_URL = "http://192.168.1.55:8080/oa.web/";
    /* test base url 内网地址*/
//    public static final String BASE_URL = "http://wjc.ngrok.io/home.web";
    /* base url 正式地址*/
//    public static final String BASE_URL = "http://email.bkvito.com:64000/";
    /* test base url 临时测试地址*/
//    public static final String BASE_URL = "http://192.168.1.42:8080/nurse.web";
//    public static final String BASE_URL = "http://192.168.1.54:8080/oa.web/";
//    public static final String BASE_URL = "http://192.168.1.61/";
    /*
    * local configs
    */
    public static final String FG_HOME_DATA = "/json/vito_home_tab_data.json";
    public static final String MY_INFO_PATH = "/json/vito_my_info.json";


    public static final String GET_UPDATE_URL = BASE_URL + "base/apk/apkManage/checkUpApk.htm";
    public static final String SYS_TYPE = "swybg";
    public static final int START_WAIT_TIME = 3000;

    /*Home*/
    public static final String SERVICE_AREA = BASE_URL + "/home/serviceType/queryHomePageArea.htm";
    public static final String TOP_SERVICES = BASE_URL + "/home/serviceType/queryHomePageTop4.htm";
    public static final String ALL_SERVICES = BASE_URL + "/home/serviceType/queryHomePageCS.htm";
    public static final String LONG_TERM_SERVICES = "/json/vito_home_long_term_service.json";

    /*Info*/
    public static final String QUERY_ADDRESS_PATH = BASE_URL + "/home/address/queryUserAddressForMobile.htm";
    public static final String SAVE_ADDRESS_PATH = BASE_URL + "/home/address/saveUserAddress.htm";
    public static final String UPDATE_ADDRESS_PATH = BASE_URL + "/home/address/updateUserAddress.htm";
    public static final String DELETE_ADDRESS_PATH = BASE_URL + "/home/address/deleteUserAddress.htm";
    public static final String QUERY_DATA_PATH = BASE_URL + "/base/authoriza/conmonData/queryData.htm";
    public static final String INFO_PATH = BASE_URL + "/home/homeNews/queryHomeNewsByPage.htm";
    public static final String INFO_TYPE_PATH = BASE_URL + "/base/authoriza/conmonData/queryDataNoCache.htm";
    public static final String UPDATE_VIEW_TIMES = BASE_URL + "/home/homeNews/updateViewTimes.htm";

    /*Services*/
    public static final String SINGLE_SERVICE_DETAIL = BASE_URL + "/home/serviceType/queryHomePageInfo.htm";
    public static final String LONG_TERM_SERVICES_DETAIL = BASE_URL + "/home/serviceType/queryServiceInfo.htm";


    /*SMS*/
    public static final String SMS_URL = BASE_URL + "/base/authoriza/user/sendSms.htm";


    /* Login */
    public static final String LOGIN_URL = BASE_URL + "/base/authoriza/login/userLogin.htm";
    public static final String GET_TIME_STAMP_URL = BASE_URL + "/base/authoriza/login/getTimestamp.htm";
    /*register*/
    public static final String CHECK_TELNEMBUER_URL = BASE_URL + "/base/authoriza/user/queryUserCount.htm";
    public static final String REGISTER_SECOND_URL = BASE_URL + "oa/base/saveRegisterUserByDeptCodeSecond.htm";
    public static final String REGISTER_FIRST_URL = BASE_URL + "oa/base/saveRegisterUserByDeptCode.htm";
    public static final String REGISTER_DEPT_URL = BASE_URL + "oa/base/saveRegisterUser.htm";

    /* AD */
    public static final String AD_IMAGE_PATH = BASE_URL + "/home/homeAd/queryHomeAdByPage.htm";
    public static final String BannerImage_DATA_PATH = "/json/vitoAd.json";
    public static final String AD_CLICK_NUM_PATH = BASE_URL + "ecs/common/ad/updAd.htm";
    public static final String AD_IMG_PATH = BASE_URL + "ecs/common/ad/getAd.htm";


    /* sign_on */
    public static final String SIGN_ON_REQUEST_URL = BASE_URL + "oa/att/checkIn.htm";
    public static final String SIGN_ON_HISTORY_URL = BASE_URL + "oa/att/user/attendance/log.htm";
    public static final String SIGN_ON_USER_INFO_URL = BASE_URL + "oa/att/user/attendance.htm";
    public static final String SIGN_ON = BASE_URL + "/home/user/saveRegisterUser.htm";
    public static final String FEED_BACK = BASE_URL + "/nurse/suggestion/saveSuggestion.htm";
    /* LoginOut */
    public static final String LOGIN_OUT = BASE_URL + "/base/authoriza/user/logout.htm";

    /*personal info*/
    public static final String PERSONAL_INFO_CHANGE = BASE_URL + "/base/authoriza/user/updateMe.htm";
    public static final String PERSONAL_PWD_CHANGE = BASE_URL + "/base/authoriza/user/updPsd.htm";
    public static final String UPLOAD_IMG_URL = BASE_URL + "/base/authoriza/fileup/uploadFile.htm";

    /* upload */
    public static final String UPLOAD_IMG_PATH = BASE_URL + "/base/authoriza/fileup/upload.htm";
    /**
     * 用户注册手机号
     */
    public static final String TELNUMBER = "telnumber";
    /**
     * 登录密码
     */
    public static final String PASSWORD = "password";
    /**
     * 是否自动登录
     */
    public static final String AUTOLOGIN = "isAutoLogin";
    /**
     * 是否记住密码
     */
    public static final String REMEMBERPASSWORD = "isRememberPassword";
    /**
     * 友盟推送相关
     */
    public static final String UPDATE_DEVICE_TOKEN = BASE_URL + "/base/msg/mq/updUserDeviceInfo.htm";
    public static final String UMENG_APP_NAME = "swybg";
    public static final String QUERY_PAYMENT_STATUS_LIST = BASE_URL + "/nurse/contract/queryOrderstatusList.htm";
    public static final String NOTICECOUNT_PATH = BASE_URL + "oa/notice/queryReceiveNoticeCount.htm";
    public static final String NAVIGATION_URL = /*BASE_URL +*/ "/json/vito_moudles_test.json";
    public static final String HOME_MORE_FUN_CONFIG = "/json/vito_all_actions.json";
    /**
     * 订单相关
     */
    public static final String ORDER_STATUS = BASE_URL + "/home/order/queryHomeServicePaged.htm";
    public static final String ORDER_STATUS_LIST_URL = "/json/order_status_list.json";
    public static final String ORDER_HEADER_LIST_URL = "/json/orderstatus.json";
    public static final String REQUEST_WXPAY_INFO_URL = BASE_URL + "/payment/wxpay/unifiedorder.htm";
    public static final String REQUEST_ALIPAY_INFO_URL = BASE_URL + "/payment/alipay/alipayMobile/queryPayString.htm";
    public static final String SIGNAL_ORDER = BASE_URL + "/home/order/saveHomeService.htm";
    public static final String CANCEL_ORDER = BASE_URL + "/home/order/cancelHomeOrder.htm";
    public static final String REFUND_ORDER = BASE_URL + "/payment/alipay/refund/alipayCommonRefund.htm";
    public static final String QUERY_ORDER_DETAILS_PATH = BASE_URL + "/home/order/queryHomeServiceInfo.htm";
    public static final String EVALUATE_CONTENT = BASE_URL + "/home/homeEvaluate/saveEvaluate.htm";
    public static final String UPDATE_CHECKED_WORKER = BASE_URL + "/home/order/updateHomeService.htm";
    public static final String PEOPLE_DETAIL = BASE_URL + "/home/user/queryUserInfoById.htm";
    public static final String PEOPLE_SCORE = BASE_URL + "/home/homeEvaluate/queryEvaluate.htm";
    public static final String RECRUIT_URL = BASE_URL + "/home/zp/queryZpInfo.htm";
    public static final String QUERY_BENEFIT_LIST = BASE_URL + "/home/benifit/queryBenifitList.htm";
    public static final String QUERY_USER_BENEFIT_LIST = BASE_URL + "/home/benifit/queryBenifitByUserList.htm";
    public static final String QUERY_ORDER_USER_BENEFIT_LIST = BASE_URL + "/home/benifit/queryBenifitByUser.htm";
    public static final String GET_BENEFIT = BASE_URL + "/home/benifit/saveBenifitByUser.htm";
    public static final String USE_BENEFIT_URL = BASE_URL + "/home/noisoAction/updateServiceOrderBenifitUse.htm";
    public static final String REMOVE_BENEFIT_URL = BASE_URL + "/home/noisoAction/updateServiceOrderBenifitUnUse.htm";
    /**
     * 分页数据 单页容量
     */
    public static final int PAGE_SIZE = 10;
    /**
     * 登录的userId
     */
    public static String USERID = "userId";
    /*
    * badgeview type
    */
    public static String mBadgeTypes[] = {"system", "community"};

}
