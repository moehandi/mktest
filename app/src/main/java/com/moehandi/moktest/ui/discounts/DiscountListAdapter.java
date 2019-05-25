package com.moehandi.moktest.ui.discounts;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.moehandi.moktest.R;
import com.moehandi.moktest.data.remote.model.Discount;

/**
 * Created by moehandi on 22/5/19.
 */

public class DiscountListAdapter extends RecyclerView.Adapter<DiscountListAdapter.ViewHolder> {

    private final ArrayList<Discount> mDiscounts;

    public DiscountListAdapter(ArrayList<Discount> mDiscounts) {
        this.mDiscounts = mDiscounts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discount, parent, false);
        return new DiscountListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Discount item = mDiscounts.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvPrice.setText(item.getDiscount()+" %");
    }

    @Override
    public int getItemCount() {
        return mDiscounts.size();
    }

    public void swapData(ArrayList<Discount> discountList) {
        mDiscounts.clear();
        mDiscounts.addAll(discountList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_discount)
        TextView tvPrice;

        public ViewHolder(View layoutView) {
            super(layoutView);
            ButterKnife.bind(this, layoutView);
        }
    }

}
