package com.example.aviya.takeandgo2.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import com.example.aviya.takeandgo2.R;
import com.example.aviya.takeandgo2.controller.adapter.MyBranchListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BranchesFragment extends Fragment {
    View view;
    SearchView branchSearchView;
    public BranchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_branches, container, false);
        final MyBranchListAdapter adapter = new MyBranchListAdapter(getContext());
        ((ExpandableListView)view.findViewById(R.id.branchesExpandableList)).setAdapter(adapter);

        //filter view
        branchSearchView = (SearchView)view.findViewById(R.id.branchSearchView);
        //if filter data changed
        branchSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }

}
