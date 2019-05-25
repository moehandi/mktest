package com.moehandi.moktest.data.remote;

/**
 * Created by moehandi on 22/5/19.
 */

public interface ResponseListener<T> {
    /**
     * Fired when request is successful.
     *
     * @param response result.
     */
    void onResponse(T response);

    /**
     * Fired when request is failed.
     *
     * @param errorMessage error message or null.
     */
    void onError(String errorMessage);
}
