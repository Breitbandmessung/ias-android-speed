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

package com.zafaco.moduleSpeed.models.measurement;


import android.content.Context;

import com.zafaco.moduleCommon.Log;
import com.zafaco.moduleCommon.interfaces.ResultInfo;
import com.zafaco.moduleCommon.models.client.ClientInfo;
import com.zafaco.moduleCommon.models.location.LocationInfo;
import com.zafaco.moduleCommon.models.measurement.AbstractMeasurementResult;
import com.zafaco.moduleCommon.models.measurement.MeasurementInfo;
import com.zafaco.moduleCommon.models.network.NetworkInfo;
import com.zafaco.moduleCommon.models.system.SystemInfo;
import com.zafaco.moduleSpeed.BuildConfig;
import com.zafaco.moduleSpeed.models.ip.IpInfo;
import com.zafaco.moduleSpeed.models.peer.PeerInfo;
import com.zafaco.moduleSpeed.models.rtt.RttUdpResult;
import com.zafaco.moduleSpeed.models.speed.BandwidthResult;
import com.zafaco.moduleSpeed.models.speed.DownloadInfo;
import com.zafaco.moduleSpeed.models.speed.UploadInfo;
import com.zafaco.moduleSpeed.models.time.TimeInfo;
import com.zafaco.moduleSpeed.models.trace.TraceServerResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MeasurementResult extends AbstractMeasurementResult
{

    public  MeasurementInfo         measurementInfo     = new MeasurementInfo();
    private List<DownloadInfo>      downloadInfoList    = new ArrayList<>();
    private List<UploadInfo>        uploadInfoList      = new ArrayList<>();
    private List<TraceServerResult> traceServerInfoList = new ArrayList<>();
    private RttUdpResult            rttResult;
    private PeerInfo                peerInfo;
    private IpInfo                  ipInfo;
    private TimeInfo                timeInfo;
    private LocationInfo            locationInfo;
    private NetworkInfo             networkInfo;
    private ClientInfo              clientInfo;
    private SystemInfo              systemInfo;

    private static final String TAG = "MEASUREMENT_RESULT";

    public MeasurementResult()
    {
        this(null);
    }

    public MeasurementResult(Context ctx)
    {
        super(ctx);
        timeInfo = new TimeInfo();
    }

    public void initMiscData()
    {
        networkInfo  = new NetworkInfo(ctx);
        systemInfo   = new SystemInfo(ctx);
        locationInfo = new LocationInfo(ctx);
        clientInfo   = new ClientInfo(ctx, BuildConfig.VERSION_NAME, "speed");
    }

    public void addResults(String result)
    {
        try
        {
            addResults(new JSONObject(result));
        } catch (JSONException ignored)
        {
        }
    }

    public void addResult(ResultInfo resultInfo)
    {
        if (resultInfo == null)
        {
        }
    }

    public void addTimeResult(TimeInfo timeInfo)
    {
        if (timeInfo != null)
            getTimeInfo().addTimes(timeInfo);
    }

    public void addTimeResult(TestCase testCase, Cmd cmd)
    {
        if (cmd == Cmd.STARTED)
        {
            getTimeInfo().addStartTime(testCase);
        } else if (cmd == Cmd.FINISH)
        {
            getTimeInfo().addEndTime(testCase);
        } else if (cmd == Cmd.COMPLETED)
        {
            getTimeInfo().setMeasurementEnd(System.currentTimeMillis() * 1000 * 1000);
        }
    }


    public void addUploadResult(JSONArray uploadInfo)
    {
        if (uploadInfo != null)
        {
            uploadInfoList = new ArrayList<>();
            try
            {
                for (int i = 0; i < uploadInfo.length(); i++)
                {
                    JSONObject bandwidth = uploadInfo.getJSONObject(i);
                    uploadInfoList.add(new UploadInfo(bandwidth.getLong("bytes"), bandwidth.getLong("bytes_distinct"), bandwidth.getLong("bytes_total"), bandwidth.getLong("bytes_slow_start"), bandwidth.getLong("duration_ns"), bandwidth.getLong("duration_ns_distinct"), bandwidth.getLong("duration_ns_total"), bandwidth.getLong("relative_time_ns_measurement_start"), bandwidth.getInt("num_streams_end"), bandwidth.getInt("num_streams_start"), (float) bandwidth.getDouble("progress"), bandwidth.getLong("throughput_avg_bps")));
                }
            } catch (JSONException ex)
            {
                Log.warning(TAG, ex);
            }
        }
    }

    public void addDownloadResult(JSONArray downloadInfo)
    {
        if (downloadInfo != null)
        {
            downloadInfoList = new ArrayList<>();
            try
            {
                for (int i = 0; i < downloadInfo.length(); i++)
                {
                    JSONObject bandwidth = downloadInfo.getJSONObject(i);
                    downloadInfoList.add(new DownloadInfo(bandwidth.getLong("bytes"), bandwidth.getLong("bytes_distinct"), bandwidth.getLong("bytes_total"), bandwidth.getLong("bytes_slow_start"), bandwidth.getLong("duration_ns"), bandwidth.getLong("duration_ns_distinct"), bandwidth.getLong("duration_ns_total"), bandwidth.getLong("relative_time_ns_measurement_start"), bandwidth.getInt("num_streams_end"), bandwidth.getInt("num_streams_start"), (float) bandwidth.getDouble("progress"), bandwidth.getLong("throughput_avg_bps")));
                }
            } catch (JSONException ex)
            {
                Log.warning(TAG, ex);
            }
        }
    }

    public void addRttResult(JSONObject rttInfo)
    {
        if (rttInfo != null)
        {
            if (rttResult == null)
                rttResult = new RttUdpResult();
            rttResult.setResults(rttInfo);
        }
    }

    public void addPeerResult(JSONObject peerInfo)
    {
        if (peerInfo != null)
        {
            this.peerInfo = new PeerInfo(peerInfo);
        }
    }

    public void addIpResult(JSONObject ipInfo)
    {
        if (this.ipInfo == null && ipInfo != null && this.cmd == Cmd.FINISH && this.testCase == TestCase.IP)
        {
            this.ipInfo = new IpInfo(ipInfo);
        }
    }

    public void addTraceIasResult(JSONObject traceInfo)
    {
        traceServerInfoList.add(new TraceServerResult(traceInfo));
    }

    public void addTraceIasResult(JSONArray traceInfo)
    {
        if (traceInfo != null)
        {
            traceServerInfoList = new ArrayList<>();
            try
            {
                for (int i = 0; i < traceInfo.length() - 2; i++)
                {
                    addTraceIasResult(traceInfo.getJSONObject(i));
                }
            } catch (JSONException ex)
            {
                Log.warning(TAG, ex);
            }
        }
    }


    public void addResults(JSONObject result)
    {
        try
        {
            cmd = Cmd.valueOf(result.optString("cmd").toUpperCase());
        } catch (IllegalArgumentException ignored)
        {
        }
        msg = result.optString("msg");
        try
        {
            testCase = TestCase.valueOf(result.optString("test_case").toUpperCase());
        } catch (IllegalArgumentException ignored)
        {
        }
        measurementInfo.setError(result.optString("error_description"));
        measurementInfo.setErrorCode(result.optInt("error_code"));
        addPeerResult(result.optJSONObject("peer_info"));
        addIpResult(result.optJSONObject("ip_info"));
        addTimeResult(new TimeInfo(result.optJSONObject("time_info")));
        if (testCase == TestCase.RTT_UDP)
            addRttResult(result.optJSONObject("rtt_udp_info"));
        if (testCase == TestCase.UPLOAD)
            addUploadResult(result.optJSONArray("upload_info"));
        if (testCase == TestCase.DOWNLOAD)
            addDownloadResult(result.optJSONArray("download_info"));
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject jData = new JSONObject();
        try
        {
            if (cmd != null)
                jData.put("cmd", cmd.toString().toLowerCase());
            jData.put("msg", msg);
            if (testCase != null)
                jData.put("test_case", testCase.toString().toLowerCase());
            if (measurementInfo.getError() != null && !measurementInfo.getError().isEmpty())
                jData.put("error_description", measurementInfo.getError());
            if (measurementInfo.getErrorCode() != 0)
                jData.put("error_code", measurementInfo.getErrorCode());
            if (timeInfo != null)
                jData.put("time_info", timeInfo.toJson());
            if (peerInfo != null)
                jData.put("peer_info", peerInfo.toJson());
            if (ipInfo != null)
                jData.put("ip_info", ipInfo.toJson());
            if (rttResult != null)
                jData.put("rtt_udp_info", rttResult.toJson());
            if (!downloadInfoList.isEmpty())
            {
                JSONArray downloadInfo = new JSONArray();
                for (BandwidthResult bandwidthResult : downloadInfoList)
                {
                    downloadInfo.put(bandwidthResult.toJson());
                }
                jData.put("download_info", downloadInfo);

            }
            if (!uploadInfoList.isEmpty())
            {
                JSONArray uploadInfo = new JSONArray();
                for (BandwidthResult bandwidthResult : uploadInfoList)
                {
                    uploadInfo.put(bandwidthResult.toJson());
                }
                jData.put("upload_info", uploadInfo);

            }

            if (!traceServerInfoList.isEmpty())
            {
                JSONArray traceInfo = new JSONArray();
                for (TraceServerResult traceResult : traceServerInfoList)
                {
                    traceInfo.put(traceResult.toJson());
                }
                jData.put("trace_server_info", traceInfo);

            }

            if (getNetworkInfo() != null)
                jData.put("network_info", getNetworkInfo().toJson());

            if (getLocationInfo() != null)
                jData.put("location_info", getLocationInfo().toJson());

            if (clientInfo != null)
                jData.put("client_info", clientInfo.toJson());

            if (getSystemInfo() != null)
                jData.put("system_info", getSystemInfo().toJson());

            return jData;
        } catch (JSONException e)
        {
            Log.warning(TAG, "toJson", e);
            return jData;
        }
    }

    public JSONObject getTimeInfoJson()
    {
        return timeInfo.toJson();
    }


    public List<DownloadInfo> getDownloadInfoList()
    {
        return downloadInfoList;
    }

    public BandwidthResult getDownloadInfo()
    {
        if (downloadInfoList != null && !downloadInfoList.isEmpty())
            return downloadInfoList.get(downloadInfoList.size() - 1);
        else
            return null;
    }

    public List<UploadInfo> getUploadInfoList()
    {
        return uploadInfoList;
    }

    public BandwidthResult getUploadInfo()
    {
        if (uploadInfoList != null && !uploadInfoList.isEmpty())
            return uploadInfoList.get(uploadInfoList.size() - 1);
        else
            return null;
    }

    public List<TraceServerResult> getTraceServerInfoList()
    {
        return traceServerInfoList;
    }

    public RttUdpResult getRttResult()
    {
        return rttResult;
    }

    public PeerInfo getPeerInfo()
    {
        return peerInfo;
    }

    public IpInfo getIpInfo()
    {
        return ipInfo;
    }

    public ClientInfo getClientInfo()
    {
        return clientInfo;
    }

    public TimeInfo getTimeInfo()
    {
        return timeInfo;
    }

    public String getError()
    {
        return measurementInfo.getError();
    }

    public int getErrorCode()
    {
        return measurementInfo.getErrorCode();
    }

    public void setDownloadInfoList(List<DownloadInfo> downloadInfoList)
    {
        this.downloadInfoList = downloadInfoList;
    }

    public void setUploadInfoList(List<UploadInfo> uploadInfoList)
    {
        this.uploadInfoList = uploadInfoList;
    }

    public void setTraceServerInfoList(List<TraceServerResult> traceServerInfoList)
    {
        this.traceServerInfoList = traceServerInfoList;
    }

    public void setRttResult(RttUdpResult rttResult)
    {
        this.rttResult = rttResult;
    }

    public void setPeerInfo(PeerInfo peerInfo)
    {
        this.peerInfo = peerInfo;
    }

    public void setIpInfo(IpInfo ipInfo)
    {
        this.ipInfo = ipInfo;
    }

    public void setError(String error)
    {
        this.measurementInfo.setError(error);
    }

    public void setErrorCode(int errorCode)
    {
        this.measurementInfo.setErrorCode(errorCode);
    }

    public void setTimeInfo(TimeInfo timeInfo)
    {
        this.timeInfo = timeInfo;
    }

    public void setClientInfo(ClientInfo clientInfo)
    {
        this.clientInfo = clientInfo;
    }

    public MeasurementInfo getMeasurementInfo()
    {
        return measurementInfo;
    }

    public void setMeasurementInfo(MeasurementInfo measurementInfo)
    {
        this.measurementInfo = measurementInfo;
    }

    public LocationInfo getLocationInfo()
    {
        return locationInfo;
    }

    public void setLocationInfo(LocationInfo locationInfo)
    {
        this.locationInfo = locationInfo;
    }

    public NetworkInfo getNetworkInfo()
    {
        return networkInfo;
    }

    public void setNetworkInfo(NetworkInfo networkInfo)
    {
        this.networkInfo = networkInfo;
    }

    public SystemInfo getSystemInfo()
    {
        return systemInfo;
    }

    public void setSystemInfo(SystemInfo systemInfo)
    {
        this.systemInfo = systemInfo;
    }
}
