package com.example.task

import com.google.gson.annotations.SerializedName


data class ChannelListData (

  @SerializedName("channels" ) var channels : ArrayList<Channels> = arrayListOf(),
  @SerializedName("ok"       ) var ok       : Boolean?            = null

)