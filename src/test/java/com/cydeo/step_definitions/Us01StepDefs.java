package com.cydeo.step_definitions;

import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Us01StepDefs {
    List<String> actualList;
    Set<String> expectedList;

    @Given("Establish the database connection")
    public void establish_the_database_connection() {
        DB_Util.createConnection();

    }

    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {
        DB_Util.runQuery("select id from users");
        actualList = DB_Util.getColumnDataAsList(1);
        expectedList = new LinkedHashSet<>();
        expectedList.addAll(actualList);

    }

    @Then("verify the result set")
    public void verify_the_result_set() {
        Assertions.assertEquals(expectedList.size(), actualList.size());
    }

    @When("Execute query to get all columns")
    public void execute_query_to_get_all_columns() {
        DB_Util.runQuery("select * from users");
    }

    @Then("verify the below columns are listed in result:")
    public void verify_the_below_columns_are_listed_in_result(List<String> expectedResult) {
        List<String> actualResult=DB_Util.getAllColumnNamesAsList();
        Assertions.assertEquals(expectedResult,actualResult);
    }
}