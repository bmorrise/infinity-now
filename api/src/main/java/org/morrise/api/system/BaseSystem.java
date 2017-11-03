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

package org.morrise.api.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.morrise.api.messages.CommandMessage;
import org.morrise.api.messages.MessageBroker;
import org.morrise.api.models.character.Character;
import org.morrise.api.system.capability.Capability;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.common.exception.InvalidAccessException;
import org.morrise.api.system.status.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by bmorrise on 9/19/17.
 */
public abstract class BaseSystem<T extends Systemable> implements System<T> {

  protected static final Logger logger = LogManager.getLogger( BaseSystem.class );
  protected Status status;
  protected T entity;
  protected List<BaseCommand> commands = new ArrayList<>();

  public BaseSystem( T entity ) {
    this.entity = entity;
  }

  @Override
  public boolean executeCommand( Character character, CommandMessage commandMessage ) throws InvalidAccessException {
    BaseCommand command = commands.stream()
        .filter( baseCommand -> baseCommand.getKeywords().contains( commandMessage.getKeyword() ) )
        .findFirst()
        .orElse( null );
    return command != null && command.execute( character, commandMessage );
  }

  @Override
  public Status getStatus() {
    return status;
  }

  @Override
  public void setStatus( Status status ) {
    this.status = status;
  }

  @Override
  public boolean isValidCommand( String Keyword ) {
    return false;
  }

  @Override
  public String getType() {
    return null;
  }

  @Override
  public List<String> getCommands() {
    List<String> commandsStrings = new ArrayList<>();
    for ( BaseCommand<?> command : commands ) {
      commandsStrings.addAll( command.getKeywords() );
    }
    return commandsStrings;
  }

  @Override
  public boolean isType( String type ) {
    return getType().equals( type );
  }

  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public List<Capability> getCapabilities() {
    return null;
  }

  @Override
  public Capability getCapability( String type ) {
    return null;
  }

  @Override
  public T getSystemable() {
    return entity;
  }

  @Override
  public void setSystemable( T value ) {
    this.entity = value;
  }

  @Override
  public void connect( T systemable ) {

  }

  @Override
  public void addCommand( BaseCommand command ) {
    commands.add( command );
  }

  @Override
  public void operate() {

  }

  @Override
  public boolean hasAccess( Character character ) {
    return false;
  }

  @Override
  public void addAccess( Character character ) {

  }

  @Override
  public String getKeyword() {
    return null;
  }

  public MessageBroker getMessageBroker() {
    return MessageBroker.getInstance();
  }

  public void sendMessage( Character character, String item, Object message ) {
    getMessageBroker().sendEventSingle( character.getUuid(), item, message );
  }

  @Override
  public String getInfo() {
    return getDescription() + " has no info";
  }
}
