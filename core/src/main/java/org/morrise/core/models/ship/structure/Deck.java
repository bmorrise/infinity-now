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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.Characterable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by bmorrise on 10/1/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deck implements Characterable {
  private List<Room> rooms = new ArrayList<>();
  private Integer number;
  private Characterable characterable;

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

  public Characterable getCharacterable() {
    return characterable;
  }

  public void setCharacterable( Characterable characterable ) {
    this.characterable = characterable;
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
  public Character getCharacterByClientId( UUID clientId ) {
    return null;
  }

  @Override
  public List<Characterable> getCharacterables() {
    return null;
  }

  @Override
  public void setCharacterables( List<Characterable> characterables ) {

  }

  @Override
  public void addCharactable( Characterable characterable ) {

  }

  @Override
  public Characterable getParent() {
    return characterable;
  }

  @Override
  public Characterable getRoot() {
    return getParent() != null ? getParent().getRoot() : null;
  }

  @Override
  public String getName() {
    return null;
  }

  public Room getRoomByName( String name ) {
    return rooms.stream()
            .filter( room -> room.getName().toLowerCase().equals( name.toLowerCase() ) )
            .findFirst()
            .orElse( null );
  }

  public void init() {
    for ( Room room : rooms ) {
      room.setParent( this );
    }
  }

  @Override
  public void removeCharacter( Character character ) {

  }
}
