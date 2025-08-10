package br.com.e2e.profiles.e2e;

import br.com.e2e.profiles.e2e.model.Profile;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Profile API E2E Tests")
@Feature("Profiles Management")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileE2ETest {

    private Long createdProfileId;

    @BeforeAll
    void setup() {
        RestAssured.baseURI = System.getProperty("baseURI", "http://localhost:8080");
        RestAssured.basePath = "/api";
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    @Order(1)
    @Story("Create Profile")
    @Description("Verify that a new profile can be created successfully via POST request.")
    void shouldCreateProfileSuccessfully() {
        Profile newProfile = createProfilePayload("John Doe", "john.doe@test.com");

        Profile createdProfile = createProfileStep(newProfile)
            .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("name", equalTo(newProfile.getName()))
                .body("email", equalTo(newProfile.getEmail()))
            .extract()
                .as(Profile.class);

        this.createdProfileId = createdProfile.getId();
        Assertions.assertNotNull(createdProfileId, "The extracted profile ID should not be null.");
    }

    @Test
    @Order(2)
    @Story("Get Profile by ID")
    @Description("Verify that a specific profile can be retrieved by its ID.")
    void shouldGetProfileById() {
        Assertions.assertNotNull(createdProfileId, "Profile ID is null. The 'create' test must run first.");

        getProfileByIdStep(createdProfileId)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(createdProfileId.intValue()))
                .body("name", not(emptyOrNullString()))
                .body("email", not(emptyOrNullString()));
    }

    @Test
    @Order(3)
    @Story("Get All Profiles")
    @Description("Verify that the list of all profiles can be retrieved and contains the created profile.")
    void shouldGetAllProfiles() {
        getAllProfilesStep()
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(greaterThan(0)))
                .body("find { it.id == " + createdProfileId + " }", notNullValue());
    }

    @Test
    @Order(4)
    @Story("Update Profile")
    @Description("Verify that an existing profile can be updated successfully.")
    void shouldUpdateProfile() {
        Assertions.assertNotNull(createdProfileId, "Profile ID is null. The 'create' test must run first.");
        Profile updatedProfilePayload = createProfilePayload("John Doe Updated", "john.doe.updated@test.com");

        updateProfileStep(createdProfileId, updatedProfilePayload)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(createdProfileId.intValue()))
                .body("name", equalTo(updatedProfilePayload.getName()))
                .body("email", equalTo(updatedProfilePayload.getEmail()));
    }

    @Test
    @Order(5)
    @Story("Delete Profile")
    @Description("Verify that a profile can be deleted and is no longer retrievable.")
    void shouldDeleteProfile() {
        Assertions.assertNotNull(createdProfileId, "Profile ID is null. The 'create' test must run first.");

        deleteProfileStep(createdProfileId)
            .then()
                .statusCode(204);

        // Verify the profile is actually gone
        getProfileByIdStep(createdProfileId)
            .then()
                .statusCode(404);
    }

    // --- Allure Steps for Reporting ---

    @Step("Payload: Create profile with name '{name}' and email '{email}'")
    private Profile createProfilePayload(String name, String email) {
        return new Profile(name, email);
    }

    @Step("API Call: POST /profiles")
    private Response createProfileStep(Profile profile) {
        return given().contentType(ContentType.JSON).body(profile).when().post("/profiles");
    }

    @Step("API Call: GET /profiles/{profileId}")
    private Response getProfileByIdStep(Long profileId) {
        return given().pathParam("id", profileId).when().get("/profiles/{id}");
    }

    @Step("API Call: GET /profiles")
    private Response getAllProfilesStep() {
        return given().when().get("/profiles");
    }

    @Step("API Call: PUT /profiles/{profileId}")
    private Response updateProfileStep(Long profileId, Profile profile) {
        return given().contentType(ContentType.JSON).pathParam("id", profileId).body(profile).when().put("/profiles/{id}");
    }

    @Step("API Call: DELETE /profiles/{profileId}")
    private Response deleteProfileStep(Long profileId) {
        return given().pathParam("id", profileId).when().delete("/profiles/{id}");
    }
}