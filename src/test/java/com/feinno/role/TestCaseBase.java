/**
 * 
 */
package com.feinno.role;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author root
 * 
 */
@ContextConfiguration(locations = { "classpath:spring_role_*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class TestCaseBase extends AbstractJUnit4SpringContextTests {
	protected static final Log log = LogFactory.getLog("spring-test");
}
