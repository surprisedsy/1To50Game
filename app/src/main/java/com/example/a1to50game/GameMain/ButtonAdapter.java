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

import java.util.Vector;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    public Vector<Integer> _1to50 = new Vector<>();
    private Vector<Integer> visible = new Vector<>();
    private Context context;

    public ButtonAdapter(Context context) {
        this.context = context;

        for (int i = 0; i < 25; i++)
            visible.add(i, View.VISIBLE);
    }

    @NonNull
    @Override
    public ButtonAdapter.ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buttons, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ButtonAdapter.ButtonViewHolder buttonViewHolder, final int position) {

        int numbers = _1to50.get(position);
        buttonViewHolder.btn.setText(String.valueOf(numbers));
        buttonViewHolder.btn.setVisibility(visible.get(position));
    }

    @Override
    public int getItemCount() {
        return _1to50.size();
    }

    public void init1to25(int number) {
        _1to50.add(number);
    }

    public void updateNum(int position, int number) {
        _1to50.remove(position);
        _1to50.add(position, number);
    }

    public void setUpVisible(int position) {
        visible.remove(position);
        visible.add(position, View.INVISIBLE);
    }

    public int getBtnNums(int number) {
        return _1to50.get(number);
    }

    class ButtonViewHolder extends RecyclerView.ViewHolder {

        private Button btn;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.btn01);
        }
    }
}
