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
import org.morrise.api.models.item.Item;
import org.morrise.api.models.item.Itemable;
import org.morrise.api.models.item.types.Settable;
import org.morrise.api.system.Systemable;

import java.util.*;

/**
 * Created by bmorrise on 10/1/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room implements Itemable, Characterable {

  private String name;
  private int width;
  private int depth;
  private String description;
  private List<Character> characters = new ArrayList<>();
  private List<Item> items = new ArrayList<>();
  private Characterable parent;
  private List<Characterable> characterables = new ArrayList<>();
  private List<String> adjacent = new ArrayList<>();
  private List<String> accessibleSystems = new ArrayList<>();

  public Room() {

  }

  public int getWidth() {
    return width;
  }

  public void setWidth( int width ) {
    this.width = width;
  }

  public int getDepth() {
    return depth;
  }

  public void setDepth( int depth ) {
    this.depth = depth;
  }

  public int getSquareFeet() {
    return width * depth;
  }

  public void addCharacter( Character character ) {
    character.setCharacterable( this );
    characters.add( character );
  }

  public List<Character> getCharacters() {
    return characters;
  }

  public void setCharacters( List<Character> characters ) {
    this.characters = characters;
  }

  @Override
  public Character getCharacterByName( String name ) {
    return characters.stream()
            .filter( character -> character.getUsername().equals( name ) )
            .findFirst()
            .orElse( null );
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
    return characterables;
  }

  @Override
  public void setCharacterables( List<Characterable> characterables ) {
    this.characterables = characterables;
  }

  @Override
  public void addCharactable( Characterable characterable ) {
    this.characterables.add( characterable );
  }

  @Override
  public Characterable getParent() {
    return parent;
  }

  public void setParent( Characterable parent ) {
    this.parent = parent;
  }

  @Override
  public Characterable getRoot() {
    return parent != null ? parent.getRoot() : null;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  @Override
  public List<Item> getItems() {
    final List<Item> full = (ArrayList) ((ArrayList) items).clone();
    for ( Item item : items ) {
      if ( item instanceof Settable ) {
        full.addAll( ((Settable) item).getItems() );
      }
    }
    return full;
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
  public Item getItemByName( String name ) {
    final List<Item> from = (ArrayList) ((ArrayList) items).clone();
    for ( Item item : items ) {
      if ( item instanceof Settable ) {
        from.addAll( ((Settable) item).getItems() );
      }
    }
    return from.stream()
            .filter( i -> i.getName().toLowerCase().equals( name.toLowerCase() ) )
            .findFirst()
            .orElse( null );
  }

  @Override
  public void removeItem( Item item ) {
    for ( Item item1 : items ) {
      if ( item1 instanceof Settable ) {
        if ( ((Settable) item1).getItems().contains( item ) ) {
          ((Settable) item1).removeItem( item );
          return;
        }
      }
    }
    items.remove( item );
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public List<String> getAdjacent() {
    return adjacent;
  }

  public void setAdjacent( List<String> adjacent ) {
    this.adjacent = adjacent;
  }

  @Override
  public void removeCharacter( Character character ) {
    characters.remove( character );
  }

  public boolean isRoom( String name ) {
    return this.name.toLowerCase().equals( name.toLowerCase() );
  }

  public List<String> getAccessibleSystems() {
    return accessibleSystems;
  }

  public void setAccessibleSystems( List<String> accessibleSystems ) {
    this.accessibleSystems = accessibleSystems;
  }

  public Map<String, List<String>> getKeywords() {
    Map<String, List<String>> keywords = new TreeMap<>();
    Characterable characterable = getRoot();
    if ( characterable instanceof Systemable ) {
      if ( accessibleSystems.size() == 1 && accessibleSystems.get( 0 ).equals( "all" ) ) {
        return ((Systemable) characterable).getKeywords();
      }
      ((Systemable) characterable).getSystems().forEach( system -> {
        if ( accessibleSystems.contains( system.getKeyword() ) ) {
          keywords.put( system.getKeyword(), system.getCommands() );
        }
      } );
    }
    return keywords;
  }

  public List<String> getPossible( Character character ) {
    List<String> possible = new ArrayList<>();
    Characterable characterable = getRoot();
    if ( characterable instanceof Systemable ) {
      ((Systemable) characterable).getSystems().forEach( system -> {
        if ( accessibleSystems.contains( system.getKeyword() ) ) {
//          possible.addAll( system.getPossible( character ) );
        }
      } );
    }
    possible.addAll( adjacent );
    return possible;
  }
}
