package com.uet.beman.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.uet.beman.R;
import com.uet.beman.common.BM_ActivitySetting;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.database.BM_ModelScheduler;
import com.uet.beman.object.SentenceNode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class BM_ActivityMain extends ActionBarActivity implements View.OnClickListener {

    Button sendBtn;
    RadioGroup txtMessage;
    Button txtPhoneNo, scheduleBtn, showBtn;
    EditText pin;
    TimePicker timer;
    DatePicker datePicker;
    SharedPreferencesHelper helper;
    BM_ModelScheduler scheduler;
    AlarmManager alarmManager;


    public static final int PICK_CONTACT = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bm_activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        sendBtn = (Button) findViewById(R.id.sendBtn);
        txtMessage = (RadioGroup) findViewById(R.id.message);
        txtPhoneNo = (Button) findViewById(R.id.number);
        scheduleBtn = (Button) findViewById(R.id.saveBtn);
        showBtn = (Button) findViewById(R.id.showBtn);
        timer = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        pin = (EditText) findViewById(R.id.pin);

        helper = SharedPreferencesHelper.getInstance();
        scheduler = BM_ModelScheduler.getInstance();
        alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        sendBtn.setOnClickListener(this);

        txtPhoneNo.setOnClickListener(this);

        scheduleBtn.setOnClickListener(this);

        showBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        if (arg0 == sendBtn) {
            int selectedId = txtMessage.getCheckedRadioButtonId();
            RadioButton tmp = (RadioButton) findViewById(selectedId);
            String message = tmp.getText().toString();
            String phoneNumber = txtPhoneNo.getText().toString();
            if(phoneNumber.length() > 0 && message.length() >0) {
                sendSMS(phoneNumber, message);
            } else {
                Toast.makeText(getBaseContext(), "please enter...",Toast.LENGTH_LONG).show();
            }
        } else if (arg0 == txtPhoneNo) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        } else if (arg0 == scheduleBtn) {
            int selectedId = txtMessage.getCheckedRadioButtonId();
            RadioButton tmp = (RadioButton) findViewById(selectedId);
            String message = tmp.getText().toString();
            String phoneNumber = txtPhoneNo.getText().toString();

            int sendMonth = datePicker.getMonth();
            int sendDay = datePicker.getDayOfMonth();
            int sendYear = datePicker.getYear();

            int sendHr = timer.getCurrentHour();
            int sendMin = timer.getCurrentMinute();
            String time = sendDay + " " + sendMonth + " " + sendYear + " " + sendHr + ":" + sendMin + ":00";
            String value = "message: " + message + " phoneNumber: " + phoneNumber + " Time: " +
                        time + " " + convertDate(time);
            helper.setDestNumber(phoneNumber);
            SentenceNode node = new SentenceNode(message, convertDate(time));
//            scheduler.addSentence(node);
            scheduler.addSchedule(node);

//            BM_NodeListHandler handler = new BM_NodeListHandler();
//            Intent intent = new Intent(this, BM_BroadcastReceiver.class);
//            intent.putExtra("phoneNo", phoneNumber);
//            intent.putExtra("message", message);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
//            long tmp1 = handler.getRemainingTime();
//            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + tmp1, pendingIntent);

            //Toast.makeText(this, String.valueOf(tmp1), Toast.LENGTH_LONG).show();
        } else if (arg0 == showBtn) {
            Intent intent = new Intent(this, BM_ActivityShowSchedule.class);
            startActivity(intent);
        }
    }

    private String convertDate(String date) {
        String MY_FORMAT = "dd MM yyyy HH:mm:ss";
        String TIMEZONE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        try {
            SimpleDateFormat format = new SimpleDateFormat(MY_FORMAT, Locale.getDefault());
            Date d = format.parse(date);
            SimpleDateFormat timeZoneFormat = new SimpleDateFormat(TIMEZONE_DATE_FORMAT, Locale.getDefault());
            return timeZoneFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                    ContentResolver contentResolver = getContentResolver();
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String name = "";
                        String no = "";

                        Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
                        if(phoneCursor.moveToFirst()) {
                            name = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            no = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
//                        String number = c.getString(c.getColumnIndex(ContactsContract.Data.DATA1));
                        // TODO Whatever you want to do with the selected contact name.
                        txtPhoneNo.setText(no);
                        phoneCursor.close();
                    }
                    c.close();
                }
                break;
        }
    }

    //---sends an SMS message to another device---
    private void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bm_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = null;
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.activity_message_list:
                intent = new Intent(this, BM_ActivityMessageList.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                intent = new Intent(this, BM_ActivitySetting.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void manageWifi(View view){
        Intent intent = new Intent(this, BM_ActivityManageWifi.class);
        startActivity(intent);
    }
}
