package com.example.assignment2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;


public class InfoWebFragment extends Fragment {
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info_web, container, false);

        webView = root.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        // default load
        webView.loadUrl("https://seekingalpha.com");

        return root;
    }

    public void loadTicker(String ticker) {
        String url = "https://seekingalpha.com/symbol/" + ticker;
        webView.loadUrl(url);
    }
}
