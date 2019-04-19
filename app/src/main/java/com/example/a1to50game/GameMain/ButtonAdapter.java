package com.example.a1to50game.GameMain;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a1to50game.R;

import java.util.ArrayList;
import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    private ArrayList<ButtonsNumInfo> numInfos = new ArrayList<>();

    @NonNull
    @Override
    public ButtonAdapter.ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buttons, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ButtonAdapter.ButtonViewHolder buttonViewHolder, final int position) {
        buttonViewHolder.btn.setText(numInfos.get(position).getButtonTxt());
    }

    @Override
    public int getItemCount() {
        return numInfos.size();
    }

    public void addItem(ButtonsNumInfo info)
    {
        numInfos.add(info);
    }

    class ButtonViewHolder extends  RecyclerView.ViewHolder{

        private Button btn;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.btn01);
        }
    }
}
