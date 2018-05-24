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

package org.morrise.api.models.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.morrise.api.models.State;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.item.types.Eatable;

/**
 * Created by bmorrise on 10/6/17.
 */
public abstract class FoodItem extends BaseItem implements Eatable {

  public FoodItem() {
    setState( FoodState.FULL );
  }

  @Override
  public boolean use( Character character ) {
    new Thread( () -> {
      doAction( character );
    } ).start();
    return true;
  }

  protected void doAction( Character character ) {
    setState( FoodState.PENDING );
    double energy = character.getEnergy();
    if ( energy < 100 ) {
      try {
        Thread.sleep( getDuration() );
      } catch ( Exception e ) {
        // Do nothing
      }
      energy += getEnergy();
      character.setEnergy( energy );
      setState( FoodState.EATEN );
    } else {
      setState( FoodState.FULL );
    }
  }

  @Override
  public State getState() {
    return super.getState();
  }

  @Override
  public void setState( State state ) {
    super.setState( state );
  }

  public enum FoodState implements State {
    FULL,
    PENDING,
    EATEN;
  }
}
