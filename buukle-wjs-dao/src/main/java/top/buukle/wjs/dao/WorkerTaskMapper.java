package top.buukle.wjs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.buukle.wjs.entity.WorkerTask;
import top.buukle.wjs.entity.WorkerTaskExample;

@Mapper
public interface WorkerTaskMapper {
    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    long countByExample(WorkerTaskExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    int deleteByExample(WorkerTaskExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    int insert(WorkerTask record);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    int insertSelective(WorkerTask record);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    List<WorkerTask> selectByExample(WorkerTaskExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    WorkerTask selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    int updateByExampleSelective(@Param("record") WorkerTask record, @Param("example") WorkerTaskExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    int updateByExample(@Param("record") WorkerTask record, @Param("example") WorkerTaskExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    int updateByPrimaryKeySelective(WorkerTask record);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:29 CST 2019
     */
    int updateByPrimaryKey(WorkerTask record);
}