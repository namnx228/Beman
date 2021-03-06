package com.uet.beman.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uet.beman.R;
import com.uet.beman.common.BM_Utils;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.object.SentenceNode;
import com.uet.beman.support.BM_Context;
import com.uet.beman.support.BM_MessageCardHandler;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BM_FragmentMessageList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BM_FragmentMessageList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BM_FragmentMessageList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private int a = 0;

    private OnFragmentInteractionListener mListener;
    private BM_MessageCardHandler mHandler;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BM_FragmentMessageList.
     */
    // TODO: Rename and change types and number of parameters
    public static BM_FragmentMessageList newInstance(String param1, String param2) {
        BM_FragmentMessageList fragment = new BM_FragmentMessageList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BM_FragmentMessageList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mHandler = BM_MessageCardHandler.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);



        // Update name reference
        /*a++;
        if (a == 2)
        {
            Log.d("vo day","vo day");
        }
*/
        TextView headline = (TextView) view.findViewById(R.id.fragment_messagelist_headline);
        String name = SharedPreferencesHelper.getInstance().getUserName();
        BM_Utils.updateNameReferences(headline, getResources(), R.string.line_fragment_message_list, name);
        BM_Context.getInstance().setOnCreateViewFragmentMessageList(true);
        mHandler.setCardView(view, R.id.card_morning, "|morning|", getActivity(), this);
        mHandler.setCardView(view, R.id.card_noon, "|noon|", getActivity(), this);
        mHandler.setCardView(view, R.id.card_night, "|night|", getActivity(), this);
        mHandler.setCardView(view, R.id.card_miss, "|miss|", getActivity(), this);
        mHandler.setCardView(view, R.id.card_eat, "|eat|", getActivity(), this);
        mHandler.setCardView(view, R.id.card_home, "home", getActivity(), this);
        mHandler.setCardView(view, R.id.card_work, "work", getActivity(), this);
        BM_Context.getInstance().setOnCreateViewFragmentMessageList(false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public void createMessageDialog(SentenceNode sentenceNode) {
        BM_FragmentMessageDialog dialog = new BM_FragmentMessageDialog();
        dialog.setNode(sentenceNode);

        FragmentManager fm = getFragmentManager();
        dialog.setTargetFragment(this, 0);
        dialog.show(fm, "ABC");
    }

}
