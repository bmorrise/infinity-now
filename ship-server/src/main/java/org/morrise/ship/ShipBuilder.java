package org.morrise.ship;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.morrise.api.models.character.CharacterRank;
import org.morrise.api.models.character.CharacterState;
import org.morrise.api.system.powered.PowerSource;
import org.morrise.api.system.powered.PowerSourceState;
import org.morrise.core.models.character.Officer;
import org.morrise.core.models.item.food.Apple;
import org.morrise.core.models.item.furniture.CaptainChair;
import org.morrise.core.models.item.furniture.Chair;
import org.morrise.core.models.item.furniture.Couch;
import org.morrise.core.models.item.furniture.Desk;
import org.morrise.core.models.ship.Ship;
import org.morrise.core.models.ship.engine.WarpEngine;
import org.morrise.core.models.ship.powersource.EthiniumCrystal;
import org.morrise.core.models.ship.shield.EnergyShield;
import org.morrise.core.models.ship.shield.Shield;
import org.morrise.core.models.ship.structure.Deck;
import org.morrise.core.models.ship.structure.Room;
import org.morrise.core.models.ship.structure.TurboLift;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bmorrise on 5/24/18.
 */
public class ShipBuilder {

  public static void build() {
    Ship ship = new Ship( "aries" );

    Officer character = new Officer();
    character.setFirst( "Ben" );
    character.setLast( "Morrise" );
    character.setUsername( "bmorrise" );
    character.setPassword( "9876" );
    character.setRank( CharacterRank.CAPTAIN );
    character.setHealth( 50 );
    character.setEnergy( 50 );
    character.setState( CharacterState.STANDING );
    character.setRoom( "Bridge" );
    ship.addCharacter( character );

    Officer character1 = new Officer();
    character1.setFirst( "John" );
    character1.setLast( "Smith" );
    character1.setUsername( "jsmith" );
    character1.setPassword( "1234" );
    character1.setRank( CharacterRank.COMMANDER );
    character1.setHealth( 50 );
    character1.setEnergy( 50 );
    character1.setState( CharacterState.STANDING );
    character1.setRoom( "Bridge" );
    ship.addCharacter( character1 );

    PowerSource powerSource = new EthiniumCrystal();
    powerSource.setState( PowerSourceState.CHARGED );
    powerSource.setUnits( 10000 );

    List<Deck> decks = new ArrayList<>();
    Deck deck1 = new Deck();
    deck1.setNumber( 1 );
    deck1.setEntryPoint( "Bridge" );
    decks.add( deck1 );

    Deck deck2 = new Deck();
    deck2.setNumber( 2 );
    decks.add( deck2 );

    // ** Bridge ** //
    Room bridge = new Room();
    bridge.setName( "Bridge" );
    bridge.setDescription( "This is the Bridge" );
    bridge.addAccessibleSystem( "all" );
    bridge.addAdjacent( "Ready room" );
    bridge.addAdjacent( "Turbo Lift 1" );
    CaptainChair captainChair = new CaptainChair();
    captainChair.setName( "Captain Chair" );
    bridge.addItem( captainChair );
    // ** End Bridge ** //

    // ** Ready room ** //
    Room readyRoom = new Room();
    readyRoom.setName( "Ready room" );
    readyRoom.setDescription( "This is the captain's ready room" );
    readyRoom.addAccessibleSystem( "sensors" );
    readyRoom.addAccessibleSystem( "communcations" );

    Chair chair = new Chair();
    chair.setName( "Chair" );
    readyRoom.addItem( chair );

    Couch couch = new Couch();
    couch.setName( "Couch" );
    readyRoom.addItem( couch );

    Desk desk = new Desk();
    desk.setName( "Desk" );
    readyRoom.addItem( desk );

    Apple apple = new Apple();
    apple.setName( "Granny Smith Apple" );
    desk.addItem( apple );
    // ** End Ready room ** //

    // ** Turbo Lift 1 ** //
    TurboLift turboLift = new TurboLift();
    turboLift.setDeck( 2 );
    turboLift.setName( "Turbo Lift 1" );
    turboLift.setDescription( "This is turbo lift 1" );
    turboLift.addAccessibleSystem( "turbolift" );
    turboLift.addAdjacent( "Deck 2 Hallway" );
    turboLift.setAccessibleTo( Arrays.asList( 1, 2 ) );
    // ** End Turbo Lift 1 ** //

    deck1.setRooms( Arrays.asList( bridge, readyRoom ) );
    ship.setDecks( decks );

    Room hallway = new Room();
    hallway.setName( "Deck 2 Hallway" );
    hallway.setDescription( "Deck 2 Hallway" );
    hallway.addAdjacent( "Turbo lift 1" );

    deck2.setEntryPoint( "Deck 2 Hallway" );
    deck2.setRooms( Arrays.asList( hallway ) );

    EnergyShield energyShield = new EnergyShield();
    energyShield.setState( Shield.ShieldState.DOWN );
    energyShield.setStrength( 100 );

    WarpEngine warpEngine = new WarpEngine();
    warpEngine.setMaxSpeed( 100 );

    ship.addItem( energyShield );
    ship.addItem( warpEngine );

    ship.setAccessibleToDecks( Arrays.asList( turboLift ) );

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable( SerializationFeature.INDENT_OUTPUT );
    try {
      objectMapper.writeValue( new File( "output.json" ), ship );
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }

}
