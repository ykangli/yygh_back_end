package com.atguigu.yygh.cmn.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 注释@Service表示是Service的实现类，交给spring管理
 *
 * @author Li
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    //ServiceImpl已经实现BaseMapper的注入，这里就不需要再实现注入了

    /***
     * 将数据字典导出
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        //设置类型
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = "dict";
        //让操作以下载方式打开
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        List<Dict> dictList = baseMapper.selectList(null);

        //Dict --> DictEeVo
        List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
        for(Dict dict : dictList) {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict, dictEeVo);
            dictVoList.add(dictEeVo);
        }


        try {
            //写到文件中去
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("数据字典")
                    .doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

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
