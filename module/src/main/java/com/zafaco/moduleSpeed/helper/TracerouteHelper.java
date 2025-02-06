/*
 *     Copyright (C) 2016-2025 zafaco GmbH
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License version 3
 *     as published by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.zafaco.moduleSpeed.helper;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.zafaco.moduleCommon.Log;
import com.zafaco.moduleCommon.interfaces.GenericInterface;
import com.zafaco.moduleSpeed.models.measurement.MeasurementResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public class TracerouteHelper
{

    private static final String                     TAG       = "TRACEROUTE_HELPER";

    private interface TracerouteApi
    {
        @POST("/")
        Call<ResponseBody> postTraceroute(@Header("Origin") String origin, @Body JsonObject jsonObject);
    }


    public void startServerTracerouteTest(String host, boolean tls, GenericInterface genericInterface)
    {
        Log.debug(TAG, "startServerTracerouteTest");
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().callTimeout(15, TimeUnit.SECONDS).build();

        String url = ((tls) ? "https://" : "http://") + host + ":" + ((tls) ? "8443" : "8080");


        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())).client(okHttpClient).build();

        TracerouteApi tracerouteApi = retrofit.create(TracerouteApi.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cmd", "traceroute");

        Call<ResponseBody> call = tracerouteApi.postTraceroute("", jsonObject);

        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response)
            {
                if (!response.isSuccessful())
                {
                    HashMap<String, Object> extras = new HashMap<>();
                    extras.put("request_url", call.request().url());
                    extras.put("request_method", call.request().method());
                    extras.put("request_headers", call.request().headers().toString());
                    extras.put("request_body", jsonObject.toString());
                    extras.put("response_code", response.code());
                    Log.info(TAG, "traceroute: request failed", extras);
                } else
                {
                    try
                    {
                        genericInterface.reportCallback(new JSONObject(response.body().string()));
                    } catch (JSONException | NullPointerException | IOException e)
                    {
                        Log.warning(TAG, "traceroute failed", e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t)
            {
                HashMap<String, Object> extras = new HashMap<>();
                extras.put("request_url", call.request().url());
                extras.put("request_method", call.request().method());
                extras.put("request_headers", call.request().headers().toString());
                extras.put("request_body", jsonObject.toString());
                Log.info(TAG, "traceroute: request failed", extras);
            }
        });


    }
}
