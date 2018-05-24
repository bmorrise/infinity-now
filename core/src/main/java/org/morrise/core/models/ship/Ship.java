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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.morrise.api.Operatable;
import org.morrise.api.models.State;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.CharacterContainer;
import org.morrise.api.models.item.Item;
import org.morrise.api.models.item.Itemable;
import org.morrise.core.models.ship.structure.AccessibleToDeck;
import org.morrise.core.models.ship.structure.Deck;
import org.morrise.core.models.ship.structure.Room;
import org.morrise.core.models.ship.structure.Structure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmorrise on 9/4/17.
 */
public class Ship implements Structure, CharacterContainer, Itemable, Operatable {

  protected static final Logger logger = LogManager.getLogger( Ship.class );

  private String name;
  private List<Character> characters = new ArrayList<>();
  private List<CharacterContainer> characterContainers = new ArrayList<>();
  private List<Item> items = new ArrayList<>();
  private List<Deck> decks = new ArrayList<>();
  private State state;
  private List<AccessibleToDeck> accessibleToDecks;
  private CentralComputer centralComputer;

  public Ship( String name ) {
    this.name = name;
  }

  public Ship() {

  }

  @Override
  public String getName() {
    return name;
  }

  public CentralComputer getCentralComputer() {
    return centralComputer;
  }

  public void setCentralComputer( CentralComputer centralComputer ) {
    this.centralComputer = centralComputer;
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

  public Character getCharacterByClientId( String clientId ) {
    return characters.stream()
            .filter( character -> character.getUuid() != null && character.getUuid().equals( clientId ) )
            .findFirst()
            .orElse( null );
  }

  public List<CharacterContainer> getCharacterContainers() {
    return characterContainers;
  }

  public void setCharacterContainers( List<CharacterContainer> characterContainers ) {
    this.characterContainers = characterContainers;
  }

  public void addCharactable( CharacterContainer characterContainer ) {
    this.characterContainers.add( characterContainer );
  }

  @Override
  @JsonIgnore
  public CharacterContainer getParent() {
    return null;
  }

  @Override
  @JsonIgnore
  public CharacterContainer getRoot() {
    return this;
  }

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

  public Deck getDeckByNumber( Integer number ) {
    return decks.stream().filter( deck -> deck.getNumber().equals( number ) ).findFirst().orElse( null );
  }

  public List<AccessibleToDeck> getAccessibleToDecks() {
    return accessibleToDecks;
  }

  public void setAccessibleToDecks( List<AccessibleToDeck> accessibleToDecks ) {
    this.accessibleToDecks = accessibleToDecks;
  }

  @Override
  public void operate() {
    characters.forEach( Character::operate );
    decks.forEach( Deck::operate );
  }

  @Override
  public Room getRoomByName( String name ) {
    for ( Deck deck : decks ) {
      Room room = deck.getRoomByName( name );
      if ( room != null ) {
        return room;
      }
    }
    return null;
  }

  @Override
  public <T> List<T> getRoomsByType( Class<T> clazz ) {
    List<T> rooms = new ArrayList<>();
    for ( Deck deck : decks ) {
      List<T> items = deck.getRoomsByType( clazz );
      for ( T item : items ) {
        if ( !rooms.contains( item ) ) {
          rooms.add( item );
        }
      }
    }
    return rooms;
  }

  public void init() {
    final Ship ship = this;
    accessibleToDecks.forEach( accessibleToDeck -> {
      accessibleToDeck.setParent( this );
    } );
    decks.forEach( deck -> {
      deck.setCharacterContainer( ship );
      deck.init();
    } );
    centralComputer = new CentralComputer( this );
    centralComputer.init();
  }

  @Override
  public void removeCharacter( Character character ) {
    characters.remove( character );
  }

  public List<String> getPossible( Character character ) {
    return null;
  }

  public boolean save() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable( SerializationFeature.INDENT_OUTPUT );
    try {
      objectMapper.writeValue( new File( "output.json" ), this );
      return true;
    } catch ( IOException e ) {
      e.printStackTrace();
    }
    return false;
  }
}
