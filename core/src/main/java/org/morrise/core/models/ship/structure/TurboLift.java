package org.morrise.core.models.ship.structure;

import org.morrise.api.Operatable;
import org.morrise.api.models.State;
import org.morrise.api.models.character.Character;

import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by bmorrise on 5/17/18.
 */
public class TurboLift extends AccessibleToDeck implements Operatable {

  private TurboLiftState state;
  private Integer deck;
  private Queue<SummonRequest> requests = new ConcurrentLinkedQueue<>();

  public TurboLift() {
    state = TurboLiftState.STOPPED;
  }

  public Integer getDeck() {
    return deck;
  }

  public TurboLiftState getState() {
    return state;
  }

  public void setDeck( Integer deck ) {
    this.deck = deck;
  }

  public boolean isMoving() {
    return state.equals( TurboLiftState.MOVING );
  }

  public boolean isOccupied() {
    return getCharacters().size() > 0;
  }

  @Override
  public boolean canEnter( Character character ) {
    Deck deck = (Deck) character.getCharacterContainer().getParent();
    if ( deck != null ) {
      if ( this.deck.equals( deck.getNumber() ) && state.equals( TurboLiftState.STOPPED ) ) {
        return true;
      }
    }
    return false;
  }

  public void summon( Deck deck, Runnable callback ) {
    requests.add( new SummonRequest( deck, callback ) );
  }

  public void operate() {
    if ( requests.size() > 0 ) {
      SummonRequest summonRequest = requests.poll();
      doSummon( summonRequest.getDeck(), summonRequest.getCallback() );
    }
  }

  public void doSummon( Deck deck, Runnable callback ) {
    try {
      Thread.sleep( Math.abs( deck.getNumber() - getDeck() ) );
      setDeck( deck.getNumber() );
      setAdjacent( Collections.singletonList( deck.getEntryPoint() ) );
      callback.run();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  public enum TurboLiftState implements State {
    STOPPED,
    MOVING;
  }

  public class SummonRequest {
    private Deck deck;
    private Runnable callback;

    public SummonRequest( Deck deck, Runnable callback ) {
      this.deck = deck;
      this.callback = callback;
    }

    public Deck getDeck() {
      return deck;
    }

    public void setDeck( Deck deck ) {
      this.deck = deck;
    }

    public Runnable getCallback() {
      return callback;
    }

    public void setCallback( Runnable callback ) {
      this.callback = callback;
    }
  }
}
