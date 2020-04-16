package com.bms.common.dao;

import com.bms.common.util.DateUtil;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.transform.BasicTransformerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.*;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.util.*;

/**
 * Hibernate类型转换应变器.
 *
 * @author luojimeng
 * @date 2020/3/19
 * @see org.hibernate.transform.BasicTransformerAdapter
 */
public class BeanTransformerAdapter extends BasicTransformerAdapter {

    /**
     * Logger available to subclasses
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(BeanTransformerAdapter.class);

    /**
     * The class we are mapping to
     */
    private Class mappedClass;

    /**
     * Whether we're strictly validating
     */
    private boolean checkFullyPopulated = false;

    /**
     * Whether we're defaulting primitives when mapping a null value
     */
    private boolean primitivesDefaultedForNullValue = false;

    /**
     * Map of the fields we provide mapping for
     */
    private Map<String, PropertyDescriptor> mappedFields;

    /**
     * Set of bean properties we provide mapping for
     */
    private Set<String> mappedProperties;

    /**
     * mappedClass new Instance.
     */
    private Object mappedObject;

    private BeanWrapper bw;

    /**
     * Create a new BeanPropertyRowMapper, accepting unpopulated properties
     * in the target bean.
     * <p>Consider using the {@link #newInstance} factory method instead,
     * which allows for specifying the mapped type once only.
     *
     * @param mappedClass the class that each row should be mapped to
     */
    public BeanTransformerAdapter(Class mappedClass) {
        initialize(mappedClass);

        instantiateMappedClass(mappedClass);
    }

    /**
     * Create a new BeanPropertyRowMapper.
     *
     * @param mappedClass         the class that each row should be mapped to
     * @param checkFullyPopulated whether we're strictly validating that
     *                            all bean properties have been mapped from corresponding database fields
     */
    public BeanTransformerAdapter(Class mappedClass, boolean checkFullyPopulated) {
        initialize(mappedClass);
        instantiateMappedClass(mappedClass);
        this.checkFullyPopulated = checkFullyPopulated;
    }

    private void instantiateMappedClass(Class mappedClass) {
        mappedObject = BeanUtils.instantiate(mappedClass);
        bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
        initBeanWrapper(bw);
    }

    /**
     * Set the class that each row should be mapped to.
     */
    public void setMappedClass(Class mappedClass) {
        if (this.mappedClass == null) {
            initialize(mappedClass);
        } else {
            if (!this.mappedClass.equals(mappedClass)) {
                throw new InvalidDataAccessApiUsageException("The mapped class can not be reassigned to map to " +
                        mappedClass + " since it is already providing mapping for " + this.mappedClass);
            }
        }
    }

    /**
     * Initialize the mapping metadata for the given class.
     *
     * @param mappedClass the mapped class.
     */
    protected void initialize(Class mappedClass) {
        this.mappedClass = mappedClass;
        this.mappedFields = new HashMap<>();
        this.mappedProperties = new HashSet<>();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null) {
                this.mappedFields.put(pd.getName().toLowerCase(), pd);
                String underscoredName = underscoreName(pd.getName());
                if (!pd.getName().toLowerCase().equals(underscoredName)) {
                    this.mappedFields.put(underscoredName, pd);
                }
                this.mappedProperties.add(pd.getName());
            }
        }
    }

    /**
     * Convert a name in camelCase to an underscored name in lower case.
     * Any upper case letters are converted to lower case with a preceding underscore.
     *
     * @param name the string containing original name
     * @return the converted name
     */
    private String underscoreName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(name.substring(0, 1).toLowerCase());
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = s.toLowerCase();
            if (!s.equals(slc)) {
                result.append("_").append(slc);
            } else {
                result.append(s);
            }
        }
        return result.toString();
    }

    /**
     * Get the class that we are mapping to.
     */
    public final Class getMappedClass() {
        return this.mappedClass;
    }

    /**
     * Set whether we're strictly validating that all bean properties have been
     * mapped from corresponding database fields.
     * <p>Default is {@code false}, accepting unpopulated properties in the
     * target bean.
     */
    public void setCheckFullyPopulated(boolean checkFullyPopulated) {
        this.checkFullyPopulated = checkFullyPopulated;
    }

    /**
     * Return whether we're strictly validating that all bean properties have been
     * mapped from corresponding database fields.
     */
    public boolean isCheckFullyPopulated() {
        return this.checkFullyPopulated;
    }

    /**
     * Set whether we're defaulting Java primitives in the case of mapping a null value
     * from corresponding database fields.
     * <p>Default is {@code false}, throwing an exception when nulls are mapped to Java primitives.
     */
    public void setPrimitivesDefaultedForNullValue(boolean primitivesDefaultedForNullValue) {
        this.primitivesDefaultedForNullValue = primitivesDefaultedForNullValue;
    }

    /**
     * Return whether we're defaulting Java primitives in the case of mapping a null value
     * from corresponding database fields.
     */
    public boolean isPrimitivesDefaultedForNullValue() {
        return primitivesDefaultedForNullValue;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Assert.state(this.mappedClass != null, "Mapped class was not specified");
        Object mappedObject = BeanUtils.instantiate(this.mappedClass);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
        initBeanWrapper(bw);

        Set<String> populatedProperties = (isCheckFullyPopulated() ? new HashSet<String>() : null);

        for (int i = 0; i < aliases.length; i++) {
            String column = aliases[i];
            PropertyDescriptor pd = this.mappedFields.get(column.replaceAll(" ", "").toLowerCase());
            if (pd != null) {
                try {
                    Object value = tuple[i];
                    try {
                        if (value instanceof String && pd.getPropertyType().getName().equals(Date.class.getName())) {
                            value = DateUtil.parseDate((String) value);
                        }
                        bw.setPropertyValue(pd.getName(), value);
                    } catch (TypeMismatchException e) {
                        if (value == null && primitivesDefaultedForNullValue) {
                            LOGGER.error("Intercepted TypeMismatchException for column " + column +
                                    " and column '" + column + "' with value " + value +
                                    " when setting property '" + pd.getName() + "' of type " + pd.getPropertyType() +
                                    " on object: " + mappedObject);
                        } else {
                            throw e;
                        }
                    } catch (ParseException e) {
                        LOGGER.error("parse date error.", e);
                    }
                    if (populatedProperties != null) {
                        populatedProperties.add(pd.getName());
                    }
                } catch (NotWritablePropertyException ex) {
                    throw new DataRetrievalFailureException("Unable to map column " + column
                            + " to property " + pd.getName(), ex);
                }
            }
        }

        if (populatedProperties != null && !populatedProperties.equals(this.mappedProperties)) {
            throw new InvalidDataAccessApiUsageException("Given ResultSet does not contain all fields " +
                    "necessary to populate object of class [" + this.mappedClass + "]: " + this.mappedProperties);
        }

        return mappedObject;
    }

    /**
     * Initialize the given BeanWrapper to be used for row mapping.
     * To be called for each row.
     * <p>The default implementation is empty. Can be overridden in subclasses.
     *
     * @param bw the BeanWrapper to initialize
     */
    protected void initBeanWrapper(BeanWrapper bw) {
    }

    /**
     * Static factory method to create a new ResultTransformer
     * (with the mapped class specified only once).
     *
     * @param mappedClass the class that each row should be mapped to
     */
    public static BeanTransformerAdapter newInstance(Class mappedClass) {
        return new BeanTransformerAdapter(mappedClass);
    }
}
