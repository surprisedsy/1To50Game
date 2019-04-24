package com.example.a1to50game.Ranking;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a1to50game.R;

import java.util.List;

public class RankAdapter extends BaseAdapter {

    private Activity activity;
    private List<RankInfo> rankInfos;
    private LayoutInflater layoutInflater;

    private TextView nameTxt, recordTxt, numberTxt;

    public RankAdapter(Activity activity, List<RankInfo> rankInfos)
    {
        this.activity = activity;
        this.rankInfos = rankInfos;
    }

    @Override
    public int getCount() {
        return rankInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return rankInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(layoutInflater == null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.fragment_rank_list, null);

        numberTxt = convertView.findViewById(R.id.rank_rankTxt);
        nameTxt = convertView.findViewById(R.id.rank_nameTxt);
        recordTxt = convertView.findViewById(R.id.rank_recordTxt);

        RankInfo info = rankInfos.get(position);

        numberTxt.setText(info.getNumberTxt());
        nameTxt.setText(info.getNameTxt());
        recordTxt.setText(info.getRecordTxt());

        return convertView;
    }
}
