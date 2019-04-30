package com.example.a1to50game.Ranking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1to50game.databinding.RanktextBinding;

import java.util.Vector;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    private Vector<RankInfo> rankInfoVector;
    private Context mContext;

    public RankAdapter(Context context, Vector<RankInfo> rankInfoVector) {
        this.mContext = context;
        this.rankInfoVector = rankInfoVector;
    }

    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new RankViewHolder(RanktextBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder rankViewHolder, int position) {

        RankInfo info = rankInfoVector.get(position);

        if (position < 10) {
            rankViewHolder.binding.rankNumTxt.setText(info.getNumberTxt());
            rankViewHolder.binding.rankNameTxt.setText(info.getNameTxt());
            rankViewHolder.binding.rankRecordTxt.setText(info.getRecordTxt());
        } else {
            rankViewHolder.binding.rankNumTxt.setVisibility(View.GONE);
            rankViewHolder.binding.rankNameTxt.setVisibility(View.GONE);
            rankViewHolder.binding.rankRecordTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return rankInfoVector.size();
    }

    class RankViewHolder extends RecyclerView.ViewHolder {

        RanktextBinding binding;

        private RankViewHolder(RanktextBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
