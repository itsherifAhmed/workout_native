package com.fitnessch19.womenabsworkout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.fitnessch19.womenabsworkout.R;

import java.util.List;

public class AllDayAdapter extends RecyclerView.Adapter<AllDayAdapter.ViewHolder> {


    public List<WorkoutData> f869a;
    public AlphaAnimation alphaAnimation;
    public Context mContext;
    public int position = -1;

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView f870a;
        public NumberProgressBar b;
        public CardView c;
        public ImageView d;
        public RelativeLayout rel1,rel2;

        public ViewHolder(View view) {
            super(view);
            this.f870a = (TextView) view.findViewById(R.id.row_day);
            this.c = (CardView) view.findViewById(R.id.cardviewone);
            this.d = (ImageView) view.findViewById(R.id.restImageView);
            this.b = (NumberProgressBar) view.findViewById(R.id.progressbar);
            this.rel1 = (RelativeLayout) view.findViewById(R.id.rel1);
            this.rel2 = (RelativeLayout) view.findViewById(R.id.rel2);
        }
    }

    public AllDayAdapter(Context context, List<WorkoutData> list) {
        this.mContext = context;
        this.f869a = list;
    }

    public int getItemCount() {
        List<WorkoutData> list = this.f869a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        viewHolder.b.setMax(100);
//        int i2 = i + 1;
//        if (i2 % 4 != 0 || i > 27) {
//            viewHolder.d.setVisibility(View.GONE);
//            TextView textView = viewHolder.f870a;
//            textView.setText(this.mContext.getResources().getString(R.string.day) + " " + i2);
//            viewHolder.b.setVisibility(View.VISIBLE);
//            if (((int) this.f869a.get(i).getProgress()) >= 99) {
//                viewHolder.b.setProgress(100);
//            } else {
//                viewHolder.b.setProgress((int) this.f869a.get(i).getProgress());
//            }
//        } else {
//            viewHolder.b.setVisibility(View.GONE);
//            viewHolder.f870a.setText(R.string.restday);
//            viewHolder.f870a.setTextColor(this.mContext.getResources().getColor(R.color.mainactivity_text));
//            viewHolder.d.setVisibility(View.VISIBLE);
//        }
//        PushDownAnim.setOnTouchPushDownAnim((View) viewHolder.c).setScale(1).setDurationPush(50).setDurationRelease(50).setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR).setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
//    }
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.b.setMax(100);
        if ((i + 1) % 4 != 0 || i > 27) {
            viewHolder.d.setVisibility(View.GONE);
            viewHolder.rel2.setVisibility(View.GONE);


            viewHolder.f870a.setText(((WorkoutData) this.f869a.get(i)).getDay());
            viewHolder.b.setVisibility(View.VISIBLE);
            viewHolder.rel1.setVisibility(View.VISIBLE);
            if (((int) ((WorkoutData) this.f869a.get(i)).getProgress()) >= 99) {
                viewHolder.b.setProgress(100);
            } else {
                viewHolder.b.setProgress((int) ((WorkoutData) this.f869a.get(i)).getProgress());
            }
        } else {
            viewHolder.b.setVisibility(View.GONE);
            viewHolder.rel1.setVisibility(View.GONE);
            viewHolder.f870a.setText("Rest Day");
            viewHolder.d.setVisibility(View.VISIBLE);
            viewHolder.rel2.setVisibility(View.VISIBLE);

        }
    }
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_days_row, viewGroup, false));
    }


}
