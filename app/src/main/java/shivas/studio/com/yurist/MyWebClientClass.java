package shivas.studio.com.yurist;

import android.webkit.WebViewClient;




import android.webkit.WebView;

public class MyWebClientClass extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // TODO Auto-generated method stub

        view.loadUrl(url);
        return true;
    }
}