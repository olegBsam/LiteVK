package ru.berbasov.litevk.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.berbasov.litevk.API.VKApiController
import ru.berbasov.litevk.Adapters.FriendsListAdapter
import ru.berbasov.litevk.R

class FriendsViewFragment : Fragment() {
    companion object {
        val TAG: String? = "FriendsViewFragmentTag"
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View  = inflater.inflate(R.layout.fragment_friends_list, null)
        var friendsList = view.findViewById<RecyclerView>(R.id.friends_list)
        friendsList.layoutManager = LinearLayoutManager(context)
        VKApiController.loadFriendsList{
            var arrayAdapter = FriendsListAdapter(it, context!!, friendsList)
            friendsList!!.adapter = arrayAdapter
        }
        return view
    }

}