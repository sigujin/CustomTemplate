<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/content_bg_color"
    android:orientation="vertical">

        <include layout="@layout/actionbar_layout_base" />

        <LinearLayout
            android:id="@+id/ll_info"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/white"
            android:paddingTop="20dp"
            android:paddingBottom="20dp"
            android:layout_gravity="center_vertical"
            android:gravity="center_vertical"
            android:orientation="horizontal">

            <ImageView
                android:id="@+id/image_view"
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_marginLeft="20dp"/>

            <LinearLayout
                android:id="@+id/ll_personal_name"
                android:layout_width="0dp"
                android:layout_weight="1"
                android:layout_height="wrap_content"
                android:layout_marginLeft="20dp"
                android:orientation="vertical">

                <TextView
                    android:id="@+id/name"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="阿达尔"/>

                <TextView
                    android:id="@+id/text_id"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="5dp"
                    android:text="ID:78787878"/>
            </LinearLayout>

            <ImageView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="@drawable/pic_15"
                android:visibility="gone" />

        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <android.support.v7.widget.RecyclerView
                android:id="@+id/recycler"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp"
                android:layout_weight="1"
                android:overScrollMode="never" />
        </LinearLayout>

</LinearLayout>