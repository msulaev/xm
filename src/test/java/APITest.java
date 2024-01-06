import com.xm.api.entity.Character;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.xm.utils.DateUtils.parseReleaseDate;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class APITest {
    private static final String BASE_URL = "https://swapi.dev/api";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void findLatestFilmAndTallestPerson() {
        Response filmsResponse = RestAssured.get("/films");
        List<Map<String, Object>> films = filmsResponse.jsonPath().getList("results");

        Map<String, Object> latestFilm = films.stream()
                .max(Comparator.comparing(f -> parseReleaseDate((String) f.get("release_date"))))
                .orElseThrow();
        assertThat(latestFilm.get("title")).isEqualTo("Revenge of the Sith");

        ArrayList<String> characters = (ArrayList<String>) latestFilm.get("characters");
        Character tallestPerson = characters.stream()
                .map(url -> RestAssured.get(url).as(Character.class))
                .max(Comparator.comparingInt(Character::getHeight))
                .orElseThrow();
        assertThat(tallestPerson.getName()).isEqualTo("Tarfful");
    }

    @Test
    public void findTallestPersonEver() {
        List<Map<String, Object>> people = new ArrayList<>();
        Response peopleResponse = RestAssured.get("/people");
        String nextPage = peopleResponse.jsonPath().getString("next");

        while (nextPage != null) {
            peopleResponse = RestAssured.get(nextPage).then()
                    .assertThat()
                    .body(matchesJsonSchemaInClasspath("people-schema.json"))
                    .extract()
                    .response();
            nextPage = peopleResponse.jsonPath().getString("next");
            people.addAll((peopleResponse.jsonPath().getList("results")));
        }

        Character tallestPerson = people.stream()
                .filter(p -> !p.get("height").equals("unknown"))
                .map(p -> {
                    Character character = new Character();
                    character.setName(p.get("name").toString());
                    character.setHeight(Integer.parseInt(p.get("height").toString()));
                    return character;
                }).max(Comparator.comparingInt(Character::getHeight)).get();
        assertThat(tallestPerson.getName()).isEqualTo("Yarael Poof");
    }

    @Test
    public void schemaValidation() {
        RestAssured.get("/people")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("people-schema.json"));
    }
}
