package shivas.studio.com.yurist;

import android.text.Html;

/**
 * Created by abz on 7/17/2015.
 */
public class Uslugi {
    Integer id,status;
    String name, content,pic_url;
    public  Uslugi(Integer id,String name,String content,String pic_url,Integer status){
        this.id=id;
        this.name=name;
        this.content= Html.fromHtml(content).toString();
        this.pic_url=pic_url;
        this.status=status;
    }
}
