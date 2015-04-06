package com.uet.beman.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uet.beman.R;

import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by PhanDuy on 4/6/2015.
 */
public class CustomHeaderColor extends CardHeader {

    public CustomHeaderColor(Context context) {
        super(context, R.layout.card_header_custom);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view != null) {
            TextView t1 = (TextView) view.findViewById(R.id.card_header_inner_frame);
            if (t1 != null)
                t1.setText(getContext().getString(R.string.day_morning));

        }
    }
}
