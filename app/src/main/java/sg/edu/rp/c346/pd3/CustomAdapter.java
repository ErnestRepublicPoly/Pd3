package sg.edu.rp.c346.pd3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<String> optionsList;


    public CustomAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        optionsList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) parent_context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout_id, parent, false);
        TextView tvName = rowView.findViewById(R.id.textViewLinks);

        String currentItem = optionsList.get(position);
        tvName.setText(currentItem);
        return rowView;
    }
}
