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

package org.morrise.space;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;

/**
 * Created by bmorrise on 9/29/17.
 */
public class Server {
  private static final Logger logger = LogManager.getLogger( Server.class );

  public static void main( String[] args ) throws Exception {
    InetAddress localHost = Inet4Address.getLocalHost();
    NetworkInterface networkInterface = NetworkInterface.getByInetAddress( localHost );

    String ipAddress = localHost.getHostAddress();
    int index = ipAddress.lastIndexOf( "." );
    String revised = ipAddress.substring( 0, index + 1 );

    for ( int i = 0; i <= 255; i++ ) {
      String newIPAddress = revised + i;
      try ( Socket socket = new Socket() ) {
        System.out.println( "Testing " + newIPAddress + "..." );
        socket.connect( new InetSocketAddress( newIPAddress, 443 ), 100 );
        System.out.println( newIPAddress );
      } catch ( Exception e ) {
        // Do nothing
      }
    }
  }

  public void start() throws Exception {

  }

}
