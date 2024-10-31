package com.loinq.unnn.Notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.loinq.unnn.MainActivity;
import com.loinq.unnn.R;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "WATER_REMINDER_CHANNEL";
    private static final int NOTIFICATION_ID = 100;

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "WATER_REMINDER_CHANNEL")
                .setSmallIcon(R.drawable.ic_stat_water)
                .setContentTitle("Nhắc nhở uống nước")
                .setContentText("Đừng quên uống nước để giữ cơ thể khỏe mạnh!")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false) // Đảm bảo thông báo không tự động hủy khi nhấn
                .setVibrate(new long[]{0, 500, 500, 500});

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1001, builder.build());

        scheduleNextReminder(context);

    }

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Water Reminder";
            String description = "Channel for water drinking reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @SuppressLint("ScheduleExactAlarm")
    public static void scheduleNextReminder(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
        long interval30Minutes  = 5 * 60 * 1000; //  30p
            long interval5Seconds = 5 * 1000;//5s
            // Sử dụng `setExactAndAllowWhileIdle` cho thời gian nhỏ
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + interval30Minutes ,
                    pendingIntent
            );
        }

    }
}
