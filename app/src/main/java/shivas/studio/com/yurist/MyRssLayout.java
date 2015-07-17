package shivas.studio.com.yurist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MyRssLayout extends RelativeLayout {

	ImageView picture;
	TextView titleView, summaryView;
	
	public MyRssLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.Options, 0, 0);
		String titleText = a.getString(R.styleable.Options_titleText);
		String summaryText = a.getString(R.styleable.Options_summaryText);
		a.recycle();


		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.new_layout, this, true);

		titleView = (TextView) findViewById(R.id.titleText);
		titleView.setText(titleText);
		titleView.setTextColor(Color.WHITE);
		summaryView = (TextView) findViewById(R.id.summaryText);
		summaryView.setText(summaryText);
		summaryView.setTextColor(Color.GRAY);
	}

	public MyRssLayout(Context context) {
		this(context, null);
	}
	
	public void setTitle(String titleText) {
		titleView.setText(titleText);
	}
	
	public void setSummary(String summaryText) {
		summaryView.setText(summaryText);
	}
	
	public ImageView getPictureView() {
		return picture;
	}
	
}
