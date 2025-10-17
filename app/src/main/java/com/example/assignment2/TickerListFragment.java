package com.example.assignment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class TickerListFragment extends Fragment {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> tickerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ticker_list, container, false);

        ListView listView = root.findViewById(R.id.tickerListView);
        tickerList = new ArrayList<>(Arrays.asList("NEE", "AAPL", "DIS"));

        adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, tickerList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String ticker = tickerList.get(position);
            ((MainActivity) requireActivity()).loadTickerInWebView(ticker);
        });

        return root;
    }

    public void addTicker(String ticker) {
        if (tickerList.size() < 6) {
            tickerList.add(ticker);
        } else {
            tickerList.set(5, ticker); //replace 6th entry
        }
        adapter.notifyDataSetChanged();
    }
}

