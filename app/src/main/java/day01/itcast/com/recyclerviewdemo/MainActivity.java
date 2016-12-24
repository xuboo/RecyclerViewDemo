package day01.itcast.com.recyclerviewdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,SampleAdapter.OnItemClickLitener {

    private static final String TAG = "MainActivity";
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private SampleAdapter adapter;
    private List<Object> dataList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.mList.addAll(dataList);
                    adapter.notifyDataSetChanged();
                    Log.e(TAG, "handleMessage: 分页加载了");
                    break;
                case 1:
                    adapter.mList.clear();
                    adapter.mList.addAll(dataList);
                    adapter.notifyDataSetChanged();
                    Log.e(TAG, "handleMessage: 下拉刷新了");

                    mSwipeRefreshWidget.setRefreshing(false);
                    break;
                case 2:
                    mSwipeRefreshWidget.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
            }
        }
    };
    private LinearLayout mProgressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mRecyclerView = (RecyclerView) findViewById(android.R.id.list);
        mProgressbar = (LinearLayout) findViewById(R.id.progressbar);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));//为条目添加分割线

        Log.e(TAG, "远程第一次修改MainActivity");



        initData1();

        //刷新按钮变色
        mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        //这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            dataList.clear();
                            dataList.addAll(RequestData.getData());//网络请求
                            handler.sendEmptyMessageDelayed(0, 1000);
                        }
                    }).start();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

        });

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new SampleAdapter(MainActivity.this, dataList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(this);//设置接口回调

    }

    private void initData1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataList.clear();
                mSwipeRefreshWidget.setVisibility(View.INVISIBLE);
                mProgressbar.setVisibility(View.VISIBLE);
                dataList.addAll(RequestData.getData());

                handler.sendEmptyMessageDelayed(2, 2000);
            }
        }).start();

    }


    //swip刷新操作,网络刷新操作。清空adapter重新装填数据
    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataList.clear();
                dataList.addAll(RequestData.getNewData());
                handler.sendEmptyMessage(1);

            }
        }).start();


    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(MainActivity.this, position + " click",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(MainActivity.this, position + " long click",
                Toast.LENGTH_SHORT).show();
//        adapter.removeData(position);
    }
}
