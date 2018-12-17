package todday.funny.seoulcatcher.util;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.places.Place;

import todday.funny.seoulcatcher.model.History;
import todday.funny.seoulcatcher.model.PlaceData;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.model.User;

public class SendBroadcast {
    public static void path(Context context, String action, String path) {
        Intent send = new Intent(action);
        send.putExtra(Keys.PATH, path);
        context.sendBroadcast(send);
    }

    public static void user(Context context, String action, User user) {
        Intent send = new Intent(action);
        send.putExtra(Keys.USER, user);
        context.sendBroadcast(send);
    }

    public static void schedule(Context context, String action, Schedule schedule) {
        Intent send = new Intent(action);
        send.putExtra(Keys.SCHEDULE, schedule);
        context.sendBroadcast(send);
    }

    public static void history(Context context, String action, History history) {
        Intent send = new Intent(action);
        send.putExtra(Keys.HISTORY, history);
        context.sendBroadcast(send);
    }

    public static void move(Context context, String action) {
        Intent send = new Intent(action);
        context.sendBroadcast(send);
    }

    public static void place(Context context, String action, Place place) {
        if (place != null) {
            String name = NullFilter.check(place.getName().toString());
            String address = "";
            if (place.getAddress() != null) {
                address = place.getAddress().toString();
            }
            PlaceData placeData = new PlaceData(name, address, place.getLatLng());
            Intent send = new Intent(action);
            send.putExtra(Keys.PLACE, placeData);
            context.sendBroadcast(send);
        }
    }
}
