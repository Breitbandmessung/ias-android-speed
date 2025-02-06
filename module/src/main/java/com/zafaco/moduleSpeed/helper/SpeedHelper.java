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

import android.content.Context;

import com.zafaco.moduleCommon.Log;
import com.zafaco.moduleCommon.interfaces.BaseInterface;
import com.zafaco.moduleSpeed.jni.JniSpeedMeasurementClient;
import com.zafaco.moduleSpeed.jni.exception.AndroidJniCppException;
import com.zafaco.moduleSpeed.models.speed.SpeedMeasurementState;
import com.zafaco.moduleSpeed.models.speed.SpeedTaskDesc;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


public class SpeedHelper
{

    private              JniSpeedMeasurementClient jniSpeedMeasurementClient;
    private              SpeedMeasurementState     currentMeasurementState;
    private              boolean                   running        = false;
    private              CallbackHelper            callbackHelper = null;
    private final        Context                   ctx;
    private final static String                    TAG            = "SPEED_HELPER";


    public SpeedHelper(Context ctx)
    {
        this.ctx = ctx;
    }

    public boolean isSpeedMeasurementRunning()
    {
        return running;
    }

    private String getLocalIpv6Address()
    {
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); )
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); )
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet6Address && !inetAddress.isLinkLocalAddress() && !inetAddress.isLoopbackAddress() && !inetAddress.isMulticastAddress())
                    {
                        return inetAddress.getHostName();
                    }
                }
            }
        } catch (Exception ex)
        {
            Log.warning(TAG, "IPv6 Address", ex);
        }
        return "0.0.0.0";
    }

    private String getLocalIpv4Address()
    {
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); )
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); )
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress())
                    {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex)
        {
            Log.warning(TAG, "IPv4 Address", ex);
        }
        return "";
    }


    public void startSpeedMeasurement(BaseInterface callback, final SpeedTaskDesc speedTaskDesc, boolean continuousLocation, boolean continuousNetwork, String appVersion, String gitHash, boolean performTrace)
    {
        if (!running)
        {

            running = true;

            new Thread(() ->
            {
                callbackHelper = new CallbackHelper(ctx, callback, continuousLocation, continuousNetwork, performTrace);

                callbackHelper.initData();
                callbackHelper.getMeasurementResult().getClientInfo().setAppVersion(appVersion);
                callbackHelper.getMeasurementResult().getClientInfo().setGitHash(gitHash);


                jniSpeedMeasurementClient = new JniSpeedMeasurementClient(speedTaskDesc);


                currentMeasurementState = jniSpeedMeasurementClient.getSpeedMeasurementState();

                jniSpeedMeasurementClient.addMeasurementListener(measurementStringListener);

                if (!speedTaskDesc.isPerformDownload() && !speedTaskDesc.isPerformUpload() && !speedTaskDesc.isPerformRtt())
                {
                    running = false;
                    callbackHelper.createCompletedMessage();
                    return;
                }
                try
                {
                    jniSpeedMeasurementClient.startMeasurement();
                } catch (AndroidJniCppException ex)
                {
                    Log.error(TAG, "startSpeedMeasurement", ex);
                }
                running = false;
            }).start();
        }
    }

    public void stopSpeedMeasurement()
    {
        if (running)
        {

            try
            {
                jniSpeedMeasurementClient.stopMeasurement();
            } catch (AndroidJniCppException ex)
            {
                Log.error(TAG, "stopSpeedMeasurement", ex);
            }

            running = false;
        }
    }

    private final JniSpeedMeasurementClient.MeasurementStringListener measurementStringListener = result ->
    {
        callbackHelper.createMessage(result);
    };
}
