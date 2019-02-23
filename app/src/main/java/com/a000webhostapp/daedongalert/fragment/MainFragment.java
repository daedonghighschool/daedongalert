package com.a000webhostapp.daedongalert.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.a000webhostapp.daedongalert.daedongalert.MealRequest;
import com.a000webhostapp.daedongalert.daedongalert.NoticeInflateActivity;
import com.a000webhostapp.daedongalert.daedongalert.R;
import com.a000webhostapp.daedongalert.daedongalert.noticeRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainFragment extends Fragment {
    final static private String TAG = MainFragment.class.getSimpleName();
    final static private String countryCode = "stu.gen.go.kr";
    final static private String schulCode = "F100000100";
    final static private String insttNm = "광주대동고등학교";
    final static private String schulCrseScCode = "4";
    final static private String schMmealScCode = "2";
    static private String schYmd = null;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.main_fragment, container, false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String getDate = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        Log.d(TAG, "현재 날짜 : "+getDate);
        schYmd = getDate;

        Response.Listener<String> responseListener_meal;
        responseListener_meal = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String mealString = response;
                Log.d(TAG, "Meal : "+mealString);
                String[] mealList = mealString.split(" ");

                TableLayout mealTable = (TableLayout) view.findViewById(R.id.mealTable);
                TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                TableRow existRow = (TableRow) view.findViewById(R.id.mealRow);
                ArrayList<TableRow> mealRow = new ArrayList<TableRow>();

                int cnt = 0;
                int Rowcnt = -1;
                for (String mealSplited : mealList) {
                    if(cnt%3==0) {
                        mealRow.add(new TableRow(getActivity()));
                        Rowcnt++;
                    }
                    TextView meals = textMake(mealSplited, Gravity.CENTER);
                    mealRow.get(Rowcnt).addView(meals);
                    cnt++;
                }
                while (!(cnt%3==0)) {
                    TextView meals = textMake(" ", Gravity.CENTER);
                    mealRow.get(Rowcnt).addView(meals);
                    cnt++;
                }
                for(TableRow a : mealRow) {
                    mealTable.addView(a);
                }
                mealTable.removeView(existRow);
                Log.d(TAG, "Meal count : "+cnt);
            }
        };
        Log.d(TAG, "Meal queued!");
        MealRequest mealRequest = new MealRequest(countryCode, schulCode, insttNm, schulCrseScCode,
                schMmealScCode, schYmd, responseListener_meal);
        RequestQueue queue_meal = Volley.newRequestQueue(getActivity());
        queue_meal.add(mealRequest);

        Response.Listener<String> responseListener_notice;
        responseListener_notice = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Notice : "+response);

                String[] listLink = response.split("LINK_BEGIN");
                final String[] noticeList = listLink[0].split("SEPARATE");
                final String[] linkList = listLink[1].split("SEPARATE");

                TableLayout noticeTable = (TableLayout) view.findViewById(R.id.noticeTable);
                TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                TableRow existRow = (TableRow) view.findViewById(R.id.noticeRow);

                for(int i=0 ; i<noticeList.length ; i++) {
                    final int final_i = i;
                    if(i>4) {
                        break;
                    }
                    TableRow noticeRow = new TableRow(getActivity());
                    TextView noticeText = textMake(noticeList[i], Gravity.LEFT);
                    noticeText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), NoticeInflateActivity.class);
                            intent.putExtra("link", linkList[final_i]);
                            intent.putExtra("subject", noticeList[final_i]);
                            startActivity(intent);
                        }
                    });
                    noticeRow.addView(noticeText);
                    noticeTable.addView(noticeRow);
                }
                noticeTable.removeView(existRow);
            }
        };
        Log.d(TAG, "Notice queued!");
        noticeRequest noticeRequest = new noticeRequest(responseListener_notice);
        RequestQueue queue_notice = Volley.newRequestQueue(getActivity());
        queue_notice.add(noticeRequest);

        return view;
    }

    TextView textMake (String set_text, int gravity) {
        TableRow.LayoutParams textParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TextView meals = new TextView(getActivity());
        meals.setText(set_text);
        meals.setLayoutParams(textParams);
        meals.setGravity(gravity);
        meals.setTextColor(Color.parseColor("#000000"));
        meals.setBackgroundResource(R.drawable.cell_shape);
        meals.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        return meals;
    }

}
