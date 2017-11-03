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

package org.morrise.api.models;

/**
 * Created by bmorrise on 9/4/17.
 */
public class Coordinates {
  private double x;
  private double y;
  private double z;

  public Coordinates() {
  }

  public Coordinates( double x, double y, double z ) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double distance( Coordinates coordinates ) {
    return Math.sqrt( squared( coordinates.getX() - getX() ) + squared( coordinates.getY() - getY() ) + squared(
        coordinates.getZ() - getZ() ) );
  }

  private double squared( double value ) {
    return Math.pow( value, 2 );
  }

  public double getX() {
    return x;
  }

  public void setX( double x ) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY( double y ) {
    this.y = y;
  }

  public double getZ() {
    return z;
  }

  public void setZ( double z ) {
    this.z = z;
  }
}
