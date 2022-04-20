package com.shahad.app.my_school

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService: FirebaseMessagingService(){

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("TAG",token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i("SellerFirebaseService ", "Message ::title : ${message.data[TITLE_KEY]}    ::body :  ${message.data[BODY_KEY]}  ")
        createNotification(message.data[TITLE_KEY],message.data[BODY_KEY])
    }

    private fun createNotification(title: String?, body: String?){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            with(getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager){
                createNotificationChannel(NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH))
                notify(
                    ID,
                    NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notifaction)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .build()
                )
            }
        }
    }

    companion object{
        const val CHANNEL_NAME ="CHANNEL_NAME"
        const val CHANNEL_ID ="CHANNEL_ID"
        const val ID = 0
        const val BODY_KEY ="body"
        const val TITLE_KEY ="title"

    }

}