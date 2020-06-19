package com.matt.instagramclone.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.matt.instagramclone.Models.User
import com.matt.instagramclone.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.user_item_layout.view.*

class UserAdapter (private var mContext: Context,
                   private var mUser: List<User>,
                   private var isFragment: Boolean = false) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_item_layout, parent, false)

        return UserAdapter.ViewHolder(view)
    }
    private var firebaeUser: FirebaseUser ?= null

    override fun getItemCount(): Int {
        return mUser.size
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val user = mUser[position]

        holder.userNameTextView.text = user.getUsername()
        holder.userFullnameTextView.text = user.getFullname()
        Picasso.get().load(user.getImage()).placeholder(R.drawable.profile).into(holder.userProfileImage)

        holder.followButton.setOnClickListener {
            if (holder.followButton.text.toString() == "Follow") {
                firebaeUser?.uid.let { it1 ->
                    FirebaseDatabase.getInstance().reference
                        .child("Follow").child(it1.toString())
                        .child("Following").child(user.getUid())
                        .setValue(true).addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                firebaeUser?.uid.let { it1 ->
                                    FirebaseDatabase.getInstance().reference
                                        .child("Follow").child(user.getUid())
                                        .child("Followers").child(it1.toString())
                                        .setValue(true).addOnCompleteListener { task ->
                                            if (task.isSuccessful) {

                                            }
                                        }
                                }
                            }
                        }
                }
            }
            else {
                firebaeUser?.uid.let { it1 ->
                    FirebaseDatabase.getInstance().reference
                        .child("Follow").child(it1.toString())
                        .child("Following").child(user.getUid())
                        .removeValue().addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                firebaeUser?.uid.let { it1 ->
                                    FirebaseDatabase.getInstance().reference
                                        .child("Follow").child(user.getUid())
                                        .child("Followers").child(it1.toString())
                                        .removeValue().addOnCompleteListener { task ->
                                            if (task.isSuccessful) {

                                            }
                                        }
                                }
                            }
                        }
                }
            }
        }

    }

    class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userNameTextView: TextView = itemView.findViewById(R.id.user_name_search)
        var userFullnameTextView: TextView = itemView.findViewById(R.id.user_full_name_search)
        var userProfileImage: CircleImageView = itemView.findViewById(R.id.user_profile_image_search)
        var followButton: Button = itemView.findViewById(R.id.follow_btn_search)
    }
}