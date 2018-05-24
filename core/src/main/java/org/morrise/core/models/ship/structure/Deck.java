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

package org.morrise.core.models.ship.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.morrise.api.Operatable;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.CharacterContainer;
import org.morrise.core.models.ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by bmorrise on 10/1/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deck implements Structure, CharacterContainer, Operatable {
  private List<Room> rooms = new ArrayList<>();
  private Integer number;
  private CharacterContainer characterContainer;
  private String entryPoint;

  public Deck() {
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber( Integer number ) {
    this.number = number;
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public void setRooms( List<Room> rooms ) {
    this.rooms = rooms;
  }

  @JsonIgnore
  public CharacterContainer getCharacterContainer() {
    return characterContainer;
  }

  public void setCharacterContainer( CharacterContainer characterContainer ) {
    this.characterContainer = characterContainer;
  }

  @Override
  public List<Character> getCharacters() {
    return null;
  }

  @Override
  public void setCharacters( List<Character> characters ) {

  }

  @Override
  public Character getCharacterByName( String name ) {
    return null;
  }

  @Override
  public Character getCharacterByUsername( String username ) {
    return null;
  }

  @Override
  public Character getCharacterByClientId( String clientId ) {
    return null;
  }

  @Override
  @JsonIgnore
  public List<CharacterContainer> getCharacterContainers() {
    return null;
  }

  @Override
  public void setCharacterContainers( List<CharacterContainer> characterContainers ) {

  }

  @Override
  public void addCharactable( CharacterContainer characterContainer ) {

  }

  @Override
  @JsonIgnore
  public CharacterContainer getParent() {
    return characterContainer;
  }

  @Override
  @JsonIgnore
  public CharacterContainer getRoot() {
    return getParent() != null ? getParent().getRoot() : null;
  }

  @Override
  public String getName() {
    return null;
  }

  public Room getRoomByName( String name ) {
    Room roomByName = rooms.stream()
            .filter( room -> room.getName().equalsIgnoreCase( name ) )
            .findFirst()
            .orElse( null );
    if ( roomByName == null ) {
      Ship ship = (Ship) getParent();
      roomByName = ship.getAccessibleToDecks().stream().filter( accessibleToDeck -> accessibleToDeck.getAccessibleTo().contains(
              getNumber() ) ).filter( room -> room.getName().equalsIgnoreCase( name ) )
              .findFirst().orElse( null );
    }
    return roomByName;
  }

  public <T> List<T> getRoomsByType( Class<T> clazz ) {
    List<T> list = new ArrayList<>();
    for ( Room room : rooms ) {
      if ( clazz.isAssignableFrom( room.getClass() ) ) {
        T t = clazz.cast( room );
        list.add( t );
      }
    }
    Ship ship = (Ship) getParent();
    for ( AccessibleToDeck accessibleToDeck : ship.getAccessibleToDecks() ) {
      if ( accessibleToDeck.getAccessibleTo().contains( getNumber() ) ) {
        if ( clazz.isAssignableFrom( accessibleToDeck.getClass() ) ) {
          T t = clazz.cast( accessibleToDeck );
          list.add( t );
        }
      }
    }
    return list;
  }

  public void init() {
    for ( Room room : rooms ) {
      room.setParent( this );
    }
  }

  @Override
  public void removeCharacter( Character character ) {

  }

  public void setEntryPoint( String entryPoint ) {
    this.entryPoint = entryPoint;
  }

  public String getEntryPoint() {
    return entryPoint;
  }

  @Override
  public void operate() {
    rooms.forEach( Room::operate );
  }
}
