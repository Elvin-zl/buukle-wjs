package top.buukle.wjs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.buukle.wjs.entity.WorkerJobLogs;
import top.buukle.wjs.entity.WorkerJobLogsExample;

@Mapper
public interface WorkerJobLogsMapper {
    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    long countByExample(WorkerJobLogsExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    int deleteByExample(WorkerJobLogsExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    int insert(WorkerJobLogs record);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    int insertSelective(WorkerJobLogs record);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    List<WorkerJobLogs> selectByExample(WorkerJobLogsExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    WorkerJobLogs selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    int updateByExampleSelective(@Param("record") WorkerJobLogs record, @Param("example") WorkerJobLogsExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    int updateByExample(@Param("record") WorkerJobLogs record, @Param("example") WorkerJobLogsExample example);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    int updateByPrimaryKeySelective(WorkerJobLogs record);

    /**
     *
     * @mbg.generated Thu Nov 28 21:33:25 CST 2019
     */
    int updateByPrimaryKey(WorkerJobLogs record);
}