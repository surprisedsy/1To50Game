package com.example.a1to50game.GameMain;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a1to50game.R;

import java.util.ArrayList;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    private ArrayList<ButtonsNumInfo> numInfos = new ArrayList<>();

    @NonNull
    @Override
    public ButtonAdapter.ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buttons, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonAdapter.ButtonViewHolder buttonViewHolder, int position) {
        buttonViewHolder.onBind(numInfos.get(position));
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

        void onBind(ButtonsNumInfo info)
        {
            btn.setText(info.getButtonTxt());
        }
    }


//    private Context context;
//    private ArrayList<ButtonsNumInfo> buttonItems;
//
//    public ButtonAdapter() {}
//
//    public ButtonAdapter(Context context, ArrayList<ButtonsNumInfo> items) {
//        this.context = context;
//        this.buttonItems = items;
//    }
//
//    @NonNull
//    @Override
//    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
//
//        context = parent.getContext();
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        View view = layoutInflater.inflate(R.layout.buttons, parent, false);
//        ButtonViewHolder btnViewHolder = new ButtonViewHolder(view);
//
//        return btnViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ButtonViewHolder viewHolder, final int position) {
//
//        ButtonsNumInfo numInfo = buttonItems.get(position);
//
//        Button button1 = viewHolder.btn1;
//        button1.setText(numInfo.getButtonTxt());
////        button1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Log.d("aaaaaaa", "누른 버튼 position 정보" + position);
////            }
////        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return buttonItems.size();
//    }
//
//    public static class ButtonViewHolder extends RecyclerView.ViewHolder
//    {
//        private Button btn1;
//
//        public ButtonViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            btn1 = (Button) itemView.findViewById(R.id.btn01);
//        }
//    }
}
