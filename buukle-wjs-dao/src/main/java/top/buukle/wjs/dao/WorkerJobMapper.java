package top.buukle.wjs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.buukle.wjs.entity.WorkerJob;
import top.buukle.wjs.entity.WorkerJobExample;

@Mapper
public interface WorkerJobMapper {
    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    long countByExample(WorkerJobExample example);

    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    int deleteByExample(WorkerJobExample example);

    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    int insert(WorkerJob record);

    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    int insertSelective(WorkerJob record);

    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    List<WorkerJob> selectByExample(WorkerJobExample example);

    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    WorkerJob selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    int updateByExampleSelective(@Param("record") WorkerJob record, @Param("example") WorkerJobExample example);

    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    int updateByExample(@Param("record") WorkerJob record, @Param("example") WorkerJobExample example);

    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    int updateByPrimaryKeySelective(WorkerJob record);

    /**
     *
     * @mbg.generated Mon Sep 09 22:03:37 CST 2019
     */
    int updateByPrimaryKey(WorkerJob record);
}