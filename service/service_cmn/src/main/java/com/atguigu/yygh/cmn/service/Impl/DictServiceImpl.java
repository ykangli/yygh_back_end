package com.atguigu.yygh.cmn.service.Impl;

import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注释@Service表示是Service的实现类，交给spring管理
 *
 * @author Li
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    //ServiceImpl已经实现BaseMapper的注入，这里就不需要再实现注入了

    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        //根据id查询子数据，就是查询数据表中parent_id=id的所有信息
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //因为实体类Dict中的属性hasChildren在数据表中没有对应的字段，所以需要手动判断添加
        //向list集合每个dict对象中设置hasChildren
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    /**
     * 判断id下面是否有子节点
     *
     * @param id
     * @return
     */
    private boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        //baseMapper.selectCount(wrapper)表示该查询条件下有几条数据
        Integer count = baseMapper.selectCount(wrapper);
        // 0>0(false)    1>0(true)
        return count > 0;
    }


}
