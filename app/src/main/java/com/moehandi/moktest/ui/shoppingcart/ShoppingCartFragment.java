package com.moehandi.moktest.ui.shoppingcart;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.moehandi.moktest.R;
import com.moehandi.moktest.di.ApplicationComponent;
import com.moehandi.moktest.data.local.model.CartItem;
import com.moehandi.moktest.ui.base.BaseFragment;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnCartItemInteractionListener}
 * interface.
 */
public class ShoppingCartFragment extends BaseFragment implements IShoppingCartView {

    @BindView(R.id.recycleview_items)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_no_items)
    TextView tvNoItems;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @Inject
    IShoppingCartPresenter mShoppingCartPresenter;

    private CartItemListAdapter mAdapter;

    private OnCartItemInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingCartFragment() {
    }


    @SuppressWarnings("unused")
    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void injectComponent(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartitem_list, container, false);
        ButterKnife.bind(this, view);

        setAdapter();
        mShoppingCartPresenter.attachView(this);

        return view;
    }

    private void setAdapter() {
        mAdapter = new CartItemListAdapter(new ArrayList<CartItem>(), mListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mShoppingCartPresenter.detachView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCartItemInteractionListener) {
            mListener = (OnCartItemInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCartItemInteractionListener");
        }
    }

    @OnClick(R.id.btn_clear)
    public void onClickClear(View v) {
        mShoppingCartPresenter.onClickClearButton();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void refreshCartList() {
        mShoppingCartPresenter.getCartItems();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCartItemInteractionListener {
        void editCartItem(CartItem item);
    }

    @Override
    public void displayCartItems(ArrayList<CartItem> items) {
        tvNoItems.setVisibility(items.size() == 0 ? View.VISIBLE : View.GONE);
        mAdapter.swapData(items);
    }

    @Override
    public void displayFinalTotalAmount(double total) {
        tvTotal.setText("$ " + String.format("%.2f", total));
    }
}
