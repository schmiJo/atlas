package com.atlas.atlas.auth;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.atlas.atlas.R;

class DownloaderNotificationBuilder {


   private static int NOT_ID = 475;
   private NotificationManager notificationManager;
   private NotificationCompat.Builder builder;
   private static DownloaderNotificationBuilder instance;
   private DownloaderNotificationBuilder(Context context){
       builder = new NotificationCompat.Builder(context);
       notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       builder.setSmallIcon(R.drawable.ic_logo_spec);
       builder.setContentTitle("Backup download");
       builder.setContentText("Downloading...");
       builder.setProgress(100, 0, false);
       notificationManager.notify(NOT_ID, builder.build());
   }

   static DownloaderNotificationBuilder getInstance(Context context){
       if(instance == null) instance = new DownloaderNotificationBuilder(context);
       return instance;
   }

   void setProgress(int progress){
       builder.setProgress(100, progress, false);
       notificationManager.notify(NOT_ID, builder.build());

   }

   void deleteNotification(){
       builder.setProgress(0, 0, false);
       builder.setContentText("Download Complete");
       notificationManager.notify(NOT_ID, builder.build());
   }


}
