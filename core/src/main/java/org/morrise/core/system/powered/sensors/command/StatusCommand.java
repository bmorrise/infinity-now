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

package org.morrise.core.system.powered.sensors.command;

import org.morrise.api.messages.MainFrame;
import org.morrise.api.models.character.Character;
import org.morrise.api.system.System;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.models.ship.Ship;
import org.morrise.core.system.powered.sensors.SensorsSystem;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bmorrise on 9/26/17.
 */
@Command(system = SensorsSystem.class, keywords = {"status"})
public class StatusCommand extends BaseCommand<SensorsSystem> {

  public StatusCommand( SensorsSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    Ship ship = (Ship) character.getCharacterContainer().getRoot();
    //Todo: CharacterContainer show all know status
    StringBuilder status = new StringBuilder();
    if ( arguments.length == 0 ) {
      status.append( "Room: " );
      status.append( character.getCharacterContainer().getName() );
      status.append( "\n" );
      character.getCharacterContainer().getCharacters().forEach( character1 -> {
        status.append( character1.getNameAndRank() );
        status.append( "\n" );
      } );
//      ship.getSystems().forEach( system -> {
//        showStatus( system, status );
//      } );
    } else if ( arguments.length == 1 ) {
//      System system = ship.getSystemByType( arguments[0].toLowerCase() );
//      if ( system != null ) {
//        showStatus( system, status );
//      } else {
//        status.append( system );
//        status.append( " not found" );
//        status.append( "\n" );
//      }
    }
    logger.info( "Processing status command..." );
    system.sendMessage( character, MainFrame.SECONDARY, status.toString() );
    return true;
  }

  private void showStatus( System system, StringBuilder status ) {
    status.append( "<div class=\"system-status " ).append( clean( system.getStatus().toString() ) ).append( "\">" );
    status.append( system.getDescription() );
    status.append( ": " );
    status.append( system.getStatus() != null ? system.getStatus() : "Unknown" );
    status.append( "\n" );
    status.append( system.getInfo() );
    status.append( "\n" );
    status.append( "</div>" );
  }

  private static String clean( String string ) {
    string = string.replace( " ", "" ).toLowerCase();
    return string;
  }
}
