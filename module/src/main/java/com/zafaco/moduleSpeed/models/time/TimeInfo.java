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

package com.zafaco.moduleSpeed.models.time;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.zafaco.moduleCommon.interfaces.ResultInfo;
import com.zafaco.moduleCommon.models.measurement.AbstractMeasurementResult;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeInfo implements ResultInfo
{
    private long measurementStart;
    private long measurementEnd;
    private long initStart;
    private long initEnd;
    private long downloadStart;
    private long downloadEnd;
    private long uploadStart;
    private long uploadEnd;
    private long rttUdpStart;
    private long rttUdpEnd;
    private long ipStart;
    private long ipEnd;

    public TimeInfo()
    {
        super();
    }

    public TimeInfo(JSONObject timeInfo)
    {
        super();
        addTimes(timeInfo);
    }


    public void addTimes(JSONObject timeInfo)
    {
        if (timeInfo.optLong("download_start") != 0 && downloadStart == 0)
            downloadStart = timeInfo.optLong("download_start");
        if (timeInfo.optLong("download_end") != 0 && downloadEnd == 0)
            downloadEnd = timeInfo.optLong("download_end");

        if (timeInfo.optLong("upload_start") != 0 && uploadStart == 0)
            uploadStart = timeInfo.optLong("upload_start");
        if (timeInfo.optLong("upload_end") != 0 && uploadEnd == 0)
            uploadEnd = timeInfo.optLong("upload_end");

        if (timeInfo.optLong("rtt_udp_start") != 0 && rttUdpStart == 0)
            rttUdpStart = timeInfo.optLong("rtt_udp_start");
        if (timeInfo.optLong("rtt_udp_end") != 0 && rttUdpEnd == 0)
            rttUdpEnd = timeInfo.optLong("rtt_udp_end");

        if (timeInfo.optLong("measurement_start") != 0 && getMeasurementStart() == 0)
            setMeasurementStart(timeInfo.optLong("measurement_start"));
        if (timeInfo.optLong("measurement_end") != 0 && getMeasurementEnd() == 0)
            setMeasurementEnd(timeInfo.optLong("measurement_end"));

        if (timeInfo.optLong("setup_start") != 0 && ipStart == 0)
            ipStart = timeInfo.optLong("setup_start");
        if (timeInfo.optLong("setup_end") != 0 && ipEnd == 0)
            ipEnd = timeInfo.optLong("setup_end");

        if (timeInfo.optLong("init_start") != 0 && getInitStart() == 0)
            setInitStart(timeInfo.optLong("init_start"));
        if (timeInfo.optLong("init_end") != 0 && getInitEnd() == 0)
            setInitEnd(timeInfo.optLong("init_end"));
    }

    public void addTimes(TimeInfo timeInfo)
    {
        if (timeInfo.downloadStart != 0 && this.downloadStart == 0)
            this.downloadStart = timeInfo.downloadStart;
        if (timeInfo.downloadEnd != 0 && this.downloadEnd == 0)
            this.downloadEnd = timeInfo.downloadEnd;

        if (timeInfo.uploadStart != 0 && this.uploadStart == 0)
            this.uploadStart = timeInfo.uploadStart;
        if (timeInfo.uploadEnd != 0 && this.uploadEnd == 0)
            this.uploadEnd = timeInfo.uploadEnd;

        if (timeInfo.rttUdpStart != 0 && this.rttUdpStart == 0)
            this.rttUdpStart = timeInfo.rttUdpStart;
        if (timeInfo.rttUdpEnd != 0 && this.rttUdpEnd == 0)
            this.rttUdpEnd = timeInfo.rttUdpEnd;

        if (timeInfo.getMeasurementStart() != 0 && this.getMeasurementStart() == 0)
            this.setMeasurementStart(timeInfo.getMeasurementStart());
        if (timeInfo.getMeasurementEnd() != 0 && this.getMeasurementEnd() == 0)
            this.setMeasurementEnd(timeInfo.getMeasurementEnd());

        if (timeInfo.ipStart != 0 && this.ipStart == 0)
            this.ipStart = timeInfo.ipStart;
        if (timeInfo.ipEnd != 0 && this.ipEnd == 0)
            this.ipEnd = timeInfo.ipEnd;

        if (timeInfo.getInitStart() != 0 && this.getInitStart() == 0)
            this.setInitStart(timeInfo.getInitStart());
        if (timeInfo.getInitEnd() != 0 && this.getInitEnd() == 0)
            this.setInitEnd(timeInfo.getInitEnd());

    }

    public void addStartTime(AbstractMeasurementResult.TestCase testCase)
    {
        long time = System.currentTimeMillis() * 1000 * 1000;
        switch (testCase)
        {
            case IP:
                this.setIpStart(time);
                break;
            case INIT:
                this.setMeasurementStart(time);
                this.setInitStart(time);
                break;
            case RTT_UDP:
                this.setRttUdpStart(time);
                break;
            case DOWNLOAD:
                this.setDownloadStart(time);
                break;
            case UPLOAD:
                this.setUploadStart(time);
                break;
            case END:
                break;
        }
    }

    public void addEndTime(AbstractMeasurementResult.TestCase testCase)
    {
        long time = System.currentTimeMillis() * 1000 * 1000;
        switch (testCase)
        {
            case IP:
                this.setIpEnd(time);
                break;
            case INIT:
                this.setInitEnd(time);
                break;
            case RTT_UDP:
                this.setRttUdpEnd(time);
                break;
            case DOWNLOAD:
                this.setDownloadEnd(time);
                break;
            case UPLOAD:
                this.setUploadEnd(time);
                break;
            case END:
                this.setMeasurementEnd(time);
                break;
        }
    }

    @Keep
    public void setMeasurementStart(long measurementStart)
    {
        this.measurementStart = measurementStart;
    }

    public long getMeasurementStart()
    {
        return measurementStart;
    }

    public void setMeasurementEnd(long measurementEnd)
    {
        this.measurementEnd = measurementEnd;
    }

    public long getMeasurementEnd()
    {
        return measurementEnd;
    }

    public long getInitStart()
    {
        return initStart;
    }

    public void setInitStart(long initStart)
    {
        this.initStart = initStart;
    }

    public long getInitEnd()
    {
        return initEnd;
    }

    public void setInitEnd(long initEnd)
    {
        this.initEnd = initEnd;
    }

    @Keep
    public void setDownloadStart(long downloadStart)
    {
        this.downloadStart = downloadStart;
    }

    @Keep
    public void setDownloadEnd(long downloadEnd)
    {
        this.downloadEnd = downloadEnd;
    }

    @Keep
    public void setUploadStart(long uploadStart)
    {
        this.uploadStart = uploadStart;
    }

    @Keep
    public void setUploadEnd(long uploadEnd)
    {
        this.uploadEnd = uploadEnd;
    }

    @Keep
    public void setRttUdpStart(long rttUdpStart)
    {
        this.rttUdpStart = rttUdpStart;
    }

    @Keep
    public void setRttUdpEnd(long rttUdpEnd)
    {
        this.rttUdpEnd = rttUdpEnd;
    }

    @Keep
    public void setIpStart(long setupStart)
    {
        this.ipStart = setupStart;
    }

    @Keep
    public void setIpEnd(long ipEnd)
    {
        this.ipEnd = ipEnd;
    }

    public long getDownloadStart()
    {
        return downloadStart;
    }

    public long getDownloadEnd()
    {
        return downloadEnd;
    }

    public long getUploadStart()
    {
        return uploadStart;
    }

    public long getUploadEnd()
    {
        return uploadEnd;
    }

    public long getRttUdpStart()
    {
        return rttUdpStart;
    }

    public long getRttUdpEnd()
    {
        return rttUdpEnd;
    }

    public long getIpStart()
    {
        return ipStart;
    }

    public long getIpEnd()
    {
        return ipEnd;
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject jData = new JSONObject();
        try
        {
            if (downloadStart != 0)
                jData.put("download_start", downloadStart);
            if (downloadEnd != 0)
                jData.put("download_end", downloadEnd);

            if (uploadStart != 0)
                jData.put("upload_start", uploadStart);
            if (uploadEnd != 0)
                jData.put("upload_end", uploadEnd);

            if (rttUdpStart != 0)
                jData.put("rtt_udp_start", rttUdpStart);
            if (rttUdpEnd != 0)
                jData.put("rtt_udp_end", rttUdpEnd);

            if (measurementStart != 0)
                jData.put("measurement_start", getMeasurementStart());
            if (measurementEnd != 0)
                jData.put("measurement_end", getMeasurementEnd());

            if (ipStart != 0)
                jData.put("setup_start", ipStart);
            if (ipEnd != 0)
                jData.put("setup_end", ipEnd);

            if (initStart != 0)
                jData.put("init_start", getInitStart());
            if (initEnd != 0)
                jData.put("init_end", getInitEnd());
            return jData;
        } catch (JSONException ex)
        {
            return jData;
        }
    }

    @Override
    public String toString()
    {
        return "TimeInfo{" + "downloadStart=" + downloadStart + ", downloadEnd=" + downloadEnd + ", rttUdpStart=" + rttUdpStart + ", rttUdpEnd=" + rttUdpEnd + ", uploadStart=" + uploadStart + ", uploadEnd=" + uploadEnd + '}';
    }


}
