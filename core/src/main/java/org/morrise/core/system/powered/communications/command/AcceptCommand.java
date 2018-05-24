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
@Command(system = CommSystem.class, keywords = {"accept"})
public class AcceptCommand extends BaseCommand<CommSystem> {

  public AcceptCommand( CommSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    logger.info( "Processing accept command..." );
    system.getAllChannels().forEach( channel -> {
      openChannels( channel.getOriginComm() );
      openChannels( channel.getDestinationComm() );
    } );
    return true;
  }

  private void openChannels( CommSystem commSystem ) {
    system.getAllChannels().forEach( channel -> {
      if ( channel.isStatus( Channel.Status.PENDING ) ) {
        channel.setStatus( Channel.Status.OPEN );
        commSystem.sendMessage( new Message<>( channel, "Channel open" ) );
      }
    } );
  }
}
