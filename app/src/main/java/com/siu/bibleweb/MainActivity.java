package com.siu.bibleweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.siu.customweb.CustomWebView;

public class MainActivity extends AppCompatActivity {

    private CustomWebView customWeb;
    private MainActivity mContext;
    private RelativeLayout container=null;
    private BackPressClosehandler backPressClosehandler;
    public static ProgressBar pgBar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        container = (RelativeLayout)findViewById(R.id.container);
        pgBar = (ProgressBar)findViewById(R.id.pgBar);

        customWeb = new CustomWebView(mContext,container);
        customWeb.initView();
        customWeb.setInitProgressBar(pgBar);
        customWeb.loadUrl("http://whoisaus.com");

        backPressClosehandler = new BackPressClosehandler(this);


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(customWeb.canGoBack()) {
                    customWeb.goPrev();
                }else{
                    backPressClosehandler.OnBackpressed();
                }
                break;
            default:
                break;

        }
        return false;
    }
}
