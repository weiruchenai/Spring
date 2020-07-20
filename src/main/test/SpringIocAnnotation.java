import com.spring_ioc_annotation.UserDao;
import com.spring_ioc_annotation.UserDaoImpl;
import com.spring_ioc_annotation.UserRepository;
import com.spring_ioc_annotation.UserRepositoryImpl;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIocAnnotation {
    //传统方法
    @Test
    public void springIocAnnotationTest1(){
        UserDao userDao = new UserDaoImpl();
        userDao.save();
    }

    //注解方式
    @Test
    public void springIocAnnotationTest2(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext2.xml");
        UserDaoImpl userDao = (UserDaoImpl)classPathXmlApplicationContext.getBean("userDao");
        userDao.save();
    }

    @Test
    public void springIocAnnotationTest3(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext2.xml");
        UserRepositoryImpl userRepository = (UserRepositoryImpl)classPathXmlApplicationContext.getBean("userRepository");
        userRepository.save();
        classPathXmlApplicationContext.close();
    }
}
