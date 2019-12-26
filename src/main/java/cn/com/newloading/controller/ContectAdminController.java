package cn.com.newloading.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.newloading.bean.Admin;
import cn.com.newloading.bean.ContectAdmin;
import cn.com.newloading.bean.Dict;
import cn.com.newloading.bean.Student;
//import cn.com.newloading.bean.Teacher;
import cn.com.newloading.enums.RoleType;
import cn.com.newloading.service.ContectAdminService;
import cn.com.newloading.service.DictService;
import cn.com.newloading.utils.StringUtil;

@Controller
@RequestMapping("/contectAdmin")
public class ContectAdminController {

	@Autowired
	private DictService dictService;
	@Autowired
	private ContectAdminService caService;

	@RequestMapping("/addContectAdmin")
	@ResponseBody
	public JSONObject addContectAdmin(HttpServletRequest request,@RequestBody Map<String, Object> params) {
		String foreignType = (String) params.get("foreignType");
		if (StringUtil.isBlank(foreignType)) {
			return responseMsg("CONADM0002", "CONADM");
		}
		if (!RoleType.ADM.getRole().equals(foreignType) && !RoleType.STU.getRole().equals(foreignType)
				&& !RoleType.TEA.getRole().equals(foreignType)) {
			return responseMsg("CONADM0003", "CONADM");
		}
		
		ContectAdmin contectAdmin = new ContectAdmin();
		contectAdmin.setForeignType(foreignType);
		
		/*取外键id*/
		if(RoleType.STU.getRole().equals(foreignType)) {
			Student student = (Student) request.getSession().getAttribute("student");
			if(null == student || "".equals(student.getId())) {
				return responseMsg("STU00006", "STUDENT");
			}
			contectAdmin.setForeignId(student.getId());
		}else if(RoleType.TEA.getRole().equals(foreignType)) {
//			Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
//			if(null == teacher || "".equals(teacher.getId())) {
//				return responseMsg("TEA00006", "TEACHER");
//			}
//			contectAdmin.setForeignId(teacher.getId());
		}else {
			Admin admin = (Admin) request.getSession().getAttribute("admin");
			if(null == admin || "".equals(admin.getId())) {
				return responseMsg("SYSMSG0004", "SYSMSG");
			}
			contectAdmin.setForeignId(admin.getId());
		}
		
		//父级id，属于哪个问题下的，如果是问题说明为空
		String pid = (String) params.get("pid");
		contectAdmin.setPid(pid);
		
		//消息内容
		String content = (String) params.get("content");
		if(StringUtil.isBlank(content)) {
			return responseMsg("CONADM0004", "CONADM");
		}
		contectAdmin.setContent(content);
		
		String retcode = caService.addContectAdmin(contectAdmin);
		return responseMsg(retcode, "CONADM");
	}
	
	@RequestMapping("/queryContectAdminForAdmin")
	@ResponseBody
	public JSONObject queryContectAdminForAdmin(@RequestBody Map<String, Object> params) {
		JSONObject json = new JSONObject();
		String foreignType = (String) params.get("foreignType");
		if (StringUtil.isBlank(foreignType)) {
			return responseMsg("CONADM0002", "CONADM");
		}
		if (!RoleType.STU.getRole().equals(foreignType) && !RoleType.TEA.getRole().equals(foreignType)) {
			return responseMsg("CONADM0003", "CONADM");
		}
		ContectAdmin contectAdmin = new ContectAdmin();
		contectAdmin.setForeignType(foreignType);
		
		//内容提供模糊查询
		String content = (String) params.get("content");
		if(StringUtil.isNotBlank(content)) {
			contectAdmin.setContent(content);
		}
		
		//是否有没回复
		String replyFlag = (String) params.get("replyFlag");//可以不选择
		if(StringUtil.isBlank(replyFlag)) {
			contectAdmin.setReplyFlag(replyFlag);
		}
		
		List<ContectAdmin> caList = caService.queryContectAdmin(contectAdmin);
		json.put("contectAdminList", caList);
		return json;
	}

	/* 错误码返回 */
	private JSONObject responseMsg(String code, String type) {
		JSONObject json = new JSONObject();
		Dict dict = new Dict();
		dict.setCode(code);
		dict.setType(type);
		List<Dict> dictList = dictService.queryDict(dict);
		dict = dictList.get(0);
		json.put("retCode", dict.getCode());
		json.put("retMsg", dict.getValue());
		return json;
	}
}
