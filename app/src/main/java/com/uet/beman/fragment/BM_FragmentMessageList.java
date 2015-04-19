package com.uet.beman.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uet.beman.R;
import com.uet.beman.common.BM_Utils;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.object.MessageCard;
import com.uet.beman.object.SentenceNode;

import it.gmariotti.cardslib.library.view.CardView;

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

    private OnFragmentInteractionListener mListener;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);

        // Update name reference
        TextView headline = (TextView) view.findViewById(R.id.fragment_messagelist_headline);
        String name = SharedPreferencesHelper.getInstance().getUserName();
        BM_Utils.updateNameReferences(headline, getResources(), R.string.line_fragment_message_list, name);

        MessageCard card = new MessageCard(getActivity(), "Morning");
        card.init();
        card.setFragment(this);
        card.updateMessageItems("|morning|");
        //card.updateScheduleItems();

//        Set card in the cardView
        CardView cardView = (CardView) view.findViewById(R.id.card_morning);
        cardView.setCard(card);

        MessageCard card1 = new MessageCard(getActivity(), "Night");
        card1.init();
        card1.setFragment(this);
        card1.updateMessageItems("|night|");
        CardView cardView1 = (CardView) view.findViewById(R.id.card_noon);
        cardView1.setCard(card1);

        MessageCard card2 = new MessageCard(getActivity(), "Miss");
        card2.init();
        card2.setFragment(this);
        card2.updateMessageItems("|miss|");
        CardView cardView2 = (CardView) view.findViewById(R.id.card_afternoon);
        cardView2.setCard(card2);

        MessageCard card3 = new MessageCard(getActivity(), "Eat");
        card3.init();
        card3.setFragment(this);
        card3.updateMessageItems("|eat|");
        CardView cardView3 = (CardView) view.findViewById(R.id.card_evening);
        cardView3.setCard(card3);

        CardView cardView4 = (CardView) view.findViewById(R.id.card_night);
        cardView4.setCard(card);
        // Inflate the layout for this fragment
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
