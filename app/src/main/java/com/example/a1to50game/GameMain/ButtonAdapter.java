package com.example.a1to50game.GameMain;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a1to50game.GameActivity;
import com.example.a1to50game.R;

import java.util.ArrayList;
import java.util.Random;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    private ArrayList<ButtonsNumInfo> buttonItems;

    public ButtonAdapter() {}

    public ButtonAdapter(ArrayList<ButtonsNumInfo> items) {
        this.buttonItems = items;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.buttons, parent, false);
        ButtonViewHolder btnViewHolder = new ButtonViewHolder(view);

        return btnViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder viewHolder, int position) {

        ButtonsNumInfo numInfo = buttonItems.get(position);

        Button button1 = viewHolder.btn1;
        button1.setText(numInfo.getButtonTxt());

        Button button2 = viewHolder.btn2;
        button2.setText(numInfo.getButtonTxt());

        Button button3 = viewHolder.btn3;
        button3.setText(numInfo.getButtonTxt());

        Button button4 = viewHolder.btn4;
        button4.setText(numInfo.getButtonTxt());

        Button button5 = viewHolder.btn5;
        button5.setText(numInfo.getButtonTxt());
    }

    @Override
    public int getItemCount() {
        return buttonItems.size();
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder
    {
        private Button btn1, btn2, btn3, btn4, btn5;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);

            btn1 = (Button) itemView.findViewById(R.id.btn01);
            btn2 = (Button) itemView.findViewById(R.id.btn02);
            btn3 = (Button) itemView.findViewById(R.id.btn03);
            btn4 = (Button) itemView.findViewById(R.id.btn04);
            btn5 = (Button) itemView.findViewById(R.id.btn05);
        }
    }
}
