package org.morrise.api.messages;

/**
 * Created by bmorrise on 9/25/17.
 */
public class CommandMessage extends Message {
  private String command;
  private String systemKeyword = "";
  private String keyword = "";
  private String payload = "";

  public CommandMessage() {

  }

  public CommandMessage( String command ) {
    this.command = command;
    parse();
  }

  public String getCommand() {
    return command;
  }

  public void setCommand( String command ) {
    this.command = command;
    parse();
  }

  public boolean isSystemKeyword( String keyword ) {
    return getSystemKeyword().equals( keyword );
  }

  private void parse() {
    if ( !command.equals( "" ) ) {
      String[] parts = this.command.split( " " );
      if ( parts.length < 2 ) {
        return;
      }
      this.systemKeyword = parts[0];
      this.keyword = parts[1];
      if ( parts.length > 2 ) {
        StringBuilder string = new StringBuilder();
        for ( int i = 2; i < parts.length; i++ ) {
          string.append( parts[i] );
          string.append( " " );
        }
        this.payload = string.toString().trim();
      }
    }
  }

  public String getSystemKeyword() {
    return systemKeyword;
  }

  public String getKeyword() {
    return keyword;
  }

  public String getPayload() {
    return payload;
  }
}
