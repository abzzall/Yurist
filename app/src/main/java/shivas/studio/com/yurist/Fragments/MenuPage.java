package shivas.studio.com.yurist.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import shivas.studio.com.yurist.R;
import shivas.studio.com.yurist.ActivityWithMenu;

public class MenuPage extends Fragment  {
    //final TarifiActivity tarifiActivity=new TarifiActivity();

    View.OnClickListener onclick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btTarifi:{
                    ((ActivityWithMenu)getActivity()).selectItem(0);
                    break;
                }
                case R.id.btUsl:{
                    ((ActivityWithMenu)getActivity()).selectItem(1);
                    break;
                }
                case R.id.btNew:{
                    ((ActivityWithMenu)getActivity()).selectItem(5);
                    break;
                }
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_menu, container,
                false);

        Button btTar=(Button)rootView.findViewById(R.id.btTarifi);
        btTar.setOnClickListener(onclick);
        rootView.findViewById(R.id.btUsl).setOnClickListener(onclick);

        rootView.findViewById(R.id.btNew).setOnClickListener(onclick);
        return rootView;
    }

}
