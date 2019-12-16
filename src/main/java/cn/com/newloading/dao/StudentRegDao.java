package cn.com.newloading.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.com.newloading.bean.StudentReg;

@Mapper
public interface StudentRegDao {

	/**
	 * 学生注册表,返回id
	 * @param student
	 * @return
	 */
	Integer registerStu(StudentReg studentReg);
	
	/**
	 * 查询学生注册表
	 * @return
	 */
	List<StudentReg> queryStu(StudentReg studentReg);
	
	/**
	 * 查账号是否有重复，学号是否有重复
	 * @param stuAccount
	 * @param stuStudyNumber
	 * @return
	 */
	List<StudentReg> queryStuByParms(@Param("stuAccount")String stuAccount,@Param("stuStudyNumber")String stuStudyNumber);
	
}