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

package org.morrise.core.system.powered.shields;

import org.morrise.api.models.item.Itemable;
import org.morrise.api.system.annotation.System;
import org.morrise.api.system.powered.BasePoweredSystemable;
import org.morrise.api.system.powered.PoweredSystem;
import org.morrise.api.system.status.SystemStatus;
import org.morrise.core.models.ship.shield.Shield;

/**
 * Created by bmorrise on 9/21/17.
 */
@System
public class ShieldsSystem<T extends BasePoweredSystemable> extends PoweredSystem<T> {
  public static String TYPE = "shields";
  public static String KEYWORD = "shields";
  private Shield shield;

  public ShieldsSystem( T entity ) {
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
    return "Shields System";
  }

  @Override
  public void connect( T systemable ) {
    Shield shield = (Shield) ((Itemable) systemable).getItemByType( Shield.class );
    if ( shield != null ) {
      this.shield = shield;
    }
    setStatus( SystemStatus.OPERATIONAL );
  }

  public Shield getShield() {
    return shield;
  }

  public void setShield( Shield shield ) {
    this.shield = shield;
  }

  @Override
  public String getInfo() {
    return "The shields are " + shield.getState();
  }
}
