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

package org.morrise.ship;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.morrise.api.models.character.BaseCharacter;
import org.morrise.api.models.character.Character;
import org.morrise.core.models.ship.Ship;
import org.morrise.core.models.ship.structure.Room;
import org.morrise.core.system.character.me.MeSystem;

import java.io.File;
import java.io.IOException;

/**
 * Created by bmorrise on 10/12/17.
 */
public class ShipLoader {

  public static void main( String[] args ) throws Exception {
  }

  public Ship load( String filename ) {
    ObjectMapper mapper = new ObjectMapper( new YAMLFactory() );
    Ship ship = null;
    try {
      ship = mapper.readValue( new File( filename ), Ship.class );
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    ship.init();
    for ( Character character : ship.getCharacters() ) {
      character.addSystem( new MeSystem<>( character ) );
      Room room = ship.getRoomByName( ((BaseCharacter) character).getRoom() );
      character.setCharacterable( room );
      room.addCharacter( character );
    }

    return ship;
  }

}
