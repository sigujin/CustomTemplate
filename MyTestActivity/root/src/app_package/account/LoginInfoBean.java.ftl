package ${packageName}.account;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

public class LoginInfoBean implements Serializable {

    @JsonProperty("address")
    private String address;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("userImg")
    private String userImg;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("userCode")
    private String userCode;

    @JsonProperty("password")
    private String password;

    @JsonProperty("status")
    private String status;

    @JsonProperty("deptId")
    private String deptId;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("device")
    private String device;

    @JsonProperty("userType")
    private int userType;

    @JsonProperty("intiPassword")
    private String intiPassword;

    @JsonProperty("userTypeName")
    private String userTypeName;

    @JsonProperty("operDate")
    private String operDate;

    @JsonProperty("statusName")
    private String statusName;

    @JsonProperty("telphone")
    private String telphone;

    @JsonProperty("cardNo")
    private String cardNo;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("deptName")
    private String deptName;

    @JsonProperty("deptParentName")
    private String deptParentName;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("pageNo")
    private int pageNo;

    @JsonProperty("checkeds")
    private String checkeds;

    @JsonProperty("email")
    private String email;


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getIntiPassword() {
        return intiPassword;
    }

    public void setIntiPassword(String intiPassword) {
        this.intiPassword = intiPassword;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeptParentName() {
        return deptParentName;
    }

    public void setDeptParentName(String deptParentName) {
        this.deptParentName = deptParentName;
    }

}
