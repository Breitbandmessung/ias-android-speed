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

package com.zafaco.moduleSpeed.models.trace;

import com.zafaco.moduleCommon.interfaces.ResultInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class TraceServerResult implements ResultInfo
{

    private static final String TAG = "SERVER_TRACE_INFO";
    public               long   rowid;
    private              int    id;
    private              String ip;

    public TraceServerResult()
    {
    }

    public TraceServerResult(JSONObject jsonObject)
    {
        this(jsonObject.optInt("id"), jsonObject.optString("ip"));
    }

    public TraceServerResult(int id, String ip)
    {
        this.id = id;
        this.ip = ip;
    }


    @Override
    public JSONObject toJson()
    {
        JSONObject jData = new JSONObject();
        try
        {
            jData.put("id", id);
            jData.put("ip", ip);
        } catch (JSONException ignored)
        {
        }

        return jData;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }
}
