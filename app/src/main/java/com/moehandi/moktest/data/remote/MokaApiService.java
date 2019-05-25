package com.moehandi.moktest.data.remote;

import java.util.List;

import io.reactivex.Observable;
import com.moehandi.moktest.data.remote.model.Item;
import retrofit2.http.GET;

/**
 * Created by moehandi on 22/5/19.
 */

public interface MokaApiService {

    @GET("/photos")
    Observable<List<Item>> getAllItems();

}
