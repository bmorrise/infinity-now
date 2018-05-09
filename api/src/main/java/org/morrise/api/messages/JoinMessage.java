package org.morrise.api.messages;

/**
 * Created by bmorrise on 9/25/17.
 */
public class JoinMessage extends Message {
  private String username;

  public JoinMessage() {

  }

  public JoinMessage( String username ) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername( String username ) {
    this.username = username;
  }
}
