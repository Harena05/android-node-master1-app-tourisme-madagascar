package itu.master1.projetandroid.menu.view;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.List;

import itu.master1.projetandroid.R;
import itu.master1.projetandroid.menu.model.Content;
import itu.master1.projetandroid.menu.view.detail.MadaDetailActivity;

public class DelayedMessageService extends IntentService {
    public static final String EXTRA_FREQUENCY = "EXTRA_FREQUENCY";
    public static final String EXTRA_CONTENT = "EXTRA_CONTENT";
    public static final int NOTIFICATION_ID = 5453;
    private boolean isContinued = true;


    public DelayedMessageService() {
        super("DelayedMessageService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        while(isContinued) {
            synchronized (this) {
                try {
                    int frequency = intent.getIntExtra(EXTRA_FREQUENCY, 3000);
                    System.out.println(frequency);
                    wait(frequency);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            List<Content> contentList = intent.getParcelableArrayListExtra(EXTRA_CONTENT);
            if(isContinued && contentList != null && contentList.size() > 0) {
                int len = contentList.size() - 1;
                int position = (int)(Math.random() * len) ;
                Content content = contentList.get(position);
                String text= content.getTitle();
                showText(text, content);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isContinued =  false;
    }

    private void showText(final String text, final Content content) {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_1")
                .setSmallIcon(R.drawable.ic_baseline_menu_24)
                .setContentTitle("HIJERY - Notification")
                .setContentText(text).setPriority(NotificationCompat.PRIORITY_HIGH).setVibrate(new long[]{0, 1000})
                .setAutoCancel(true);

        Intent actionIntent = new Intent(this, MadaDetailActivity.class);
        actionIntent.putExtra(MadaDetailActivity.EXTRA_CONTENT, content);

        PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(actionPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_1";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
