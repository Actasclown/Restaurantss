package cn.edu.pku.hyq.app.restaurants.consts;

import java.util.HashMap;

/**
 * Created by Yue on 2017/3/20.
 */
public class AppConsts {

    public static final String LOCAL_IP = "127.0.0.1";

    public static final int START_PORT = 1700;

    public static final int END_PORT = 1800;

    public static final String API_PORTER = "APIPorter";

    public static final String METHOD_GET_PROCESS_NAME = "getProcessName";

    public static final String[] APP_PACKAGES = new String[]{
            "com.sankuai.meituan", "com.sankuai.meituan.takeoutnew", "me.ele", "com.baidu.lbs.waimai"
    };

    public static final HashMap<String, String> APP_THREAD = new HashMap<String, String>(){
        {
            put( "com.sankuai.meituan", "MeiTuanThread");
            put( "com.jingdong.app.mall", "JingDongThread");
        }
    };

}
