package com.uet.beman.object;

/**
 * Created by PhanDuy on 3/23/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.uet.beman.R;
import com.uet.beman.fragment.BM_FragmentMessageList;
import com.uet.beman.support.BM_Context;
import com.uet.beman.support.BM_NodeListHandler;
import com.uet.beman.support.BM_StorageHandler;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

public class MessageCard extends CardWithList {

    BM_NodeListHandler handler = new BM_NodeListHandler();
    List<MessageObject> objs = new ArrayList<>();
    Activity activity;
    BM_FragmentMessageList fragment;
    private String title;
    private BM_StorageHandler storageHandler;
//    MessageObject obj;

    public MessageCard(Context context, String header, Fragment fragment) {
        super(context);
//        BM_FragmentMessageList fm = (Fragment) context;
        title = header;
        storageHandler = BM_StorageHandler.getInstance();
        this.fragment = (BM_FragmentMessageList)fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = (BM_FragmentMessageList) fragment;
    }

    public void updateScheduleItems() {

        //Update the array inside the card

        List<SentenceNode> sentenceNodes = handler.getScheduleList();
        for(SentenceNode i : sentenceNodes) {
            MessageObject tmp = new MessageObject(this, i);
            objs.add(tmp);
        }
        getLinearListAdapter().addAll(objs);

        //use this line if your are using the progress bar
        //updateProgressBar(true,true);
    }

    public void updateMessageItems(String tag) {
        //Update the array inside the card

        tag = "\"" + tag + "\"";
        List<SentenceNode> sentenceNodes = storageHandler.getListInMessageSet(tag);
        for(SentenceNode i : sentenceNodes) {
            MessageObject tmp = new MessageObject(this, i);
            objs.add(tmp);
        }
        getLinearListAdapter().addAll(objs);

        //use this line if your are using the progress bar
        //updateProgressBar(true,true);
    }

//    public void messy() {
//        getLinearListAdapter().
//    }



    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {
        final ToggleButton checkmark = (ToggleButton) convertView.findViewById(R.id.checkmark_message);
        TextView content = (TextView) convertView.findViewById(R.id.messagecard_dayDate);

        final MessageObject obj = (MessageObject) object;

        content.setText(obj.sentenceNode.getMessage());

        if(obj.sentenceNode.getEnabled().equals("1")) checkmark.setChecked(true);
        else checkmark.setChecked(false);

        checkmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (BM_Context.getInstance().getOnCreateViewFragmentMessageList()) return;
                if(isChecked) {
                    obj.sentenceNode.setEnabled("1");
                    BM_StorageHandler.getInstance().updateItemInMessageSet(getCardTitle(), obj.sentenceNode);
                    //BM_MessageCardHandler.getInstance().setCardView(null, 0, getCardTitle(), fragment.getActivity(), fragment);
                }
                else {
                    obj.sentenceNode.setEnabled("0");
                    BM_StorageHandler.getInstance().updateItemInMessageSet(getCardTitle(), obj.sentenceNode);
                    //BM_MessageCardHandler.getInstance().setCardView(null, 0, getCardTitle(), fragment.getActivity(), fragment);
                }
            }
        });

        return  convertView;
    }
    @Override
    public int getChildLayoutId() {
        return R.layout.messagecard_inner_main;
    }

    @Override
    protected CardHeader initCardHeader() {
        //Create a CardHeader
//        CustomHeaderColor header = new CustomHeaderColor(getContext());

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

        //Add Header
        final CardHeader header = new CardHeader(getContext());

        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.menu_popup_item, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_add_item:
                        //Toast.makeText(getContext(), "Add item", Toast.LENGTH_LONG).show();
                        SentenceNode node = new SentenceNode();
                        node.setLabel(getCardTitle());
                        node.setLanguage("VIE");
                        fragment.createMessageDialog(node);
                        break;
                    case R.id.action_settings:
                        //Toast.makeText(getContext(), "Setting", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        header.setTitle(getCardTitle()); //should use R.string.
        return header;
    }

    @Override
    protected void initCard() {
//        setSwipeable(true);
//        setOnSwipeListener(new Card.OnSwipeListener() {
//            @Override
//            public void onSwipe(Card card) {
//                Toast.makeText(getContext(), "Swipe on " + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //Provide a custom view for the ViewStud EmptyView
        setEmptyViewViewStubLayoutId(R.layout.carddemo_extras_base_withlist_empty);

        setUseProgressBar(true);
    }

    @Override
    protected List<ListObject> initChildren() {

//        BM_NodeListHandler handler = new BM_NodeListHandler();
//        List<SentenceNode> res = new ArrayList<>();
//        res = handler.getScheduleList();
        return null;
    }

    public class MessageObject extends CardWithList.DefaultListObject {

        public SentenceNode sentenceNode;

        public MessageObject(Card parentCard, SentenceNode node) {
            super(parentCard);
            sentenceNode = node;
            init();
        }

        private void init() {
            setOnItemClickListener(new CardWithList.OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView linearListView, View view, int i, CardWithList.ListObject listObject) {
//                    Toast.makeText(getContext(), "Click on " + sentenceNode.getMessage(), Toast.LENGTH_SHORT).show();
                  fragment.createMessageDialog(sentenceNode);
//                try {
//                    BM_ActivitySimpleSetup setup = (BM_ActivitySimpleSetup) activity;
//                    setup.createMessageDialog(sentenceNode);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                }

            });
        }
    }

    public String getCardTitle() {
        return title;
    }

}

