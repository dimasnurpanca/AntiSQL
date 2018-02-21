package com.dnp.antisql.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnp.antisql.R;
import com.dnp.antisql.adapter.LogAdapter;
import com.dnp.antisql.retrofit.APIClient;
import com.dnp.antisql.retrofit.APIInterface;
import com.dnp.antisql.retrofit.IPList;
import com.dnp.antisql.retrofit.IPRespond;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dimasnurpanca on 10/2/2017.
 */

public class LogActivity extends AppCompatActivity{

    @BindView(R.id.progressBar1)
    ProgressBar spinner;
    APIInterface apiInterface;
    ArrayList<IPList> dataModels;
    ListView listView;
    private static LogAdapter adapter;
    private static final String TAG = "LogActivity";
    
    Activity context = this;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);





        setTitle("Log SQL Injection");

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
            view.setBackgroundColor(context.getResources().getColor(R.color.warnaDark));
        }


        apiInterface = APIClient.getClient().create(APIInterface.class); //retrofit
        listView=(ListView)findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                IPList dataModel= dataModels.get(position);
                final String ids = dataModel.getID();
                final String ips = dataModel.getIP();
                if(dataModel.getStatus().equals("2")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);
                    builder.setTitle("Aksi");
                    builder.setMessage("IP "+ips+" Ini Melakukan Percobaan SQL Injection");

                    //Yes Button
                    builder.setPositiveButton("Blokir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            aksi(ids);
                            reload();
                            //AddStoryActivity.this.onSuperBackPressed();
                        }
                    });

                    //No Button
                    builder.setNegativeButton("Abaikan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }

            }
        });

loadJSONIP();


    }

    private void loadJSONIP(){
        spinner.setVisibility(View.VISIBLE);
        Call<List<IPList>> call3 = apiInterface.getJSONIP();
        call3.enqueue(new Callback<List<IPList>>() {
            @Override
            public void onResponse(Call<List<IPList>> call, Response<List<IPList>> response) {

                List<IPList> jsonResponse = response.body();
                //Log.e("ticker 2 : ", jsonResponse.get(1).getTicker());

                dataModels= new ArrayList<>();
                for (int i = 0; i < jsonResponse.size(); i++) {
                    if(!jsonResponse.get(i).getIP().equals("0")){
                        dataModels.add(new IPList(jsonResponse.get(i).getID(),jsonResponse.get(i).getIP(), jsonResponse.get(i).getDate(),jsonResponse.get(i).getStatus()));
    }
                    }


                adapter= new LogAdapter(dataModels,getApplicationContext());

                listView.setAdapter(adapter);
                //data = new ArrayList<>(Arrays.asList(response.body()));
                //   adapter = new LiveAdapter(data);
                //   recyclerView.setAdapter(adapter);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<IPList>> call, Throwable t) {
                spinner.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();
        // loadJSONLive();
    }
    @Override
    public void onBackPressed()
    {
        // moveTaskToBack(true);
        //super.onBackPressed();
        //  startActivity(new Intent(NewsActivity.this, FavoriteActivity.class));
        finish();

    }

    private void aksi(String id) {

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LogActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        RequestBody val_id =RequestBody.create(MediaType.parse("multipart/form-data"), id);
        Call<IPRespond> resultCall = apiInterface.aksi(val_id);

        resultCall.enqueue(new Callback<IPRespond>() {
            @Override
            public void onResponse(Call<IPRespond> call, Response<IPRespond> response) {

                progressDialog.dismiss();

                // Response Success or Fail

                if (!response.body().getError()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();}



            }

            @Override
            public void onFailure(Call<IPRespond> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void reload(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.recreate();
        } else {
            final Intent intent = this.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            this.finish();
            this.overridePendingTransition(0, 0);
            this.startActivity(intent);
            this.overridePendingTransition(0, 0);
        }
    }

}