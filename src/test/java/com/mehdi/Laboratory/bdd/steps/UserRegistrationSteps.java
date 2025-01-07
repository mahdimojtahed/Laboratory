package com.mehdi.Laboratory.bdd.steps;


import com.mehdi.Laboratory.bdd.enums.ParamType;
import com.mehdi.Laboratory.dto.register.AddUserReqDTO;
import com.mehdi.Laboratory.shared.constants.Routes;
import io.cucumber.java.en.When;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

public class UserRegistrationSteps extends AbstractSteps implements En {


    @When("the user tries to register with following data")
    public void ok(List<AddUserReqDTO> table) {
        String userApiRoute = Routes.USERS + "/register";
        AddUserReqDTO addUserReqDTO = table.getFirst();
        testContext().setPayload(addUserReqDTO);
        executePost(userApiRoute, null, null, ParamType.JSON);
    }


    public UserRegistrationSteps() {

        Then("the user should see the error message {string}", (String errorMessage) -> {
            Response response = testContext().getResponse();

            assertThat(response)
                    .satisfies(re -> {
                        assertThat(re.statusCode()).isEqualTo(400);
                        assertThat(re.jsonPath().getString("message")).isEqualTo(errorMessage);
                    });

        });
    }




}
