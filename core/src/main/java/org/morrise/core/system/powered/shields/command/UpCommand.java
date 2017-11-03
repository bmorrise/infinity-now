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

package org.morrise.core.system.powered.shields.command;

import org.morrise.api.messages.MessageBroker;
import org.morrise.api.models.character.Character;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.models.ship.shield.Shield;
import org.morrise.core.system.powered.shields.ShieldsSystem;

/**
 * Created by bmorrise on 10/4/17.
 */
@Command(system = ShieldsSystem.class, keywords = {"up"})
public class UpCommand extends BaseCommand<ShieldsSystem> {

  public UpCommand( ShieldsSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    logger.info( "Processing up command..." );
    Shield shield = system.getShield();
    if ( shield.isDown() && !shield.isMalfunction() ) {
      shield.up();
      system.sendMessage( character, MessageBroker.STATUS, "Shields up" );
    } else if ( shield.isMalfunction() ) {
      system.sendMessage( character, MessageBroker.STATUS, "Shields have a malfunction" );
    }
    return true;
  }
}
