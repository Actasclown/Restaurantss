package cn.edu.pku.hyq.app.restaurants.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * Created by Yue on 2017/3/20.
 */
public class SearchService {

    private Context context;

    private Handler mHandler;

    private List<Runnable> asyncTaskthreads;

//    public Handler cachedThreadPoolHandler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch(msg.what) {
//                case ThreadConsts.ONE_APP_SEARCH_DOEN :
//                    Message newMsg = new Message();
//                    msg.what = ThreadConsts.ONE_APP_SEARCH_DOEN;
//                    newMsg.obj = msg.obj;
//                    mHandler.sendMessage(msg);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    public SearchService(Context context,  Handler mHandler) {
        this.context = context;
        this.mHandler = mHandler;
        init();
    }

    private void init() {
        asyncTaskthreads = new ArrayList<Runnable>();
    }

    /**
     * 开始异步搜索
     */
    public void startAsyncSearch(String searchQuery) {
        // 开始启动异步任务
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for(Runnable asyncTaskthread : asyncTaskthreads) {
            cachedThreadPool.execute(asyncTaskthread);
        }
        cachedThreadPool.shutdown();
        while(true){
            if(cachedThreadPool.isTerminated()){
                Message msg = new Message();
//                msg.what = ThreadConsts.ALL_APP_SEARCH_DOEN;
                mHandler.sendMessage(msg);
                System.out.println("所有的子线程都结束了！");
                break;
            }
        }
    }


    public boolean isNetAvailable() {
        ConnectivityManager manager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean netAvailable = manager.getActiveNetworkInfo().isAvailable();
        return netAvailable;
    }
}
