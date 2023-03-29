package Day1;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.Test;

public class httpRequests {
	
	int id;
	
	@Test(priority=1)
	void getUsers() {
		
		given()
		
		.when()
		    .get("https://reqres.in/api/users?page=2")
		
		.then()
		     .statusCode(200)
		     .body("page", equalTo(2))
		     .log().all();
		
	}
	
	@Test(priority=2)
	void createUser() {
		//We are need to create HashMap object to create a test data
		HashMap data = new HashMap();
		data.put("name", "Aasif");
		data.put("job", "Manual QA");
		
		id=given()
		     .contentType("application/json")
		     .body(data)
		
		.when()
		     .post("https://reqres.in/api/users")
		   //we are capture id from the responsebody which is used for next request
		     .jsonPath().getInt("id"); 
		
		//.then()
		//    .statusCode(201)
		//    .log().all();
		
	}
	
	
	@Test(priority=3,dependsOnMethods={"createUser"})
	void updateUser() {
		//We are need to create HashMap object to create a new updated test data
		HashMap data = new HashMap();
		data.put("name", "Aasif");
		data.put("job", "Automation QA");
		
		given()
		     .contentType("application/json")
		     .body(data)
		
		.when()
		     .get("https://reqres.in/api/users"+id)
		
		.then()
		    .statusCode(200)
		    .log().all();
		
	}
	
	
	@Test(priority=4)
	void deleteUser() {
		
		given()
		
		.when()
		    .delete("https://reqres.in/api/users"+id)
		
		.then()
		     .statusCode(204);
		
	}
	

}
