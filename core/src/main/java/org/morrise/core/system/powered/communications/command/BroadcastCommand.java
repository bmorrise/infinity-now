package org.morrise.core.system.powered.communications.command;

import org.morrise.api.models.character.Character;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.system.powered.communications.CommSystem;

/**
 * Created by bmorrise on 9/26/17.
 */
@Command(system = CommSystem.class, keywords = {"all"})
public class BroadcastCommand extends BaseCommand<CommSystem> {

  public BroadcastCommand( CommSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    system.sendAll( character, arguments[0] );
    return true;
  }
}
