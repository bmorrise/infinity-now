package org.morrise.api.system.powered;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.morrise.api.models.State;

/**
 * Created by bmorrise on 5/16/18.
 */
public enum PowerSourceState implements State {
  CHARGED;
}
