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

package org.morrise.core.system.powered.sensors;

import org.morrise.api.system.annotation.System;
import org.morrise.api.system.powered.BasePoweredSystemable;
import org.morrise.api.system.powered.PoweredSystem;

/**
 * Created by bmorrise on 9/26/17.
 */
@System
public class SensorsSystem<T extends BasePoweredSystemable> extends PoweredSystem<T> {

  public static final String TYPE = "sensors";
  public static final String KEYWORD = "sensors";

  public SensorsSystem( T entity ) {
    super( entity, 50, 100 );
  }

  @Override
  public String getType() {
    return TYPE;
  }

  @Override
  public String getKeyword() {
    return KEYWORD;
  }

  @Override
  public String getDescription() {
    return "Sensors";
  }

  @Override
  public void operate() {

  }
}
