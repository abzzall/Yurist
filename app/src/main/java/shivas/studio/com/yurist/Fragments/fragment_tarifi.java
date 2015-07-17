package shivas.studio.com.yurist.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import shivas.studio.com.yurist.R;
import shivas.studio.com.yurist.Tarif;
import shivas.studio.com.yurist.TarifAdapter;
import shivas.studio.com.yurist.ActivityWithMenu;

public class fragment_tarifi extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tarifi, container,
                false);
        //TextView urlTextOut = (TextView) rootView.findViewById(R.id.tvTest);
        final String[] str = {null};
        new Thread() {

            StringBuilder text = new StringBuilder();
            @Override
            public void run() {
                String rez = null;

                try {
                    URL url = new URL("http://app.xn----ctbtcyocgj0j.xn--p1ai/mobile/tarifs");
                    //http://app.свой-юрист.рф/
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
        ArrayList<Tarif>tarifs=new ArrayList<Tarif>();

        while (str[0]==null);
        try {
            JSONArray jsonArray=new JSONArray(str[0]);
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Tarif tarif=new Tarif(
                        jsonObject.getInt("tarif_id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("content"),
                        jsonObject.getInt("price"),
                        jsonObject.getInt("time"),
                        jsonObject.getInt("status")
                );
                tarifs.add(tarif);
            }
            TarifAdapter tarifAdapter=new TarifAdapter(((ActivityWithMenu)getActivity()),tarifs);
            ListView lv=(ListView)rootView.findViewById(R.id.lv);
            lv.setAdapter(tarifAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //urlTextOut.setText(str[0]);
        return rootView;
    }

}