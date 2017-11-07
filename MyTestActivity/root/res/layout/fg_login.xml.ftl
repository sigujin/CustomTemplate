<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center_horizontal"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/iv_ic"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="60dp"
            android:background="@mipmap/ic_launcher"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="5dp"
            android:text="@string/app_name"/>

        <EditText
            android:id="@+id/et_username"
            android:layout_width="@dimen/sign_up_column_width"
            android:layout_height="@dimen/sign_up_column_height"
            android:layout_gravity="center_horizontal"
            android:layout_marginTop="60dp"
            android:background="@color/actionbar_bg_color"
            android:gravity="center"
            android:maxLength="11"
            android:textColor="@color/sign_up_text"
            android:textColorHint="@color/sign_up_hint"
            android:singleLine="true"
            android:textSize="@dimen/text_middle_m"
            />

        <EditText
            android:id="@+id/et_password"
            android:layout_width="@dimen/sign_up_column_width"
            android:layout_height="@dimen/sign_up_column_height"
            android:layout_gravity="center_horizontal"
            android:layout_marginTop="20dp"
            android:background="@color/actionbar_bg_color"
            android:gravity="center"
            android:inputType="textPassword"
            android:maxLength="20"
            android:textColor="@color/sign_up_text"
            android:textColorHint="@color/sign_up_hint"
            android:singleLine="true"
            android:textSize="@dimen/text_middle_m"/>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="50dp"
            android:layout_marginRight="50dp"
            android:orientation="horizontal"
            android:visibility="gone">

            <CheckBox
                android:id="@+id/cb_login"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:padding="5dp"
                android:text="@string/auto_login"
                android:textSize="@dimen/text_small_l"/>


            <CheckBox
                android:id="@+id/cb_remember_password"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="right"
                android:padding="5dp"
                android:text="@string/remember_password"
                android:textSize="@dimen/text_small_l"/>
        </LinearLayout>

        <TextView
            android:id="@+id/btn_login"
            android:layout_width="@dimen/sign_up_column_width"
            android:layout_height="@dimen/sign_up_column_height"
            android:layout_gravity="center_horizontal"
            android:layout_marginTop="170dp"
            android:gravity="center"
            android:text="@string/login_btn_text"
            android:textColor="@color/green"
            android:textSize="@dimen/text_small_l"/>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="50dp"
            android:layout_marginRight="50dp"
            android:layout_marginTop="10dp"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/tv_reset_pwd"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="忘记密码"
                android:textSize="@dimen/text_small_m"/>

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text=""/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="还未注册，"
                android:textSize="@dimen/text_small_m"/>

            <TextView
                android:id="@+id/tv_sign_up"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="点击这里"
                android:textColor="@color/green07"
                android:textSize="@dimen/text_small_m"
                />
        </LinearLayout>
    </LinearLayout>

</RelativeLayout>