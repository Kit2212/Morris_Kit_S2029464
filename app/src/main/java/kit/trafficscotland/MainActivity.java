//Kit Morris
//S2029464

package kit.trafficscotland;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements
        OnClickListener {
    private ArrayList<Traffic> trafficList = new ArrayList<>();
    private Traffic traffic;
    private TrafficParser trafficParser = new TrafficParser();
    private ArrayList<Traffic> searchList = new ArrayList<>();
    private DatePickerDialog datePickerDialog;
    private ArrayList<Traffic> tempList = new ArrayList<>();

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String dateinString;
    ListView simpleList;
    EditText editText;
    EditText editTextDate;
    Date date;
    private Button searchButton;
    private Button showMore;
    private TextView rawDataDisplay;
    private Button startButton;
    private String result = "";
    private String url1 = "";
    private String
            urlSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String
            urlSource2 = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String
            urlSource3 = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = findViewById(R.id.simpleListView);
        editText = findViewById(R.id.editTextSimple);
        editTextDate = findViewById(R.id.editTextDate);
        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView) findViewById(R.id.rawDataDisplay);
        getTrafficList();

        searchButton = findViewById(R.id.searchButton);
        editTextDate.setOnClickListener(view -> {
            searchList.clear();
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            editTextDate.setText(i2 + "/" + (i1 + 1) + "/" + i);
                        }
                    }, year, month, day);

            datePickerDialog.show();
        });

        searchButton.setOnClickListener(view -> {
            searchList.clear();
            try {
                dateinString = editTextDate.getText().toString();
                date = simpleDateFormat.parse(dateinString);
                Log.i("dateinstring", dateinString);
            }catch (ParseException e) {
                e.printStackTrace();
            }

            if(editText.getText().length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please enter a search term");
                builder.setCancelable(true);
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                String searchValue = editText.getText().toString();



                for (Traffic traffic: trafficList) {
                    boolean midDate = false;
                    if(traffic.getStartDate().before(date) && traffic.getEndDate().after(date)) {
                        midDate = true;
                    }
                    Log.i("traffic get start date", date.toString());
                    if(traffic.getTitle().toLowerCase().contains(searchValue.toLowerCase())) {
                        Log.i("inside first if", searchValue);
                        if(traffic.getStartDate().compareTo(date) == 0 || traffic.getEndDate().compareTo(date) ==0 || midDate == true) {
                            searchList.add(traffic);
                        }
                    }
                }
                ListAdapter searchButton = new ListAdapter(this, searchList);
                simpleList.setAdapter(searchButton);
            }
        });
    }

    public void getTrafficList(){
        executorService.execute(()->{
            //background stuff goes here
            URL aurl;
            URLConnection yc;
            try{
                aurl = new URL(urlSource);
                yc = aurl.openConnection();
                trafficList = trafficParser.trafficList(yc.getInputStream());
                for (Traffic traffic: trafficList) {
                    traffic.setType(Type.ROADWORKS);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            try{
                aurl = new URL(urlSource2);
                yc = aurl.openConnection();
                tempList.addAll(trafficParser.trafficList(yc.getInputStream()));
                for(Traffic traffic: tempList) {
                    traffic.setType(Type.PLANNEDROADWORKS);
                }
                trafficList.addAll(tempList);
                tempList.clear();


//                trafficList = trafficParser.trafficList(yc.getInputStream());
            }catch(IOException e){
                e.printStackTrace();
            }
            try{
                aurl = new URL(urlSource3);
                yc = aurl.openConnection();
                tempList.addAll(trafficParser.trafficList(yc.getInputStream()));
                for(Traffic traffic: tempList) {
                    traffic.setType(Type.CURRENTINCIDENTS);
                }
                trafficList.addAll(tempList);
//                trafficList = trafficParser.trafficList(yc.getInputStream());
            }catch(IOException e){
                e.printStackTrace();
            }
            handler.post(()->{
                //UI stuff here
                ListAdapter listAdapter = new ListAdapter(this, trafficList);
                simpleList.setAdapter(listAdapter);

            });
        });
    }

    @Override
    public void onClick(View view) {

    }
}