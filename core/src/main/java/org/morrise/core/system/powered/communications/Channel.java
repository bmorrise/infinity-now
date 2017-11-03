/*
 * Copyright 2017 Benjamin Morrise
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.morrise.core.system.powered.communications;

/**
 * Created by bmorrise on 9/21/17.
 */
public class Channel {

  private Status status;
  private CommSystem originComm;
  private CommSystem destinationComm;

  public Channel( CommSystem senderCom, CommSystem receiverCom ) {
    this.status = Status.PENDING;
    this.originComm = senderCom;
    this.destinationComm = receiverCom;
  }

  public CommSystem getOriginComm() {
    return originComm;
  }

  public void setOriginComm( CommSystem originComm ) {
    this.originComm = originComm;
  }

  public CommSystem getDestinationComm() {
    return destinationComm;
  }

  public void setDestinationComm( CommSystem destinationComm ) {
    this.destinationComm = destinationComm;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus( Status status ) {
    this.status = status;
  }

  public boolean isStatus( Status status ) {
    return this.status.equals( status );
  }

  @Override
  public String toString() {
    return "Channel{" +
            "status=" + status +
            ", originComm=" + originComm +
            ", destinationComm=" + destinationComm +
            '}';
  }

  public enum Status {
    OPEN(),
    PENDING(),
    CLOSED()
  }
}
