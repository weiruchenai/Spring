import com.spring_test.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest1 {
    /*传统方式*/
    @Test
    public void springTest1(){
        UserServiceImpl userService = new UserServiceImpl();
        userService.save();
        UserDatabaseImpl userDatabase = new UserDatabaseImpl();
        userDatabase.save();
    }

    /*Spring的IOC方式*/
    @Test
    public void springTest2(){
        //创建Spring的工厂
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserServiceImpl userService = (UserServiceImpl) applicationContext.getBean("userService");//里面写bean的id
        userService.save();
        System.out.println(userService);
    }

    @Test
    public void springTest3(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
       /* NameDao nameDao = (NameDao)applicationContext.getBean("nameDao");
        nameDao.save();
        applicationContext.close();*/
        NameDao2 nameDao2 = (NameDao2)applicationContext.getBean("nameDao2");
        System.out.println(nameDao2);
    }

}
