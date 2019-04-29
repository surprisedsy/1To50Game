package com.example.a1to50game.GameMain;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a1to50game.R;

import java.util.Vector;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private Vector<Integer> entireGroup_1to50 = new Vector<>();
    private Vector<Integer> visibleGroup = new Vector<>();
    private Context mContext;

    public GameAdapter(Context context) {
        this.mContext = context;

        for (int i = 0; i < 25; i++)
            visibleGroup.add(i, View.VISIBLE);
    }

    @NonNull
    @Override
    public GameAdapter.GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buttons, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GameAdapter.GameViewHolder viewHolder, final int position) {

        int numbers = entireGroup_1to50.get(position);
        viewHolder.btn.setText(String.valueOf(numbers));
        viewHolder.btn.setVisibility(visibleGroup.get(position));
    }

    @Override
    public int getItemCount() {
        return entireGroup_1to50.size();
    }

    /**
     *
     * @param number 초기화 할 숫자
     */
    public void init1to25(int number) {
        entireGroup_1to50.add(number);
    }

    /**
     *
      * @param position button position
     * @param number 지워진 button position에 넣을 랜덤숫자
     */
    public void updateNum(int position, int number) {
        entireGroup_1to50.remove(position);
        entireGroup_1to50.add(position, number);
    }

    /**
     *
     * @param position number < 26 일때 position remove & button INVISIBLE
     */
    public void setUpVisible(int position) {
        visibleGroup.remove(position);
        visibleGroup.add(position, View.INVISIBLE);
    }

    /**
     *
     * @param number position index
     * @return 생성된 button position의 숫자를 가져오려고 만들었음.
     */
    public int getBtnNums(int number) {
        return entireGroup_1to50.get(number);
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        private Button btn;

        private GameViewHolder(@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.btn01);
        }
    }
}
