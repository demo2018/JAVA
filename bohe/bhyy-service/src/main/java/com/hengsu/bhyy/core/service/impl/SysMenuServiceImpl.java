package com.hengsu.bhyy.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.hengsu.bhyy.core.entity.SysMenu;
import com.hengsu.bhyy.core.repository.SysMenuRepository;
import com.hengsu.bhyy.core.model.SysMenuModel;
import com.hengsu.bhyy.core.service.SysMenuService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private SysMenuRepository sysMenuRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public int create(SysMenuModel sysMenuModel) {
        return sysMenuRepo.insert(beanMapper.map(sysMenuModel, SysMenu.class));
    }

    @Transactional
    @Override
    public int createSelective(SysMenuModel sysMenuModel) {
        return sysMenuRepo.insertSelective(beanMapper.map(sysMenuModel, SysMenu.class));
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysMenuRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public SysMenuModel findByPrimaryKey(Long id) {
        SysMenu sysMenu = sysMenuRepo.selectByPrimaryKey(id);
        return beanMapper.map(sysMenu, SysMenuModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(SysMenuModel sysMenuModel) {
        return sysMenuRepo.selectCount(beanMapper.map(sysMenuModel, SysMenu.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<SysMenuModel> selectPage(SysMenuModel sysMenuModel, Pageable pageable) {
        SysMenu sysMenu = beanMapper.map(sysMenuModel, SysMenu.class);
        return beanMapper.mapAsList(sysMenuRepo.selectPage(sysMenu, pageable), SysMenuModel.class);
    }

    @Override
    public List<SysMenuModel> selectByRoleId(Long roleId) {
        String sql = "SELECT sm.id,sm.name,sm.url,sm.parent_id as parentId,sm.rank,sm.icon,sm.`key`,sm.controller\n" +
                "FROM sys_menu sm,sys_role_menu srm WHERE sm.id=srm.menu_id and srm.role_id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SysMenuModel.class), roleId);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(SysMenuModel sysMenuModel) {
        return sysMenuRepo.updateByPrimaryKey(beanMapper.map(sysMenuModel, SysMenu.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(SysMenuModel sysMenuModel) {
        return sysMenuRepo.updateByPrimaryKeySelective(beanMapper.map(sysMenuModel, SysMenu.class));
    }

}
