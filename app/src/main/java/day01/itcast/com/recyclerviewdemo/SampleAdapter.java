package day01.itcast.com.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by me2 on 2016/12/9.
 */

public class SampleAdapter extends RecyclerView.Adapter {

    public Context mContext;
    public List<Object> mList = new ArrayList<>();


    public SampleAdapter(Context context, List<Object> list) {
        mContext = context;
        mList.addAll(list);
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (position != mList.size()) {
            return R.layout.item_demo;
        } else {
            return R.layout.loading_more;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == R.layout.item_demo) {
             holder = new MyHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.item_demo, parent,
                    false));
        } else if (viewType == R.layout.loading_more) {
            holder = new LoadingHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.loading_more, parent,
                    false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.tv.setText((String) mList.get(position));
        } else if (holder instanceof LoadingHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_item);
        }

    }

    public class LoadingHolder extends RecyclerView.ViewHolder {
        public LoadingHolder(View itemView) {
            super(itemView);
        }
    }

}
