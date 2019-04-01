package com.fevziomurtekin.hackernewsapp.data.network.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.fevziomurtekin.hackernewsapp.R
import com.fevziomurtekin.hackernewsapp.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val id = 32489

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        var link = ""
        var message = ""
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Timber.d("From: " + remoteMessage.getFrom())

        // Check if message contains a data payload.


        if (!message.isEmpty())
            sendNotification(this, message, link)
        // Also if you intend on generating your own

    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Timber.d( "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Timber.d( "Short lived task is done.")
    }

    /**
     * Persist token to third-party servers.
     *
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String) {
        // TODO: Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param message
     * @param link    FCM message body received.
     */
    private fun sendNotification(context: Context, message: String?, link: String?) {
        val title = context.getString(R.string.app_name)
        if (message == null) return
        val tick: String
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        tick = if (message.length < 50) message else message.substring(0, 45) + "..."
        val builder = NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(context.getResources().getColor(R.color.orange))
            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification))
            .setVibrate(longArrayOf(0, 150, 100, 150, 100, 150))
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setTicker(tick)
            .setLights(resources.getColor(R.color.orange), 500, 1500)
            .setContentTitle(title)
            .setSound(defaultSoundUri)
        if (message.length > 30)
            builder.setStyle(NotificationCompat.BigTextStyle().bigText(message))
        builder.setContentText(message)
        val notificationIntent = Intent(this, MainActivity::class.java)
        if (link != null)
            notificationIntent.putExtra("link", link)
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        val contentIntent = PendingIntent.getActivity(
            context, id, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        builder.setContentIntent(contentIntent)
        // Add as notification
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        manager!!.notify(id, builder.build())

    }

    companion object {


        private val TAG = "MyFirebaseMsgService"
    }

}