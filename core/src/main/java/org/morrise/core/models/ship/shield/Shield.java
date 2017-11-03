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

package org.morrise.core.models.ship.shield;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.morrise.api.models.State;
import org.morrise.api.models.item.BaseItem;

/**
 * Created by bmorrise on 9/15/17.
 */
public abstract class Shield extends BaseItem {

  private int strength;

  public Shield( int strength ) {
    this.strength = strength;
    setState( ShieldState.DOWN );
  }

  public int getStrength() {
    return strength;
  }

  public void setStrength( int strength ) {
    this.strength = strength;
  }

  public void up() {
    setState( ShieldState.UP );
  }

  public void down() {
    setState( ShieldState.DOWN );
  }

  public boolean isUp() {
    return getState().equals( ShieldState.UP );
  }

  public boolean isDown() {
    return getState().equals( ShieldState.DOWN );
  }

  public boolean isMalfunction() {
    return getState().equals( ShieldState.MALFUNCTION );
  }

  @Override
  public String getType() {
    return null;
  }

  @Override
  @JsonDeserialize(as = ShieldState.class)
  public State getState() {
    return super.getState();
  }

  @Override
  public void setState( State state ) {
    super.setState( state );
  }

  private enum ShieldState implements State {
    UP("up"),
    DOWN("down"),
    MALFUNCTION("malfunction");

    private String description;

    ShieldState( String description ) {
      this.description = description;
    }

    @Override
    public String toString() {
      return description;
    }
  }
}
