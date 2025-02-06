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

package com.zafaco.moduleSpeed.models.ip;

import com.zafaco.moduleCommon.Log;
import com.zafaco.moduleCommon.interfaces.ResultInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class IpInfo implements ResultInfo
{
    private static final String TAG = "SETUP_INFO";

    private String clientIpVersion;
    private String client;
    private String tlsVersion;
    private String tlsCipherSuite;
    private String tlsOverhead;

    public IpInfo()
    {
    }

    public IpInfo(JSONObject jsonObject)
    {
        this(jsonObject.optString("client_ip_version"), jsonObject.optString("client"), jsonObject.optString("tls_version"), jsonObject.optString("tls_cipher_suite"), jsonObject.optString("tls_overhead"));
    }

    public IpInfo(String clientIpVersion, String client, String tlsVersion, String tlsCipherSuite, String tlsOverhead)
    {
        this.clientIpVersion = clientIpVersion;
        this.client          = client;
        this.tlsVersion      = tlsVersion;
        this.tlsCipherSuite  = tlsCipherSuite;
        this.tlsOverhead     = tlsOverhead;
    }

    public JSONObject toJson()
    {
        JSONObject jData = new JSONObject();
        try
        {
            jData.put("client", this.client);
            jData.put("client_ip_version", this.clientIpVersion);
            jData.put("tls_version", this.tlsVersion);
            jData.put("tls_cipher_suite", this.tlsCipherSuite);
            jData.put("tls_overhead", this.tlsOverhead);
            return jData;
        } catch (JSONException ex)
        {
            Log.error(TAG, "toJson", ex);
            return jData;
        }
    }

    public String getClientIpVersion()
    {
        return clientIpVersion;
    }

    public String getClient()
    {
        return client;
    }

    public String getTlsVersion()
    {
        return tlsVersion;
    }

    public String getTlsCipherSuite()
    {
        return tlsCipherSuite;
    }

    public String getTlsOverhead()
    {
        return tlsOverhead;
    }

    public void setClientIpVersion(String clientIpVersion)
    {
        this.clientIpVersion = clientIpVersion;
    }

    public void setClient(String client)
    {
        this.client = client;
    }

    public void setTlsVersion(String tlsVersion)
    {
        this.tlsVersion = tlsVersion;
    }

    public void setTlsCipherSuite(String tlsCipherSuite)
    {
        this.tlsCipherSuite = tlsCipherSuite;
    }

    public void setTlsOverhead(String tlsOverhead)
    {
        this.tlsOverhead = tlsOverhead;
    }

}


