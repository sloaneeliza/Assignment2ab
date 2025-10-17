package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private InfoWebFragment infoWebFragment;
    private TickerListFragment tickerListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tickerListFragment = (TickerListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.tickerListFragment);
        infoWebFragment = (InfoWebFragment) getSupportFragmentManager()
                .findFragmentById(R.id.infoWebFragment);
    }

    public void loadTickerInWebView(String ticker) {
        infoWebFragment.loadTicker(ticker);
    }

    public void addTickerFromSMS(String ticker) {
        tickerListFragment.addTicker(ticker);
    }

    private void handleIncomingIntent() {
        if (getIntent() == null) return;
        String action = getIntent().getStringExtra("sms_action");
        if (action == null) return;

        switch (action) {
            case "valid_ticker":
                String ticker = getIntent().getStringExtra("new_ticker");
                if (ticker != null && !ticker.isEmpty()) {
                    if (tickerListFragment != null) {
                        tickerListFragment.addTicker(ticker);
                    }
                    if (infoWebFragment != null) {
                        infoWebFragment.loadTicker(ticker);
                    }
                }
                break;

            case "invalid_ticker":
                String bad = getIntent().getStringExtra("received_ticker");
                if (bad != null) {
                    android.widget.Toast.makeText(this,
                            "Invalid ticker received: " + bad,
                            android.widget.Toast.LENGTH_LONG).show();
                }
                break;

            case "no_valid_format":
                android.widget.Toast.makeText(this,
                        "No valid watchlist entry found in SMS",
                        android.widget.Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIncomingIntent();
    }
}
