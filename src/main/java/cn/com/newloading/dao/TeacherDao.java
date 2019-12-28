package cn.com.newloading.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.com.newloading.bean.Teacher;

@Mapper
public interface TeacherDao {

	/**
	 * 查询教师
	 * @param t
	 * @return
	 */
	List<Teacher> queryTea(Teacher t);
	
	/**
	 * 修改教师信息
	 * @param t
	 * @return
	 */
	Integer editTea(Teacher t);
	
	/**
	 * 删除教师
	 * @param t
	 * @return
	 */
	Integer delTea(Teacher t);
	
	/**
	 * 新增教师
	 * @param t
	 * @return
	 */
	Integer addTea(Teacher t);
}
