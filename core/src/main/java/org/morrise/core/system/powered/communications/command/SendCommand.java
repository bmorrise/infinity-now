package org.morrise.core.system.powered.communications.command;

import org.morrise.api.models.character.Character;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.system.powered.communications.Channel;
import org.morrise.core.system.powered.communications.CommSystem;
import org.morrise.core.system.powered.communications.Message;

/**
 * Created by bmorrise on 9/26/17.
 */
@Command(system = CommSystem.class, keywords = {"send"})
public class SendCommand extends BaseCommand<CommSystem<?>> {

  public SendCommand( CommSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    logger.info( "Processing send command..." );
    system.getAllChannels().forEach( channel -> {
      if ( channel.isStatus( Channel.Status.OPEN ) ) {
        system.sendMessage( new Message<>( channel, arguments[0] ) );
      }
    } );
    return true;
  }
}
