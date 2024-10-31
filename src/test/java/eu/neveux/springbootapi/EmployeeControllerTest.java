package eu.neveux.springbootapi;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.neveux.springbootapi.model.Employee;
import net.bytebuddy.utility.RandomString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class EmployeeControllerTest {
    
    @Autowired
    public MockMvc mockMvc;

    @Test
    void testGetEmployees() throws Exception {
        mockMvc.perform(get("/employees"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].firstName", is("Laurent")));
    }

    @Test
    void testGetEmployee() throws Exception {
        mockMvc.perform(get("/employees/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("firstName", is("Laurent")));
    }

    @Test
    void testPostEmployees() throws Exception {

        //creat new employee Tester
        Employee employeeTest = new Employee();
        employeeTest.setFirstName("Tester");
        employeeTest.setLastName("Test");
        employeeTest.setMail("Tester@test.test");
        employeeTest.setPassword(new RandomString().toString());

        //test post employee
        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/employees")
            .content(asJsonString(employeeTest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("firstName", is("Tester")));
    }


    /**
    * Object to String
    * @param obj
    * @return - An object Json
    */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
