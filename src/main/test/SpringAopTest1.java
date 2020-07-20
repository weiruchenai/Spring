import com.spring_aop_xml.ProductDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext3.xml")
public class SpringAopTest1 {

/*    @Autowired
    @Qualifier(value = "productDao")*/
    @Resource(name = "productDao")
    private ProductDaoImpl productDao;

    @Test
    public void springAopTest1(){
        productDao.save();
        productDao.delete();
        productDao.update();
        productDao.search();
    }
}
