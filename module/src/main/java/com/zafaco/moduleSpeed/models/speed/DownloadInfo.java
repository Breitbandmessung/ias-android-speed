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

public class DownloadInfo extends BandwidthResult
{
    public DownloadInfo(long bytes, long bytesDistinct, long bytesTotal, long bytesSlowStart, long durationNs, long durationNsDistinct, long durationNsTotal, long relativeTimeNsMeasurementStart, int numStreamsEnd, int numStreamsStart, float progress, long throughputAvgBps)
    {
        super(bytes, bytesDistinct, bytesTotal, bytesSlowStart, durationNs, durationNsDistinct, durationNsTotal, relativeTimeNsMeasurementStart, numStreamsEnd, numStreamsStart, progress, throughputAvgBps);
    }
}

