package com.example.splashscreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splashscreen.utils.AppReceiver;
import com.example.splashscreen.utils.LoadingDialog;
import com.example.splashscreen.utils.PrefManager;
import com.example.splashscreen.utils.apihelpers.ApiInterface;
import com.example.splashscreen.utils.apihelpers.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private PendingIntent pendingIntent;
    private static  int ALARM_REQUEST_CODE=134;

    private int interval_seconds = 10;
    private int NOTIFICATION_ID = 1;

    @BindView(R.id.signUp)
    TextView signUp;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;

    ApiInterface apiInterface;
    PrefManager prefManager;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadingDialog = new LoadingDialog(this);
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString())) {
                    username.setError("Field Cannot Empty");
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Field Cannot Empty");
                } else {
                    fetchApi();
                }

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DaftarActivity2.class);
                startActivity(i);
            }
        });
        Intent alarmIntent = new Intent(this, AppReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, alarmIntent, 0);
        startAlarmManager();
    }
    public void startAlarmManager(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, interval_seconds);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "AlarmManager Start.", Toast.LENGTH_SHORT).show();
    }

    private void fetchApi() {
        loadingDialog.startLoadingDialog();
        apiInterface.loginApi(username.getText().toString(),
                password.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        loadingDialog.dismissLoadingDialog();
                        if (jsonObject.getString("status").equals("200")){
                            Toast.makeText(MainActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                            prefManager.spString(PrefManager.SP_ID, jsonObject1.getString("email")+"");
                            prefManager.spString(PrefManager.SP_TOKEN_USER, jsonObject1.getString("token")+"");
                            prefManager.saveSession();

                            Intent intent = new Intent(getApplicationContext(), AntrianBarberActivity2.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(MainActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loadingDialog.dismissLoadingDialog();
                Toast.makeText(MainActivity.this, "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        PrefManager prefManager = new PrefManager(this);
        boolean userId = prefManager.getSession();
        if (userId) {
            Intent intent = new Intent(getApplicationContext(), AntrianBarberActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}