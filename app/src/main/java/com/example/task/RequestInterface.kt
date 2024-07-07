package com.example.task

import android.text.Editable
import com.example.task.model.LoginRes
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface RequestInterface {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("iwauthentication.login.plain")
    @FormUrlEncoded
    fun loginReq(@Field("username")username: String,
                 @Field("password")password: String,
                 @Field("Host")Host: String): Observable<LoginRes>


    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("channels.list")
    @FormUrlEncoded
    fun getChannels(@Field("token")token: String,
                 @Field("include_unread_count")include_unread_count: Boolean,
                 @Field("exclude_members")exclude_members: Boolean,
                 @Field("include_permissions")include_permissions: Boolean): Observable<ChannelListData>

}