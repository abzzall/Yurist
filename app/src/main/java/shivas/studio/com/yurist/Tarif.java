package shivas.studio.com.yurist;

import android.text.Html;
import android.text.TextUtils;

/**
 * Created by abz on 7/15/2015.
 */
public class Tarif {
    Integer id;

    String name,content;
    Integer price,time,status;
    public Tarif(Integer id,String name,String content,Integer price,Integer time,Integer status){
        this.id=id;
        this.name=name;
        this.content= Html.fromHtml(content).toString();
        this.price=price;
        this.time=time;
        this.status=status;
    }

}
