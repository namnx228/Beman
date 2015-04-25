package com.uet.beman.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.uet.beman.R;
import com.uet.beman.object.SentenceNode;
import com.uet.beman.support.BM_MessageCardHandler;
import com.uet.beman.support.BM_StorageHandler;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BM_FragmentMessageDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BM_FragmentMessageDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BM_FragmentMessageDialog extends DialogFragment implements CompoundButton.OnCheckedChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SentenceNode currentNode;
    private Boolean nodeInit = true;
    private String msg;
    private String id;
    private char[] charArray = new char[7];
    private BM_StorageHandler storageHandler;
    private BM_MessageCardHandler messageCardHandler;
    private HashMap<String, ToggleButton> btnSet;
    Boolean activate;
    View content, title;

    ToggleButton monBtn, tueBtn, wedBtn, thuBtn, friBtn, satBtn, sunBtn;
    Switch enabled;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MessageDialogListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BM_FragmentMessageDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static BM_FragmentMessageDialog newInstance(String param1, String param2) {
        BM_FragmentMessageDialog fragment = new BM_FragmentMessageDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BM_FragmentMessageDialog() {
        // Required empty public constructor
    }

    public void setNode(SentenceNode sentenceNode)
    {
        currentNode = sentenceNode;
        msg = sentenceNode.getMessage();
        id = sentenceNode.getId();

        if (sentenceNode.getDays() == null) Arrays.fill(charArray, '1');
        else charArray = sentenceNode.getDays().toCharArray();

        activate = (sentenceNode.getEnabled() == null || (sentenceNode.getEnabled().equals("1")));
        if (sentenceNode.getEnabled() == null) nodeInit = false;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        Arrays.fill(charArray, '0');
        btnSet = new HashMap<>();
        storageHandler = BM_StorageHandler.getInstance();
        messageCardHandler = BM_MessageCardHandler.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getActivity().setContentView(R.layout.dialog_message_info_title);
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        content = inflater.inflate(R.layout.dialog_message_info_content, null);
        title = inflater.inflate(R.layout.dialog_message_info_title, null);
        final EditText message = (EditText) content.findViewById(R.id.dialog_message_content);
        message.setText(msg);

        // Set title and content layout for dialog, as well as set up listener
        builder.setCustomTitle(title);
        builder.setView(content);

        setButtonDaysListener(content);
        setSwitchListener(title);

        builder.setPositiveButton(R.string.dialog_action_save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                msg = message.getText().toString();
                currentNode.setEnabled(activate ? "1" : "0");
                currentNode.setMessage(msg);
                currentNode.setDays(String.valueOf(charArray));

                if(nodeInit) {
                    storageHandler.updateItemInMessageSet(currentNode.getLabel(), currentNode);
                } else {
                    currentNode.setId("0");
                    currentNode.setEnabled("1");
                    storageHandler.addItemInMessageSet(currentNode);
                }

                mListener.onDialogPositiveClick(BM_FragmentMessageDialog.this, currentNode);
            }
        })
                .setNegativeButton(R.string.dialog_action_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(BM_FragmentMessageDialog.this);
                    }
                });
        return builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface MessageDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, SentenceNode currentNode);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (MessageDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MessageDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setSwitchListener(View view) {
        enabled = (Switch) view.findViewById(R.id.dialog_message_switch);
        if(activate) {
            enabled.setChecked(true);
            enableDisableView(content, true);
            enableDisableView(title, true);
        } else {
            enabled.setChecked(false);
            enableDisableView(content, false);
            enableDisableView(title, false);
        }
        enabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                //do stuff when Switch is ON
                enableDisableView(content, true);
                enableDisableView(title, true);

            } else {
                //do stuff when Switch if OFF
                enableDisableView(content, false);
                enableDisableView(title, false);
            }
            }
        });
    }

    private void enableDisableView(View view, boolean enabled) {
        if(view == title) {
            TextView textView = (TextView) view.findViewById(R.id.dialog_message_title_text);
            textView.setEnabled(enabled);
            return;
        } else {
            view.setEnabled(enabled);
        }
        activate = enabled;
        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                View child = group.getChildAt(idx);
                if ( child instanceof Button) {
                    Button button = (Button)child;
                    button.setEnabled(enabled);
                }
                enableDisableView(child, enabled);
            }
        }
    }

    private void setButtonDaysListener(View view) {
        monBtn = (ToggleButton) view.findViewById(R.id.dialog_monBtn);
        tueBtn = (ToggleButton) view.findViewById(R.id.dialog_tueBtn);
        wedBtn = (ToggleButton) view.findViewById(R.id.dialog_wedBtn);
        thuBtn = (ToggleButton) view.findViewById(R.id.dialog_thuBtn);
        friBtn = (ToggleButton) view.findViewById(R.id.dialog_friBtn);
        satBtn = (ToggleButton) view.findViewById(R.id.dialog_satBtn);
        sunBtn = (ToggleButton) view.findViewById(R.id.dialog_sunBtn);

        btnSet.put("0", monBtn);
        btnSet.put("1", tueBtn);
        btnSet.put("2", wedBtn);
        btnSet.put("3", thuBtn);
        btnSet.put("4", friBtn);
        btnSet.put("5", satBtn);
        btnSet.put("6", sunBtn);

        for(int i = 0; i < 7; i++) {
            if(charArray[i] == '1') {
                String idx = String.valueOf(i);
                ToggleButton btn = btnSet.get(idx);
                btn.setChecked(true);
                btnSet.put(idx, btn);
            }
        }

        monBtn.setOnCheckedChangeListener(this);
        tueBtn.setOnCheckedChangeListener(this);
        wedBtn.setOnCheckedChangeListener(this);
        thuBtn.setOnCheckedChangeListener(this);
        friBtn.setOnCheckedChangeListener(this);
        satBtn.setOnCheckedChangeListener(this);
        sunBtn.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Button btn = (Button) view.findViewById(view.getId());

        switch(buttonView.getId()) {
            case R.id.dialog_monBtn:
                customValue(buttonView, 0, isChecked);
                break;
            case R.id.dialog_tueBtn:
                customValue(buttonView, 1, isChecked);
                break;
            case R.id.dialog_wedBtn:
                customValue(buttonView, 2, isChecked);
                break;
            case R.id.dialog_thuBtn:
                customValue(buttonView, 3, isChecked);
                break;
            case R.id.dialog_friBtn:
                customValue(buttonView, 4, isChecked);
                break;
            case R.id.dialog_satBtn:
                customValue(buttonView, 5, isChecked);
                break;
            case R.id.dialog_sunBtn:
                customValue(buttonView, 6, isChecked);
                break;
        }
    }

    private void customValue(CompoundButton btn, int index, boolean isChecked) {
        if(isChecked) {
            charArray[index] = '1';
        }
        else {
            charArray[index] = '0';
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
