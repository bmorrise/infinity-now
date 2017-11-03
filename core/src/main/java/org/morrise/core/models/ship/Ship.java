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

package org.morrise.core.models.ship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.morrise.api.models.State;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.Characterable;
import org.morrise.api.models.item.Item;
import org.morrise.api.models.item.Itemable;
import org.morrise.api.system.System;
import org.morrise.api.system.powered.BasePoweredSystemable;
import org.morrise.core.models.ship.structure.Deck;
import org.morrise.core.models.ship.structure.Room;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by bmorrise on 9/4/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ship extends BasePoweredSystemable implements Characterable, Itemable {

  protected static final Logger logger = LogManager.getLogger( Ship.class );

  private List<Character> characters = new ArrayList<>();
  private List<Characterable> characterables = new ArrayList<>();
  private List<Item> items = new ArrayList<>();
  private List<Deck> decks = new ArrayList<>();
  private State state;

  public Ship() {

  }

  public Ship( String name ) {
    super( name );
  }

  private void initSystems() {
    Reflections reflections = new Reflections( "org.morrise" );
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith( org.morrise.api.system.annotation.System.class );
    annotated.forEach( clazz -> {
      try {
        System<?> system = (System) clazz.getDeclaredConstructor( BasePoweredSystemable.class ).newInstance( this );
        addSystem( system );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    } );
  }

  public void addCharacter( Character character ) {
    characters.add( character );
  }

  public List<Character> getCharacters() {
    return characters;
  }

  public void setCharacters( List<Character> characters ) {
    this.characters = characters;
  }

  public Character getCharacterByName( String name ) {
    return characters.stream().filter( character -> character.getFullName().equals( name ) ).findFirst().orElse( null );
  }

  public Character getCharacterByUsername( String username ) {
    return characters.stream().filter( character -> character.getUsername().equals( username ) ).findFirst().orElse(
            null );
  }

  public Character getCharacterByClientId( UUID clientId ) {
    return characters.stream()
            .filter( character -> character.getUuid() != null && character.getUuid().equals( clientId ) )
            .findFirst()
            .orElse( null );
  }

  public List<Characterable> getCharacterables() {
    return characterables;
  }

  public void setCharacterables( List<Characterable> characterables ) {
    this.characterables = characterables;
  }

  public void addCharactable( Characterable characterable ) {
    this.characterables.add( characterable );
  }

  @Override
  public Characterable getParent() {
    return null;
  }

  @Override
  public Characterable getRoot() {
    return this;
  }

  public void addAccess( String type, Character character ) {
    getSystemByType( type ).addAccess( character );
  }

  public boolean hasAccess( System system, Character character ) {
    return system.hasAccess( character );
  }

  @Override
  public List<String> getCommands() {
    return null;
  }

  @Override
  public List<Item> getItems() {
    return items;
  }

  @Override
  public void setItems( List<Item> items ) {
    this.items = items;
  }

  @Override
  public void addItem( Item item ) {
    items.add( item );
  }

  @Override
  public void removeItem( Item item ) {
    items.remove( item );
  }

  public State getState() {
    return state;
  }

  public void setState( State state ) {
    this.state = state;
  }

  public List<Deck> getDecks() {
    return decks;
  }

  public void setDecks( List<Deck> decks ) {
    this.decks = decks;
  }

  @Override
  public void operate() {
    super.operate();
    characters.forEach( Character::operate );
  }

  public Room getRoomByName( String name ) {
    for ( Deck deck : decks ) {
      Room room = deck.getRoomByName( name );
      if ( room != null ) {
        return room;
      }
    }
    return null;
  }

  public void init() {
    initSystems();
    final Ship ship = this;
    decks.forEach( deck -> {
      deck.setCharacterable( ship );
      deck.init();
    } );
  }

  @Override
  public void removeCharacter( Character character ) {
    characters.remove( character );
  }

  public List<String> getPossible( Character character ) {
    return null;
  }
}
