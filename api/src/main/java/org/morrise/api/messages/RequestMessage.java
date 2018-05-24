package org.morrise.api.messages;

/**
 * Created by bmorrise on 5/23/18.
 */
public class RequestMessage extends Message {

  private String UUID;
  private String payload;

  public String getUUID() {
    return UUID;
  }

  public void setUUID( String UUID ) {
    this.UUID = UUID;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload( String payload ) {
    this.payload = payload;
  }
}
