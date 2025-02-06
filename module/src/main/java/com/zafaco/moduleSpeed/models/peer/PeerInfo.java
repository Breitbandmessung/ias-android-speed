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

package com.zafaco.moduleSpeed.models.peer;

import com.zafaco.moduleCommon.Log;
import com.zafaco.moduleCommon.interfaces.ResultInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class PeerInfo implements ResultInfo
{
    private static final String TAG = "PEER_INFO";

    private String  url;
    private int     port;
    private boolean tls;
    private String  ip;

    public PeerInfo()
    {
    }

    public PeerInfo(JSONObject jsonObject)
    {
        this(jsonObject.optString("url"), jsonObject.optInt("port"), jsonObject.optInt("tls"), jsonObject.optString("ip"));
    }


    public PeerInfo(String url, int port, int tls, String ip)
    {
        if (url.substring(url.lastIndexOf(".") + 1).isEmpty() && !url.isEmpty())
            this.url = url.substring(0, url.length() - 1);
        else
            this.url = url;
        this.port = port;
        this.tls  = tls == 1;
        this.ip   = ip;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public boolean isTls()
    {
        return tls;
    }

    public void setTls(boolean tls)
    {
        this.tls = tls;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject jData = new JSONObject();
        try
        {
            jData.put("url", getUrl());
            jData.put("port", getPort());
            jData.put("tls", isTls());
            jData.put("ip", getIp());
            return jData;
        } catch (JSONException ex)
        {
            Log.warning(TAG, "toJson for PeerInfo failed", ex);
            return jData;
        }
    }
}
