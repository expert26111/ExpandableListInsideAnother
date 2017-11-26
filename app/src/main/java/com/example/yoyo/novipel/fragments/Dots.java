package com.example.yoyo.novipel.fragments;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yoyo.novipel.utility.ProgressBarAnimation;
import com.example.yoyo.novipel.R;

//the fragment on which the three animated dots need to lay
public class Dots extends Fragment {


    private static final int PROGRESS = 0x1;
    private long fileSize = 0;
    private ImageView mProgress;
    private boolean mProgressStatus = false;
    private Handler mHandler = new Handler();

    static TextView mDotsText[];
    private int mDotsCount = 3;
    private LinearLayout mDotsLayout;

    private TextView first;
    private TextView second;
    private TextView third;
    private TextView fourth;
    TextView[] arrayString = {first, second, third, fourth};
    int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_base_animation, container, false);
        mProgress = (ImageView) v.findViewById(R.id.rocket);
       // ProgressBarAnimation mProgressAnimation = new ProgressBarAnimation(mProgress, 10, 100);
        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.move);
        mProgress.startAnimation(animation1);
        animation1.setRepeatCount(Animation.INFINITE);
        animation1.setRepeatMode(AlphaAnimation.INFINITE);
      //  mProgressAnimation.setDuration(10);
        ///mProgress.startAnimation(mProgressAnimation);

        return v;
    }
}

//
//    public boolean doSomeTasks() {
//
//        while (fileSize <= 1000000000) {
//
//            fileSize++;
//            if(fileSize==1000000000)
//            {
//                return true;
//            }
//
//        }
//        return true;
//
//
//    }
//        public void receiveUpdate(int dot) {
//            // Do something
//            mDotsText[dot].setTextColor(android.graphics.Color.RED);
//        }
//}
