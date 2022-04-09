package kit.trafficscotland;

import android.app.AlertDialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Traffic> {
    private Context context;
    private List<Traffic> allTraffic;

    ListAdapter (@NonNull Context c, @NonNull ArrayList<Traffic> allTraffic) {
        super(c, R.layout.activity_listview, allTraffic);
        this.context = c;
        this.allTraffic = allTraffic;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.activity_listview, parent, false);

        TextView currentTitle = row.findViewById(R.id.rawDataDisplay);
        TextView date = row.findViewById(R.id.dates);
        Button showMore = row.findViewById(R.id.showMore);



        currentTitle.setText(allTraffic.get(position).getTitle());
//        Log.i("TitlePosition", allTraffic.get(position).getTitle());
        date.setText(allTraffic.get(position).getStartDate() + " - " + allTraffic.get(position).getEndDate());











        showMore.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            builder.setMessage("Link: " + allTraffic.get(position).getLink() + " \n" + "Publish Date: " + allTraffic.get(position).getPubDate());
            builder.setCancelable(true);
            AlertDialog alert = builder.create();
            alert.show();

        });


        return row;

    }


}
