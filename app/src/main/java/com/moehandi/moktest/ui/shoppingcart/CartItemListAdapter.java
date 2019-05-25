package com.moehandi.moktest.ui.shoppingcart;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.moehandi.moktest.R;
import com.moehandi.moktest.data.local.model.CartItem;
import com.moehandi.moktest.ui.shoppingcart.ShoppingCartFragment.OnCartItemInteractionListener;

public class CartItemListAdapter extends RecyclerView.Adapter<CartItemListAdapter.ViewHolder> {

    private final List<CartItem> mValues;
    private final ShoppingCartFragment.OnCartItemInteractionListener mListener;

    public CartItemListAdapter(List<CartItem> items, OnCartItemInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CartItem item = mValues.get(position);

        if (item.getItemId() == -1) {
            holder.tvPrice.setText("$ " + String.valueOf(item.getTotalPrice()));
            holder.tvTitle.setText(item.getItemTitle());
            holder.tvQuantity.setText("");
            holder.itemView.setOnClickListener(null);
            return;
        }

        holder.tvTitle.setText(item.getItemTitle());
        holder.tvQuantity.setText("x" + item.getQuantity());
        holder.tvPrice.setText("$ " + String.valueOf(item.getTotalPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editCartItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void swapData(ArrayList<CartItem> items) {
        mValues.clear();
        mValues.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_quantity)
        TextView tvQuantity;

        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
