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

package com.zafaco.moduleSpeed.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zafaco.moduleCommon.Log;
import com.zafaco.moduleCommon.interfaces.BaseInterface;
import com.zafaco.moduleSpeed.BuildConfig;
import com.zafaco.moduleSpeed.R;
import com.zafaco.moduleSpeed.helper.SpeedHelper;
import com.zafaco.moduleSpeed.models.speed.SpeedTaskDesc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimeZone;

public class Speed
{
    private static final String            TAG                = "Speed";
    private final        Context           ctx;
    private              SpeedTaskDesc     speedTaskDesc;
    private final        SharedPreferences sharedPreferences;
    private static       Speed             INSTANCE;
    private              boolean           continuousLocation = true;
    private              boolean           continuousNetwork  = true;
    private              boolean           performTrace       = true;
    private              String            appVersion;
    private              String            gitHash;

    private final SpeedHelper mSpeedHelper;

    private Speed(Context context, @Nullable String appVersion, @Nullable String gitHash)
    {
        this.ctx          = context.getApplicationContext();
        sharedPreferences = ctx.getSharedPreferences(ctx.getString(R.string.preference_file), Context.MODE_PRIVATE);

        if (appVersion != null)
            setAppVersion(appVersion);
        else
        {
            try
            {
                setAppVersion(ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName);
            } catch (PackageManager.NameNotFoundException e)
            {
                Log.error(TAG, e);
            }
        }

        if (gitHash != null)
            setGitHash(gitHash);

        setCallingApplicationId(ctx.getPackageName());

        mSpeedHelper = new SpeedHelper(ctx);
        setSpeedMeasurementConfig();
    }


    public static Speed getInstance(Context ctx)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Speed(ctx, null, null);
        }

        return INSTANCE;
    }


    public static Speed getInstance(Context ctx, String appVersion, String gitHash)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Speed(ctx, appVersion, gitHash);
        }

        return INSTANCE;
    }


    public void setDebugMode(boolean debug)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ctx.getString(R.string.debug), debug);
        editor.apply();
    }


    private void setAppVersion(String appVersion)
    {
        this.appVersion = appVersion;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ctx.getString(R.string.appVersion), appVersion);
        editor.apply();
    }

    public String getGitHash()
    {
        return gitHash;
    }

    public void setGitHash(String gitHash)
    {
        this.gitHash = gitHash;
    }


    private void setCallingApplicationId(String applicationId)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ctx.getString(R.string.applicationId), applicationId);
        editor.apply();
    }

    public JSONObject getMetaData()
    {
        JSONObject jsonObject = new JSONObject();

        try
        {
            jsonObject.put("osName", "Android");
            jsonObject.put("osVersion", Build.VERSION.SDK_INT);
            jsonObject.put("timezone", TimeZone.getDefault().getRawOffset() / 1000);
            jsonObject.put("deviceType", "mobile");
            jsonObject.put("deviceManufacturer", Build.MANUFACTURER);
            jsonObject.put("deviceModel", Build.MODEL);
            jsonObject.put("speedVersion", BuildConfig.VERSION_NAME);
            jsonObject.put("commonVersion", com.zafaco.moduleCommon.BuildConfig.VERSION_NAME);
        } catch (JSONException ignored)
        {
        }

        return jsonObject;
    }


    private void setSpeedMeasurementConfig()
    {
        setSpeedMeasurementConfig(new String[]{"example.com"}, 443, 80, true, 4, 4);
    }

    public void setSpeedMeasurementConfig(@NonNull JSONArray servers, int speedPort, int rttPort, boolean encryption, int downloadStreams, int uploadStreams)
    {
        String[] serversArray = new String[servers.length()];
        for (int i = 0; i < servers.length(); i++)
        {
            serversArray[i] = servers.optString(i);
        }
        setSpeedMeasurementConfig(serversArray, speedPort, rttPort, encryption, downloadStreams, uploadStreams);
    }

    public void setSpeedMeasurementConfig(String[] servers, int speedPort, int rttPort, boolean encryption, int downloadStreams, int uploadStreams)
    {
        speedTaskDesc = new SpeedTaskDesc();
        speedTaskDesc.setSpeedServerAddrs(servers);
        speedTaskDesc.setUseEncryption(encryption);
        speedTaskDesc.setSpeedServerPort(speedPort);
        speedTaskDesc.setSpeedServerPortRtt(rttPort);
        speedTaskDesc.setDownloadStreams(downloadStreams);
        speedTaskDesc.setUploadStreams(uploadStreams);
    }


    public void setPerformDownload(boolean enabled)
    {
        speedTaskDesc.setPerformDownload(enabled);
    }

    public void setPerformUpload(boolean enabled)
    {
        speedTaskDesc.setPerformUpload(enabled);
    }

    public void setPerformRttUdp(boolean enabled)
    {
        speedTaskDesc.setPerformRtt(enabled);
    }

    public boolean isMeasurementRunning()
    {
        return mSpeedHelper.isSpeedMeasurementRunning();
    }

    public boolean isContinuousLocation()
    {
        return continuousLocation;
    }

    public void setContinuousLocation(boolean continuousLocation)
    {
        this.continuousLocation = continuousLocation;
    }

    public boolean isContinuousNetwork()
    {
        return continuousNetwork;
    }

    public void setContinuousNetwork(boolean continuousNetwork)
    {
        this.continuousNetwork = continuousNetwork;
    }

    public boolean isPerformTrace()
    {
        return performTrace;
    }

    public void setPerformTrace(boolean performTrace)
    {
        this.performTrace = performTrace;
    }

    public void startMeasurement(BaseInterface genericInterface)
    {
        mSpeedHelper.startSpeedMeasurement(genericInterface, speedTaskDesc, continuousLocation, continuousNetwork, appVersion, gitHash, performTrace);
    }

    public void stopMeasurement()
    {
        mSpeedHelper.stopSpeedMeasurement();
    }
}
