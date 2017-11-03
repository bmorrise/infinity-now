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

package org.morrise.core.autowrite;

/**
 * Created by bmorrise on 10/9/17.
 */
public class Sentence {

  private String subject;
  private String verb;
  private String directObject;
  private Type type;

  public Sentence( Builder builder ) {
    this.subject = builder.subject;
    this.verb = builder.verb;
    this.directObject = builder.directObject;
    this.type = builder.type;
  }

  @Override
  public String toString() {
    return subject +
            " " +
            verb +
            " " +
            Utils.aOrAn( directObject ) +
            " " +
            directObject +
            (type.equals( Type.QUESTION ) ? "?" : ".");
  }

  public enum Type {
    STATEMENT,
    QUESTION
  }

  public static class Builder {

    private String subject;
    private String verb;
    private String directObject;
    private Type type;

    public Builder() {
    }

    public Builder subject( String subject ) {
      this.subject = subject;
      return this;
    }

    public Builder verb( String verb ) {
      this.verb = verb;
      return this;
    }

    public Builder directObject( String directObject ) {
      this.directObject = directObject;
      return this;
    }

    public Builder type( Type type ) {
      this.type = type;
      return this;
    }

    public Sentence build() {
      return new Sentence( this );
    }
  }
}
