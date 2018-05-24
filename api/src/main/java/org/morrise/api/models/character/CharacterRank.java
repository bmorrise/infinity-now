package org.morrise.api.models.character;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by bmorrise on 5/10/18.
 */
public enum CharacterRank implements Rank {
  ADMIRAL( "Admiral" ),
  CAPTAIN( "Captain" ),
  COMMANDER( "Commander" ),
  LIEUTENANT_COMMANDER( "Lieutenant Commander" ),
  LIEUTENANT( "Lieutenant" ),
  ENSIGN( "Ensign" );

  private String name;

  CharacterRank( String name ) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
