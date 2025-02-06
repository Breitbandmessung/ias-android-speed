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

import com.zafaco.moduleSpeed.models.rtt.RttUdpResult;
import com.zafaco.moduleSpeed.models.time.TimeInfo;

import java.util.ArrayList;
import java.util.List;

public class JniSpeedMeasurementResult
{

    private List<BandwidthResult> downloadInfoList = new ArrayList<>();

    private List<BandwidthResult> uploadInfoList = new ArrayList<>();

    private RttUdpResult rttUdpResult;

    private TimeInfo timeInfo;

    private String measurementServerIp;

    private String clientIp;

    public RttUdpResult getRttUdpResult()
    {
        return rttUdpResult;
    }

    @Keep
    public void setRttUdpResult(RttUdpResult rttUdpResult)
    {
        this.rttUdpResult = rttUdpResult;
    }

    public List<BandwidthResult> getDownloadInfoList()
    {
        return downloadInfoList;
    }

    public void setDownloadInfoList(List<BandwidthResult> downloadInfoList)
    {
        this.downloadInfoList = downloadInfoList;
    }

    public List<BandwidthResult> getUploadInfoList()
    {
        return uploadInfoList;
    }

    public void setUploadInfoList(List<BandwidthResult> uploadInfoList)
    {
        this.uploadInfoList = uploadInfoList;
    }

    @Keep
    public void addDownloadInfo(final BandwidthResult downloadResult)
    {
        this.downloadInfoList.add(downloadResult);
    }

    @Keep
    public void addUploadInfo(final BandwidthResult uploadResult)
    {
        this.uploadInfoList.add(uploadResult);
    }

    public TimeInfo getTimeInfo()
    {
        return timeInfo;
    }

    @Keep
    public void setTimeInfo(TimeInfo timeInfo)
    {
        this.timeInfo = timeInfo;
    }

    public String getMeasurementServerIp()
    {
        return measurementServerIp;
    }

    @Keep
    public void setMeasurementServerIp(String measurementServerIp)
    {
        this.measurementServerIp = measurementServerIp;

        if (this.rttUdpResult != null)
        {
            this.rttUdpResult.setPeer(measurementServerIp);
        }
    }

    public String getClientIp()
    {
        return clientIp;
    }

    @Keep
    public void setClientIp(String clientIp)
    {
        this.clientIp = clientIp;
    }

    @Override
    public String toString()
    {
        return "JniSpeedMeasurementResult{" + "downloadInfoList=" + downloadInfoList + ", uploadInfoList=" + uploadInfoList + ", rttUdpResult=" + rttUdpResult + ", timeInfo=" + timeInfo + ", measurementServerIp='" + measurementServerIp + '\'' + ", clientIp='" + clientIp + '\'' + '}';
    }


}
