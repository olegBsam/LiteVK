package ru.berbasov.litevk

import android.arch.lifecycle.LiveData
import com.vk.sdk.api.model.VKApiDialog
import com.vk.sdk.api.model.VKList

class MainLiveData : LiveData<VKList<VKApiDialog>>() {

}