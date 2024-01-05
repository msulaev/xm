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

public class APITest {
    private static final String BASE_URL = "https://swapi.dev/api";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void findLatestFilm(){
        Response filmsResponse = RestAssured.get(BASE_URL + "/films");
        List<Map<String, Object>> films = filmsResponse.jsonPath().getList("results");

        Map<String, Object> latestFilm = films.stream()
                .max(Comparator.comparing(f -> parseReleaseDate((String) f.get("release_date"))))
                .orElseThrow();
        ArrayList<String> characters = (ArrayList<String>) latestFilm.get("characters");

        Character tallestPerson = characters.stream()
                .map(url -> RestAssured.get(url).as(Character.class))
                .max(Comparator.comparingInt(Character::getHeight))
                .orElseThrow();
        System.out.println("The tallest character from the film with the latest release date is " + tallestPerson.getName());

    }
}
