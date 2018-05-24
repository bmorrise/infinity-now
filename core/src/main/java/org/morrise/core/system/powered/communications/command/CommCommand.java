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

package org.morrise.core.system.powered.communications.command;

import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.CharacterContainer;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.api.system.status.SystemStatus;
import org.morrise.core.system.powered.communications.CommSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bmorrise on 9/27/17.
 */
@Command(system = CommSystem.class)
public class CommCommand extends BaseCommand<CommSystem> {

  public static final List<String> KEYWORDS = Arrays.asList( "start", "shutdown" );

  public CommCommand( CommSystem system ) {
    super( system );
  }

  @Override
  public List<String> getKeywords() {
    List<String> keywords = new ArrayList<>();
    ((CharacterContainer) system.getSystemable().getStructure()).getCharacters().forEach( character -> keywords.add(
            character.getFullName() ) );
    keywords.addAll( KEYWORDS );
    return keywords;
  }

  @Override
  public List<String> getValidCommands() {
    List<String> commands = new ArrayList<>();
    getKeywords().forEach( keyword -> commands.add( '(' + keyword + ')' ) );
    commands.add( "(.*)" );
    return commands;
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    if ( arguments != null ) {
      String command = arguments[0];
      switch ( command ) {
        case "start":
          system.sendMessage( character, "Starting Communications" );
          system.setStatus( SystemStatus.OPERATIONAL );
          break;
        case "shutdown":
          system.sendMessage( character, "Shutting Down Communications" );
          system.setStatus( SystemStatus.INACTIVE );
          break;
        default:
          Character toCharacter = ((CharacterContainer) system.getSystemable()).getCharacterByName( arguments[0] );
          if ( toCharacter != null ) {
            system.sendSingle( character, toCharacter, arguments[1] );
          }
      }
      return true;
    }
    return false;
  }

  @Override
  public boolean getIncludeKeyword() {
    return true;
  }
}
