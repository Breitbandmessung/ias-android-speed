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

package com.zafaco.moduleSpeed.models.speed;

import androidx.annotation.Keep;

import com.zafaco.moduleCommon.Log;

public class SpeedMeasurementState
{

    private static final String TAG = "SpeedMeasurementState";

    private String measurementUuid;

    private SpeedPhaseState downloadMeasurement = new SpeedPhaseState();

    private SpeedPhaseState uploadMeasurement = new SpeedPhaseState();

    private PingPhaseState pingMeasurement = new PingPhaseState();

    private float progress;

    private MeasurementPhase measurementPhase = MeasurementPhase.INIT;

    public SpeedPhaseState getDownloadMeasurement()
    {
        return downloadMeasurement;
    }

    public void setDownloadMeasurement(SpeedPhaseState downloadMeasurement)
    {
        this.downloadMeasurement = downloadMeasurement;
    }

    public SpeedPhaseState getUploadMeasurement()
    {
        return uploadMeasurement;
    }

    public void setUploadMeasurement(SpeedPhaseState uploadMeasurement)
    {
        this.uploadMeasurement = uploadMeasurement;
    }

    public PingPhaseState getPingMeasurement()
    {
        return pingMeasurement;
    }

    public void setPingMeasurement(PingPhaseState pingMeasurement)
    {
        this.pingMeasurement = pingMeasurement;
    }

    @Keep
    public void setMeasurementPhaseByStringValue(final String state)
    {
        if (state == null)
        {
            return;
        }
        try
        {
            measurementPhase = MeasurementPhase.valueOf(state);
        } catch (IllegalArgumentException ex)
        {
            Log.warning(TAG, "setMeasurementPhaseByStringValue", ex);
        }
    }

    public float getProgress()
    {
        return progress;
    }

    public void setProgress(float progress)
    {
        this.progress = progress;
    }

    public MeasurementPhase getMeasurementPhase()
    {
        return measurementPhase;
    }

    public void setMeasurementPhase(MeasurementPhase measurementPhase)
    {
        this.measurementPhase = measurementPhase;
    }

    public String getMeasurementUuid()
    {
        return measurementUuid;
    }

    public void setMeasurementUuid(String measurementUuid)
    {
        this.measurementUuid = measurementUuid;
    }

    @Override
    public String toString()
    {
        return "SpeedMeasurementState{" + "measurementUuid='" + measurementUuid + '\'' + ", downloadMeasurement=" + downloadMeasurement + ", uploadMeasurement=" + uploadMeasurement + ", pingMeasurement=" + pingMeasurement + ", progress=" + progress + ", measurementPhase=" + measurementPhase + '}';
    }

    public enum MeasurementPhase
    {
        IP, INIT, PING, DOWNLOAD, UPLOAD, END
    }

    public class PingPhaseState
    {
        private long averageMs;

        public long getAverageMs()
        {
            return averageMs;
        }

        public void setAverageMs(long averageMs)
        {
            this.averageMs = averageMs;
        }
    }

    public class SpeedPhaseState
    {
        private long throughputAvgBps;

        public long getThroughputAvgBps()
        {
            return throughputAvgBps;
        }

        public void setThroughputAvgBps(long throughputAvgBps)
        {
            this.throughputAvgBps = throughputAvgBps;
        }

        @Override
        public String toString()
        {
            return "JniSpeedState{" + "throughputAvgBps=" + throughputAvgBps + '}';
        }
    }
}
