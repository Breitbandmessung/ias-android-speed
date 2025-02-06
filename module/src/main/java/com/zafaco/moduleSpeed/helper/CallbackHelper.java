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

import com.zafaco.moduleCommon.AbstractCallbackHelper;
import com.zafaco.moduleCommon.Log;
import com.zafaco.moduleCommon.Tool;
import com.zafaco.moduleCommon.interfaces.BaseInterface;
import com.zafaco.moduleCommon.interfaces.GenericInterface;
import com.zafaco.moduleCommon.models.measurement.AbstractMeasurementResult;
import com.zafaco.moduleCommon.models.measurement.AbstractMeasurementResult.TestCase;
import com.zafaco.moduleCommon.models.network.NetworkInfo;
import com.zafaco.moduleSpeed.api.Speed;
import com.zafaco.moduleSpeed.models.measurement.MeasurementResult;
import com.zafaco.moduleSpeed.models.peer.PeerInfo;

import org.json.JSONObject;

public class CallbackHelper extends AbstractCallbackHelper
{

    private static final String TAG = "CALLBACK_HELPER";

    private final boolean continuousLocation;
    private final boolean continuousNetwork;
    private final boolean performTrace;

    CallbackHelper(Context ctx, BaseInterface genericInterface)
    {
        this(ctx, genericInterface, false, false,  false);
    }

    CallbackHelper(Context ctx, BaseInterface genericInterface, boolean continuousLocation, boolean continuousNetwork, boolean performTrace)
    {
        super(ctx, genericInterface, new MeasurementResult(ctx));
        this.continuousLocation = continuousLocation;
        this.continuousNetwork  = continuousNetwork;
        this.performTrace       = performTrace;
    }

    public void initData()
    {
        createStartMessage(MeasurementResult.TestCase.INIT, "");
        createInfoMessage(MeasurementResult.TestCase.INIT, "starting measurement");
        createInfoMessage(MeasurementResult.TestCase.INIT, "measurement started");

        measurementResult.initMiscData();

        if (continuousLocation)
            getMeasurementResult().getLocationInfo().startLocationListener(ctx);

        if (continuousNetwork)
            getMeasurementResult().getNetworkInfo().startNetworkListener(ctx);

        createReportMessage(MeasurementResult.TestCase.INIT, measurementResult);
        createFinishMessage(MeasurementResult.TestCase.INIT, "measurement completed");
    }

    @Override
    public MeasurementResult getMeasurementResult()
    {
        return (MeasurementResult) measurementResult;
    }


    @Override
    public void createStartMessage(AbstractMeasurementResult.TestCase testCase, String message)
    {
        getMeasurementResult().addTimeResult(testCase, AbstractMeasurementResult.Cmd.STARTED);

        NetworkInfo networkInfo = getMeasurementResult().getNetworkInfo();
        if (networkInfo != null)
        {
            networkInfo.setConnectionType(new Tool().getConnectionType(ctx));
            networkInfo.setCurrentTestCase(testCase);
        }

        if (performTrace)
        {
            switch (testCase)
            {
                case DOWNLOAD:
                {
                    PeerInfo peerInfo = getMeasurementResult().getPeerInfo();
                    if (peerInfo != null)
                    {
                        new TracerouteHelper().startServerTracerouteTest(peerInfo.getUrl(), peerInfo.isTls(), traceInterface);
                    } else
                        Log.warning(TAG, "unable to start traceroute");

                    break;
                }
            }
        }

        super.createStartMessage(testCase, message);
    }

    @Override
    public void createFinishMessage(TestCase testCase, String message)
    {
        getMeasurementResult().addTimeResult(testCase, AbstractMeasurementResult.Cmd.FINISH);
        super.createFinishMessage(testCase, message);
    }

    @Override
    public void createCompletedMessage()
    {
        getMeasurementResult().addTimeResult(TestCase.END, AbstractMeasurementResult.Cmd.COMPLETED);

        if (continuousLocation)
            getMeasurementResult().getLocationInfo().stopLocationListener();
        if (continuousNetwork)
            getMeasurementResult().getNetworkInfo().stopNetworkListener();

        super.createCompletedMessage();
    }

    public void createErrorMessage()
    {
        getMeasurementResult().addTimeResult(TestCase.END, AbstractMeasurementResult.Cmd.COMPLETED);

        if (continuousLocation)
            getMeasurementResult().getLocationInfo().stopLocationListener();
        if (continuousNetwork)
            getMeasurementResult().getNetworkInfo().stopNetworkListener();

        super.createErrorMessage();
    }


    private final GenericInterface traceInterface = new GenericInterface()
    {
        @Override
        public void reportCallback(JSONObject message)
        {
            getMeasurementResult().addTraceIasResult(message.optJSONArray("hops"));
        }

        @Override
        public void consoleCallback(String message)
        {

        }
    };
}
