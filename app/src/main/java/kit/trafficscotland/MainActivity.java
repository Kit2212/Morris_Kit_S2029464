package kit.trafficscotland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements
        OnClickListener {
    private ArrayList<Traffic> trafficList = new ArrayList<>();
    private Traffic traffic;
    private TrafficParser trafficParser = new TrafficParser();




    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    ListView simpleList;
    private Button showMore;
    private TextView rawDataDisplay;
    private Button startButton;
    private String result = "";
    private String url1 = "";
    private String
            urlSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = findViewById(R.id.simpleListView);
        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView) findViewById(R.id.rawDataDisplay);
        getTrafficList();
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
//                Log.i("Try", trafficList.toString());
            }catch(IOException e){
                e.printStackTrace();

            }


            handler.post(()->{
                //UI stuff here
//                Log.i("trafficList",  trafficList.toString());
                ListAdapter listAdapter = new ListAdapter(this, trafficList);
//                Log.i("ListAdapterToString", listAdapter.toString());
                simpleList.setAdapter(listAdapter);








            });
        });
    }

    @Override
    public void onClick(View view) {

    }
}