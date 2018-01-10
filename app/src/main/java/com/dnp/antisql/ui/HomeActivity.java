package com.dnp.antisql.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dnp.antisql.R;
import com.dnp.antisql.fragment.GridFragment;
import com.dnp.antisql.retrofit.APIClient;
import com.dnp.antisql.retrofit.APIInterface;
import com.dnp.antisql.retrofit.QueryList;
import com.dnp.antisql.retrofit.StatusList;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements GridFragment.OnFragmentInteractionListener {
    @BindView(R.id.statusbutton)
    Button statusbutton;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeToRefresh;
    APIInterface apiInterface;
    Activity context = this;
    String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            int result = 0;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            Window w = context.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //status bar height
            int statusBarHeight = result;

            View view = new View(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.getLayoutParams().height = statusBarHeight;
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, GridFragment.newInstance());
        transaction.commit();


        CheckStatus();
        statusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
status = statusbutton.getText().toString();
                if(status.equals("ON")){
                    UpdateStatus(0);
                   // CheckStatus();
                }else{
                    UpdateStatus(1);
                  //  CheckStatus();
                }

            }
        });

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CheckStatus();
                swipeToRefresh.setRefreshing(false);
            }
        });
        subscribeToPushService("antisql");
    }
    private void CheckStatus(){

        Call<StatusList> call = apiInterface.doGetStatusList();
        call.enqueue(new Callback<StatusList>() {
            @Override
            public void onResponse(Call<StatusList> call, Response<StatusList> response) {
                StatusList statusList = response.body();
                String status = statusList.status;

                Log.e("Status : ", statusList.status);

                if(status.equals("1")){
                    statusbutton.setBackgroundResource(R.drawable.round_button);
                    statusbutton.setText("ON");
                }else{
                    statusbutton.setBackgroundResource(R.drawable.round_button_off);
                    statusbutton.setText("OFF");
                }



            }

            @Override
            public void onFailure(Call<StatusList> call, Throwable t) {
                call.cancel();
            }
        });
    }
    private void UpdateStatus(int query){

        Call<QueryList> call = apiInterface.doGetQueryList(query);
        call.enqueue(new Callback<QueryList>() {
            @Override
            public void onResponse(Call<QueryList> call, Response<QueryList> response) {
                QueryList queryList = response.body();
                String status = queryList.status;

                Log.e("queryList : ", queryList.status);
                if(status.equals("1")){
                    statusbutton.setBackgroundResource(R.drawable.round_button);
                    statusbutton.setText("ON");
                }else{
                    statusbutton.setBackgroundResource(R.drawable.round_button_off);
                    statusbutton.setText("OFF");
                }


            }

            @Override
            public void onFailure(Call<QueryList> call, Throwable t) {
                call.cancel();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void subscribeToPushService(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic);

        Log.d("antisql", "Subscribed");
        //Toast.makeText(HomeActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        Log.d("antisql token", token);
        //Toast.makeText(HomeActivity.this, token, Toast.LENGTH_SHORT).show();
    }
}
