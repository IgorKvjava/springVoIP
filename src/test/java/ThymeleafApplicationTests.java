import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import ua.kvelinskyi.controllers.AdminController;
import ua.kvelinskyi.controllers.LoginController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ThymeleafApplicationTests.class )
public class ThymeleafApplicationTests {

    @Autowired
    private LoginController loginController;

    @Autowired
    AdminController adminController;

    @Test
    public void contextLoads() throws Exception {
        assert (loginController).getInfoPage().isEmpty();
    }
}
