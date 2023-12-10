package com.SafiaAmitaJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.SafiaAmitaJBusBR.jbus_android.model.BaseResponse;
import com.SafiaAmitaJBusBR.jbus_android.model.Bus;
import com.SafiaAmitaJBusBR.jbus_android.model.Schedule;
import com.SafiaAmitaJBusBR.jbus_android.request.BaseApiService;
import com.SafiaAmitaJBusBR.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddScheduleActivity extends AppCompatActivity {
    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 10;
    private int listSize;
    private int noOfPages;
    private List<Schedule> listSchedules = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;
    private TextView busName, capacity, price, departure, arrival, facilities, busType;
    private EditText schedAdd;
    private String busNameS, capacityS, priceS, departureS, arrivalS, facilitiesS, busTypeS;
    private BaseApiService mApiService;
    private Context mContext;
    private String selectedTime;
    private String selectedDate;
    private TextView txtDate, txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_add_schedule);
        mContext = this;
        mApiService = UtilsApi.getApiService();

        busName = findViewById(R.id.name_bus);
        departure = findViewById(R.id.departure_dropdown);
        arrival = findViewById(R.id.arrival_dropdown);
        prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);
        busListView = findViewById(R.id.list_schedules);
        listSchedules = AccountBusArrayAdapter.selectedBus.schedules;
        listSize = listSchedules.size();
        paginationFooter();
        goToPage(currentPage);

        busNameS = AccountBusArrayAdapter.selectedBus.name;
        Integer cap = Integer.valueOf(AccountBusArrayAdapter.selectedBus.capacity);
        departureS = AccountBusArrayAdapter.selectedBus.departure.city.toString();
        arrivalS = AccountBusArrayAdapter.selectedBus.arrival.city.toString();

        busName.setText(busNameS);
        departure.setText(departureS);
        arrival.setText(arrivalS);

        txtDate=(TextView) findViewById(R.id.in_date);
        txtTime=(TextView)findViewById(R.id.in_time);


        Button addSched = findViewById(R.id.add_schedule_button);
        addSched.setOnClickListener(v->{
            openDatePickerDialog();
        });

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v->{
            finish();
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v->{
            handleAddSchedule();
        });

        prevButton.setOnClickListener(v -> {
            currentPage = currentPage != 0? currentPage-1 : 0;
            goToPage(currentPage);
        });

        nextButton.setOnClickListener(v -> {
            currentPage = currentPage != noOfPages -1? currentPage+1 : currentPage;
            goToPage(currentPage);
        });
    }

    private void paginationFooter() {
        int val = listSize % pageSize;
        val = val == 0 ? 0:1;
        noOfPages = listSize / pageSize + val;
        LinearLayout ll = findViewById(R.id.btn_layout);
        btns = new Button[noOfPages];
        if (noOfPages <= 6) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity =
                    Gravity.CENTER;
        }
        for (int i = 0; i < noOfPages; i++) {
            btns[i]=new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+(i+1));
            btns[i].setTextColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100,
                    100);
            ll.addView(btns[i], lp);
            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }

    private void goToPage(int index) {

        for (int i = 0; i< noOfPages; i++) {
            if (i == index) {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
                scrollToItem(btns[index]);
                viewPaginatedList(listSchedules, currentPage);

            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
            }
        }
    }

    private void scrollToItem(Button item) {
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) / 2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }
    private void viewPaginatedList(List<Schedule> listSchedules, int page) {
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listSchedules.size());
        List<Schedule> paginatedList = listSchedules.subList(startIndex, endIndex);
        SchedulesListAdapter paginatedAdapter = new SchedulesListAdapter(this, paginatedList);
        busListView = findViewById(R.id.list_schedules);
        busListView.setAdapter(paginatedAdapter);
    }

    public void openTimePickerDialog() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        // Handle the selected time (e.g., display it)
                        selectedTime = selectedHour + ":" + selectedMinute + ":00";
                        txtTime.setText(selectedHour + ":" + selectedMinute + ":00");
                    }
                }, hourOfDay, minute, true // 24-hour format
        );

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    public void openDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        // Handle the selected date
                        selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        txtDate.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                        openTimePickerDialog();
                    }
                }, year, month, dayOfMonth
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    protected void handleAddSchedule() {
        String timeS = selectedDate + " " + selectedTime;

        mApiService.addSchedule(AccountBusArrayAdapter.selectedBus.id, timeS).enqueue(new Callback<BaseResponse<Bus>>(){
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " +
                            response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Bus> res = response.body();
                if (res.success) {
                    AccountBusArrayAdapter.selectedBus = res.payload;
                    moveActivity(mContext, ManageBusActivity.class);
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}