//Kit Morris
//S2029464

package kit.trafficscotland;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListAdapter extends ArrayAdapter<Traffic> {
    private Context context;
    private List<Traffic> allTraffic;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    ListAdapter(@NonNull Context c, @NonNull ArrayList<Traffic> allTraffic) {
        super(c, R.layout.activity_listview, allTraffic);
        this.context = c;
        this.allTraffic = allTraffic;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.activity_listview, parent, false);

        TextView currentTitle = row.findViewById(R.id.rawDataDisplay);
        TextView date = row.findViewById(R.id.dates);
        Button showMore = row.findViewById(R.id.showMore);
        TextView duration = row.findViewById(R.id.duration);

        currentTitle.setText(allTraffic.get(position).getTitle());
//        Log.i("TitlePosition", allTraffic.get(position).getTitle());
        String startDate = formatter.format(allTraffic.get(position).getStartDate());
        String endDate = formatter.format(allTraffic.get(position).getEndDate());
        date.setText("Dates: " + startDate + " - " + endDate);


        //Setting duration and colo(u)r
        long timeBetween = dateCompare(allTraffic.get(position).getStartDate(), allTraffic.get(position).getEndDate());
        if (timeBetween == 1) {
            duration.setTextColor(Color.GREEN);
            duration.setText("Duration: " + timeBetween + " Day");
        } else if (timeBetween <= 10) {
            duration.setTextColor(Color.GREEN);
            duration.setText("Duration: " + timeBetween + " Days");
        } else if (timeBetween > 10 && timeBetween < 20) {
            duration.setTextColor(Color.MAGENTA);
            duration.setText("Duration: " + timeBetween + " Days");
        } else if (timeBetween > 20) {
            duration.setTextColor(Color.RED);
            duration.setText("Duration: " + timeBetween + " Days");
        }


        showMore.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            builder.setMessage("Link: " + allTraffic.get(position).getLink() + " \n" + "Publish Date: " + allTraffic.get(position).getPubDate());
            builder.setCancelable(true);
            AlertDialog alert = builder.create();
            alert.show();

        });


        return row;

    }

    public long dateCompare(Date start, Date end) {
        long differenceBetween = Math.abs(end.getTime() - start.getTime());
        long finalTime = TimeUnit.DAYS.convert(differenceBetween, TimeUnit.MILLISECONDS);
        if (finalTime == 0) {
            finalTime = 1;
        }
        return finalTime;
    }


}
