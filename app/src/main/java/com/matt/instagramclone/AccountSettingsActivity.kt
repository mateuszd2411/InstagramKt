package com.matt.instagramclone

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.matt.instagramclone.Models.User
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_account_settings.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class AccountSettingsActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""
    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storageProfilePicRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Profile Pictures")

        logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this@AccountSettingsActivity, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        change_image_text_btn.setOnClickListener {

            checker = "clicked"

            CropImage.activity()
                .setAspectRatio(1, 1)
                .start(this@AccountSettingsActivity)
        }

        save_info_profile_btn.setOnClickListener {
            if (checker == "clicked") {
                updateImageAndUpdateInfo()
            } else {
                updateUserInfoOnly()
            }
        }

        userInfo()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            profile_image_view_profile_frag.setImageURI(imageUri)
        }
    }

    private fun updateUserInfoOnly() {

        when {
            TextUtils.isEmpty(full_name_profile_frag.text.toString()) -> Toast.makeText(
                this,
                "Please write full name first...",
                Toast.LENGTH_LONG
            ).show()
            username_profile_frag.text.toString() == "" -> Toast.makeText(
                this,
                "Please write user name first...",
                Toast.LENGTH_LONG
            ).show()
            bio_profile_frag.text.toString() == "" -> Toast.makeText(
                this,
                "Please write your bio first...",
                Toast.LENGTH_LONG
            ).show()
            else -> {
                val usersRef =
                    FirebaseDatabase.getInstance().reference.child("UsersKt")

                val userMap = HashMap<String, Any>()
                userMap["fullname"] = full_name_profile_frag.text.toString().toLowerCase()
                userMap["username"] = username_profile_frag.text.toString().toLowerCase()
                userMap["bio"] = bio_profile_frag.text.toString().toLowerCase()

                usersRef.child(firebaseUser.uid).updateChildren(userMap)

                Toast.makeText(
                    this,
                    "Account Information has been updated successfully...",
                    Toast.LENGTH_LONG
                ).show()

                val intent = Intent(this@AccountSettingsActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun userInfo() {
        val usersRef =
            FirebaseDatabase.getInstance().getReference().child("UsersKt").child(firebaseUser.uid)

        usersRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user = p0.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile)
                        .into(profile_image_view_profile_frag)
                    username_profile_frag.setText(user!!.getUsername())
                    full_name_profile_frag.setText(user!!.getFullname())
                    bio_profile_frag.setText(user!!.getBio())
                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    private fun updateImageAndUpdateInfo() {

        when {
            imageUri == null -> Toast.makeText(
                this,
                "Please select image first...",
                Toast.LENGTH_LONG
            ).show()
            TextUtils.isEmpty(full_name_profile_frag.text.toString()) -> Toast.makeText(
                this,
                "Please write full name first...",
                Toast.LENGTH_LONG
            ).show()
            username_profile_frag.text.toString() == "" -> Toast.makeText(
                this,
                "Please write user name first...",
                Toast.LENGTH_LONG
            ).show()
            bio_profile_frag.text.toString() == "" -> Toast.makeText(
                this,
                "Please write your bio first...",
                Toast.LENGTH_LONG
            ).show()

            else ->{
                val fileref = storageProfilePicRef!!.child(firebaseUser!!.uid + ".jpg")
            }
        }


    }

}
