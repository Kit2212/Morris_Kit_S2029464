package kit.trafficscotland;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

public class TrafficParser {
    private Traffic traffic;
    private ArrayList<Traffic> listOfTraffic = new ArrayList<>();

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
                            String startDate = description.substring(firstComma, firstDash).trim();
                            traffic.setStartDate(startDate);

                            //Second description date
                            String secondDesc = description.substring(firstDash + 1);
                            int lastComma = secondDesc.indexOf(",") +2;
                            int lastDash = secondDesc.indexOf("-");
                            String endDate = secondDesc.substring(lastComma, lastDash).trim();
                            traffic.setEndDate(endDate);



                            Log.i("endDate", startDate + "," + endDate);
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