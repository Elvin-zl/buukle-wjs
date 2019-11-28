package top.buukle.wjs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.buukle.wjs.entity.WorkerTaskLogs;
import top.buukle.wjs.entity.WorkerTaskLogsExample;

@Mapper
public interface WorkerTaskLogsMapper {
    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    long countByExample(WorkerTaskLogsExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    int deleteByExample(WorkerTaskLogsExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    int insert(WorkerTaskLogs record);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    int insertSelective(WorkerTaskLogs record);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    List<WorkerTaskLogs> selectByExample(WorkerTaskLogsExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    WorkerTaskLogs selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    int updateByExampleSelective(@Param("record") WorkerTaskLogs record, @Param("example") WorkerTaskLogsExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    int updateByExample(@Param("record") WorkerTaskLogs record, @Param("example") WorkerTaskLogsExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    int updateByPrimaryKeySelective(WorkerTaskLogs record);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:33 CST 2019
     */
    int updateByPrimaryKey(WorkerTaskLogs record);
}