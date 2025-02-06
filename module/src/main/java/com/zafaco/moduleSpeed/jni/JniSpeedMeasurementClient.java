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

package com.zafaco.moduleSpeed.jni;


import androidx.annotation.Keep;

import com.zafaco.moduleSpeed.jni.exception.AndroidJniCppException;
import com.zafaco.moduleSpeed.models.speed.JniSpeedMeasurementResult;
import com.zafaco.moduleSpeed.models.speed.SpeedMeasurementState;
import com.zafaco.moduleSpeed.models.speed.SpeedTaskDesc;

import java.util.ArrayList;
import java.util.List;

public class JniSpeedMeasurementClient
{

    static
    {
        System.loadLibrary("ias-client");
    }

    private static final String TAG = "SPEED_MEASUREMENT_JNI";

    private final SpeedMeasurementState speedMeasurementState;

    private final SpeedTaskDesc speedTaskDesc;

    private final List<MeasurementStringListener> stringListeners = new ArrayList<>();

    private final List<MeasurementFinishedStringListener> finishedStringListeners = new ArrayList<>();

    private final List<MeasurementFinishedListener> finishedListeners = new ArrayList<>();

    private final List<MeasurementPhaseListener> measurementPhaseListeners = new ArrayList<>();

    private SpeedMeasurementState.MeasurementPhase previousMeasurementPhase = SpeedMeasurementState.MeasurementPhase.INIT;

    public JniSpeedMeasurementClient(final SpeedTaskDesc speedTaskDesc)
    {
        this.speedTaskDesc    = speedTaskDesc;
        speedMeasurementState = new SpeedMeasurementState();
        shareMeasurementState(speedTaskDesc, speedMeasurementState, speedMeasurementState.getPingMeasurement(), speedMeasurementState.getDownloadMeasurement(), speedMeasurementState.getUploadMeasurement());
    }

    @Keep
    public void cppCallback(final String message)
    {
        if (previousMeasurementPhase != speedMeasurementState.getMeasurementPhase())
        {

            for (MeasurementPhaseListener l : measurementPhaseListeners)
            {
                l.onMeasurementPhaseFinished(previousMeasurementPhase);
                l.onMeasurementPhaseStarted(speedMeasurementState.getMeasurementPhase());
            }
            previousMeasurementPhase = speedMeasurementState.getMeasurementPhase();
        }


        for (MeasurementStringListener l : stringListeners)
        {
            if (!message.isEmpty())
                l.onMeasurement(message);
        }
    }

    @Keep
    public void cppCallbackFinished(final String message, final JniSpeedMeasurementResult result)
    {
        for (MeasurementFinishedStringListener l : finishedStringListeners)
        {
            l.onMeasurementFinished(message);
        }
        for (MeasurementFinishedListener l : finishedListeners)
        {
            l.onMeasurementFinished(result, speedTaskDesc);
        }
    }

    public SpeedMeasurementState getSpeedMeasurementState()
    {
        return speedMeasurementState;
    }

    public native void startMeasurement() throws AndroidJniCppException;

    public native void stopMeasurement() throws AndroidJniCppException;


    private native void shareMeasurementState(final SpeedTaskDesc speedTaskDesc, final SpeedMeasurementState speedMeasurementState, final SpeedMeasurementState.PingPhaseState pingMeasurementState, final SpeedMeasurementState.SpeedPhaseState downloadMeasurementState, final SpeedMeasurementState.SpeedPhaseState uploadMeasurementState);

    public void addMeasurementListener(final MeasurementStringListener listener)
    {
        stringListeners.add(listener);
    }

    public void addMeasurementFinishedListener(final MeasurementFinishedStringListener listener)
    {
        finishedStringListeners.add(listener);
    }

    public void addMeasurementFinishedListener(final MeasurementFinishedListener listener)
    {
        finishedListeners.add(listener);
    }

    public void removeMeasurementFinishedListener(final MeasurementFinishedStringListener listener)
    {
        finishedStringListeners.remove(listener);
    }

    public void removeMeasurementFinishedListener(final MeasurementFinishedListener listener)
    {
        finishedListeners.remove(listener);
    }

    public void addMeasurementPhaseListener(final MeasurementPhaseListener listener)
    {
        measurementPhaseListeners.add(listener);
    }

    public void removeMeasurementPhaseListener(final MeasurementPhaseListener listener)
    {
        measurementPhaseListeners.remove(listener);
    }

    public interface MeasurementStringListener
    {
        void onMeasurement(final String result);
    }

    public interface MeasurementFinishedStringListener
    {

        void onMeasurementFinished(final String result);

    }

    public interface MeasurementFinishedListener
    {

        void onMeasurementFinished(final JniSpeedMeasurementResult result, final SpeedTaskDesc taskDesc);
    }

    public interface MeasurementPhaseListener
    {
        void onMeasurementPhaseStarted(final SpeedMeasurementState.MeasurementPhase startedPhase);

        void onMeasurementPhaseFinished(final SpeedMeasurementState.MeasurementPhase finishedPhase);
    }
}
