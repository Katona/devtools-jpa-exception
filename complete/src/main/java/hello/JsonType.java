package hello;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonType implements DynamicParameterizedType, UserType {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ClassLoaderService classLoaderService = new ClassLoaderServiceImpl();

    private Class<?> returnedClass;

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return returnedClass;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return false;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return 0;
    }

    // other methods

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names,
            SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        String phoneNumberString = rs.getString(names[0]);
        try {
            return objectMapper.readValue(phoneNumberString, returnedClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void nullSafeSet(PreparedStatement st, Object value,
            int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {

        if (Objects.isNull(value)) {
            st.setNull(index, Types.VARCHAR);
        } else {
            try {
                st.setString(index, objectMapper.writeValueAsString(value));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return null;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return null;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        final String returnedClassName = (String) parameters.get(RETURNED_CLASS);
        returnedClass = classLoaderService.classForName(returnedClassName);
    }
}
