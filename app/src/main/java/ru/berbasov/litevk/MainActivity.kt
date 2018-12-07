package ru.berbasov.litevk

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.Toast
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import ru.berbasov.litevk.Fragments.FriendsViewFragment
import ru.berbasov.litevk.Fragments.MessagesViewFragment


class MainActivity : FragmentActivity() {

    var loadedFragment : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(this,
                    VKScope.MESSAGES,
                    VKScope.WALL,
                    VKScope.FRIENDS)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        Toast.makeText(applicationContext, "Ok", Toast.LENGTH_LONG).show()
                    }
                    override fun onError(error: VKError) {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }))
        {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onClick(view: View){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()

        when (view.id) {
            R.id.friends_button -> {
                if (supportFragmentManager.findFragmentByTag(FriendsViewFragment.TAG) == null){
                    loadedFragment?.let {
                        transaction.remove(loadedFragment!!)
                    }
                    title = getString(R.string.title_friends)
                    loadedFragment = FriendsViewFragment()
                    transaction.add(R.id.main_fragments_container, loadedFragment!!, FriendsViewFragment.TAG)
                }
            }
            R.id.messages_button -> {
                if (supportFragmentManager.findFragmentByTag(MessagesViewFragment.TAG) == null){
                    loadedFragment?.let {
                        transaction.remove(loadedFragment!!)
                    }
                    title = getString(R.string.title_message);
                    loadedFragment = MessagesViewFragment()
                    transaction.add(R.id.main_fragments_container, loadedFragment!!, MessagesViewFragment.TAG)
                }
            }

        }

        transaction.commit()
    }
}
