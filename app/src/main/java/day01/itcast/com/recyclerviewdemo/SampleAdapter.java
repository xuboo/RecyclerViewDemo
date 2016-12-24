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
    private OnItemClickLitener mOnItemClickLitener;


    public SampleAdapter(Context context, List<Object> list) {
        mContext = context;
        mList.addAll(list);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
        Log.e(TAG, "远程第一次修改SampleAdapter");

    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
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
            final MyHolder myHolder = (MyHolder) holder;
            myHolder.tv.setText((String) mList.get(position));
            //通过回调，添加点击事件
            if (mOnItemClickLitener != null) {
                myHolder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = myHolder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(myHolder.tv,pos);
                    }
                });

                myHolder.tv.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        int pos = myHolder.getLayoutPosition();
                        mOnItemClickLitener.onItemLongClick(myHolder.tv, pos);
                        return false;
                    }
                });
            }


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
