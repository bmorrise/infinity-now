package org.morrise.api.messages;

import java.util.List;

/**
 * Created by bmorrise on 5/22/18.
 */
public class ConnectMessage extends Message {
  private String system;
  private List<String> commands;

  public String getSystem() {
    return system;
  }

  public void setSystem( String system ) {
    this.system = system;
  }

  public List<String> getCommands() {
    return commands;
  }

  public void setCommands( List<String> commands ) {
    this.commands = commands;
  }
}
