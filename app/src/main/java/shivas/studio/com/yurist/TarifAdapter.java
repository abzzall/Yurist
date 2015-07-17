package shivas.studio.com.yurist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abz on 7/15/2015.
 */
public class TarifAdapter extends BaseAdapter {
    ArrayList<Tarif>objects;
    Context ctx;
    LayoutInflater lInflater;
    public  TarifAdapter(Context context, ArrayList<Tarif> objects) {
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
            view=lInflater.inflate(R.layout.tarif,parent,false);
        }
        Tarif tarif=objects.get(position);
        TextView title=(TextView)view.findViewById(R.id.tvTitle);
        title.setText(tarif.name);
        TextView content=(TextView)view.findViewById(R.id.tvDescr);
        content.setText(tarif.content);
        TextView srok=(TextView)view.findViewById(R.id.tvSrok);
        srok.setText("Срок "+tarif.time+" месяца.");
        TextView price=(TextView)view.findViewById(R.id.tvPrice);
        price.setText(tarif.price.toString());
        return view;
    }
}
