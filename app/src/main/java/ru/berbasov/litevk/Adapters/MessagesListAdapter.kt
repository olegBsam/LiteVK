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
import kotlinx.android.synthetic.main.messages_list_item.view.*
import ru.berbasov.litevk.DialogItemModel
import ru.berbasov.litevk.R

class MessagesListAdapter(val items : ArrayList<DialogItemModel>, val context: Context, val recycler: RecyclerView) : RecyclerView.Adapter<MessagesListViewHolder>() {

    fun addElements(newItems : ArrayList<DialogItemModel>){
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MessagesListViewHolder {
        var view : View = LayoutInflater.from(p0.context).
                inflate(R.layout.messages_list_item, p0, false)
        return MessagesListViewHolder(view)
    }
    @SuppressLint("SetTextI18n")

    override fun onBindViewHolder(holder: MessagesListViewHolder, position: Int) {
        holder.userName?.text = items[position].userName
        holder.textTitle?.text = items[position].message
        var iv : ImageView =  holder.photo
        Glide.with(iv.context).load(items[position].photo).listener(object : RequestListener<Drawable> { override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            return false
        }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false
            }
        }).into(holder.photo)
    }

    override fun getItemCount(): Int {
        return items?.size
    }
}
class MessagesListViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val userName = view.collocutor_name
    val textTitle = view.message
    val photo = view.user_avatar
}