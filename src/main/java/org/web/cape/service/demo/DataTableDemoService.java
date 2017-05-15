package org.web.cape.service.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.web.cape.entity.DataTableDemo;
import org.web.cape.repository.DataTableDemoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;


@Service
public class DataTableDemoService {

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private DataTableDemoDao dao;

    public DataTableDemo getDemoById(String id) {
        return dao.findOne(id);
    }

    public void deleteById(String id) {
        dao.delete(id);
    }

    public DataTableDemo getDemoBySql(String id) {
        return dao.getDemoByNativeSql(id);
    }

    @Transactional
    public void deleteDemoByIdUseSql(String id) {
        dao.deleteDemoByIdUseSql(id);
    }

    public DataTableDemo saveEntity(DataTableDemo entity) throws Exception {
        if (StringUtils.isBlank(entity.getTheid())) {
            entity.setTheid(UUID.randomUUID().toString());
        }
        return dao.save(entity);
    }

    public DataTableDemo saveEntityByJDBC(final DataTableDemo entity) throws Exception {
        if (StringUtils.isBlank(entity.getTheid())) {
            entity.setTheid(UUID.randomUUID().toString());
            jt.execute(new ConnectionCallback<Object>() {
                @Override
                public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                    String sql = "insert into example_demo (id,code,memo,name,isdefault) values (?,?,?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    try {
                        preparedStatement.setString(1, entity.getTheid());
                        preparedStatement.setString(2, entity.getCode());
                        preparedStatement.setString(3, entity.getMemo());
                        preparedStatement.setString(4, entity.getName());
                        preparedStatement.setString(5, entity.getIsdefault());
                        preparedStatement.executeUpdate();
                    } finally {
                        preparedStatement.close();
                    }
                    return null;
                }
            });
        } else {
            jt.execute(new ConnectionCallback<Object>() {
                @Override
                public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                    String sql = "UPDATE example_demo set code=?,memo=?,name=?,isdefault=? where id=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    try {
                        preparedStatement.setString(1, entity.getCode());
                        preparedStatement.setString(2, entity.getMemo());
                        preparedStatement.setString(3, entity.getName());
                        preparedStatement.setString(4, entity.getIsdefault());
                        preparedStatement.setString(5, entity.getTheid());
                        preparedStatement.executeUpdate();
                    } finally {
                        preparedStatement.close();
                    }
                    return null;
                }
            });
        }


        return entity;
    }

    public Page<DataTableDemo> getDemoPage(Map<String, Object> searchParams, PageRequest pageRequest) {
        Specification<DataTableDemo> spec = buildSpecification(searchParams);
        return dao.findAll(spec, pageRequest);
    }

    /**
     * 创建动态查询条件组合.
     */
    public Specification<DataTableDemo> buildSpecification(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<DataTableDemo> spec = DynamicSpecifications.bySearchFilter(filters.values(), DataTableDemo.class);
        return spec;
    }
}
