package org.morrise.api.models.character;

import java.util.List;
import java.util.UUID;

/**
 * Created by bmorrise on 10/9/17.
 */
public interface CharacterContainer {
  List<Character> getCharacters();

  void setCharacters( List<Character> characters );

  Character getCharacterByName( String name );

  Character getCharacterByUsername( String username );

  Character getCharacterByClientId( String clientId );

  void removeCharacter( Character character );

  List<CharacterContainer> getCharacterContainers();

  void setCharacterContainers( List<CharacterContainer> characterContainers );

  void addCharactable( CharacterContainer characterContainer );

  CharacterContainer getParent();

  CharacterContainer getRoot();

  String getName();
}
