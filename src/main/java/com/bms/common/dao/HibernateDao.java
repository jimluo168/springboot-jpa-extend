package com.bms.common.dao;

import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 使用Hibernate进行操作.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
public class HibernateDao implements IDao {
    private static final Logger logger = LoggerFactory.getLogger(HibernateDao.class);

    /**
     * sql 的类型，hql 和 sql.
     */
    protected static final String HQL = "hql";
    protected static final String SQL = "sql";
    protected static final String TYPE = "[@type]";
    protected static final String dot = ".";
    /**
     * queries 的 namespace属性 是属于哪个DaoCmd类，同命名空间。
     */
    protected static final String ATTR_NAMESPACE = "[@namespace]";

    private Configuration FREEMARKER;

    private Map<String, String> queryFileKeyMap;

    protected final EntityManager entityManager;

    public HibernateDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 通过JPA的EntityManager获取Hibernate Session.
     *
     * @return
     */
    protected final Session getSession() {
        return (Session) entityManager.getDelegate();
    }

    @Override
    public <T> T getSingle(DaoCmd cmd) {
        Query query = createQuery(cmd);

        if (cmd.getResultClass() != null) {
            query.setResultTransformer(getTransformerAdapter(cmd.getResultClass()));
        }

        return (T) query.getSingleResult();
    }

    @Override
    public <T> List<T> getList(DaoCmd cmd) {
        return this.getList(cmd, null, null);
    }

    @Override
    public <T> List<T> getList(DaoCmd cmd, Integer startFrom, Integer maxResult) {
        Query query = createQuery(cmd);

        if (startFrom != null) {
            query.setFirstResult(startFrom);
        }

        if (maxResult != null) {
            query.setMaxResults(maxResult);
        }

        if (cmd.getResultClass() != null) {
            query.setResultTransformer(getTransformerAdapter(cmd.getResultClass()));
        }

        return query.list();
    }

    @Override
    public long getCount(DaoCmd cmd) {
        Query query = createCountQuery(cmd);
        return new BigInteger(query.getSingleResult().toString()).longValue();
    }

    @Override
    public <T> PageList<T> findAll(PageRequest request, DaoCmd cmd) {
        List<T> list = getList(cmd, request.getOffset(), request.getSize());
        long count = getCount(cmd);
        return new PageList<T>(count, list);
    }

    @Override
    public int executeUpdate(DaoCmd cmd) {
        Query query = createQuery(cmd);
        return query.executeUpdate();
    }

    protected <T> Query createQuery(DaoCmd cmd) {
        Session session = getSession();
        String queryType = getQueryType(cmd);
        if (queryType == null) {
            queryType = HQL;
        }

        Query query;
        if (HQL.equals(queryType)) {

            String queryString = processTemplate(cmd);

            if (StringUtils.isNotBlank(cmd.getOrderString())) {
                if (StringUtils.contains(queryString.toLowerCase(), " order ")) {
                    queryString = queryString + "," + cmd.getOrderString();
                } else {
                    queryString = queryString + " order by " + cmd.getOrderString();
                }
            }

            query = session.createQuery(queryString);
        } else if (SQL.equals(queryType)) {

            String queryString = processTemplate(cmd);
            if (StringUtils.isNotBlank(cmd.getOrderString())) {
                if (StringUtils.contains(queryString.toLowerCase(), " order ")) {
                    queryString = queryString + "," + cmd.getOrderString();
                } else {
                    queryString = queryString + " order by " + cmd.getOrderString();
                }
            }

            query = session.createNativeQuery(queryString);
        } else {
            logger.error("Unknown query type: " + queryType);
            throw ErrorCodes.build(ErrorCodes.DATABASE_ERR);
        }

        Map<String, Object> params = cmd.getParams();
        if (params != null && !params.isEmpty()) {
            query.setProperties(params);
        }
        return query;
    }

    private Query createCountQuery(DaoCmd cmd) {
        Session session = getSession();

        String queryType = getQueryType(cmd);
        if (queryType == null) {
            queryType = HQL;
        }

        Query query;
        if (HQL.equals(queryType)) {

            String queryString = processTemplate(cmd);
            queryString = processCountHql(queryString);
            query = session.createQuery(queryString);

        } else if (SQL.equals(queryType)) {
            String queryString = processTemplate(cmd);
            String countString = processCountSQL(queryString);
            query = session.createNativeQuery(countString);
        } else {
            logger.error("Unknown query type: " + queryType);
            throw ErrorCodes.build(ErrorCodes.DATABASE_ERR);
        }
        Map<String, Object> params = cmd.getParams();
        if (params != null && !params.isEmpty()) {
            query.setProperties(params);
        }
        return query;
    }

    protected String processCountHql(String orgHql) {
        String fromHql = orgHql;
        fromHql = StringUtils.substring(fromHql, StringUtils.indexOf(fromHql.toLowerCase(), "from"));
        int indexof = StringUtils.lastIndexOf(fromHql.toLowerCase(), "order by");
        if (indexof > -1) {
            fromHql = StringUtils.substring(fromHql, 0, indexof);
        }
        return "SELECT COUNT(*) " + fromHql;
    }

    protected String processCountSQL(String orgSql) {
        String fromSql = orgSql;
        int indexof = StringUtils.lastIndexOf(fromSql.toLowerCase(), "order by");
        if (indexof > -1) {
            fromSql = StringUtils.substring(fromSql, 0, indexof);
        }
        String countSql = "SELECT COUNT(*) FROM (" + fromSql + ") AA";
        return countSql;
    }

    /**
     * FreeMarker Template Process
     */
    protected String processTemplate(DaoCmd cmd) {
        String queryKey = buildQueryKeyType(cmd);

        try {
            StringWriter writer = new StringWriter();
            FREEMARKER.getTemplate(queryKey).process(cmd.getParams(), writer);
            return writer.toString();
        } catch (Exception e) {
            logger.error("An exception occurred while processing the query template", e);
            throw ErrorCodes.build(ErrorCodes.DATABASE_ERR);
        }
    }

    public void setQueryFiles(Resource[] queryFiles) throws ConfigurationException {
        if (FREEMARKER == null) {
            FREEMARKER = new Configuration(Configuration.VERSION_2_3_23);
        }
        FREEMARKER.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        FREEMARKER.setDefaultEncoding(StandardCharsets.UTF_8.name());

        if (queryFileKeyMap == null) {
            queryFileKeyMap = new HashMap<>();
        }
        List<TemplateLoader> templateLoaderList = new ArrayList<>();
        try {
            for (Resource resource : queryFiles) {
                Configurations configs = new Configurations();
                XMLConfiguration xmlConfigs = configs.xml(resource.getURL());
                xmlConfigs.setThrowExceptionOnMissing(false);

                String namespace = xmlConfigs.getString(ATTR_NAMESPACE);
                TemplateLoader templateLoader = setTemplateLoader(namespace, xmlConfigs);
                templateLoaderList.add(templateLoader);
            }
        } catch (IOException e) {
            logger.error("queryFiles:[" + StringUtils.join(queryFiles, " ") + "] getURL error" + e.getMessage(), e);
            throw ErrorCodes.build(ErrorCodes.DATABASE_ERR);
        }
        FREEMARKER.setTemplateLoader(new MultiTemplateLoader(templateLoaderList.toArray(new TemplateLoader[0])));
    }

    /**
     * 初始化时，将xml的内容加载到StringTempLateLoadder中.
     *
     * @param queryFile
     */
    private TemplateLoader setTemplateLoader(String namespace, XMLConfiguration queryFile) {
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        Iterator<String> iterators = queryFile.getKeys();
        while (iterators.hasNext()) {
            String queryKey = iterators.next();
            if (StringUtils.contains(queryKey, TYPE)) {
                continue;
            }
            String queryString = queryFile.getString(queryKey);
            String type = queryFile.getString(queryKey + TYPE);

            if (StringUtils.isBlank(queryString)) {
                queryString = StringUtils.EMPTY;
            }
            // namespace + dot + queryKey+TYPE;
            String key = buildQueryKeyType(namespace, queryKey);
            queryFileKeyMap.put(key, type);
            stringTemplateLoader.putTemplate(key, queryString);
        }

        return stringTemplateLoader;
    }

    private String buildQueryKeyType(String namespace, String queryKey) {
        return namespace + dot + queryKey + TYPE;
    }

    private String buildQueryKeyType(DaoCmd cmd) {
        return buildQueryKeyType(cmd.getClass().getName(), cmd.getQueryKey());
    }

    protected String getQueryType(DaoCmd cmd) {
        String key = buildQueryKeyType(cmd);
        return queryFileKeyMap.get(key);
    }

    /**
     * 将class转换为ResultTransformer.
     *
     * @param clazz
     * @return
     */
    public ResultTransformer getTransformerAdapter(Class clazz) {
        return Map.class.isAssignableFrom(clazz) ? Transformers.ALIAS_TO_ENTITY_MAP : BeanTransformerAdapter.newInstance(clazz);
    }
}
