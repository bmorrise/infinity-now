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

package org.morrise.core.system.character.me.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.morrise.api.messages.MainFrame;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.CharacterState;
import org.morrise.core.system.character.me.MeSystem;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bmorrise on 10/17/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SitCommandTest {

  @Mock
  private MeSystem meSystem;

  @Mock
  private Character character;

  private SitCommand sitCommand;

  @Before
  public void setup() {
    sitCommand = new SitCommand( meSystem );
    when( character.getState() ).thenReturn( CharacterState.SITTING );
  }

  @Test
  public void testCharacterSittingState() {
    sitCommand.process( character );
    verify( meSystem ).sendMessage( character, MainFrame.STATUS, "You are already sitting" );
  }

  @Test
  public void testCharacterSit() {

  }
}
