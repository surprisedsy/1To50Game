package com.example.a1to50game.Ranking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1to50game.R;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranktext, parent, false);
        return new RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder rankViewHolder, int position) {

        RankInfo info = rankInfoVector.get(position);

        if (position < 10) {
            rankViewHolder.numberTxt.setText(info.getNumberTxt());
            rankViewHolder.nameTxt.setText(info.getNameTxt());
            rankViewHolder.recordTxt.setText(info.getRecordTxt());
        } else {
            rankViewHolder.numberTxt.setVisibility(View.GONE);
            rankViewHolder.nameTxt.setVisibility(View.GONE);
            rankViewHolder.recordTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return rankInfoVector.size();
    }

    class RankViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTxt, recordTxt, numberTxt;

        private RankViewHolder(@NonNull View itemView) {
            super(itemView);

            numberTxt = itemView.findViewById(R.id.rank_rankTxt);
            nameTxt = itemView.findViewById(R.id.rank_nameTxt);
            recordTxt = itemView.findViewById(R.id.rank_recordTxt);
        }
    }
}
