package cn.edu.pku.hyq.app.restaurants.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;


import cn.edu.pku.hyq.app.restaurants.R;
import cn.edu.pku.hyq.app.restaurants.service.BlurBehind;

public class BlurredActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_blurred);

		BlurBehind.getInstance()
                .withAlpha(30)
                .withFilterColor(Color.parseColor("#52575b"))
                .setBackground(this);
				// #0075c0 蓝色
				// #52575b 黑色
	}
}
