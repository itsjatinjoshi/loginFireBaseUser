package com.example.myapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static java.util.Calendar.DAY_OF_WEEK;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {


    TextView tvWelcome, currentWeatherDay, minTemp,feelsLike, maxTemp, weatherState, currentTemp, humidity, predictability,

    ivDay1Max, ivDay2Max, ivDay3Max, ivDay4Max, ivDay5Max,
            ivDay1Min, ivDay2Min, ivDay3Min, ivDay4Min, ivDay5Min,

    tvDay1, tvDay2, tvDay3, tvDay4, tvDay5,

    lastLogin;


    Button btnLogout;
    ImageView weatherImage, ivDay1, ivDay2, ivDay3, ivDay4, ivDay5;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth auth;
    JSONArray wArray;

    String wAPI, presentDay, currentTime, day1, day2, day3, day4, day5, feelLike;


    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);

        calendar.add(DAY_OF_WEEK, 1);

        SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
        currentTime = sdf.format(new Date());
        presentDay = sdf1.format(now);


        day1 = calendar.getTime().toString().substring(0, 3) + ", " + calendar.getTime().toString().substring(4, 10);
        calendar.add(DAY_OF_WEEK, 1);

        day2 = calendar.getTime().toString().substring(0, 3) + ", " + calendar.getTime().toString().substring(4, 10);
        calendar.add(DAY_OF_WEEK, 1);

        day3 = calendar.getTime().toString().substring(0, 3) + ", " + calendar.getTime().toString().substring(4, 10);
        calendar.add(DAY_OF_WEEK, 1);

        day4 = calendar.getTime().toString().substring(0, 3) + ", " + calendar.getTime().toString().substring(4, 10);
        calendar.add(DAY_OF_WEEK, 1);

        day5 = calendar.getTime().toString().substring(0, 3) + ", " + calendar.getTime().toString().substring(4, 10);

        // System.out.println("Tomorrow: " + calendar.getTime().toString().substring(0, 3) + ", " + calendar.getTime().toString().substring(4, 10));

        wAPI = "https://www.metaweather.com/api/location/3534/";
        String myjson = null;
        try {
            myjson = new syncdata().execute(wAPI).get();
            //  Montreal m=new Montreal(myjson);
            JSONObject montreal = new JSONObject(myjson);
            Weather montrealWeather = new Weather();

            // montrealWeather.setConsolidatedWeather(getJSONArray("consolidated_weather"));
            wArray = montreal.getJSONArray("consolidated_weather");
            System.out.println("JSON object " + montreal.getJSONArray("consolidated_weather"));

        } catch (ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //  auth = FirebaseAuth.getInstance().lastSignInDate;
        user = getArguments().getParcelable("user");
        db = FirebaseFirestore.getInstance();
    }

    public void readFireStore() {
        DocumentReference documentReference = db.collection("users").document(user.getUid());

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        System.out.println(snapshot.getData());
                        tvWelcome.setText("Welcome " + snapshot.get("Name") + " !!");
                    }

                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        readFireStore();

        tvWelcome = view.findViewById(R.id.tvWelcome);
        currentWeatherDay = view.findViewById(R.id.currentWeatherDay);
        minTemp = view.findViewById(R.id.minTemp);
        maxTemp = view.findViewById(R.id.maxTemp);
        weatherState= view.findViewById(R.id.weatherState);
        currentTemp = view.findViewById(R.id.currentTemp);
        humidity = view.findViewById(R.id.humidity);
        predictability = view.findViewById(R.id.predictability);
        feelsLike = view.findViewById(R.id.feelsLike);

        weatherImage = view.findViewById(R.id.weatherImage);
        ivDay1 = view.findViewById(R.id.ivDay1);
        ivDay2 = view.findViewById(R.id.ivDay2);
        ivDay3 = view.findViewById(R.id.ivDay3);
        ivDay4 = view.findViewById(R.id.ivDay4);
        ivDay5 = view.findViewById(R.id.ivDay5);

        ivDay1Max = view.findViewById(R.id.ivDay1Max);
        ivDay2Max = view.findViewById(R.id.ivDay2Max);
        ivDay3Max = view.findViewById(R.id.ivDay3Max);
        ivDay4Max = view.findViewById(R.id.ivDay4Max);
        ivDay5Max = view.findViewById(R.id.ivDay5Max);

        ivDay1Min = view.findViewById(R.id.ivDay1Min);
        ivDay2Min = view.findViewById(R.id.ivDay2Min);
        ivDay3Min = view.findViewById(R.id.ivDay3Min);
        ivDay4Min = view.findViewById(R.id.ivDay4Min);
        ivDay5Min = view.findViewById(R.id.ivDay5Min);

        tvDay1 = view.findViewById(R.id.tvDay1);
        tvDay2 = view.findViewById(R.id.tvDay2);
        tvDay3 = view.findViewById(R.id.tvDay3);
        tvDay4 = view.findViewById(R.id.tvDay4);
        tvDay5 = view.findViewById(R.id.tvDay5);

      //  lastLogin = view.findViewById(R.id.lastLogin);

        try {

            Glide.with(getContext()).asBitmap().load("https://www.metaweather.com/static/img/weather/png/" + wArray.getJSONObject(0).getString("weather_state_abbr") + ".png").into(weatherImage);
            currentWeatherDay.setText(presentDay + " ," + currentTime);

            weatherState.setText(wArray.getJSONObject(0).getString("weather_state_name"));
            currentTemp.setText(wArray.getJSONObject(0).getString("the_temp").substring(0, 4) + "°");

            feelsLike.setText("Feel Like: " +wArray.getJSONObject(0).getString("the_temp").substring(0, 4) );
            //feelLike.setText( "Feels Like " + feelsLike);

            minTemp.setText(wArray.getJSONObject(0).getString("min_temp").substring(0, 4) + "°");
            maxTemp.setText(wArray.getJSONObject(0).getString("max_temp").substring(0, 4) + "°");
            humidity.setText(wArray.getJSONObject(0).getString("humidity") + "%");
            predictability.setText(wArray.getJSONObject(0).getString("predictability") + "%");

            Glide.with(getContext()).asBitmap().load("https://www.metaweather.com/static/img/weather/png/" + wArray.getJSONObject(1).getString("weather_state_abbr") + ".png").into(ivDay1);
            tvDay1.setText(day1);
            ivDay1Min.setText(wArray.getJSONObject(1).getString("min_temp").substring(0, 4) + "°");
            ivDay1Max.setText(wArray.getJSONObject(1).getString("max_temp").substring(0, 4) + "°");


            Glide.with(getContext()).asBitmap().load("https://www.metaweather.com/static/img/weather/png/" + wArray.getJSONObject(2).getString("weather_state_abbr") + ".png").into(ivDay2);
            tvDay2.setText(day2);
            ivDay2Min.setText(wArray.getJSONObject(2).getString("min_temp").substring(0, 4) + "°");
            ivDay2Max.setText(wArray.getJSONObject(2).getString("max_temp").substring(0, 4) + "°");


            Glide.with(getContext()).asBitmap().load("https://www.metaweather.com/static/img/weather/png/" + wArray.getJSONObject(3).getString("weather_state_abbr") + ".png").into(ivDay3);
            tvDay3.setText(day3);
            ivDay3Min.setText(wArray.getJSONObject(3).getString("min_temp").substring(0, 4) + "°");
            ivDay3Max.setText(wArray.getJSONObject(3).getString("max_temp").substring(0, 4) + "°");

            Glide.with(getContext()).asBitmap().load("https://www.metaweather.com/static/img/weather/png/" + wArray.getJSONObject(4).getString("weather_state_abbr") + ".png").into(ivDay4);
            tvDay4.setText(day4);
            ivDay4Min.setText(wArray.getJSONObject(4).getString("min_temp").substring(0, 4) + "°");
            ivDay4Max.setText(wArray.getJSONObject(4).getString("max_temp").substring(0, 4) + "°");

            Glide.with(getContext()).asBitmap().load("https://www.metaweather.com/static/img/weather/png/" + wArray.getJSONObject(5).getString("weather_state_abbr") + ".png").into(ivDay5);
            tvDay5.setText(day5);
            ivDay5Min.setText(wArray.getJSONObject(5).getString("min_temp").substring(0, 4) + "°");
            ivDay5Max.setText(wArray.getJSONObject(5).getString("max_temp").substring(0, 4) + "°");




        } catch (JSONException e) {
            e.printStackTrace();
        }


        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                NavController navController = Navigation.findNavController(getActivity(), R.id.fragment);
                navController.navigate(R.id.loginFragment);
            }
        });

    }

}
