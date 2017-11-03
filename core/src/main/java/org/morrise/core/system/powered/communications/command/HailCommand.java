package org.morrise.core.system.powered.communications.command;

import org.morrise.api.models.character.Character;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.system.powered.communications.CommSystem;

/**
 * Created by bmorrise on 9/15/17.
 */
@Command(system = CommSystem.class, keywords = {"hail"},
        validCommands = {"them",
                "the (.*)",
                "contact (.*) at (.*)",
                "get me (.*) on subspace",
                "get me (.*) at (.*)",
                "get me (.*)",
                "(.*)"})
public class HailCommand extends BaseCommand<CommSystem> {

  public HailCommand( CommSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    logger.info( "Processing hail command..." );
    if ( arguments.length == 0 ) {
      system.connectCloseProximity();
    }
    if ( arguments.length == 1 ) {
      // Try and contact the specific person/ship
      system.connect( arguments[0] );
    }
    if ( arguments.length == 2 ) {
      // Try and contact the specific person/ship at specific place
    }
    return true;
  }
}
