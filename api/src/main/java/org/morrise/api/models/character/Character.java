package org.morrise.api.models.character;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.morrise.api.models.State;
import org.morrise.api.models.item.Itemable;
import org.morrise.api.system.Commandable;
import org.morrise.api.system.Systemable;

import java.util.UUID;

/**
 * Created by bmorrise on 10/8/17.
 */
@JsonDeserialize(as = BaseCharacter.class)
public interface Character extends Systemable, Commandable, Itemable {
  Characterable getCharacterable();

  void setCharacterable( Characterable characterable );

  UUID getUuid();

  void setUuid( UUID uuid );

  String getPassword();

  void setPassword( String password );

  String getFirst();

  void setFirst( String first );

  String getLast();

  void setLast( String last );

  String getUsername();

  void setUsername( String username );

  String getFullName();

  String getTokenName();

  String getNameAndRank();

  double getHealth();

  void setHealth( double health );

  double getEnergy();

  void setEnergy( double energy );

  Rank getRank();

  void setRank( Rank rank );

  State getState();

  void setState( State state );

  public enum Rank {
    ADMIRAL( "Admiral" ),
    CAPTAIN( "Captain" ),
    COMMANDER( "Commander" ),
    LIEUTENANT_COMMANDER( "Lieutenant Commander" ),
    LIEUTENANT( "Lieutenant" ),
    ENSIGN( "Ensign" );

    private String name;

    Rank( String name ) {
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
}
