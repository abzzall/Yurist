package shivas.studio.com.yurist.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import shivas.studio.com.yurist.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import shivas.studio.com.yurist.ActivityWithMenu;
import shivas.studio.com.yurist.Uslugi;
import shivas.studio.com.yurist.UslugiAdapter;

public class fragment_uslugi extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_uslugi, container,
                false);
        TextView urlTextOut = (TextView) rootView.findViewById(R.id.tvTest);
        final String[] str = {null};
        new Thread() {

            StringBuilder text = new StringBuilder();
            @Override
            public void run() {
                String rez = null;

                try {
                    URL url = new URL("http://app.xn----ctbtcyocgj0j.xn--p1ai/mobile/services/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setDoInput(true);

                    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                    // Starts the query
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    rez = buffer.toString();
                } catch (ClientProtocolException e) {
                    rez = e.getStackTrace().toString();
                } catch (IOException e) {
                    rez = e.getStackTrace().toString();
                }

                str[0] =rez;
            }
        }.start();
        ArrayList<Uslugi> tarifs=new ArrayList<Uslugi>();
        while (str[0]==null);
        //urlTextOut.setText(str[0]);
        try {
            JSONArray jsonArray=new JSONArray(str[0]);
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Uslugi tarif=new Uslugi(
                        jsonObject.getInt("questions_cat_id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("content"),
                        jsonObject.getString("logo"),
                        jsonObject.getInt("status")
                );
                tarifs.add(tarif);
            }
            UslugiAdapter tarifAdapter=new UslugiAdapter(((ActivityWithMenu)getActivity()),tarifs);
            ListView lv=(ListView)rootView.findViewById(R.id.lv);
            lv.setAdapter(tarifAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootView;
    }

}