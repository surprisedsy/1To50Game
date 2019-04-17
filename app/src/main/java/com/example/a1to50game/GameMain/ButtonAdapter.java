package com.example.a1to50game.GameMain;

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

    private ArrayList<ButtonItems> buttonItems;

    public ButtonAdapter() {}

    public ButtonAdapter(ArrayList<ButtonItems> items) {
        buttonItems = items;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buttons, viewGroup, false);
        ButtonViewHolder btnViewHolder = new ButtonViewHolder(v);
        return btnViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder viewHolder, int position) {
        viewHolder.btn1.setText(GameActivity.buttonItems.get(position).getButtonTxt());
        viewHolder.btn2.setText(GameActivity.buttonItems.get(position).getButtonTxt());
    }

    @Override
    public int getItemCount() {
        return GameActivity.buttonItems.size();
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Button btn1, btn2, btn3, btn4, btn5;


        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);

            btn1 = (Button) itemView.findViewById(R.id.btn01);
            btn2 = (Button) itemView.findViewById(R.id.btn02);
            btn3 = (Button) itemView.findViewById(R.id.btn03);
            btn4 = (Button) itemView.findViewById(R.id.btn04);
            btn5 = (Button) itemView.findViewById(R.id.btn05);

            btn1.setTag(R.integer.btn01, itemView);
            btn2.setTag(R.integer.btn02, itemView);
            btn3.setTag(R.integer.btn03, itemView);
            btn4.setTag(R.integer.btn04, itemView);
            btn5.setTag(R.integer.btn05, itemView);

            btn1.setOnClickListener(this);
            btn2.setOnClickListener(this);
            btn3.setOnClickListener(this);
            btn4.setOnClickListener(this);
            btn5.setOnClickListener(this);

            int rndNum1 = randomize(1, 25);

            btn1.setText("" + rndNum1);
            btn2.setText("" + rndNum1);
            btn3.setText("" + rndNum1);
            btn4.setText("" + rndNum1);
            btn5.setText("" + rndNum1);
        }

        @Override
        public void onClick(View v) {

            int rndNum2 = randomize(26, 50);

            if(v.getId() == btn1.getId())
                btn1.setText("" + rndNum2);
            if(v.getId() == btn2.getId())
                btn2.setText("" + rndNum2);
            if(v.getId() == btn3.getId())
                btn3.setText("" + rndNum2);
            if(v.getId() == btn4.getId())
                btn4.setText("" + rndNum2);
            if(v.getId() == btn5.getId())
                btn5.setText("" + rndNum2);
        }

        private int randomize(int from, int to)
        {
            Random rnd = new Random();
            int i = rnd.nextInt(to) + from;
            return i;
        }

    }
}
