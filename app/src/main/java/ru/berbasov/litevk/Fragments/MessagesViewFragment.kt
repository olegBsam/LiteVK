package ru.berbasov.litevk.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.berbasov.litevk.API.VKApiController
import ru.berbasov.litevk.Adapters.MessagesListAdapter
import ru.berbasov.litevk.R

class MessagesViewFragment: Fragment() {
    companion object {
        val TAG: String? = "MessagesViewFragmentTag"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View  = inflater.inflate(R.layout.fragment_messages_list, null)
        val messagesList = view.findViewById<RecyclerView>(R.id.messages_list)

        messagesList.layoutManager = LinearLayoutManager(context)
        val arrayAdapter = MessagesListAdapter(ArrayList(), context!!, messagesList)

        messagesList!!.adapter = arrayAdapter

        VKApiController.loadMessageList{
            arrayAdapter.addElements(it)
        }
        return view
    }
}