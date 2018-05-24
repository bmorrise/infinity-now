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
package org.morrise.core.system.powered.turbolift.command;

import org.morrise.api.messages.MainFrame;
import org.morrise.api.models.character.Character;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.models.ship.Ship;
import org.morrise.core.models.ship.structure.Deck;
import org.morrise.core.models.ship.structure.TurboLift;
import org.morrise.core.system.powered.turbolift.TurboLiftSystem;

import java.util.Collections;

/**
 * Implementation of the MoveCommand in the TurboLiftSystem
 */
@Command(system = TurboLiftSystem.class, keywords = {"move"})
public class MoveCommand extends BaseCommand<TurboLiftSystem> {

  public MoveCommand( TurboLiftSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    if ( arguments.length == 1 ) {
      TurboLift turboLift = (TurboLift) character.getCharacterContainer();
      turboLift.setAdjacent( null );
      Ship ship = (Ship) character.getCharacterContainer().getRoot();
      Deck deck = ship.getDeckByNumber( Integer.valueOf( arguments[0] ) );
      if ( deck != null ) {
        turboLift.setAdjacent( Collections.singletonList( deck.getEntryPoint() ) );
        turboLift.setParent( deck );
        turboLift.setDeck( deck.getNumber() );
        system.sendMessage( character, MainFrame.STATUS, "You are now on Deck " + deck.getNumber() );
      } else {
        system.sendMessage( character, MainFrame.STATUS, "Invalid deck number." );
      }
    } else {
      system.sendMessage( character, MainFrame.STATUS, "Invalid arguments." );
    }
    return true;
  }
}
