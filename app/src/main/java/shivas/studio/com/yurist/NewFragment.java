package shivas.studio.com.yurist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class NewFragment extends Fragment {
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState) {
        if(!isNetworkAvailable()) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Ошибка!")
                    .setMessage("У вас не подключен интернет.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            getActivity().finish();
                        }
                    }).show();
            return null;
        }
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_new);
        rootView = inflater.inflate(R.layout.activity_new, container,
                false);
        parse();

        return rootView;


    }
    public void parse() {
        RSSParserClass rssParser = new RSSParserClass("http://www.injuris.ru/feed/");
        rssParser.fetchXML();

        while(rssParser.parsingComplete) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        List<String> titles, links, descriptions;
        titles = rssParser.getTitle();
        links = rssParser.getLink();
        descriptions = rssParser.getDescription();

        setData(titles, links, descriptions);
    }
    private void setData(List<String> titles, List<String> links, List<String> descriptions) {
        String data = "";
        String title = "";
        int index = 0;
        int startIndex, endIndex;

        for(int i = 0; i < titles.size(); i++) {
            if(descriptions.get(i).length() > 90) {
                index = descriptions.get(i).indexOf(" ", 90);
                if(index < 89) {
                    index = descriptions.get(i).length();
                }
                data = descriptions.get(i).substring(0, index);
            } else {
                data = descriptions.get(i);
            }

            if(titles.get(i).length() > 32) {
                index = titles.get(i).indexOf(" ", 32);
                if(index < 31) {
                    index = titles.get(i).length();
                }
                title = titles.get(i).substring(0, index);
            } else {
                title = titles.get(i);
            }

            data = data.replace("<br>", "");
            data = data.replace("&nbsp;", "");
            data = data.replace("&quot;", "");
            startIndex = data.indexOf("<![CDATA[");
            endIndex = data.indexOf("]]>");
            if(startIndex > 0) {
                data = data.substring(startIndex + 9, endIndex);
            }

            MyRssLayout myNew = new MyRssLayout(getActivity());

            myNew.setTitle(title + "...");
            myNew.setSummary(data + "...");
            ArrayList<MyRssLayout> rssLayouts = new ArrayList<MyRssLayout>();
            LinearLayout newsLayout = (LinearLayout) rootView.findViewById(R.id.newsLayout);
            rssLayouts.add(myNew);
            newsLayout.addView(myNew);
            myNew.setOnClickListener(new ClickOnLabelListener(rssLayouts,newsLayout, links));
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class ClickOnLabelListener implements View.OnClickListener {
        ArrayList<MyRssLayout> rssLayouts;
        LinearLayout newsLayout;
        List<String> links;
        public ClickOnLabelListener(ArrayList<MyRssLayout> rssLayouts, LinearLayout newsLayout, List<String> links) {
            this.rssLayouts=rssLayouts;
            this.newsLayout=newsLayout;
            this.links=links;
        }

        @Override
        public void onClick(View v) {
            for(int i = 0; i < rssLayouts.size(); i++) {
                if(rssLayouts.get(i).equals(v)) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(links.get(i)));
                    startActivity(browserIntent);
                    break;
                }
            }
        }

    }
}
