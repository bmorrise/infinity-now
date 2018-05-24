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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.morrise.api.messages.CommandMessage;
import org.morrise.api.models.BaseEntity;
import org.morrise.api.models.character.Character;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.api.system.common.exception.InvalidAccessException;
import org.reflections.Reflections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bmorrise on 9/21/17.
 */
public abstract class BaseSystemable extends BaseEntity implements Systemable, Commandable {

  private static Map<Class<? extends System>, List<Class<?>>> commands = new ConcurrentHashMap<>();

//  static {
//    initCommands();
//  }

  protected List<System<? extends Systemable>> systems = new ArrayList<>();

  public BaseSystemable() {
  }

  public BaseSystemable( String name ) {
    super( name );
  }

  private void initSystems() {
    Reflections reflections = new Reflections( "org.morrise" );
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith( org.morrise.api.system.annotation.System.class );
    annotated.forEach( clazz -> {
      try {
        System<?> system = (System) clazz.getDeclaredConstructor( this.getClass() ).newInstance( this );
        addSystem( system );
      } catch ( Exception ignored ) {
        // Ignore systems that aren't part of this systemable
      }
    } );
  }

  public void init() {
    initSystems();
  }

//  private static void initCommands() {
//    Reflections reflections = new Reflections( "org.morrise" );
//    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith( Command.class );
//    annotated.forEach( clazz -> {
//      Command command = clazz.getAnnotation( Command.class );
//      List<Class<?>> classList = commands.getOrDefault( command.system(), new ArrayList<>() );
//      classList.add( clazz );
//      commands.put( command.system(), classList );
//    } );
//  }

//  private void addCommands( System system ) {
//    List<Class<?>> classList = commands.getOrDefault( system.getClass(), Collections.emptyList() );
//    classList.forEach( clazz -> {
//      try {
//        BaseCommand command = (BaseCommand) clazz.getDeclaredConstructor( system.getClass() ).newInstance( system );
//        Command command1 = command.getClass().getAnnotation( Command.class );
//        command.setCommands( Arrays.asList( command1.validCommands() ) );
//        command.setKeywords( Arrays.asList( command1.keywords() ) );
//      } catch ( Exception e ) {
//        e.printStackTrace();
//      }
//    } );
//  }

  @Override
  public void addSystem( System<? extends Systemable> system ) {
//    addCommands( system );
    systems.add( system );
  }

  @Override
  public boolean hasSystem( String type ) {
    return getSystemByType( type ) != null;
  }

  @Override
  public boolean isSystem( String keyword ) {
    return systems.stream()
        .anyMatch( system -> system.getKeyword().equals( keyword ) );
  }

  @Override
  public System getSystemByType( String type ) {
    return systems.stream()
        .filter( system -> system.isType( type ) )
        .findFirst()
        .orElse( null );
  }

  @Override
  public System getSystemByKeyword( String keyword ) {
    return systems.stream()
        .filter( system -> system.getKeyword().equals( keyword ) )
        .findFirst()
        .orElse( null );

  }

  @Override
  public <T> List<T> getSystemsByType( Class<T> clazz ) {
    List<T> list = new ArrayList<>();
    for ( System system : getSystems() ) {
      if ( clazz.isAssignableFrom( system.getClass() ) ) {
        T t = clazz.cast( system );
        list.add( t );
      }
    }
    return list;
  }

  @Override
  @JsonIgnore
  public List<System<? extends Systemable>> getSystems() {
    return systems;
  }

  @Override
  @JsonIgnore
  public Map<String, List<String>> getKeywords() {
    Map<String, List<String>> keywords = new TreeMap<>();
    systems.forEach( system -> keywords.put( system.getKeyword(), system.getCommands() ) );
    return keywords;
  }

  @Override
  public void operate() {
    systems.forEach( System::operate );
  }

  @Override
  public boolean executeCommand( Character character, CommandMessage commandMessage ) throws InvalidAccessException {
    System system = getSystemByKeyword( commandMessage.getSystemKeyword() );
    return system.executeCommand( character, commandMessage );
  }

  @Override
  public boolean isValidCommand( String keyword ) {
    return getSystemByKeyword( keyword ) != null;
  }
}
