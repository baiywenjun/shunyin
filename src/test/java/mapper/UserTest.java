package mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shunyin.entity.SysUser;
import com.shunyin.mapper.SysUserMapper;
import com.shunyin.service.SysUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/25 16:16
 */
public class UserTest {

    private ApplicationContext app;
    private SysUserService sysUserService;
    private SysUserMapper sysUserMapper;

    @Before
    public void setUp(){
        app = new ClassPathXmlApplicationContext("spring/spring-dao.xml","spring/spring-service.xml");
        System.out.println(app);
        sysUserService = (SysUserService) app.getBean("sysUserService");
        sysUserMapper = (SysUserMapper) app.getBean("sysUserMapper");
    }

    @Test
    public void insertUser(){
        SysUser sysUser = new SysUser();
        sysUser.setUserName("aaaa");
        sysUser.setPassword("!23");
        //boolean insert = sysUserService.insert(sysUser);
        Integer insert = sysUserMapper.insert(sysUser);
        System.out.println(insert);
    }

    @Test
    public void selectOne(){
        SysUser sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("user_name", "hello"));
        System.out.println(sysUser);
    }

}
