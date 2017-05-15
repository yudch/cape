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
import org.web.cape.entity.Demo;
import org.web.cape.repository.DemoDao;
import org.web.cape.repository.DemoJdbcDao;

import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

@Service
public class DemoService {

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private DemoDao dao;

    @Autowired
    private DemoJdbcDao jdbcdao;

    public Demo getDemoById(String id) {
        return dao.findOne(id);
    }

    public void deleteById(String id) {
        dao.delete(id);
    }

    public Demo getDemoBySql(String id) {
        return dao.getDemoByNativeSql(id);
    }

    @Transactional
    public void deleteDemoByIdUseSql(String id) {
        dao.deleteDemoByIdUseSql(id);
    }

    public Demo saveEntity(Demo entity) throws Exception {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(UUID.randomUUID().toString());
        }
        return dao.save(entity);
    }


    public Demo saveEntityByJDBC(final Demo entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(UUID.randomUUID().toString());
            jt.execute(new ConnectionCallback<Object>() {
                @Override
                public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                    String sql = "insert into example_demo (id,code,memo,name,isdefault) values (?,?,?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    try {
                        preparedStatement.setString(1, entity.getId());
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
                        preparedStatement.setString(5, entity.getId());
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


    public Page<Demo> getDemoPage(Map<String, Object> searchParams, PageRequest pageRequest) {
        Specification<Demo> spec = buildSpecification(searchParams);
        return dao.findAll(spec, pageRequest);
    }

    /**
     * 创建动态查询条件组合.
     */
    public Specification<Demo> buildSpecification(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<Demo> spec = DynamicSpecifications.bySearchFilter(filters.values(), Demo.class);
        return spec;
    }


    /**
     * 根据传递进来的id ，批量删除
     */
    public void delBatchJDBC(HttpServletRequest resq) {
        String[] ids = this.getParame(resq);
        jdbcdao.delBatch(ids);
    }

    private String[] getParame(HttpServletRequest resq) {
        String markeds = resq.getParameter("ids");   //多个id以 逗号 分开。
        String[] s_ids = markeds.split(",");
        return s_ids;
    }

    /**
     * jpa 方式 批量删除
     */
    public void delBatchJPA(HttpServletRequest resq) {
        String[] ids = this.getParame(resq);
        this.delBatchJPA(ids);
    }

    @Transactional
    public void delBatchJPA(String[] ids) {
        dao.delBatch(ids);
    }
}
