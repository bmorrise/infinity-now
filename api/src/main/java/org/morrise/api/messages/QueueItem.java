package org.morrise.api.messages;

import java.util.UUID;

/**
 * Created by bmorrise on 9/26/17.
 */
public class QueueItem<T extends Message> {
  final private UUID clientId;
  final private T message;

  public QueueItem( UUID clientId, T message ) {
    this.clientId = clientId;
    this.message = message;
  }

  public UUID getClientId() {
    return clientId;
  }

  public T getMessage() {
    return message;
  }
}
