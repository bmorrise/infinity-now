package org.morrise.core.models.ship.structure;

import java.util.List;

/**
 * Created by bmorrise on 5/24/18.
 */
public interface Structure {
  Room getRoomByName( String name );
  <T> List<T> getRoomsByType( Class<T> clazz );
}
