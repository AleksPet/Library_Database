package com.cydeo.step_definitions;

import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import com.cydeo.utility.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class Us02StepDefs {

    @When("I execute query to inner join users and book_borrow on Id")
    public void iExecuteQueryToInnerJoinUsersAndBook_borrowOnId() {
        DB_Util.runQuery("select count(u.full_name)\n" +
                "from book_borrow b\n" +
                "inner join users u on u.id = b.user_id\n" +
                "where b.returned_date is null");
    }

    @Then("verify amount of people who had borrowed books")
    public void verifyAmountOfPeopleWhoHadBorrowedBooks() {
        String dbResult=DB_Util.getCellValue(1,1);
        BrowserUtil.logIn();
        BrowserUtil.waitFor(3);
        String uiResult=BrowserUtil.bookCount();
        Driver.getDriver().quit();
        Assertions.assertEquals(dbResult,uiResult);

    }

}
