package ru.berbasov.litevk.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.vk.sdk.api.model.VKApiUser
import com.vk.sdk.api.model.VKList
import kotlinx.android.synthetic.main.friends_list_item.view.*
import ru.berbasov.litevk.R

class FriendsListAdapter(val items : VKList<VKApiUser>, val context: Context, val recycler: RecyclerView) : RecyclerView.Adapter<FriendsListViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FriendsListViewHolder {
        var view : View = LayoutInflater.from(p0.context).
                inflate(R.layout.friends_list_item, p0, false)
        return FriendsListViewHolder(view)
    }
    @SuppressLint("SetTextI18n")

    override fun onBindViewHolder(holder: FriendsListViewHolder, position: Int) {
        holder.tvFriendName?.text = items[position].first_name + ' ' + items[position].last_name
        var iv : ImageView =  holder.ivFriendAvatar
        Glide.with(iv.context).load(items[position].photo_100).listener(object : RequestListener<Drawable>{
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false
            }
        }).into(holder.ivFriendAvatar)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
class FriendsListViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tvFriendName = view.user_name
    val ivFriendAvatar = view.user_avatar
}