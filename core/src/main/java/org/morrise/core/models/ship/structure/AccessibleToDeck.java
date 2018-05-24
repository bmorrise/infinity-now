package org.morrise.core.models.ship.structure;

import java.util.List;

/**
 * Created by bmorrise on 5/18/18.
 */
public class AccessibleToDeck extends Room {
  private List<Integer> accessibleTo;

  public List<Integer> getAccessibleTo() {
    return accessibleTo;
  }

  public void setAccessibleTo( List<Integer> accessibleTo ) {
    this.accessibleTo = accessibleTo;
  }
}
