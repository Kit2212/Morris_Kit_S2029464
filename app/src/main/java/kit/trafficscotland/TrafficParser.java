package kit.trafficscotland;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrafficParser {
    private Traffic traffic;
    private ArrayList<Traffic> listOfTraffic = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
    SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");


    public ArrayList<Traffic> trafficList(InputStream inputStream) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream, null);
            int eventType = xpp.getEventType();
            boolean insideItem = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
//                Log.i("Inside End Document", "Also End document");
                if (eventType == XmlPullParser.START_TAG) {
//                    Log.i("Inside Start Tag", "Also inside Start Tag");
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        traffic = new Traffic();
                        insideItem = true;

                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem) {
                            traffic.setTitle(xpp.nextText());
//                            Log.i("Inside Title", "Also inside Title");
                        }
                    } else if (xpp.getName().equalsIgnoreCase("description")){
                        if (insideItem) {
                            //first description date
                            String description = xpp.nextText();
                            traffic.setDescription(description);
                            int firstComma = description.indexOf(",") + 2;
                            int firstDash = description.indexOf("-");
                            String startDateString = description.substring(firstComma, firstDash).trim();
                            Date startDate = formatter.parse(startDateString);
                            startDateString = formatter2.format(startDate);
                            startDate = formatter2.parse(startDateString);
                            traffic.setStartDate(startDate);

                            //Second description date
                            String secondDesc = description.substring(firstDash + 1);
                            int lastComma = secondDesc.indexOf(",") +2;
                            int lastDash = secondDesc.indexOf("-");
                            String endDateString = secondDesc.substring(lastComma, lastDash).trim();
                            Date endDate = formatter.parse(endDateString);
                            endDateString = formatter2.format(endDate);
                            endDate = formatter2.parse(endDateString);
                            traffic.setEndDate(endDate);



//                            Log.i("endDate", startDate + "," + endDate);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        if(insideItem) {
                            traffic.setLink(xpp.nextText());
                        }
                    } else if (xpp.getName().equalsIgnoreCase("point")){
                        if(insideItem) {
                            traffic.setPoint(xpp.nextText());
                        }
                    } else if (xpp.getName().equalsIgnoreCase("pubDate")){
                        if(insideItem) {
                            traffic.setPubDate(xpp.nextText());
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                    listOfTraffic.add(traffic);
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {

        }
        return listOfTraffic;
    }
}