package com.matt.instagramclone.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.matt.instagramclone.Adapters.NotificationAdapter
import com.matt.instagramclone.Models.Notifications

import com.matt.instagramclone.R
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class NotificationsFragment : Fragment() {

    private var notificationList: List<Notifications>? = null
    private var notificationAdapter: NotificationAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        var recyclerView: RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_notifications)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        notificationList = ArrayList()

        notificationAdapter =
            NotificationAdapter(context!!, notificationList as ArrayList<Notifications>)
        recyclerView.adapter = notificationAdapter

        readNotifications()

        return view
    }

    private fun readNotifications() {
        val notiRef = FirebaseDatabase.getInstance()
            .reference.child("NotificationsKt")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        notiRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    (notificationList as ArrayList<Notifications>).clear()

                    for (snapshot in dataSnapshot.children) {
                        val notification = snapshot.getValue(Notifications::class.java)

                        (notificationList as ArrayList<Notifications>).add(notification!!)
                    }

                    Collections.reverse(notificationList)
                    notificationAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }


}
