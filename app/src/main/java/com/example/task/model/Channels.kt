package com.example.task

import com.google.gson.annotations.SerializedName


data class Channels (

  @SerializedName("id"                  ) var id                : String?           = null,
  @SerializedName("name"                ) var name              : String?           = null,
  @SerializedName("created"             ) var created           : Int?              = null,
  @SerializedName("creator"             ) var creator           : String?           = null,
  @SerializedName("is_archived"         ) var isArchived        : Boolean?          = null,
  @SerializedName("is_member"           ) var isMember          : Boolean?          = null,
  @SerializedName("group_email"         ) var groupEmail        : String?           = null,
  @SerializedName("group_folder_name"   ) var groupFolderName   : String?           = null,
  @SerializedName("is_active"           ) var isActive          : Boolean?          = null,
  @SerializedName("is_auto_followed"    ) var isAutoFollowed    : Boolean?          = null,
  @SerializedName("is_notifications"    ) var isNotifications   : Boolean?          = null,
  @SerializedName("last_seen"           ) var lastSeen          : String?           = null,
  @SerializedName("latest"              ) var latest            : Int?              = null,
  @SerializedName("unread_count"        ) var unreadCount       : Int?              = null,
  @SerializedName("thread_unread_count" ) var threadUnreadCount : Int?              = null,
  @SerializedName("members"             ) var members           : ArrayList<String> = arrayListOf()

)