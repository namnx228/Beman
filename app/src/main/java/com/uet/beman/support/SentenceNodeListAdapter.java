package com.uet.beman.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uet.beman.R;
import com.uet.beman.object.SentenceNode;

import java.util.List;

/**
 * Created by thanhpd on 3/15/2015.
 */
public class SentenceNodeListAdapter extends ArrayAdapter<SentenceNode> {
    public SentenceNodeListAdapter (Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public SentenceNodeListAdapter (Context context, int resource, List<SentenceNode> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        SentenceNode p = getItem(position);

        if(p != null) {
            TextView tt = (TextView) v.findViewById(R.id.id);
//            TextView tt1 = (TextView) v.findViewById(R.id.categoryId);
            TextView tt3 = (TextView) v.findViewById(R.id.description);

            if (tt != null) {
                tt.setText(p.getSendTime());
            }
//            if (tt1 != null) {
//
//                tt1.setText(p.getMessageId());
//            }
            if (tt3 != null) {

                tt3.setText(p.getMessage());
            }
        }

        return v;
    }
}
