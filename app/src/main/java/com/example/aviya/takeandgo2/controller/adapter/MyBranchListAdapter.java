package com.example.aviya.takeandgo2.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aviya.takeandgo2.R;
import com.example.aviya.takeandgo2.model.datasource.List_DB_manager;
import com.example.aviya.takeandgo2.model.datasource.getManager;
import com.example.aviya.takeandgo2.model.entities.Branch;
import com.example.aviya.takeandgo2.model.entities.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aviya on 07/01/2018.
 */

public class MyBranchListAdapter extends BaseExpandableListAdapter implements Filterable{
    List<Branch> branchList;
    List<Branch> originalBranchList; // keeps the original list, while branchList is changing according to filter values
    List_DB_manager manager;
    Context context;
    LayoutInflater mInflater;

    public MyBranchListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        manager = getManager.getInstance();
        branchList = manager.allBranches();
        originalBranchList = branchList;
    }

    @Override
    public int getGroupCount() {
        return branchList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View  view = mInflater.inflate(R.layout.expnd_list_header,parent,false);
        TextView branchNumberHeader = (TextView)view.findViewById(R.id.branchNumberHeader);
        TextView branchParkingsHeader = (TextView)view.findViewById(R.id.branchParkingsHeader);
        Branch branch = branchList.get(groupPosition);
        branchNumberHeader.setText(String.valueOf(branch.getBranchNumber()));
        branchParkingsHeader.setText(String.valueOf(branch.getParkingSpaces()));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.expnd_list_item,parent,false);
        Branch branch = branchList.get(groupPosition);
        List<Car> carList = manager.availableCarsForBranch(branch.getBranchNumber());

        TextView branchNumber = (TextView)view.findViewById(R.id.branchNumberView);
        TextView branchAddressNumber = (TextView)view.findViewById(R.id.branchAddressNumberView);
        TextView branchAddressStreet = (TextView)view.findViewById(R.id.branchAddressStreetView);
        TextView branchAddressCity = (TextView)view.findViewById(R.id.branchAddressCityView);
        TextView branchParkingSpaces = (TextView)view.findViewById(R.id.branchParkingSpaces);
        TextView branchCarsNumber = (TextView)view.findViewById(R.id.branchCarsNumberView);
        ListView carListInExpndList = (ListView)view.findViewById(R.id.carListInExpndList);

        //allows to scroll the internal list view
        carListInExpndList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        branchNumber.setText(String.valueOf(branch.getBranchNumber()));
        branchAddressCity.setText(manager.toProperFormat(branch.getCity()));
        branchAddressNumber.setText(String.valueOf(branch.getHouseNumber()));
        branchAddressStreet.setText(manager.toProperFormat(branch.getStreet()));
        branchParkingSpaces.setText(String.valueOf(branch.getParkingSpaces()));
        branchCarsNumber.setText(String.valueOf(carList.size()));
        //checking is done because adapter is set for a list with values
        if(carList.size() != 0)
            carListInExpndList.setAdapter(new MyListViewAdapter(carList,context));
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            /**
             * private function to check if a CharSequence is formed from only digits
             * the func purpose is to help know the filter type of data so it know where to look for the word
             * @param word a parameter to check if fully formed from digits
             * @return true if only digits
             */
            private boolean onlyDigits(CharSequence word)
            {
                try{
                    Integer.parseInt(word.toString());
                    return true;
                }
                catch (Exception e){
                    return false;
                }
            }

            /**
             * private function to check if a CharSequence is formed from only letters
             * the func purpose is to help know the filter type of data so it know where to look for the word
             * @param word a parameter to check if fully formed from letters
             * @return true if only letters
             */
            private boolean onlyLetters(CharSequence word){
                for (int i=0 ;i<word.length();i++)
                    try{
                        Integer.parseInt(String.valueOf(word.charAt(i)));
                        return false;
                    }
                    catch (Exception e){}
                return true;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null){
                    results.values = branchList;
                    results.count = branchList.size();
                }
                else {
                    List<Branch> mBranchList = new ArrayList<>();
                    /*we look for results in te original branch list so it includes all the branch that
                     satisfies the constraint and nort only the ones that in the already filtered list
                     */
                    for (Branch b:originalBranchList)
                    {
                        if(onlyLetters(constraint)) {
                            if (b.getCity().toLowerCase().contains(constraint) ||
                                    b.getStreet().toLowerCase().contains(constraint))
                                mBranchList.add(b);
                        }
                        else if(onlyDigits(constraint)){
                            if(String.valueOf(b.getBranchNumber()).contains(constraint) ||
                                    String.valueOf(b.getHouseNumber()).contains(constraint))
                                mBranchList.add(b);
                        }
                    }
                    results.values = mBranchList;
                    results.count = mBranchList.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count == 0)
                    notifyDataSetInvalidated();
                else{
                    branchList = (List<Branch>)results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}
