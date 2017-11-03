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

package org.morrise.core.system.character.me;

import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.CharacterState;
import org.morrise.api.system.BaseSystem;

/**
 * Created by bmorrise on 10/4/17.
 */
public class MeSystem<T extends Character> extends BaseSystem<T> {

  public static final String KEYWORD = "me";

  public MeSystem( T entity ) {
    super( entity );
  }

  @Override
  public String getType() {
    return null;
  }

  @Override
  public String getKeyword() {
    return KEYWORD;
  }

  @Override
  public void operate() {
    if ( entity.getState().equals( CharacterState.SITTING ) ) {
      if ( entity.getEnergy() < 100 ) {
        entity.setEnergy( entity.getEnergy() + .01 );
      }
    }
    entity.setEnergy( entity.getEnergy() - .001 );
    super.operate();
  }
}
