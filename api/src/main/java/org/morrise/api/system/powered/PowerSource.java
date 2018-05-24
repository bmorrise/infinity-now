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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.morrise.api.InheritanceTypeIdResolver;
import org.morrise.api.models.State;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bmorrise on 9/27/17.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeIdResolver(InheritanceTypeIdResolver.class)
public class PowerSource {
  private int units;
  private Map<PoweredSystem, Integer> connections = new HashMap<>();

  private State state;

  public PowerSource() {

  }

  public PowerSource( int units ) {
    this.units = units;
    setState( PowerSourceState.CHARGED );
  }

  public static int sum( Collection<Integer> list ) {
    int sum = 0;
    for ( int i : list ) {
      sum += i;
    }
    return sum;
  }

  public int getUnits() {
    return units;
  }

  public void setUnits( int units ) {
    this.units = units;
  }

  public void addConnection( PoweredSystem system, Integer allocation ) {
    connections.put( system, allocation );
  }

  @JsonIgnore
  public int getAvailable() {
    return getUnits() - sum( connections.values() );
  }

  public boolean isConnected( PoweredSystem system ) {
    return connections.get( system ) != null;
  }

  public State getState() {
    return state;
  }

  public void setState( State state ) {
    this.state = state;
  }
}
