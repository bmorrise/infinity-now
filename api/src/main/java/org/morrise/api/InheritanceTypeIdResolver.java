package org.morrise.api;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by bmorrise on 5/16/18.
 */
public class InheritanceTypeIdResolver implements TypeIdResolver {
  private JavaType baseType;
  private Map<String, JavaType> typeMap = new HashMap<>();

  @Override
  public void init( JavaType javaType ) {
    baseType = javaType;

    Class<?> clazz = baseType.getRawClass();

    Reflections reflections = new Reflections( "org.morrise" ); // root package to scan for subclasses
    Set<Class<?>> subtypes = (Set<Class<?>>) reflections.getSubTypesOf( clazz );

    int classModifiers = clazz.getModifiers();

    if ( !Modifier.isAbstract( classModifiers ) && !Modifier.isInterface( classModifiers ) ) {
      subtypes.add( clazz );
    }

    subtypes.forEach( type -> {
      String key = type.getSimpleName();

      if ( typeMap.containsKey( key ) ) {
        throw new IllegalStateException( "Type name \"" + key + "\" already exists." );
      }

      typeMap.put( type.getSimpleName(), TypeFactory.defaultInstance().constructSpecializedType( baseType, type ) );
    } );
  }

  @Override
  public String idFromValue( Object o ) {
    return idFromValueAndType( o, o.getClass() );
  }

  @Override
  public String idFromBaseType() {
    return idFromValueAndType( null, baseType.getRawClass() );
  }

  @Override
  public String idFromValueAndType( Object o, Class<?> aClass ) {
    String name = aClass.getSimpleName();

    if ( typeMap.containsKey( name ) ) {
      return typeMap.get( name ).getRawClass().getSimpleName();
    }

    return null;
  }

  @Override
  public JavaType typeFromId( DatabindContext databindContext, String s ) {
    if ( typeMap.containsKey( s ) ) {
      return typeMap.get( s );
    }

    return null;
  }

  @Override
  public String getDescForKnownTypeIds() {
    return null;
  }

  @Override
  public JsonTypeInfo.Id getMechanism() {
    return JsonTypeInfo.Id.CUSTOM;
  }

  @Override
  public JavaType typeFromId( String s ) {
    return null;
  }
}
