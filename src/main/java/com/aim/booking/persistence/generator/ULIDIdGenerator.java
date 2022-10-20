package com.aim.booking.persistence.generator;

import de.huxhorn.sulky.ulid.ULID;
import java.io.Serializable;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class ULIDIdGenerator implements IdentifierGenerator, Configurable {

  private final ULID ULID_GENERATOR = new ULID();

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object obj)
      throws HibernateException {
    return ULID_GENERATOR.nextULID();
  }

  @Override
  public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry)
      throws MappingException {

  }
}