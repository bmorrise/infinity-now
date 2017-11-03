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

package org.morrise.core.models.item.furniture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.morrise.api.models.State;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.CharacterState;
import org.morrise.api.models.item.BaseItem;
import org.morrise.api.models.item.Callback;
import org.morrise.api.models.item.types.Sitable;

/**
 * Created by bmorrise on 10/6/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Chair extends BaseItem implements Sitable {

  public static final String TYPE = "chair";

  public Chair() {
    setState( ChairState.UNOCCUPIED );
  }

  @Override
  public boolean use( Character character ) {
    character.addItem( this );
    character.setState( CharacterState.SITTING );
    setState( ChairState.OCCUPIED );
    return true;
  }

  @Override
  public boolean unuse( Character character ) {
    setState( Chair.ChairState.UNOCCUPIED );
    character.setState( CharacterState.STANDING );
    character.removeItem( this );
    return true;
  }

  @Override
  public String getType() {
    return TYPE;
  }

  public enum ChairState implements State {
    OCCUPIED,
    UNOCCUPIED;
  }
}
