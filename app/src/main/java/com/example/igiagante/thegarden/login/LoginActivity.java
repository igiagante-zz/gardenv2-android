package com.example.igiagante.thegarden.login;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.home.MainActivity;
import com.example.igiagante.thegarden.login.di.DaggerLoginComponent;
import com.example.igiagante.thegarden.login.di.LoginComponent;
import com.example.igiagante.thegarden.login.fragments.LoginFragment;
import com.google.android.gms.analytics.HitBuilders;

import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class LoginActivity extends BaseActivity implements HasComponent<LoginComponent> {

    private LoginComponent loginComponent;

    // one hour
    private final static int INTERVAL_TIME = 60 * 60 * 1000;

    @Override
    public LoginComponent getComponent() {
        return loginComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initializeInjector();

        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        tracker.setScreenName("LoginActivity");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fragment loginFragment = new LoginFragment();
        addFragment(R.id.login_container_data, loginFragment);

        scheduleNotification(getNotification(), INTERVAL_TIME);
    }

    private void initializeInjector() {
        this.loginComponent = DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    /**
     * Schedule notification to be sent each hour
     *
     * @param notification Notification object
     * @param delay        interval time
     */
    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    /**
     * Create notification
     *
     * @return Notification
     */
    private Notification getNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        return new NotificationCompat.Builder(this)
                .setTicker(getString(R.string.notification_title))
                .setSmallIcon(R.drawable.ic_water)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
    }
}
