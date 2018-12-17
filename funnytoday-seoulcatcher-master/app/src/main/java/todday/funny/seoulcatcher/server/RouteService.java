package todday.funny.seoulcatcher.server;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import todday.funny.seoulcatcher.model.messageModel.Message;
import todday.funny.seoulcatcher.model.messageModel.SendCallData;
import todday.funny.seoulcatcher.model.routeModel.Route;
import todday.funny.seoulcatcher.util.Keys;

public interface RouteService {

    //FCM 보내기
    @GET("routes/pedestrian")
    Observable<Route> route(
            @Header("appKey") String appKey,
            @Query("startX") String startX,
            @Query("startY") String startY,
            @Query("endX") String endX,
            @Query("endY") String endY,
            @Query("startName") String startName,
            @Query("endName") String endName

    );

    class Creator {
        public static RouteService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Keys.TMAP_URL_API)
                    .build();
            return retrofit.create(RouteService.class);
        }
    }

}
