package com.moehandi.moktest.ui.item;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.moehandi.moktest.R;
import com.moehandi.moktest.di.ApplicationComponent;
import com.moehandi.moktest.data.remote.model.Item;
import com.moehandi.moktest.ui.base.BaseFragment;
import com.moehandi.moktest.util.AppUtil;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnAllItemClickListener}
 * interface.
 */
public class ItemListFragment extends BaseFragment implements IItemListView {

    @BindView(R.id.recycleview_items)
    RecyclerView mRecyclerView;

    @Inject
    IItemListPresenter mItemListPresenter;

    private ItemListAdapter mAdapter;
    private OnAllItemClickListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListFragment() {
    }

    @SuppressWarnings("unused")
    public static ItemListFragment newInstance() {
        ItemListFragment fragment = new ItemListFragment();
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
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);

        setAdapter();
        mItemListPresenter.attachView(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mItemListPresenter.detachView();
    }

    private void setAdapter() {
        mAdapter = new ItemListAdapter(new ArrayList<Item>(), mListener, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAllItemClickListener) {
            mListener = (OnAllItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAllItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnAllItemClickListener {
        void onListFragmentInteraction(Item item);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////              METHODS FROM @IItemListView INTERFACE                 //////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onAllItemsSuccess(List<Item> itemList) {
        mAdapter.swapData(itemList);
    }

    @Override
    public void onAllItemsFailure(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public boolean isNetworkConnected() {
        return AppUtil.isNetworkConnected(getContext());
    }
}
