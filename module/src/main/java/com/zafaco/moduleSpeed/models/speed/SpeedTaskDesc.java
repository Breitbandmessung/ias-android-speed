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

import java.io.Serializable;

public class SpeedTaskDesc implements Serializable
{

    private String[] speedServerAddrs;

    private int speedServerPort = 443;

    private int speedServerPortRtt = 80;

    private int rttCount = 10;

    private int downloadStreams = 4;

    private int uploadStreams = 4;

    private boolean performDownload = true;

    private boolean performUpload = true;

    private boolean performRtt = true;

    private boolean useEncryption = true;

    public int getRttCount()
    {
        return rttCount;
    }

    public void setRttCount(int rttCount)
    {
        this.rttCount = rttCount;
    }

    public int getSpeedServerPortRtt()
    {
        return speedServerPortRtt;
    }

    public void setSpeedServerPortRtt(int speedServerPortRtt)
    {
        this.speedServerPortRtt = speedServerPortRtt;
    }

    public int getDownloadStreams()
    {
        return downloadStreams;
    }

    public void setDownloadStreams(int downloadStreams)
    {
        this.downloadStreams = downloadStreams;
    }

    public int getUploadStreams()
    {
        return uploadStreams;
    }

    public void setUploadStreams(int uploadStreams)
    {
        this.uploadStreams = uploadStreams;
    }

    public int getSpeedServerPort()
    {
        return speedServerPort;
    }

    public void setSpeedServerPort(int speedServerPort)
    {
        this.speedServerPort = speedServerPort;
    }

    public boolean isPerformDownload()
    {
        return performDownload;
    }

    public void setPerformDownload(boolean performDownload)
    {
        this.performDownload = performDownload;
    }

    public boolean isPerformUpload()
    {
        return performUpload;
    }

    public void setPerformUpload(boolean performUpload)
    {
        this.performUpload = performUpload;
    }

    public boolean isPerformRtt()
    {
        return performRtt;
    }

    public void setPerformRtt(boolean performRtt)
    {
        this.performRtt = performRtt;
    }

    public boolean isUseEncryption()
    {
        return useEncryption;
    }

    public void setUseEncryption(boolean useEncryption)
    {
        this.useEncryption = useEncryption;
    }

    public String[] getSpeedServerAddrs()
    {
        return speedServerAddrs;
    }

    public void setSpeedServerAddrs(String[] speedServerAddrs)
    {
        this.speedServerAddrs = speedServerAddrs;
    }

    @Override
    public String toString()
    {
        return "SpeedTaskDesc{" + "speedServerAddrs='" + speedServerAddrs[0] + '\'' + ", speedServerPort=" + speedServerPort + ", rttCount=" + rttCount + ", speedServerPortRtt=" + speedServerPortRtt + ", downloadStreams=" + downloadStreams + ", uploadStreams=" + uploadStreams + ", performDownload=" + performDownload + ", performUpload=" + performUpload + ", performRtt=" + performRtt + ", useEncryption=" + useEncryption + '}';
    }
}
