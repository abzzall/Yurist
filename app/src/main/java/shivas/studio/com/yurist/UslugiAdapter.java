package shivas.studio.com.yurist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by abz on 7/15/2015.
 */
public class UslugiAdapter extends BaseAdapter {
    ArrayList<Uslugi>objects;
    Context ctx;
    LayoutInflater lInflater;
    public  UslugiAdapter(Context context, ArrayList<Uslugi> objects) {
        ctx = context;
        this.objects = objects;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (view == null) {
            view=lInflater.inflate(R.layout.uslugi,parent,false);
        }
        Uslugi uslugi=objects.get(position);
        TextView title=(TextView)view.findViewById(R.id.tvTitle);
        title.setText(uslugi.name);
        ImageView img=(ImageView)view.findViewById(R.id.img);
        final Bitmap[] bitmap = {null};
        final String pic_url=uslugi.pic_url;
        new Thread() {
            @Override
            public void run() {
                try {
                    bitmap[0] = BitmapFactory.decodeStream((InputStream) new URL("http://app.xn----ctbtcyocgj0j.xn--p1ai/skin/base/img/" + pic_url).getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }.start();
        while (bitmap[0]==null);
        img.setImageBitmap(bitmap[0]);
        return view;
    }
}