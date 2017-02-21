package com.vlk.multimager.activities;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.MenuItem;
import android.widget.Toast;

import com.vlk.multimager.R;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Params;

/**
 * Created by vansikrishna on 6/14/2016.
 */
public class MultiCameraActivity extends BaseActivity{

    Params params;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_capture);
        init();
        initFragment();
    }

    private void init(){
        if(this.getIntent() != null){
            if(this.getIntent().hasExtra(Constants.KEY_PARAMS)) {
                Object object = this.getIntent().getSerializableExtra(Constants.KEY_PARAMS);
                if(object instanceof Params)
                    params = (Params) object;
                else{
                    Toast.makeText(this, "Provided serializable data is not an instance of Params object.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        if(fragment instanceof CameraFragment){
            ((CameraFragment) fragment).onBackPressed();
        }
        else{
            ((Camera2Fragment) fragment).onBackPressed();
        }
    }

    private void initFragment(){
        fragment = CameraFragment.newInstance(params);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
//        if(Build.VERSION.SDK_INT >= 21){
//            fragment = Camera2Fragment.newInstance(params);
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.container, fragment)
//                    .commit();
//        }
//        else{
//            fragment = CameraFragment.newInstance(params);
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.container, fragment)
//                    .commit();
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
