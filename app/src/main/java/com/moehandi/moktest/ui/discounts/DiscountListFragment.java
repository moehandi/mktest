package com.moehandi.moktest.ui.discounts;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.moehandi.moktest.R;
import com.moehandi.moktest.di.ApplicationComponent;
import com.moehandi.moktest.data.remote.model.Discount;
import com.moehandi.moktest.ui.base.BaseFragment;

public class DiscountListFragment extends BaseFragment implements IDiscountView {

    @BindView(R.id.recycleview_discounts)
    RecyclerView mRecyclerView;

    @Inject
    IDiscountPresenter mDiscountPresenter;

    private DiscountListAdapter mAdapter;

    public DiscountListFragment() {
        // Required empty public constructor
    }

    public static DiscountListFragment newInstance() {
        DiscountListFragment fragment = new DiscountListFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discount_list, container, false);

        ButterKnife.bind(this, view);

        setAdapter();
        mDiscountPresenter.attachView(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDiscountPresenter.detachView();
    }

    private void setAdapter() {
        mAdapter = new DiscountListAdapter(new ArrayList<Discount>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void displayDiscounts(ArrayList<Discount> discountList) {
        mAdapter.swapData(discountList);
    }
}
