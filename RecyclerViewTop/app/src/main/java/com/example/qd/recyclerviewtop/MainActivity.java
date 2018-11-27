package com.example.qd.recyclerviewtop;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainAdapter adapter;
    private RecyclerView recyclerView;
    private List<String> mDatas;
    private View include;
    private String str;
    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        include = findViewById(R.id.include);
        recyclerView = findViewById(R.id.recyclerView);

        //添加数据
        addData();
        adapter = new MainAdapter(MainActivity.this, mDatas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //定位初始点坐标
                View stickyInfoView = recyclerView.findChildViewUnder(recyclerView.getMeasuredWidth() / 2, 1);
                if (stickyInfoView != null) {
                    TextView typeName = stickyInfoView.findViewById(R.id.typeName);
                    if (typeName != null) {
                        str = typeName.getText().toString();
                    }
                    if (str.equals("标题")) {
                        include.setVisibility(View.GONE);
                    } else if (str.equals("筛选")) {
                        include.setVisibility(View.VISIBLE);
                    } else if (str.equals("内容")) {
                        include.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    smoothMoveToPosition(recyclerView, mToPosition);
                }
            }
        });

        adapter.setmOnItemClickListerer(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != -1) {
                    smoothMoveToPosition(recyclerView, position);
                }
            }
        });
    }

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    private void addData() {
        mDatas = new ArrayList<>();
        //手动添加三条数据，adapter判断的依据
        mDatas.add("0");
        mDatas.add("1");
        //for循环加入30条数据
        for (int i = 1; i <= 30; i++) {
            mDatas.add("这是第" + i + "条数据");
        }
    }

    public void MoveToPosition(LinearLayoutManager manager, int n, boolean isMove) {
        if (isMove) {
            manager.scrollToPositionWithOffset(n, 0);
        }
    }
}
