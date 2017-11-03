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

package org.morrise.api.models.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.morrise.api.models.State;
import org.morrise.api.models.item.Item;
import org.morrise.api.models.item.Itemable;
import org.morrise.api.models.item.types.ItemType;
import org.morrise.api.system.BaseSystemable;
import org.morrise.api.system.System;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by bmorrise on 9/25/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseCharacter extends BaseSystemable implements Character, Itemable {
  private String first;
  private String last;
  private String username;
  private String password;
  private Characterable characterable;
  private UUID uuid;
  private Character.Rank rank;
  private double health;
  private double energy;
  private List<Item> items = new ArrayList<>();
  private String room;

  @JsonDeserialize(as = CharacterState.class)
  private State state;

  public BaseCharacter() {
  }

  public BaseCharacter( String first, String last, String username, String password ) {
    this.first = first;
    this.last = last;
    this.username = username;
    this.password = password;
  }

  @Override
  public List<String> getCommands() {
    return null;
  }

  @Override
  public Characterable getCharacterable() {
    return characterable;
  }

  @Override
  public void setCharacterable( Characterable characterable ) {
    this.characterable = characterable;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid( UUID uuid ) {
    this.uuid = uuid;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword( String password ) {
    this.password = password;
  }

  public String getFirst() {
    return first;
  }

  public void setFirst( String first ) {
    this.first = first;
  }

  public String getLast() {
    return last;
  }

  public void setLast( String last ) {
    this.last = last;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername( String username ) {
    this.username = username;
  }

  public String getFullName() {
    return first + " " + last;
  }

  public String getTokenName() {
    return first + "." + last;
  }

  public String getNameAndRank() {
    return rank.getName() + " " + last;
  }

  public double getHealth() {
    return health;
  }

  public void setHealth( double health ) {
    this.health = health;
  }

  public double getEnergy() {
    return energy;
  }

  public void setEnergy( double energy ) {
    this.energy = energy;
  }

  public Character.Rank getRank() {
    return rank;
  }

  public void setRank( Character.Rank rank ) {
    this.rank = rank;
  }

  public State getState() {
    return state;
  }

  public void setState( State state ) {
    this.state = state;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems( List<Item> items ) {
    this.items = items;
  }

  public void addItem( Item item ) {
    items.add( item );
  }

  public Item getItemByName( String name ) {
    return items.stream()
        .filter( i -> i.getName().toLowerCase().equals( name.toLowerCase() ) )
        .findFirst()
        .orElse( null );
  }

  public Item getItemByType( Class<? extends ItemType> clazz ) {
    return items.stream()
        .filter( i -> clazz.isAssignableFrom( i.getClass() ) )
        .findFirst()
        .orElse( null );
  }

  @Override
  public void removeItem( Item item ) {
    items.remove( item );
  }

  @Override
  public void operate() {
    systems.forEach( System::operate );
  }

  @Override
  public String toString() {
    return "BaseCharacter{" +
        "first='" + first + '\'' +
        ", last='" + last + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", characterable=" + characterable +
        ", uuid=" + uuid +
        ", rank=" + rank +
        ", health=" + health +
        ", energy=" + energy +
        ", items=" + items +
        ", state=" + state +
        '}';
  }

  public String getRoom() {
    return room;
  }

  public void setRoom( String room ) {
    this.room = room;
  }
}
