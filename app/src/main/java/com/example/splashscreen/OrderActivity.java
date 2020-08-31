package com.example.splashscreen;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.splashscreen.utils.AppReceiver;
import com.example.splashscreen.utils.LoadingDialog;
import com.example.splashscreen.utils.PrefManager;
import com.example.splashscreen.utils.apihelpers.ApiInterface;
import com.example.splashscreen.utils.apihelpers.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    static final long ONE_MINUTE_IN_MILLIS=60000;
    private PendingIntent pendingIntent;
    private static  int ALARM_REQUEST_CODE=134;
    private int interval_seconds = 10;
    private int NOTIFICATION_ID = 1;

    @BindView(R.id.toolbarOrder)
    Toolbar toolbarOrder;
    @BindView(R.id.imgOrder)
    ImageView imgOrder;
    @BindView(R.id.order_hair_name)
    TextView orderHairName;
    @BindView(R.id.order_hair_price)
    TextView orderHairPrice;
    @BindView(R.id.order_hair_keterangan)
    TextView orderHairKeterangan;
    @BindView(R.id.txtFormDate)
    EditText txtFormDate;
    @BindView(R.id.txtFormTime)
    EditText txtFormTime;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnOrder)
    Button btnOrder;

    Calendar calendar, calender1;
    DatePickerDialog dialog;
    Time time;
    TimePickerDialog dialog1;

    ApiInterface apiInterface;
    PrefManager prefManager;
    LoadingDialog loadingDialog;
    String kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarOrder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        kode = intent.getStringExtra("KODE");
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(this);
        loadingDialog = new LoadingDialog(this);

        txtFormDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int month = calendar.get(Calendar.MONTH);
                final int year = calendar.get(Calendar.YEAR);

                dialog = new DatePickerDialog(OrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String bulan = "" + i1;
                        String tgl = "" + i2;
                        if (i1 < 10) {
                            bulan = "0" + (i1 + 1);
                        }
                        if (i2 < 10) {
                            tgl = "0" + i2;
                        }
                        txtFormDate.setText(i + "-" + bulan + "-" + tgl);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
        txtFormTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calender1 = Calendar.getInstance();
                final int hour = calender1.get(Calendar.HOUR_OF_DAY);
                final int minute = calender1.get(Calendar.MINUTE);

                dialog1 = new TimePickerDialog(OrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String jam = i + "";
                        String menit = i1 + "";
                        if (i < 10) {
                            jam = "0" + i;
                        }
                        if (i1 < 10) {
                            menit = "0" + i1;
                        }
                        txtFormTime.setText(jam + ":" + menit + ":00");
                    }
                }, hour, minute, DateFormat.is24HourFormat(getApplicationContext()));
                dialog1.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fetchDataService();
    }

    private void fetchDataService() {
        loadingDialog.startLoadingDialog();
        apiInterface.getServiceID(kode).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        loadingDialog.dismissLoadingDialog();
                        if (jsonObject.getString("status").equals("200")) {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                            orderHairName.setText(jsonObject1.getString("nama_service"));
                            orderHairPrice.setText(jsonObject1.getString("harga"));
                            orderHairKeterangan.setText(jsonObject1.getString("keterangan"));
                            Glide
                                    .with(getApplicationContext())
                                    .load(UtilsApi.BASE_URL+jsonObject1.getString("gambar")+"")
                                    .centerCrop()
                                    .into(imgOrder);

                            btnOrder.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (txtFormTime.getText().toString().isEmpty() || txtFormDate.getText().toString().isEmpty()){
                                        Toast.makeText(OrderActivity.this, "Field Cant Be Blank", Toast.LENGTH_SHORT).show();
                                    }else{
                                        fetchBooking();
                                    }

                                }
                            });
                        } else {
                            Toast.makeText(OrderActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(OrderActivity.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissLoadingDialog();
            }
        });
    }
    private void setAlarmForBooking(){
        String[] txtWaktuTanggal = txtFormDate.getText().toString().split("-");
        String[] txtWaktuJam = txtFormTime.getText().toString().split(":");

        Calendar cal =Calendar.getInstance();
        cal.set(Integer.parseInt(txtWaktuTanggal[0]),
                Integer.parseInt(txtWaktuTanggal[1]),
                Integer.parseInt(txtWaktuTanggal[2]),
                Integer.parseInt(txtWaktuJam[0]),
                Integer.parseInt(txtWaktuJam[1]),
                Integer.parseInt(txtWaktuJam[2])
                );

        SimpleDateFormat df =new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        long t =cal.getTimeInMillis();
        Date afterRemoveTenMins=new Date(t - (30 * ONE_MINUTE_IN_MILLIS));
        prefManager.setAlarm("alarm", df.format(afterRemoveTenMins));
        Intent alarmIntent = new Intent(this, AppReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, alarmIntent, 0);
        startAlarmManager();
        //Toast.makeText(this, prefManager.getAlarm(), Toast.LENGTH_SHORT).show();

    }
    public void startAlarmManager(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, interval_seconds);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "AlarmManager Start.", Toast.LENGTH_SHORT).show();
    }
    private void fetchBooking() {
        loadingDialog.startLoadingDialog();
        apiInterface.bookingApi(kode,
                prefManager.getEmailr(),
                prefManager.getTokenUser(),
                txtFormDate.getText().toString() + " " + txtFormTime.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        loadingDialog.dismissLoadingDialog();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("200")){
                            Toast.makeText(OrderActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AntrianBarberActivity2.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            setAlarmForBooking();
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(OrderActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(OrderActivity.this, "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}