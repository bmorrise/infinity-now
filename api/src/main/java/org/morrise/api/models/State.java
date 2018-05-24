package org.morrise.api.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.morrise.api.InheritanceTypeIdResolver;

/**
 * Created by bmorrise on 10/7/17.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeIdResolver(InheritanceTypeIdResolver.class)
public interface State {
}
