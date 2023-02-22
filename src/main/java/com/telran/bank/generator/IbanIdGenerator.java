package com.telran.bank.generator;

import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.iban4j.Iban;

import java.io.Serializable;

@AllArgsConstructor
public class IbanIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return Iban.random().toFormattedString();
    }
}