package org.morrise.api.models.item.types;

/**
 * Created by bmorrise on 10/7/17.
 */
public interface Eatable extends ItemType {
  int getDuration();
  int getEnergy();
}
