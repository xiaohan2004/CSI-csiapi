package org.example.csiapi.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.csiapi.pojo.OperateLog;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    //插入日志数据
    @Insert("insert into operate_log (operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    public void insertOperateLog(OperateLog log);

    @Select("select * from operate_log order by id desc limit 250")
    public List<OperateLog> getAllOperateLogs();

}
