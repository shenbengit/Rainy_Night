package com.example.ben.rainy_night.fragment.share_frag.frag.analysis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;

import butterknife.BindView;

/**
 * @author Ben
 * @date 2018/4/22
 */

public class WebViewFragment extends BaseFragment {

    private static final String TITLE = "title";
    private static final String URL = "url";

    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.pb_webView)
    ProgressBar pbWebView;
    @BindView(R.id.webView)
    WebView webView;


    public static WebViewFragment newInstance(String title, String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mTitle;
    private String mUrl;

    /**
     * 获取界面布局
     *
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_web_view;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {

    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(TITLE);
            mUrl = bundle.getString(URL);
        }
        baseToolbar.setTitle(mTitle);
        initToolbarNav(baseToolbar);
    }

    /**
     * 初始化数据
     */
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {
        WebSettings webSettings = webView.getSettings();
        //加载缓存否则网络
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//
//        //图片自动缩放 打开
//        webSettings.setLoadsImagesAutomatically(true);
//
//        //软件解码
//        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // 设置支持javascript脚本
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 设置不可以支持缩放
        webSettings.setSupportZoom(false);
        // 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        webSettings.setBuiltInZoomControls(false);
        //隐藏缩放工具
        webSettings.setDisplayZoomControls(true);
        // 扩大比例的缩放
//        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

//        webSettings.setDatabaseEnabled(true);
        webView.setKeepScreenOn(true);
        // 设置setWebChromeClient对象
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                pbWebView.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });

        //设置此方法可在WebView中打开链接，反之用浏览器打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                }
                // Otherwise allow the OS to handle things like tel, mailto, etc.
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // TODO Auto-generated method stub
                pbWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbWebView.setVisibility(View.GONE);
            }
        });

        webView.loadUrl(mUrl);
    }

    /**
     * 是否透明化状态栏
     *
     * @return 是 或 否
     */
    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }
}
