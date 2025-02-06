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

public class SingleRttResult
{
    private Long rttNs;

    private Long relativeTimeNsMeasurementStart;

    public SingleRttResult()
    {

    }

    public SingleRttResult(Long rttNs, Long relativeTimeNsMeasurementStart)
    {
        this.rttNs                          = rttNs;
        this.relativeTimeNsMeasurementStart = relativeTimeNsMeasurementStart;
    }

    public Long getRttNs()
    {
        return rttNs;
    }

    @Keep
    public void setRttNs(Long rttNs)
    {
        this.rttNs = rttNs;
    }

    public Long getRelativeTimeNsMeasurementStart()
    {
        return relativeTimeNsMeasurementStart;
    }

    @Keep
    public void setRelativeTimeNsMeasurementStart(Long relativeTimeNsMeasurementStart)
    {
        this.relativeTimeNsMeasurementStart = relativeTimeNsMeasurementStart;
    }

    @Override
    public String toString()
    {
        return "SingleRtt{" + "rttNs=" + rttNs + ", relativeTimeNsMeasurementStart=" + relativeTimeNsMeasurementStart + '}';
    }
}
