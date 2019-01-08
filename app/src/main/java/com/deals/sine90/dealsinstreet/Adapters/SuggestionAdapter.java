package com.deals.sine90.dealsinstreet.Adapters;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Toast;

import com.deals.sine90.dealsinstreet.R;
import com.deals.sine90.dealsinstreet.SearchPojo.SuggestGetSet_search;
import com.deals.sine90.dealsinstreet.Webservicecall.Serach_Json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sine90 on 3/28/2017.
 */

public class SuggestionAdapter extends ArrayAdapter<String> {
    protected static final String TAG = "SuggestionAdapter";
    private List<String> suggestions;
    public SuggestionAdapter(Activity context, String nameFilter) {
        super(context, R.layout.sample_search);
        suggestions = new ArrayList<String>();
    }
    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public String getItem(int index) {
        return suggestions.get(index);
    }
    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                Serach_Json jp=new Serach_Json();
                if (constraint != null) {
                    // A class that queries a web API, parses the data and
                    // returns an ArrayList<GoEuroGetSet>
                    List<SuggestGetSet_search> new_suggestions =jp.getParseJsonWCF(constraint.toString());
                    suggestions.clear();
                    for (int i=0;i<new_suggestions.size();i++) {
                        suggestions.add(new_suggestions.get(i).getP_name());
                        /*suggestions.add(new_suggestions.get(i).getId());*/
                    }

                    // Now assign the values and count to the FilterResults
                    // object
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                }
               if (constraint==null)
                {
                    //Toast.makeText(getContext(), "No such products to show", Toast.LENGTH_SHORT).show();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }
}
