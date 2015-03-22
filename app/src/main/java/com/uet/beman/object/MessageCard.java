package com.uet.beman.object;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uet.beman.R;
import com.uet.beman.support.BM_NodeListHandler;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * Created by PhanDuy on 3/19/2015.
 */
public class MessageCard extends CardWithList {

    BM_NodeListHandler handler = new BM_NodeListHandler();
    List<MessageObject> objs = new ArrayList<>();

    public MessageCard(Context context) {
        super(context);
    }

    public void updateItems() {

        //Update the array inside the card

        List<SentenceNode> sentenceNodes = handler.getNodeList();
        for(SentenceNode i : sentenceNodes) {
            MessageObject tmp = new MessageObject(this, i);
            objs.add(tmp);
        }
        getLinearListAdapter().addAll(objs);

        //use this line if your are using the progress bar
        //updateProgressBar(true,true);
    }



    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        TextView dayName = (TextView) convertView.findViewById(R.id.messagecard_dayName);
        TextView dayDate = (TextView) convertView.findViewById(R.id.messagecard_dayDate);
        TextView content = (TextView) convertView.findViewById(R.id.messagecard_content);

        MessageObject obj = (MessageObject) object;
        dayDate.setText(obj.sentenceNode.getSendTime());
        content.setText(obj.sentenceNode.getMessage());
//        WeatherObject weatherObject= (WeatherObject)object;
//        icon.setImageResource(weatherObject.weatherIcon);
//        city.setText(weatherObject.city);
//        temperature.setText(weatherObject.temperature + weatherObject.temperatureUnit);

        return  convertView;
    }

    public int getChildLayoutId() {
        return R.layout.messagecard_inner_main;
    }

    @Override
    protected CardHeader initCardHeader() {

        //Add Header
        CardHeader header = new CardHeader(getContext());

        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.menu_popup_item, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_add_item:
                        Toast.makeText(getContext(), "Add item", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_settings:
                        Toast.makeText(getContext(), "Setting", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        header.setTitle("Morning"); //should use R.string.
        return header;
    }

    @Override
    protected void initCard() {
        setSwipeable(true);
        setOnSwipeListener(new Card.OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                Toast.makeText(getContext(), "Swipe on " + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        //Provide a custom view for the ViewStud EmptyView
        setEmptyViewViewStubLayoutId(R.layout.carddemo_extras_base_withlist_empty);

        setUseProgressBar(true);
    }

    @Override
    protected List<ListObject> initChildren() {

        BM_NodeListHandler handler = new BM_NodeListHandler();
        List<SentenceNode> res = new ArrayList<>();
        res = handler.getNodeList();
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
                    Toast.makeText(getContext(), "Click on " + sentenceNode.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
