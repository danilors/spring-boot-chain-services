package br.com.e2e.profiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileE2ETest {

    private Long createdProfileId;
    private String userEmail;
    private String updatedUserEmail;

    @BeforeAll
    void setup() {
        RestAssured.baseURI = System.getProperty("baseURI", "http://localhost:33501");
        RestAssured.basePath = "/api";

        // Generate unique emails for this specific test run to ensure idempotency
        // and avoid conflicts from previous test runs.
        long timestamp = System.currentTimeMillis();
        this.userEmail = "john.doe." + timestamp + "@test.com";
        this.updatedUserEmail = "john.doe.updated." + timestamp + "@test.com";
    }

    @Test
    @Order(1)
    @DisplayName("Should create a new profile successfully")
    void shouldCreateProfileSuccessfully() {
        // Arrange: Define the payload for the new profile.
        Profile newProfile = createProfilePayload("John Doe", this.userEmail, 1L, 1L);

        // Act: Send the POST request to create the profile.
        Profile createdProfile = createProfileStep(newProfile)
            .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("name", equalTo(newProfile.name()))
                .body("email", equalTo(newProfile.email()))
            .extract()
                .as(Profile.class);

        this.createdProfileId = createdProfile.id();
        Assertions.assertNotNull(createdProfileId, "The extracted profile ID should not be null.");
    }

    @Test
    @Order(2)
    @DisplayName("Should retrieve a specific profile by its ID")
    void shouldGetProfileById() {
        Assertions.assertNotNull(createdProfileId, "Profile ID is null. The 'create' test must run first.");

        getProfileByIdStep(createdProfileId)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(createdProfileId.intValue()))
                .body("name", equalTo("John Doe"))
                .body("email", equalTo(this.userEmail));
    }

    @Test
    @Order(3)
    @DisplayName("Should retrieve all profiles and find the created one")
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
    @DisplayName("Should update an existing profile successfully")
    void shouldUpdateProfile() {
        Assertions.assertNotNull(createdProfileId, "Profile ID is null. The 'create' test must run first.");
        Profile updatedProfilePayload = createProfilePayload("John Doe Updated", this.updatedUserEmail, 2L, 2L);

        updateProfileStep(createdProfileId, updatedProfilePayload)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(createdProfileId.intValue()))
                .body("name", equalTo(updatedProfilePayload.name()))
                .body("email", equalTo(updatedProfilePayload.email()));
    }

    @Test
    @Order(5)
    @DisplayName("Should delete a profile and verify it is gone")
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

    // --- Helper Methods ---

    private Profile createProfilePayload(String name, String email, Long addressId, Long occupationId) {
        return new Profile(name, email, addressId, occupationId);
    }

    private Response createProfileStep(Profile profile) {
        return given().contentType(ContentType.JSON).body(profile).when().post("/profiles");
    }

    private Response getProfileByIdStep(Long profileId) {
        return given().pathParam("id", profileId).when().get("/profiles/{id}");
    }

    private Response getAllProfilesStep() {
        return given().when().get("/profiles");
    }

    private Response updateProfileStep(Long profileId, Profile profile) {
        return given().contentType(ContentType.JSON).pathParam("id", profileId).body(profile).when().put("/profiles/{id}");
    }

    private Response deleteProfileStep(Long profileId) {
        return given().pathParam("id", profileId).when().delete("/profiles/{id}");
    }
}