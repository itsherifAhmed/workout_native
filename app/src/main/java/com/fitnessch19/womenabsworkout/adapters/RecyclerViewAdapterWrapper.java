package com.fitnessch19.womenabsworkout.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapterWrapper extends RecyclerView.Adapter {
    public final RecyclerView.Adapter<RecyclerView.ViewHolder> wrapped;

    public RecyclerViewAdapterWrapper(RecyclerView.Adapter adapter) {
        this.wrapped = adapter;
        this.wrapped.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onChanged() {
                RecyclerViewAdapterWrapper.this.notifyDataSetChanged();
            }

            public void onItemRangeChanged(int i, int i2) {
                RecyclerViewAdapterWrapper.this.notifyItemRangeChanged(i, i2);
            }

            public void onItemRangeInserted(int i, int i2) {
                RecyclerViewAdapterWrapper.this.notifyItemRangeInserted(i, i2);
            }

            public void onItemRangeMoved(int i, int i2, int i3) {
                RecyclerViewAdapterWrapper.this.notifyItemMoved(i, i2);
            }

            public void onItemRangeRemoved(int i, int i2) {
                RecyclerViewAdapterWrapper.this.notifyItemRangeRemoved(i, i2);
            }
        });
    }

    public int getItemCount() {
        return this.wrapped.getItemCount();
    }

    public long getItemId(int i) {
        return this.wrapped.getItemId(i);
    }

    public int getItemViewType(int i) {
        return this.wrapped.getItemViewType(i);
    }

    public RecyclerView.Adapter<RecyclerView.ViewHolder> getWrappedAdapter() {
        return this.wrapped;
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.wrapped.onAttachedToRecyclerView(recyclerView);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        this.wrapped.onBindViewHolder(viewHolder, i);
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return this.wrapped.onCreateViewHolder(viewGroup, i);
    }

    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.wrapped.onDetachedFromRecyclerView(recyclerView);
    }

    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder viewHolder) {
        return this.wrapped.onFailedToRecycleView(viewHolder);
    }

    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
        this.wrapped.onViewAttachedToWindow(viewHolder);
    }

    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
        this.wrapped.onViewDetachedFromWindow(viewHolder);
    }

    public void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
        this.wrapped.onViewRecycled(viewHolder);
    }

    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver adapterDataObserver) {
        this.wrapped.registerAdapterDataObserver(adapterDataObserver);
    }

    public void setHasStableIds(boolean z) {
        this.wrapped.setHasStableIds(z);
    }

    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver adapterDataObserver) {
        this.wrapped.unregisterAdapterDataObserver(adapterDataObserver);
    }
}
