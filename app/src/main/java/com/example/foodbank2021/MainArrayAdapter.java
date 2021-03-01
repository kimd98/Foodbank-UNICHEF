package com.example.foodbank2021;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

// helper class for Home Fragment
public class MainArrayAdapter extends ArrayAdapter<Food> {
    public MainArrayAdapter(@NonNull Context context, @NonNull List<Food> foodList) { super(context, 0, foodList); }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        ViewHolder viewholder;

        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home,parent, false);
            viewholder = new ViewHolder(itemView);
            itemView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) itemView.getTag();
        }
        Food food = getItem(position);
        viewholder.foodNameTextView.setText(food.getName());
        return super.getView(position, convertView, parent);
    }

    // helper class
    public class ViewHolder {
        TextView foodNameTextView;

        public ViewHolder (View itemView) {
            foodNameTextView = itemView.findViewById(R.id.foodlist);
        }
    }
}
