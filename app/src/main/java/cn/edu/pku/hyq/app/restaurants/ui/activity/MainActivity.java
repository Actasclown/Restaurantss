package cn.edu.pku.hyq.app.restaurants.ui.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ListView;
import cn.edu.pku.hyq.app.restaurants.Listener.OnBlurCompleteListener;
import cn.edu.pku.hyq.app.restaurants.R;
import cn.edu.pku.hyq.app.restaurants.consts.TagConsts;
import cn.edu.pku.hyq.app.restaurants.service.BlurBehind;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private Context context;

    private String oAuthVerifier = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_down, R.anim.abc_fade_out);
        setContentView(R.layout.activity_main_1);
        context = this;

        /* 开启strict mode 观察程序性能 */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /*
          处理地址栏
         */


        /*
          处理搜索栏
         */


        /*
          处理ListView
         */
        //初始化一个Adapter
        //this.scanPort();
        //通过ID获取listView
        ListView listView = (ListView) findViewById(R.id.ListViewId);
        // Model model = new Model();

        button = (Button)findViewById(R.id.search_btn_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchActivity();
            }
        });


    }

    private void startSearchActivity() {
        BlurBehind.getInstance().execute(MainActivity.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            try {
                Log.d(TagConsts.YUE_TAG, "MainActivity Result OK");
            } catch (Exception e) {
                Log.e("Failed", e.getMessage());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
