package cn.edu.pku.hyq.app.restaurants.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.edu.pku.hyq.app.restaurants.R;
import cn.edu.pku.hyq.app.restaurants.model.AppSearchResult;
import com.yancloud.android.reflection.YanCloud;

import java.lang.reflect.Field;
import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;

    private List<AppSearchResult> appSearchResults;

    public void setData(List<AppSearchResult> appSearchResults) {
        this.appSearchResults = appSearchResults;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView itemDetail;
        CardView cardView;
        private ImageView resultItemIcon;
        private TextView resultItemtitle;
        private TextView resultItemcontent;
        private TextView url;

        public ViewHolder(View v) {
            super(v);
            view= v;
        }
    }

    public SearchAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        context =  parent.getContext();
        final ViewHolder vh = new ViewHolder(v);
        TextView itemDetail = (TextView) v.findViewById(R.id.search_result_item_detail);    // 详细
        vh.cardView = (CardView) v.findViewById(R.id.search_result_item_card);
        vh.resultItemIcon = (ImageView) v.findViewById(R.id.search_result_item_icon);
        vh.resultItemtitle = (TextView) v.findViewById(R.id.search_result_item_title);
        vh.resultItemcontent = (TextView) v.findViewById(R.id.search_result_item_content);
        vh.url = (TextView) v.findViewById(R.id.search_result_item_url);
        // v.startAnimation(SearchActivity.scaleAnimation);
        // 展开
        // 注册点击事件
        itemDetail.setClickable(true);
        itemDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int index;
                    View realView = LayoutInflater.from(context).inflate(R.layout.search_result_item, parent, false);
                    // 尽量用线性垂直布局来动态的添加视图
                    LinearLayout linearLayout = (LinearLayout) realView.findViewById(R.id.search_result_item_right);
                    Log.d("textClick","1111111111----》》22222222");
                    View subView = LayoutInflater.from(context).inflate(R.layout.search_result_item_more, (ViewGroup) realView, false);
                    Log.d("textClick","22222222");
                    // 利用cell控件的Tag值来标记cell是否被点击过,因为已经将重用机制取消，cell退出当前界面时就会被销毁，Tag值也就不存在了。
                    // 如果不取消重用，那么将会出现未曾点击就已经添加子视图的效果，再点击的时候会继续添加而不是收回。
                    if (realView.findViewById(R.id.search_result_item_right).getTag() == null) {
                        index = 1;
                    } else {
                        index = (int) realView.findViewById(R.id.search_result_item_right).getTag();
                    }
                    Log.d("textClick","333333333");
                    // close状态: 添加视图
                    if (index == 1) {
                        linearLayout.addView(subView);
                        subView.setTag(1000);
                        realView.findViewById(R.id.search_result_item_right).setTag(2);
                        Log.d("textClick","444444444");
                    } else {
                        // open状态： 移除视图
                        linearLayout.removeView(realView.findViewWithTag(1000));
                        realView.findViewById(R.id.search_result_item_right).setTag(1);
                        Log.d("textClick","55555555");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // 取消viewHolder的重用机制（很重要）
        vh.setIsRecyclable(false);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(appSearchResults != null && !appSearchResults.isEmpty()) {
            AppSearchResult appSearchResult = appSearchResults.get(position);
            int iconResourceId = R.drawable.com_jingdong_app_mall;  // TODO 默认图片
            try {
                String packageName = appSearchResult.getPackageName();
                String resourceName = this.getResourceName(packageName);
                Field idField = R.drawable.class.getDeclaredField(resourceName);
                iconResourceId = idField.getInt(idField);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            holder.resultItemIcon.setImageResource(iconResourceId);
            holder.resultItemtitle.setText(appSearchResult.getTitle());
            holder.resultItemcontent.setText(appSearchResult.getContent());
            if(appSearchResult.getUrl() != null) {
                holder.url.setText(appSearchResult.getUrl());
            }
            final String packagename = appSearchResult.getPackageName();
            final String type = appSearchResult.getType();

            String params =  appSearchResult.getParams();
            if(params != null) {
                params = params.replace("${1}", appSearchResult.getTitle());
            } else {

            }

            final String finalParams = params;
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("222222222222", "333333333333333");
                    //new YanCloud.Builder().configSet(context)
                    //YanCloudStub.set(packagename, type, finalParams);
                    // YanCloudSet.getInstance(context).set(packagename, type, params);
                    // YanCloud.fromSet(context).set("com.jingdong.app.mall", "Product", "{\"productID\":\"3500590\"}");
                    if(finalParams == null) {
                        YanCloud.fromSet(context).set("com.jingdong.app.mall", "Product", "{\"productID\":\"2303984\"}");
                    } else {
                        YanCloud.fromSet(context).set(packagename, type, finalParams);
                    }
                }
            });
            // fade in TODO scaleAnimation的来源
            // mRecyclerView.setItemAnimator(scaleAnimation);
        }
    }

    /**
     * 获取到资源名称
     * @return
     */
    public String getResourceName(String packageName) {
        String[] fields = packageName.split("\\.");
        String resourceName = "";
        int index = 0;
        for(String field : fields) {
            if(index == 0) {
                resourceName += field.toLowerCase();
            } else {
                resourceName = resourceName + "_" + field.toLowerCase();
            }
            ++index;
        }
        return resourceName;
    }

    @Override
    public int getItemCount() {
        if(appSearchResults != null && !appSearchResults.isEmpty()) {
            return appSearchResults.size();
        } else {
            return 0;
        }
    }




}