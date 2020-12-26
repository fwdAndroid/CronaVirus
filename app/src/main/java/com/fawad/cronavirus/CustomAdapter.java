package com.fawad.cronavirus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<CountryModals> {
    Context context;
    List<CountryModals> countryModalsList;
    List<CountryModals> countryModalsListFiltered;
    public CustomAdapter(Context context, List<CountryModals> countryModalsList) {
        super(context, R.layout.list_custom_item,countryModalsList);

        this.context = context;
        this.countryModalsList = countryModalsList;
        this.countryModalsListFiltered = countryModalsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);
        TextView tv = v.findViewById(R.id.tvCountryName);
        ImageView imn = v.findViewById(R.id.imageFlag);

        tv.setText(countryModalsListFiltered.get(position).getCountry());
        Glide.with(context).load(countryModalsListFiltered.get(position).getFlag()).into(imn);
        return v;
    }

    @Override
    public int getCount() {
        return countryModalsListFiltered.size();
    }

    @Nullable
    @Override
    public CountryModals getItem(int position) {
        return countryModalsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countryModalsList.size();
                    filterResults.values = countryModalsList;

                }else{
                    List<CountryModals> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CountryModals itemsModel:countryModalsList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModalsListFiltered = (List<CountryModals>) results.values;
                AffectedCountries.countryModelsList = (List<CountryModals>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}

