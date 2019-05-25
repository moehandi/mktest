package com.moehandi.moktest.ui.addtocart;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.moehandi.moktest.R;
import com.moehandi.moktest.data.remote.model.Discount;

/**
 * Created by moehandi on 22/5/19.
 */

public class ApplyDiscountListAdapter extends RecyclerView.Adapter<ApplyDiscountListAdapter.ViewHolder> {

    private final ArrayList<Discount> mDiscounts;
    private double mEnabledDiscount;

    public ApplyDiscountListAdapter(ArrayList<Discount> mDiscounts, double enabledPosition) {
        this.mDiscounts = mDiscounts;
        this.mEnabledDiscount = enabledPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discount_apply, parent, false);
        return new ApplyDiscountListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Discount item = mDiscounts.get(position);
        holder.tvTitle.setText(item.getTitle() + " (" + item.getDiscount() + "%)");

        holder.switchDiscount.setOnCheckedChangeListener(null);

        final double discount = item.getDiscount();
        holder.switchDiscount.setChecked(mEnabledDiscount == discount);

        holder.switchDiscount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEnabledDiscount = discount;
                } else {
                    mEnabledDiscount = -1;
                }
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDiscounts.size();
    }

    public double getDiscount() {
        return mEnabledDiscount == -1 ? 0 : mEnabledDiscount;
    }

    public void swapData(ArrayList<Discount> discountList) {
        mDiscounts.clear();
        mDiscounts.addAll(discountList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.switch_discount)
        Switch switchDiscount;

        public ViewHolder(View layoutView) {
            super(layoutView);
            ButterKnife.bind(this, layoutView);
        }
    }
}
