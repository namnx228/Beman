package com.uet.beman.object;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.uet.beman.activity.BM_ActivityManageWifi;

import java.util.ArrayList;
import java.util.Arrays;

public class ManageWifiDialogFragment extends DialogFragment {

    private static final String CONFIGURED_WIFI_KEY = "configured";
    private static final String SAVED_WIFI_KEY = "saved";

    private ArrayList<String> result;

    public static ManageWifiDialogFragment getInstance(ArrayList<String> configuredWifi, ArrayList<String> savedWifi) {
        Bundle args = new Bundle();
        args.putStringArrayList(CONFIGURED_WIFI_KEY, configuredWifi);
        args.putStringArrayList(SAVED_WIFI_KEY, savedWifi);

        ManageWifiDialogFragment frag = new ManageWifiDialogFragment();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Initialize
        Bundle args = getArguments();
        ArrayList<String> configuredWifi = args.getStringArrayList(CONFIGURED_WIFI_KEY);
        ArrayList<String> savedWifi = args.getStringArrayList(SAVED_WIFI_KEY);

        result = new ArrayList<>(savedWifi);

        final String wifiArray[] = configuredWifi.toArray(new String[configuredWifi.size()]);
        boolean selected[] = getSelected(configuredWifi, savedWifi);

        // Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Wifi");

        builder.setMultiChoiceItems(wifiArray, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String s = wifiArray[which];
                if (isChecked)
                    result.add(s);
                else
                    result.remove(s);
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((BM_ActivityManageWifi) getActivity()).saveWifiList(result);
            }
        });

        return builder.create();
    }

    private boolean[] getSelected(ArrayList<String> configuredWifi, ArrayList<String> savedWifi) {
        boolean selected[] = new boolean[configuredWifi.size()];
        Arrays.fill(selected, false);
        for (int i = 0; i < configuredWifi.size(); i++)
            if (savedWifi.contains(configuredWifi.get(i)))
                selected[i] = true;
        return selected;
    }
}
