package com.siu.customweb;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.siu.bibleweb.R;

/**
 * Created by duranno on 2017. 8. 1..
 */

public class CustomWebView {

    private static final String TAG = "";
    private WebView webView;
    private ProgressBar pgBar;
    private RelativeLayout container;
    private WebView mainWeb;
    Activity context;
    public CustomWebView(Activity mContext, RelativeLayout con) {
        context = mContext;
        container = con;

    }

    public void initView(){

        mainWeb = new WebView(context);
        mClient wvClient = new mClient();
        chromeClient chClient = new chromeClient();
        mainWeb.setWebViewClient(wvClient);
        mainWeb.setWebChromeClient(chClient);
        WebSettings webSettings = mainWeb.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);

        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        mainWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mainWeb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (Build.VERSION.SDK_INT >= 19) {
            mainWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mainWeb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        mainWeb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        container.addView(mainWeb);
    }

    public void setInitProgressBar(ProgressBar progBar){
        pgBar = progBar;
        pgBar.bringToFront();
    }

    public boolean canGoBack(){
        return mainWeb.canGoBack();
    }
    public void goPrev(){
        if(mainWeb.canGoBack()){
            mainWeb.goBack();
        }
    }

    public void loadUrl(final String str){

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainWeb.loadUrl(str);
            }
        });
    }
    public class mClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            try {
                pgBar.setVisibility(View.VISIBLE);
                //progress bar start
            } catch (Exception e) {
                Log.e(getClass().getName(), "onPageStarted : " + e.toString());
            }
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            // open a new page

            view.loadUrl(request.getUrl().toString());
            return true;
            //final Uri uri = request.getUrl();
            //return handleUri(uri);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
                pgBar.setVisibility(View.GONE);
            } catch (Exception e) {
                Log.e(getClass().getName(), "onPageFinished : " + e.toString());
            }
        }

        @Override
        public void onReceivedError(final WebView view, int errorCode, String description, String failingUrl) {


            pgBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            Log.d("onReceivedSslError", "onReceivedSslError");
            pgBar.setVisibility(View.GONE);
            //super.onReceivedSslError(view, handler, error);
        }

        private boolean handleUri(final Uri uri) {
            Log.i(TAG, "Uri =" + uri);
            final String host = uri.getHost();
            final String scheme = uri.getScheme();
            final String url = uri.toString();
            // Based on some condition you need to determine if you are going to load the url
            // in your web view itself or in a browser.
            // You can use `host` or `scheme` or any part of the `uri` to decide.
            if (url.startsWith("http")) {
                // Returning false means that you are going to load this url in the webView itself
                return false;
            } else {
                // Returning true means that you need to handle what to do with the url
                // e.g. open web page in a Browser
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
                return true;
            }
        }
    }

    public class chromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;


        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view,CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            /*
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            subView.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);

            customViewCallback = callback;
            */
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            /*
            if (mCustomView == null)
                return;

            subView.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
            */
        }
    }



}
