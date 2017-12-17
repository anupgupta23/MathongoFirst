package com.learnacad.learnacad.Notifications;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.learnacad.learnacad.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sahil Malhotra on 14-10-2017.
 */

public class NotificationHandler {

    private Context mContext;

    public NotificationHandler(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationMessage(String title,String message,String timeStamp,Intent intent){
        showNotificationMessage(title,message,timeStamp,intent,null);
    }

    public void showNotificationMessage(String title,String message,String timeStamp,Intent intent,String imageUrl){

        if(TextUtils.isEmpty(message)){
            return;
        }

        final int icon = R.drawable.logo;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(mContext,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);


        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext,"MyTEmpChannel");

        showSmallNotification(mBuilder,icon,title,message,timeStamp,resultPendingIntent);
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);

       // Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.notificationlogo);

        Notification notification;


        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(inboxStyle)
                .setWhen(getTimeMilliSec(timeStamp))
                .setSmallIcon(icon)
                .setContentText(message)
                .build();


        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID,notification);

    }

    public static boolean isAppInBackground(Context context){

        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo processInfo : runningProcesses){

            if(processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){

                for(String activeProcess : processInfo.pkgList){

                    if(activeProcess.equals(context.getPackageName())){

                        isInBackground = false;
                    }
                }
            }

        }

        return isInBackground;
    }

    public static void clearNotifications(Context context){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
