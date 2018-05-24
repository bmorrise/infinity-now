package org.morrise.core.system.powered.turbolift;

import org.morrise.api.system.annotation.System;
import org.morrise.api.system.powered.BasePoweredSystemable;
import org.morrise.api.system.powered.PoweredSystem;
import org.morrise.core.models.ship.CentralComputer;
import org.morrise.core.models.ship.Ship;
import org.morrise.core.models.ship.structure.Room;
import org.morrise.core.models.ship.structure.TurboLift;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by bmorrise on 5/17/18.
 */
@System
public class TurboLiftSystem extends PoweredSystem<CentralComputer> {

  public static String TYPE = "turbolift";
  public static String KEYWORD = "turbolift";
  private List<TurboLift> turboLifts;

  public TurboLiftSystem( CentralComputer entity ) {
    super( entity );
  }

  @Override
  public String getType() {
    return TYPE;
  }

  @Override
  public String getKeyword() {
    return KEYWORD;
  }

  @Override
  public String getDescription() {
    return "Turbo Lift System";
  }

  @Override
  public void connect( CentralComputer centralComputer ) {
    turboLifts = centralComputer.getStructure().getRoomsByType( TurboLift.class );
  }

  @Override
  public String getInfo() {
    StringBuilder stringBuilder = new StringBuilder();
    for ( TurboLift turboLift : turboLifts ) {
      stringBuilder.append( turboLift.getName() );
      stringBuilder.append( "\n" );
    }
    return stringBuilder.toString();
  }

  public List<TurboLift> getTurboLifts() {
    return turboLifts;
  }

  public TurboLift getTurboLiftByName( String name ) {
    return turboLifts.stream().filter( t -> t.getName().contains( name ) ).findFirst().orElse( null );
  }

  @Override
  public void operate() {
    turboLifts.forEach( TurboLift::operate );
  }
}
