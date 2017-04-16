package cn.edu.pku.hyq.app.restaurants.ui.activity;


import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import cn.edu.pku.hyq.app.restaurants.R;
import cn.edu.pku.hyq.app.restaurants.adapter.SearchAdapter;
import cn.edu.pku.hyq.app.restaurants.consts.AppConsts;
import cn.edu.pku.hyq.app.restaurants.consts.ThreadConsts;
import cn.edu.pku.hyq.app.restaurants.model.AppSearchResult;
import cn.edu.pku.hyq.app.restaurants.service.BlurBehind;
import cn.edu.pku.hyq.app.restaurants.service.SearchService;
import cn.edu.pku.hyq.app.restaurants.utils.Utils;
import com.alibaba.fastjson.JSONObject;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.HashMap;
import java.util.List;


import com.yancloud.android.reflection.YanCloud;
import com.yancloud.android.reflection.get.YanCloudGet;

public class SearchActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    private SearchView searchView;
    private RecyclerView mRecyclerView;
    private CardView cardView;
    private SearchAdapter searchAdapter;
    private SearchListener searchListener;
    private Context context;
    private CircularProgressView progressView;
    public static Animation scaleAnimation; // TODO

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch(msg.what) {
                case ThreadConsts.ONE_APP_SEARCH_DOEN :
                    updateSearchResults((List<AppSearchResult>) msg.obj);
                    break;
                case ThreadConsts.ALL_APP_SEARCH_DOEN :
                    allFinished();
                    break;
                default:
                    break;
            }
        }
    };

    // TODO
    public static SearchActivity searchActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_up, R.anim.abc_fade_out);
        setContentView(R.layout.activity_main_1);

        // 设置透明样式
        BlurBehind.getInstance()
                .withAlpha(85)
                .withFilterColor(Color.parseColor("#262626"))
                .setBackground(this);

        context = this;
        searchActivity = this;



        // get views
        mRecyclerView = (RecyclerView) findViewById(R.id.result_recycler);
        //mRecyclerView.setVisibility(View.INVISIBLE);
        searchView = (SearchView) findViewById(R.id.search_bar);
        cardView = (CardView) findViewById(R.id.search_result_item_card);


        HashMap<String, Integer> aliveApps = Utils.scanPort();

        String searchQuery = "汉堡";

        for(String appPackages : AppConsts.APP_PACKAGES) {
            // 查询美团
//            if(s.get)
            int port = 0;
            if(appPackages.equals("com.sankuai.meituan.takeoutnew")) {
                if(aliveApps.get(appPackages) != null) {
                    port = aliveApps.get(appPackages);
                }
                String testSearch = "{\"lat\":\"39986316\",\"lng\":\"116304664\",\"keyword\":\" "+ searchQuery +" \",\"pageNum\":\"0\"}";
                YanCloud yanCloud = YanCloud.fromGet(AppConsts.LOCAL_IP, 1788);
                String searchResult = yanCloud.get("com.sankuai.meituan.takeoutnew", "getSearch", testSearch);
                Log.d("美团外卖搜索结果:", searchResult);
            }
            if(appPackages.equals("me.ele")) {
                if(aliveApps.get(appPackages) != null) {
                    port = aliveApps.get(appPackages);
                }
                String json = "{\"latitude\": \"39.966714\",\"longitude\": \"116.306533\",\"keyword\": \" " + searchQuery + "\"}";
                YanCloud yanCloud = YanCloud.fromGet(AppConsts.LOCAL_IP, 1729);
                String searchResult = yanCloud.get("me.ele", "searchRestaurant", json);
                Log.d("饿了么外卖搜索结果:", searchResult);
            }
            if(appPackages.equals("com.baidu.lbs.waimai")) {
                if(aliveApps.get(appPackages) != null) {
                    port = aliveApps.get(appPackages);
                }
                YanCloud yanCloud = YanCloud.fromGet(AppConsts.LOCAL_IP, 1759);
                String json = "{\"keyword\": \"" + searchQuery + "\"}";
                String searchResult = (String) yanCloud.get("comm", "queryRestaurant", json);
                Log.d("百度外卖搜索结果:", searchResult);
            }
        }



//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new KeepOneH<>().toggle();
//            }
//        });

//        progressView = (CircularProgressView) findViewById(R.id.progress);
//
//        searchListener = new SearchListener();
//        searchView.setOnQueryTextListener(searchListener);
//
//        searchAdapter = new SearchAdapter();
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(searchAdapter);
//        LayoutAnimationController layoutAnimationController= new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.result_fade_in));
//        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        scaleAnimation =  AnimationUtils.loadAnimation(this, R.anim.result_fade_in);
//        // mRecyclerView.setItemAnimator(scaleAnimation);
//        mRecyclerView.startAnimation(scaleAnimation);
//        mRecyclerView.setLayoutAnimation(layoutAnimationController);

    }


    void enterReveal(View myView) {

        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

            // make the view visible and start the animation
            myView.setVisibility(View.VISIBLE);
            anim.start();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 接受子线程数据，配合主线程更新UI
     */
    public void allFinished() {
        stopProgress();
        searchAdapter.notifyDataSetChanged();
        // Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }
    /**
     * 更新UI界面
     * @param
     */
    public void updateSearchResults(List<AppSearchResult> appSearchResults) {
        searchAdapter.setData(appSearchResults);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                searchAdapter.notifyDataSetChanged();
                Log.d("界面刷新:时间点七", String.valueOf(System.currentTimeMillis()));
                enterReveal(mRecyclerView);
            }
        }, 50);

//        Animation scaleAnimation =  AnimationUtils.loadAnimation(this, R.anim.result_fade_in);
//        // mRecyclerView.setItemAnimator(scaleAnimation);
//        mRecyclerView.startAnimation(scaleAnimation);

//      searchFetchTask.cancel(true);

    }

    /**
     * 搜索事件监听
     */
    private class SearchListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String searchQuery) {
            if (!searchQuery.isEmpty()) { // 如果搜索串非空
                hideKeyboard(); // 掩藏键盘
                startProgress();    // 搜索进度条

                Log.d("开始异步搜索任务:时间点一", String.valueOf(System.currentTimeMillis()));

                SearchService searchService = new SearchService(getApplicationContext(), mHandler);
                searchService.startAsyncSearch(searchQuery);    // 开始异步搜索任务
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.isEmpty()) {
                searchAdapter.setData(null);
                searchAdapter.notifyDataSetChanged();
            }
            return false;
        }
    }

    /**
     * 掩藏键盘
     */
    private void hideKeyboard() {
        // 检查是否没有视图获得焦点
        View view = SearchActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 开始搜索进度条
     */
    private void startProgress() {
        progressView.setVisibility(View.VISIBLE);
        progressView.startAnimation();
    }

    /**
     * 停止搜索进度条
     */
    private void stopProgress() {
        progressView.setVisibility(View.GONE);
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }
}
