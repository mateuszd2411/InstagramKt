package com.matt.instagramclone.Adapters

import android.content.Context
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.matt.instagramclone.Models.Comment
import com.matt.instagramclone.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.comments_item_layout.view.*

class CommentsAdapter (private val mContext: Context,
                       private val mComment: MutableList<Comment>?
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsAdapter.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: CommentsAdapter.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageProfile: CircleImageView
        var userNameTv: TextureView
        var commentTv: TextView

        init {
            imageProfile = itemView.findViewById(R.id.user_profile_image_comment)
            userNameTv = itemView.findViewById(R.id.user_name_comment)
            commentTv = itemView.findViewById(R.id.comment_comment)
        }
    }

}
