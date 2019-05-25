package com.moehandi.moktest.ui.addtocart;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.moehandi.moktest.R;
import com.moehandi.moktest.di.ApplicationComponent;
import com.moehandi.moktest.data.remote.model.Discount;
import com.moehandi.moktest.ui.base.BaseDialogFragment;
import com.moehandi.moktest.ui.discounts.IDiscountPresenter;
import com.moehandi.moktest.ui.discounts.IDiscountView;
import com.moehandi.moktest.ui.item.ItemListFragment;
import com.moehandi.moktest.util.InputNumberFilter;

/**
 * Created by moehandi on 22/5/19.
 */

public class AddToCartDialogFragment extends BaseDialogFragment implements IAddToCartView, IDiscountView {

    private static final String TAG = AddToCartDialogFragment.class.getName();

    private static final String ARG_EXTRA_INT_ITEM_ID = "arg_extra_int_item_id_" + TAG;
    private static final String ARG_EXTRA_STRING_ITEM_TITLE = "arg_extra_int_item_title_" + TAG;
    private static final String ARG_EXTRA_DOUBLE_ITEM_PRICE = "arg_extra_int_item_price_" + TAG;
    private static final String ARG_EXTRA_BOOLEAN_FLAG_EDIT = "arg_extra_int_flag_edit_" + TAG;
    private static final String ARG_EXTRA_INT_ITEM_QUANTITY = "arg_extra_int_item_quantity_" + TAG;
    private static final String ARG_EXTRA_INT_ITEM_DISCOUNT = "arg_extra_int_item_discount_" + TAG;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.edt_quantity)
    EditText edtQuantity;

    @BindView(R.id.recycleview_discounts)
    RecyclerView mRecyclerView;

    private ApplyDiscountListAdapter mAdapter;

    @Inject
    IAddToCartPresenter mAddToCartPresenter;

    @Inject
    IDiscountPresenter mDiscountPresenter;

    private OnItemAddedToCartListener mListener;

    public AddToCartDialogFragment() {
    }

    public static AddToCartDialogFragment newInstance(int itemId, String itemTitle, double itemPrice, int quantity, double discount, boolean editFlag) {
        AddToCartDialogFragment dialogFragment = new AddToCartDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_EXTRA_INT_ITEM_ID, itemId);
        bundle.putString(ARG_EXTRA_STRING_ITEM_TITLE, itemTitle);
        bundle.putDouble(ARG_EXTRA_DOUBLE_ITEM_PRICE, itemPrice);
        bundle.putBoolean(ARG_EXTRA_BOOLEAN_FLAG_EDIT, editFlag);
        bundle.putInt(ARG_EXTRA_INT_ITEM_QUANTITY, quantity);
        bundle.putDouble(ARG_EXTRA_INT_ITEM_DISCOUNT, discount);

        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void injectComponent(ApplicationComponent component) {
        component.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_to_cart, container, false);
        ButterKnife.bind(this, view);

        setAdapter();
        setEditTextQuantity();
        mAddToCartPresenter.attachView(this);
        mDiscountPresenter.attachView(this);
        return view;
    }

    private void setEditTextQuantity() {
        edtQuantity.setFilters(new InputFilter[]{new InputNumberFilter(1, 1000)});
        edtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAddToCartPresenter.onQuantityEdited(s.toString());
            }
        });
    }

    private void setAdapter() {
        mAdapter = new ApplyDiscountListAdapter(new ArrayList<Discount>(), getItemDiscount());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemListFragment.OnAllItemClickListener) {
            mListener = (OnItemAddedToCartListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAllItemClickListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAddToCartPresenter.detachView();
        mDiscountPresenter.detachView();
    }

    @OnClick({R.id.btn_increase, R.id.btn_decrease})
    public void onChangeQuantity(View view) {
        switch (view.getId()) {
            case R.id.btn_increase:
                mAddToCartPresenter.onClickIncrement();
                break;
            case R.id.btn_decrease:
                mAddToCartPresenter.onClickDecrement();
                break;
        }
    }

    @OnClick(R.id.btn_cancel)
    public void onClickCancel(View view) {
        mAddToCartPresenter.onClickCancel();
    }

    @OnClick(R.id.btn_save)
    public void onClickSave(View view) {
        mAddToCartPresenter.onclickSave();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnItemAddedToCartListener {
        void onItemAdded();
    }

    @Override
    public int getItemId() {
        return getArguments().getInt(ARG_EXTRA_INT_ITEM_ID);
    }

    @Override
    public String getItemTitle() {
        return getArguments().getString(ARG_EXTRA_STRING_ITEM_TITLE);
    }

    @Override
    public double getItemPrice() {
        return getArguments().getDouble(ARG_EXTRA_DOUBLE_ITEM_PRICE);
    }

    @Override
    public int getItemQuantity() {
        return getArguments().getInt(ARG_EXTRA_INT_ITEM_QUANTITY);
    }

    @Override
    public double getItemDiscount() {
        return getArguments().getDouble(ARG_EXTRA_INT_ITEM_DISCOUNT);
    }

    @Override
    public boolean isEdit() {
        return getArguments().getBoolean(ARG_EXTRA_BOOLEAN_FLAG_EDIT, false);
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setQuantity(int quantity) {
        edtQuantity.setText(String.valueOf(quantity));
        edtQuantity.setSelection(edtQuantity.getText().length());
    }

    @Override
    public double getChosenDiscount() {
        return mAdapter.getDiscount();
    }

    @Override
    public void addedToCart() {
        if (mListener != null) {
            mListener.onItemAdded();
        }
    }

    @Override
    public void displayDiscounts(ArrayList<Discount> discountList) {
        mAdapter.swapData(discountList);
    }
}
