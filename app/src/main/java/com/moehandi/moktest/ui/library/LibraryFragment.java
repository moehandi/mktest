package com.moehandi.moktest.ui.library;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import com.moehandi.moktest.R;


public class LibraryFragment extends Fragment {

    public static final int ON_CLICK_DISCOUNTS = 1;
    public static final int ON_CLICK_ALL_ITEMS = 2;

    private OnLibraryInteractionListener mListener;

    public LibraryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LibraryFragment.
     */
    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLibraryInteractionListener) {
            mListener = (OnLibraryInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLibraryInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick({R.id.layout_discount, R.id.layout_items})
    public void onClickDiscounts(View view) {
        if (mListener != null) {
            int clickedItem = view.getId() == R.id.layout_discount ? ON_CLICK_DISCOUNTS : ON_CLICK_ALL_ITEMS;
            mListener.onLibraryFragmentInteraction(clickedItem);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnLibraryInteractionListener {
        void onLibraryFragmentInteraction(int clickedItem);
    }
}
