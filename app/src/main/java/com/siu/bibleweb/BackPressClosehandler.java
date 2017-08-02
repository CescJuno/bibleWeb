package com.siu.bibleweb;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by seoljongho on 2016. 1. 15..
 */
public class BackPressClosehandler {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    public BackPressClosehandler(Activity conText){ this.activity = conText;}

    public void OnBackpressed(){
        //현제 시간보다 백키누른시간이 2초가 작다면 알림창 띄움
        if(System.currentTimeMillis() > backKeyPressedTime + 2000)
        {
            showGuide();
            backKeyPressedTime = System.currentTimeMillis();
        }
        else if(System.currentTimeMillis() <= backKeyPressedTime + 2000)
        {
            clClose();
            toast.cancel();
        }
    }

    private void clClose(){
        activity.moveTaskToBack(true);
        activity.finish();

    }

    public void showGuide(){
        toast = Toast.makeText(activity,"뒤로 버튼을 한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }

}
