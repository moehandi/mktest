package com.moehandi.moktest.ui.item;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.moehandi.moktest.R;
import com.moehandi.moktest.data.remote.model.Item;
import com.moehandi.moktest.ui.item.ItemListFragment.OnAllItemClickListener;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList<Item> mItems;
    private final OnAllItemClickListener mListener;

    public ItemListAdapter(ArrayList<Item> items, OnAllItemClickListener listener, Context context) {
        mItems = items;
        mListener = listener;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Item item = mItems.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvPrice.setText(String.valueOf(item.getPrice()));

        Glide.with(mContext)
                .load(item.getThumbnailUrl())
                .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_thumbnail_img_24dp))
                .into(holder.imgThumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentInteraction(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void swapData(List<Item> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_thumbnail)
        ImageView imgThumbnail;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ViewHolder(View layoutView) {
            super(layoutView);
            ButterKnife.bind(this, layoutView);
        }
    }
}
