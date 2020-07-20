import com.spring_aop_annotation.OrderDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext4.xml")
public class SpringAopTest2 {
    @Resource(name = "orderDao")
    private OrderDaoImpl orderDao;

    @Test
    public void springAopAnnotationTest(){
        orderDao.add();
        orderDao.update();
    }

}
