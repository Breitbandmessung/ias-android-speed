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

package com.zafaco.moduleSpeed.models.rtt;

import androidx.annotation.Keep;

import com.zafaco.moduleCommon.Log;
import com.zafaco.moduleCommon.interfaces.ResultInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RttUdpResult implements ResultInfo
{
    private static final String TAG = "RttUdpResult";

    public long   id;

    private long averageNs;

    private long durationNs;

    private long maxNs;

    private long medianNs;

    private long minNs;

    private int numError;

    private int numMissing;

    private int numReceived;

    private int numSent;

    private int packetSize;

    private String peer;

    private float progress;

    private long standardDeviationNs;

    private List<SingleRttResult> singleRtts = new ArrayList<>();

    @Keep
    public void addSingleRtt(long rttNs, long relativeTimeNs)
    {
        final SingleRttResult toAdd = new SingleRttResult();
        toAdd.setRttNs(rttNs);
        toAdd.setRelativeTimeNsMeasurementStart(relativeTimeNs);
        singleRtts.add(toAdd);
    }

    public List<SingleRttResult> getSingleRtts()
    {
        return singleRtts;
    }

    @Keep
    public void setSingleRtts(List<SingleRttResult> singleRtts)
    {
        this.singleRtts = singleRtts;
    }

    public long getAverageNs()
    {
        return averageNs;
    }

    @Keep
    public void setAverageNs(long averageNs)
    {
        this.averageNs = averageNs;
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

    public long getMaxNs()
    {
        return maxNs;
    }

    @Keep
    public void setMaxNs(long maxNs)
    {
        this.maxNs = maxNs;
    }

    public long getMedianNs()
    {
        return medianNs;
    }

    @Keep
    public void setMedianNs(long medianNs)
    {
        this.medianNs = medianNs;
    }

    public long getMinNs()
    {
        return minNs;
    }

    @Keep
    public void setMinNs(long minNs)
    {
        this.minNs = minNs;
    }

    public int getNumError()
    {
        return numError;
    }

    @Keep
    public void setNumError(int numError)
    {
        this.numError = numError;
    }

    public int getNumMissing()
    {
        return numMissing;
    }

    @Keep
    public void setNumMissing(int numMissing)
    {
        this.numMissing = numMissing;
    }

    public int getNumReceived()
    {
        return numReceived;
    }

    @Keep
    public void setNumReceived(int numReceived)
    {
        this.numReceived = numReceived;
    }

    public int getNumSent()
    {
        return numSent;
    }

    @Keep
    public void setNumSent(int numSent)
    {
        this.numSent = numSent;
    }

    public int getPacketSize()
    {
        return packetSize;
    }

    @Keep
    public void setPacketSize(int packetSize)
    {
        this.packetSize = packetSize;
    }

    public String getPeer()
    {
        return peer;
    }

    @Keep
    public void setPeer(String peer)
    {
        this.peer = peer;
    }

    public long getStandardDeviationNs()
    {
        return standardDeviationNs;
    }

    @Keep
    public void setStandardDeviationNs(long standardDeviationNs)
    {
        this.standardDeviationNs = standardDeviationNs;
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

    public void setResults(JSONObject rttInfo)
    {
        try
        {
            setAverageNs(rttInfo.getLong("average_ns"));
            setDurationNs(rttInfo.getLong("duration_ns"));
            setMaxNs(rttInfo.getLong("max_ns"));
            setMedianNs(rttInfo.getLong("median_ns"));
            setMinNs(rttInfo.getLong("min_ns"));
            setNumError(rttInfo.getInt("num_error"));
            setNumMissing(rttInfo.getInt("num_missing"));
            setNumReceived(rttInfo.getInt("num_received"));
            setNumSent(rttInfo.getInt("num_sent"));
            setPacketSize(rttInfo.getInt("packet_size"));
            setProgress((float) rttInfo.getDouble("progress"));
            setStandardDeviationNs(rttInfo.getLong("standard_deviation_ns"));
            if (!rttInfo.optString("peer").equals(""))
                setPeer(rttInfo.getString("peer"));

            List<SingleRttResult> list  = new ArrayList<>();
            JSONArray             array = rttInfo.getJSONArray("rtts");
            for (int i = 0; i < array.length(); i++)
            {
                list.add(new SingleRttResult(array.getJSONObject(i).getLong("rtt_ns"), array.getJSONObject(i).getLong("relative_time_ns_measurement_start")));
            }
            setSingleRtts(list);
        } catch (JSONException e)
        {
            Log.warning(TAG, "setResults", e);
        }
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject jData = new JSONObject();
        try
        {
            jData.put("average_ns", getAverageNs());
            jData.put("duration_ns", getDurationNs());
            jData.put("max_ns", getMaxNs());
            jData.put("median_ns", getMedianNs());
            jData.put("min_ns", getMinNs());
            jData.put("num_error", getNumError());
            jData.put("num_missing", getNumMissing());
            jData.put("num_received", getNumReceived());
            jData.put("num_sent", getNumSent());
            jData.put("packet_size", getPacketSize());
            jData.put("peer", getPeer());
            jData.put("progress", getProgress());
            jData.put("standard_deviation_ns", getStandardDeviationNs());
            return jData;

        } catch (JSONException ignored)
        {
            return jData;
        }
    }

    @Override
    public String toString()
    {
        return "RttUdpInfo{" + "averageNs=" + averageNs + ", durationNs=" + durationNs + ", maxNs=" + maxNs + ", medianNs=" + medianNs + ", minNs=" + minNs + ", numError=" + numError + ", numMissing=" + numMissing + ", numReceived=" + numReceived + ", numSent=" + numSent + ", packetSize=" + packetSize + ", peer='" + peer + '\'' + ", progress=" + progress + ", standardDeviationNs=" + standardDeviationNs + '}';
    }
}
