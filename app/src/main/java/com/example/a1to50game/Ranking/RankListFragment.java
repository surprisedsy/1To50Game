package com.example.a1to50game.Ranking;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1to50game.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankListFragment extends Fragment {

    public RankListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rank_list, container, false);
    }
}
