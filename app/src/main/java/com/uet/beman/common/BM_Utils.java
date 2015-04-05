package com.uet.beman.common;

import android.app.Activity;
import android.content.res.Resources;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by PhanDuy on 4/5/2015.
 */
public class BM_Utils {

    private static BM_Utils instance = new BM_Utils();

    public static BM_Utils getInstance() {
        return instance;
    }

    private BM_Utils() {
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void updateNameReferences(TextView textView, Resources resources, int id, String text) {
        String res = String.format(resources.getString(id), text);
        textView.setText(res);
    }

    public static void updateNameReferences(TextView textView, Resources resources, int id, String text1, String text2) {
        String res = String.format(resources.getString(id), text1, text2);
        textView.setText(res);
    }
}
