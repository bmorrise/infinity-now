package org.morrise.api.models.character;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.morrise.api.InheritanceTypeIdResolver;

/**
 * Created by bmorrise on 5/10/18.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeIdResolver(InheritanceTypeIdResolver.class)
public interface Rank {
  String getName();
}
