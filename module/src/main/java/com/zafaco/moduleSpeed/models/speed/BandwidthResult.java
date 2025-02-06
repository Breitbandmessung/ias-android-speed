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

import com.zafaco.moduleCommon.interfaces.ResultInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class BandwidthResult implements ResultInfo
{
    public long   id;

    private long bytes;

    private long bytesDistinct;

    private long bytesTotal;

    private long bytesSlowStart;

    private long durationNs;

    private long durationNsDistinct;

    private long durationNsTotal;

    private long relativeTimeNsMeasurementStart;

    private int numStreamsEnd;

    private int numStreamsStart;

    private float progress;

    private long throughputAvgBps;


    public BandwidthResult()
    {
    }

    public BandwidthResult(long bytes, long bytesDistinct, long bytesTotal, long bytesSlowStart, long durationNs, long durationNsDistinct, long durationNsTotal, long relativeTimeNsMeasurementStart, int numStreamsEnd, int numStreamsStart, float progress, long throughputAvgBps)
    {
        setBytes(bytes);
        setBytesDistinct(bytesDistinct);
        setBytesTotal(bytesTotal);
        setBytesSlowStart(bytesSlowStart);
        setDurationNs(durationNs);
        setDurationNsDistinct(durationNsDistinct);
        setDurationNsTotal(durationNsTotal);
        setRelativeTimeNsMeasurementStart(relativeTimeNsMeasurementStart);
        setNumStreamsEnd(numStreamsEnd);
        setNumStreamsStart(numStreamsStart);
        setProgress(progress);
        setThroughputAvgBps(throughputAvgBps);
    }

    public long getBytes()
    {
        return bytes;
    }

    @Keep
    public void setBytes(long bytes)
    {
        this.bytes = bytes;
    }

    public long getBytesDistinct()
    {
        return bytesDistinct;
    }

    @Keep
    public void setBytesDistinct(long bytesDistinct)
    {
        this.bytesDistinct = bytesDistinct;
    }

    public long getBytesTotal()
    {
        return bytesTotal;
    }

    @Keep
    public void setBytesTotal(long bytesTotal)
    {
        this.bytesTotal = bytesTotal;
    }

    public long getDurationNs()
    {
        return durationNs;
    }

    @Keep
    public void setDurationNs(long durationNs)
    {
        this.durationNs = durationNs;
    }

    public long getDurationNsDistinct()
    {
        return durationNsDistinct;
    }

    @Keep
    public void setDurationNsDistinct(long durationNsDistinct)
    {
        this.durationNsDistinct = durationNsDistinct;
    }

    public long getDurationNsTotal()
    {
        return durationNsTotal;
    }

    @Keep
    public void setDurationNsTotal(long durationNsTotal)
    {
        this.durationNsTotal = durationNsTotal;
    }

    public int getNumStreamsEnd()
    {
        return numStreamsEnd;
    }

    @Keep
    public void setNumStreamsEnd(int numStreamsEnd)
    {
        this.numStreamsEnd = numStreamsEnd;
    }

    public int getNumStreamsStart()
    {
        return numStreamsStart;
    }

    @Keep
    public void setNumStreamsStart(int numStreamsStart)
    {
        this.numStreamsStart = numStreamsStart;
    }

    public float getProgress()
    {
        return progress;
    }

    @Keep
    public void setProgress(float progress)
    {
        this.progress = progress;
    }

    public long getThroughputAvgBps()
    {
        return throughputAvgBps;
    }

    @Keep
    public void setThroughputAvgBps(long throughputAvgBps)
    {
        this.throughputAvgBps = throughputAvgBps;
    }

    @Keep
    public long getBytesSlowStart()
    {
        return bytesSlowStart;
    }

    @Keep
    public void setBytesSlowStart(long bytesSlowStart)
    {
        this.bytesSlowStart = bytesSlowStart;
    }

    @Keep
    public long getRelativeTimeNsMeasurementStart()
    {
        return relativeTimeNsMeasurementStart;
    }

    @Keep
    public void setRelativeTimeNsMeasurementStart(long relativeTimeNsMeasurementStart)
    {
        this.relativeTimeNsMeasurementStart = relativeTimeNsMeasurementStart;
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject jData = new JSONObject();
        try
        {
            jData.put("bytes", getBytes());
            jData.put("bytes_distinct", getBytesDistinct());
            jData.put("bytes_total", getBytesTotal());
            jData.put("bytes_slow_start", getBytesSlowStart());
            jData.put("duration_ns", getDurationNs());
            jData.put("duration_ns_distinct", getDurationNsDistinct());
            jData.put("duration_ns_total", getDurationNsTotal());
            jData.put("relative_time_ns_measurement_start", getRelativeTimeNsMeasurementStart());
            jData.put("num_streams_end", getNumStreamsEnd());
            jData.put("num_streams_start", getNumStreamsStart());
            jData.put("progress", getProgress());
            jData.put("throughput_avg_bps", getThroughputAvgBps());
            return jData;

        } catch (JSONException ex)
        {
            return jData;
        }
    }

    @Override
    public String toString()
    {
        return "BandwidthResult{" + "bytes=" + bytes + "bytesDistinct=" + bytesDistinct + ", bytesTotal=" + bytesTotal + ", bytesSlowStart=" + getBytesSlowStart() + ", durationNs=" + durationNs + ", durationNsDistinct=" + durationNsDistinct + ", durationNsTotal=" + durationNsTotal + ", relativeTimeNsMeasurementStart=" + getRelativeTimeNsMeasurementStart() + ", numStreamsEnd=" + numStreamsEnd + ", numStreamsStart=" + numStreamsStart + ", progress=" + progress + ", throughputAvgBps=" + throughputAvgBps + '}';
    }
}
