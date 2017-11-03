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

package org.morrise.core.models.item.furniture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.morrise.api.models.item.BaseItem;
import org.morrise.api.models.item.Item;
import org.morrise.api.models.item.types.Settable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmorrise on 10/6/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Desk extends BaseItem implements Settable {

  public static final String TYPE = "desk";
  private List<Item> items = new ArrayList<>();

  @Override
  public String getType() {
    return TYPE;
  }

  @Override
  public List<Item> getItems() {
    return items;
  }

  @Override
  public void setItems( List<Item> items ) {
    this.items = items;
  }

  @Override
  public void addItem( Item item ) {
    items.add( item );
  }

  @Override
  public void removeItem( Item item ) {
    items.remove( item );
  }
}
