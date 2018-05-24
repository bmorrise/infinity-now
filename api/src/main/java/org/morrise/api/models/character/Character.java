package org.morrise.api.models.character;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.morrise.api.InheritanceTypeIdResolver;
import org.morrise.api.models.State;
import org.morrise.api.models.item.Itemable;
import org.morrise.api.system.Commandable;
import org.morrise.api.system.Systemable;

import java.util.List;

/**
 * Created by bmorrise on 10/8/17.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeIdResolver(InheritanceTypeIdResolver.class)
public interface Character extends Systemable, Commandable, Itemable {
  CharacterContainer getCharacterContainer();

  void setCharacterContainer( CharacterContainer characterContainer );

  String getUuid();

  void setUuid( String uuid );

  String getPassword();

  void setPassword( String password );

  String getFirst();

  void setFirst( String first );

  String getLast();

  void setLast( String last );

  String getUsername();

  void setUsername( String username );

  @JsonIgnore
  String getFullName();

  @JsonIgnore
  String getTokenName();

  @JsonIgnore
  String getNameAndRank();

  double getHealth();

  void setHealth( double health );

  double getEnergy();

  void setEnergy( double energy );

  Rank getRank();

  void setRank( Rank rank );

  State getState();

  void setState( State state );

  void setRoom( String room );

  String getRoom();

  List<String> getPossible();
}
