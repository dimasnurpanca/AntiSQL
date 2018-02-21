package com.dnp.antisql.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dnp.antisql.R;
import com.dnp.antisql.adapter.HomeAdapter;
import com.dnp.antisql.ui.GrafikActivity;
import com.dnp.antisql.ui.HomeActivity;
import com.dnp.antisql.ui.InformasiActivity;
import com.dnp.antisql.ui.LogActivity;
import com.dnp.antisql.ui.PercobaanActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class GridFragment extends Fragment {

    @BindView(R.id.gridView)
    GridView gridView;
    Unbinder unbinder;

    private OnFragmentInteractionListener mListener;

    public static GridFragment newInstance() {
        GridFragment fragment = new GridFragment();
        return fragment;
    }

    public GridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> list = initGridData();
        setAdapter(list);
    }

    @NonNull
    private List<String> initGridData() {
        List<String> list = new ArrayList<>();
        list.add("Log");
        list.add("Grafik");
        list.add("Percobaan");
        list.add("Informasi");
        return list;
    }

    private void setAdapter(List<String> list) {
        HomeAdapter HomeAdapter = new HomeAdapter(getActivity().getApplicationContext(), list);
        gridView.setAdapter(HomeAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onGridClicked(position);
            }
        });
    }

    private void onGridClicked(int position) {
        switch (position){
            case 0:
             Intent i1 = new Intent(getActivity().getApplicationContext(), LogActivity.class);
               startActivityForResult(i1,0);
                break;
            case 1:
              Intent i2 = new Intent(getActivity().getApplicationContext(), GrafikActivity.class);
               startActivityForResult(i2,0);
                break;
            case 2:
             Intent i3 = new Intent(getActivity().getApplicationContext(), PercobaanActivity.class);
               startActivityForResult(i3,0);
                break;
            case 3:
            Intent i4 = new Intent(getActivity().getApplicationContext(), InformasiActivity.class);
          startActivityForResult(i4,0);
                break;


        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
