package com.matt.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.matt.instagramclone.Adapters.CommentsAdapter
import com.matt.instagramclone.Models.Comment
import com.matt.instagramclone.Models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_account_settings.*
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity() {

    private var postId = ""
    private var publisherId = ""
    private var firebaseUser: FirebaseUser? = null
    private var commentAdapter: CommentsAdapter? = null
    private var commentList: MutableList<Comment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val intent = intent
        postId = intent.getStringExtra("postId")
        publisherId = intent.getStringExtra("publisherId")

        firebaseUser = FirebaseAuth.getInstance().currentUser

        var recyclerView: RecyclerView
        recyclerView = findViewById(R.id.recycler_view_comments)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        recyclerView.layoutManager = linearLayoutManager

        commentList = ArrayList()
        commentAdapter = CommentsAdapter(this, commentList)
        recyclerView.adapter = commentAdapter

        userInfo()
        readComments()
        getPostImage()

        post_comment.setOnClickListener(View.OnClickListener {
            if (add_comment!!.text.toString() == "") {
                Toast.makeText(
                    this@CommentActivity,
                    "Please write comment first...",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                addComment()
            }
        })

    }

    private fun addComment() {
        val commentsRef =
            FirebaseDatabase.getInstance().reference
                .child("CommentsKt")
                .child(postId!!)

        val commentsMap = HashMap<String, Any>()
        commentsMap["comment"] = add_comment!!.text.toString()
        commentsMap["publisher"] = firebaseUser!!.uid

        commentsRef.push().setValue(commentsMap)

        addNotification()

        add_comment!!.text.clear()
    }

    private fun userInfo() {
        val usersRef =
            FirebaseDatabase.getInstance().reference.child("UsersKt").child(firebaseUser!!.uid)

        usersRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile)
                        .into(profile_image_comment)
                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    private fun getPostImage() {
        val postsRef =
            FirebaseDatabase.getInstance()
                .reference.child("PostsKt")
                .child(postId!!).child("postimage")

        postsRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val image = p0.value.toString()

                    Picasso.get().load(image).placeholder(R.drawable.profile)
                        .into(post_image_comment)
                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    private fun readComments() {
        val commentsRef = FirebaseDatabase.getInstance()
            .reference.child("CommentsKt")
            .child(postId)

        commentsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()) {
                    commentList!!.clear()

                    for (snapshot in p0.children) {
                        val comment = snapshot.getValue(Comment::class.java)
                        commentList!!.add(comment!!)
                    }
                    commentAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    private fun addNotification() {
        val notiRef = FirebaseDatabase.getInstance()
            .reference.child("NotificationsKt")
            .child(publisherId!!)

        val notiMap = HashMap<String, Any>()
        notiMap["userid"] = firebaseUser!!.uid
        notiMap["text"] = "commented: " + add_comment!!.text.toString()
        notiMap["postid"] = postId
        notiMap["ispost"] = true

        notiRef.push().setValue(notiMap)
    }

}
