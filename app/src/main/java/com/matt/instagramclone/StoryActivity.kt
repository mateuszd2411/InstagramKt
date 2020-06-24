package com.matt.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.matt.instagramclone.Models.Story
import com.matt.instagramclone.Models.User
import com.squareup.picasso.Picasso
import jp.shts.android.storiesprogressview.StoriesProgressView
import kotlinx.android.synthetic.main.activity_story.*

class StoryActivity : AppCompatActivity(), StoriesProgressView.StoriesListener {

    var currentUserId: String = ""
    var userId: String = ""
    var counter = 0

    var imagesList: List<String>? = null
    var storyIdsList: List<String>? = null

    var storiesProgressView: StoriesProgressView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        userId = intent.getStringExtra("userId")

        storiesProgressView = findViewById(R.id.stories_progress)
    }

    private fun getStories(userId: String) {
        imagesList = ArrayList()
        storyIdsList = ArrayList()

        val ref = FirebaseDatabase.getInstance().reference
            .child("StoryKt")
            .child(userId!!)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                (imagesList as ArrayList<String>).clear()
                (storyIdsList as ArrayList<String>).clear()

                for (snapshot in p0.children) {

                    val story: Story? = snapshot.getValue<Story>(Story::class.java)
                    val timeCurrent = System.currentTimeMillis()

                    if (timeCurrent > story!!.getTimeStart() && timeCurrent < story.getTimeEnd()) {
                        (imagesList as ArrayList<String>).add(story.getImageUrl())
                        (storyIdsList as ArrayList<String>).add(story.getStoryId())
                    }
                }

                storiesProgressView!!.setStoriesCount((imagesList as ArrayList<String>).size)
                storiesProgressView!!.setStoryDuration(6000L)
                storiesProgressView!!.setStoriesListener(this@StoryActivity)
                storiesProgressView!!.startStories(counter)
                Picasso.get().load(imagesList!!.get(counter)).placeholder(R.drawable.profile)
                    .into(image_story)

                addViewToStory(storyIdsList!!.get(counter))
                seenNumber(storyIdsList!!.get(counter))

            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    private fun userInfo(userId: String) {
        val usersRef =
            FirebaseDatabase.getInstance().getReference().child("UsersKt").child(userId)

        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile)
                        .into(story_profile_image)

                    story_username.text = user.getUsername()
                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun addViewToStory(storyId: String) {
        FirebaseDatabase.getInstance().reference
            .child("StoryKt")
            .child(userId!!)
            .child(storyId)
            .child("views")
            .child(currentUserId)
            .setValue(true)
    }

    private fun seenNumber(storyId: String) {
        val ref = FirebaseDatabase.getInstance().reference
            .child("StoryKt")
            .child(userId!!)
            .child(storyId)
            .child("views")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                seen_number.text = "" + p0.childrenCount
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    override fun onComplete() {
        TODO("Not yet implemented")
    }

    override fun onPrev() {
        TODO("Not yet implemented")
    }

    override fun onNext() {
        TODO("Not yet implemented")
    }
}
