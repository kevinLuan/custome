/**
 * 
 */
package com.feinno.role.services;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;

import com.feinno.role.TestCaseBase;
import com.feinno.role.dao.model.RoleUser;

/**
 * @author <a href="mailto:wxh512@gmail.com">Wang XuHui</a>
 * 
 */
@Ignore
public class RoleUserServiceTest extends TestCaseBase {

	@Resource(name = "roleUserService")
	RoleUserService roleUserService;

	@Test
	public void testInsertRoleUser() {
		try {
			RoleUser roleUser = new RoleUser();
			roleUser.setNickName("test");
			roleUser.setUserName("test");
			roleUser.setPassWord("test");
			int buryResult = roleUserService.insertRoleUser(roleUser);
			System.out.println(buryResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
