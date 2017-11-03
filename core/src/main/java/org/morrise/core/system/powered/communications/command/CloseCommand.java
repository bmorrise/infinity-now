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
@Command(system = CommSystem.class, keywords = {"close"})
public class CloseCommand extends BaseCommand<CommSystem<?>> {

  public CloseCommand( CommSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    logger.info( "Processing close command..." );
    system.getAllChannels().forEach( channel -> {
      closeChannels( channel.getOriginComm() );
      closeChannels( channel.getDestinationComm() );
    } );
    return true;
  }

  private void closeChannels( CommSystem commSystem ) {
    system.getAllChannels().forEach( channel -> {
      channel.setStatus( Channel.Status.CLOSED );
      commSystem.sendMessage( new Message<>( channel, "Channel closed" ) );
    } );
  }
}
