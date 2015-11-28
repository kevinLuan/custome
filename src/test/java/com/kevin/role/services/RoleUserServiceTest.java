/**
 * 
 */
package com.kevin.role.services;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;

import com.kevin.role.TestCaseBase;
import com.kevin.role.dao.model.RoleUser;
import com.kevin.role.services.RoleUserService;

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
      e.printStackTrace();
    }

  }
}
