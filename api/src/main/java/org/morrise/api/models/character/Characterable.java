package org.morrise.api.models.character;

import java.util.List;
import java.util.UUID;

/**
 * Created by bmorrise on 10/9/17.
 */
public interface Characterable {
  List<Character> getCharacters();

  void setCharacters( List<Character> characters );

  Character getCharacterByName( String name );

  Character getCharacterByUsername( String username );

  Character getCharacterByClientId( UUID clientId );

  void removeCharacter( Character character );

  List<Characterable> getCharacterables();

  void setCharacterables( List<Characterable> characterables );

  void addCharactable( Characterable characterable );

  Characterable getParent();

  Characterable getRoot();

  String getName();
}
