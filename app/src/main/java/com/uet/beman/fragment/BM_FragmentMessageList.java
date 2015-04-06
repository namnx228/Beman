package com.uet.beman.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uet.beman.R;
import com.uet.beman.object.MessageCard;

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

        MessageCard card = new MessageCard(getActivity());
        card.init();
//        //Create a CardHeader
//        CustomHeaderColor header = new CustomHeaderColor(getActivity());
//        //Set the header title
//        header.setTitle("DEMO");
//
//        header.setOtherButtonVisible(true);
//        header.setOtherButtonClickListener(new CardHeader.OnClickCardHeaderOtherButtonListener() {
//            @Override
//            public void onButtonItemClick(Card card, View view) {
//                //Example to change dinamically the button resources
//                if (Build.VERSION.SDK_INT >= Constants.API_L) {
//                    card.getCardHeader().setOtherButtonDrawable(R.drawable.ic_action_add);
//                } else {
//                    card.getCardHeader().setOtherButtonDrawable(R.drawable.card_menu_button_other_add);
//                }
//                card.getCardView().refreshCard(card);
//            }
//        });
//        card.addCardHeader(header);
        card.updateMessageItems("|morning|");
        //card.updateScheduleItems();

//        Set card in the cardView
        CardView cardView = (CardView) view.findViewById(R.id.card_morning);
        cardView.setCard(card);

        CardView cardView1 = (CardView) view.findViewById(R.id.card_noon);
        cardView1.setCard(card);

        CardView cardView2 = (CardView) view.findViewById(R.id.card_afternoon);
        cardView2.setCard(card);

        CardView cardView3 = (CardView) view.findViewById(R.id.card_evening);
        cardView3.setCard(card);

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

}
