package ru.berbasov.litevk.API

import android.arch.lifecycle.MutableLiveData
import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKApiDialog
import com.vk.sdk.api.model.VKApiGetDialogResponse
import com.vk.sdk.api.model.VKApiUser
import com.vk.sdk.api.model.VKList
import ru.berbasov.litevk.DialogItemModel


object VKApiController{

    var mld: MutableLiveData<VKApiDialog> = MutableLiveData()

    fun loadFriendsList(printToScreen : (VKList<VKApiUser>) -> Unit){

        var request: VKRequest = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "first_name, last_name, photo_100"))

        request.executeWithListener(object : VKRequest.VKRequestListener(){

            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                val list = response!!.parsedModel as VKList<VKApiUser>
                printToScreen(list)
            }

            override fun onError(error: VKError?) {
                //TODO
                super.onError(error)
            }
        })
    }

    fun  loadUsersNames(dialogList: VKList<VKApiDialog>, addElements : (ArrayList<DialogItemModel>) -> Unit, offset: Int){

        var sBuilder: StringBuilder = java.lang.StringBuilder()
        dialogList.map { item -> sBuilder.append(item.message.user_id.toString() + ',') }

        var request: VKRequest = VKApi.users().get(VKParameters.from(
                VKApiConst.USER_IDS, sBuilder, VKApiConst.FIELDS, "first_name, last_name, photo_100"))

        request.executeWithListener(object : VKRequest.VKRequestListener(){
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                val list = response!!.parsedModel as VKList<VKApiUser>


                var listDialogItems = ArrayList<DialogItemModel>()
                var i = 0
                while (i < list.size){
                    listDialogItems.add(DialogItemModel(
                            if (dialogList[i].message.body.length > 50)
                            {
                                dialogList[i].message.body.substring(0,50) + "..."
                            }
                            else{
                                dialogList[i].message.body
                            },
                            getName(dialogList[i], list)
                            ,
                            list.find{ item -> item.id ==  dialogList[i].message.user_id}!!.photo_100))
                    i++
                }


                addElements(listDialogItems)
                if (dialogList.size >= 20) {
                    //TODO
                    //Задержка
                    getDialogsList(offset + 20, addElements)
                }

            }

            override fun onError(error: VKError?) {
                //TODO
                super.onError(error)
            }
        })
    }

    private fun getName(dialog :VKApiDialog, users : VKList<VKApiUser>) : String{
        return if(dialog.message.title != ""){
            dialog.message.title
        }
        else{
            val a = users.find{ item -> item.id ==  dialog.message.user_id}
            a!!.first_name + ' ' +a!!.last_name
        }
    }

    private fun getDialogsList(offset: Int, addElements : (ArrayList<DialogItemModel>) -> Unit){
        var request: VKRequest =   VKApi.messages().getDialogs(VKParameters.from(VKApiConst.OFFSET, offset))


        request.executeWithListener(object : VKRequest.VKRequestListener(){
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)

                val getMessagesResponse = response!!.parsedModel as VKApiGetDialogResponse
                var list = getMessagesResponse.items as VKList<VKApiDialog>

                loadUsersNames(list, addElements, offset)
            }
            override fun onError(error: VKError?) {
                super.onError(error)
            }
        })
    }

    fun loadMessageList(addElements : (ArrayList<DialogItemModel>) -> Unit){
        getDialogsList(0, addElements)
    }
}

