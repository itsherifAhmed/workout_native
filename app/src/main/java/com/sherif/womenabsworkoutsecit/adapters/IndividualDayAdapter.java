package com.sherif.womenabsworkoutsecit.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sherif.womenabsworkoutsecit.R;
import com.sherif.womenabsworkoutsecit.activities.ExcDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;

import kr.pe.burt.android.lib.faimageview.FAImageView;

public class IndividualDayAdapter extends RecyclerView.Adapter<IndividualDayAdapter.ViewHolder> {
    public HashMap<String, ArrayList<Integer>> arrayListHashMap = this.arrayListHashMap;
    public Context context;
    public String day;
    public int screenWidth;
    public ArrayList<WorkoutData> workoutDataList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        /* renamed from: a  reason: collision with root package name */
        public TextView f875a;
        public TextView b;
        public FAImageView c;
        public RelativeLayout d;

        public ViewHolder(View view) {
            super(view);
            this.f875a = (TextView) view.findViewById(R.id.exerciseName);
            this.b = (TextView) view.findViewById(R.id.rotation);
            this.c = (FAImageView) view.findViewById(R.id.animation);
            this.d = (RelativeLayout) view.findViewById(R.id.cardViewInRecycler);
        }
    }

    public IndividualDayAdapter(Context context2, String str, ArrayList<WorkoutData> arrayList, int i) {
        this.context = context2;
        this.screenWidth = i;
        this.day = str;
        this.workoutDataList = arrayList;
    }

    public int getItemCount() {
        return this.workoutDataList.size();
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        StringBuilder sb;
        TextView textView;
        if (i < this.workoutDataList.size()) {
            viewHolder.d.setVisibility(View.VISIBLE);
            viewHolder.c.setInterval(1000);
            viewHolder.c.setLoop(true);
            viewHolder.c.reset();
            for (int addImageFrame : this.workoutDataList.get(i).getImageIdList()) {
                viewHolder.c.addImageFrame(addImageFrame);
            }
            viewHolder.c.startAnimation();
            viewHolder.f875a.setText(this.workoutDataList.get(i).getExcName().replace("_", " ").toUpperCase());
            if (this.workoutDataList.get(i).getExcName().equals("plank")) {
                textView = viewHolder.b;
                sb = new StringBuilder();
                sb.append(this.workoutDataList.get(i).getExcCycles());
                sb.append("s");
            } else {
                textView = viewHolder.b;
                sb = new StringBuilder();
                sb.append("x");
                sb.append(this.workoutDataList.get(i).getExcCycles());
            }
            textView.setText(sb.toString());
        } else {
            viewHolder.d.setVisibility(View.GONE);
        }
        viewHolder.d.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(IndividualDayAdapter.this.context, ExcDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("day", IndividualDayAdapter.this.day);
                bundle.putIntArray("framesIdArray", ((WorkoutData) IndividualDayAdapter.this.workoutDataList.get(i)).getImageIdList());
                bundle.putString("excName", ((WorkoutData) IndividualDayAdapter.this.workoutDataList.get(i)).getExcName());
                bundle.putInt("excNameDescResId", ((WorkoutData) IndividualDayAdapter.this.workoutDataList.get(i)).getExcDescResId());
                bundle.putInt("excCycle", ((WorkoutData) IndividualDayAdapter.this.workoutDataList.get(i)).getExcCycles());
                bundle.putInt("excPosition", i);
                intent.putExtras(bundle);
                IndividualDayAdapter.this.context.startActivity(intent);
            }
        });
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_days, viewGroup, false));
    }
}
