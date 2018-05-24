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

package org.morrise.api.system.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.morrise.api.messages.CommandMessage;
import org.morrise.api.models.character.Character;
import org.morrise.api.system.System;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bmorrise on 9/4/17.
 */
public abstract class BaseCommand<T extends System> {
  protected static final Logger logger = LogManager.getLogger( BaseCommand.class );
  protected T system;
  private List<String> keywords;
  private List<String> commands;

  public BaseCommand( T system ) {
    this.system = system;
  }

  public String getExample() {
    return "";
  }

  public List<String> getKeywords() {
    return Arrays.asList( this.getClass().getAnnotation( Command.class ).keywords() );
  }

  public List<String> getValidCommands() {
    return Arrays.asList( this.getClass().getAnnotation( Command.class ).validCommands() );
  }

  public void setKeywords( List<String> keywords ) {
    this.keywords = keywords;
  }

  public void setCommands( List<String> commands ) {
    this.commands = commands;
  }

  public abstract boolean process( Character character, String... arguments );

  public boolean isKeyword( String keyword ) {
    return getKeywords().contains( keyword );
  }

  public boolean isValid( String input ) {
    return getValidCommands().stream().anyMatch( valid -> Pattern.compile( valid ).matcher( input.toLowerCase() )
        .matches() );
  }

  public boolean execute( Character character, CommandMessage commandMessage ) {
    List<String> parameters = new ArrayList<>();
    if ( getIncludeKeyword() ) {
      parameters.add( commandMessage.getKeyword() );
    }
    parameters.addAll( getParameters( commandMessage.getPayload() ) );
    return process( character, parameters.toArray( new String[parameters.size()] ) );
  }

  protected List<String> getParameters( String command ) {
    if ( command != null ) {
      for ( String valid : getValidCommands() ) {
        Matcher matcher = Pattern.compile( valid ).matcher( command );
        if ( matcher.matches() ) {
          List<String> parameters = new ArrayList<>();
          for ( int i = 1; i < matcher.groupCount() + 1; i++ ) {
            if ( !matcher.group( i ).equals( "" ) ) {
              parameters.add( matcher.group( i ) );
            }
          }
          return parameters;
        }
      }
    }
    return Collections.singletonList( command );
  }

  protected T getSystem() {
    return system;
  }

  public boolean getIncludeKeyword() {
    return false;
  }
}
