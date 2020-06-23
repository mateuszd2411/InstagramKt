package com.matt.instagramclone.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matt.instagramclone.Adapters.NotificationAdapter
import com.matt.instagramclone.Models.Notifications

import com.matt.instagramclone.R

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

        return view
    }


}
