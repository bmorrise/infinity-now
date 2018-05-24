/*
 * Copyright 2017 Benjamin Morrise
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.morrise.api.system;

import org.morrise.api.Operatable;
import org.morrise.api.models.Entity;
import org.morrise.api.models.character.Character;
import org.morrise.api.system.capability.Capability;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.status.Status;

import java.util.List;

/**
 * Created by bmorrise on 9/18/17.
 */
public interface System<T extends Systemable> extends Commandable, Operatable {
  Status getStatus();

  void setStatus( Status status );

  String getType();

  boolean isType( String type );

  String getDescription();

  List<Capability> getCapabilities();

  Capability getCapability( String type );

  T getSystemable();

  void setSystemable( T value );

  void connect( T systemable );

  void connect();

  void addCommand( BaseCommand command );

  void operate();

  boolean hasAccess( Character character );

  void addAccess( Character character );

  String getKeyword();

  String getInfo();

  List<String> getPossible( Character character );
}
