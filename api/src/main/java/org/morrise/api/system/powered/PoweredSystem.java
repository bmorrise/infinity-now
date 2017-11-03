/*
 * Copyright 2017 Benjamin Morrise
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.morrise.api.system.powered;

import org.morrise.api.system.BaseSystem;
import org.morrise.api.system.Systemable;
import org.morrise.api.system.status.SystemStatus;

/**
 * Created by bmorrise on 9/27/17.
 */
public abstract class PoweredSystem<T extends Systemable> extends BaseSystem<T> {

  private PowerSource powerSource;
  private int minimum;
  private int maximum;
  private int allocation;

  public PoweredSystem( T entity ) {
    super( entity );
    connect( entity );
  }

  public PoweredSystem( T entity, int minimum, int maximum ) {
    this( entity );
    this.minimum = minimum;
    this.maximum = maximum;
  }

  public PowerSource getPowerSource() {
    return powerSource;
  }

  public void setPowerSource( PowerSource powerSource ) {
    this.powerSource = powerSource;
  }

  public int getMinimum() {
    return minimum;
  }

  public int getMaximum() {
    return maximum;
  }

  public int getAllocation() {
    return allocation;
  }

  public void setAllocation( int allocation ) {
    this.allocation = allocation;
  }

  public void connect( PowerSource powerSource ) {
    if ( powerSource.getAvailable() > getMinimum() ) {
      powerSource.addConnection( this, minimum );
      status = SystemStatus.OPERATIONAL;
    } else {
      status = SystemStatus.LOW_POWER;
    }
  }
}
