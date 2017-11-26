import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
// to start the server with a random port
@SpringBootTest(classes = CellOpenWebTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CellOpenWebTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testRedirect() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
               // .andExpect(super().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));   }
}
