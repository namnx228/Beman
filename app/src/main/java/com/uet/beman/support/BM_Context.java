package com.uet.beman.support;

import android.content.Context;

/**
 * Created by nam on 29/04/2015.
 */
public class BM_Context {
    private static BM_Context instance;
    private  Context context;
    public void setContext(Context context)
    {
        this.context = context;
    }
    public  Context getContext()
    {
        return context;
    }

    public static BM_Context getInstance()
    {
        if (instance == null) instance = new BM_Context();
        return instance;
    }

    private Boolean onCreateViewFragmentMessageList;
    public void setOnCreateViewFragmentMessageList(boolean onCreateViewFragmentMessageList)
    {
        this.onCreateViewFragmentMessageList = onCreateViewFragmentMessageList;
    }

    public boolean getOnCreateViewFragmentMessageList()
    {
        return onCreateViewFragmentMessageList;
    }
}
