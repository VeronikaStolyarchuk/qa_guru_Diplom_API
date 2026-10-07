package testData;

import com.github.javafaker.Faker;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class TestData {
    Faker faker = new Faker(new Locale("ru_RU"));
    public String userName = faker.name().lastName();
    public String userEmail = faker.internet().emailAddress();
    public String password = faker.internet().password();
    public String loginIncorrectPassword = faker.internet().password();
    public String userPhone = faker.numerify("89#########");
    public String userCompany = faker.company().name();
    public String noteTitle = "Заметка: " + faker.commerce().productName();
    public String noteDescription = faker.lorem().paragraph();
    public String notExistingNoteId = faker.internet().uuid();

    public static final String LOGIN_EMAIL = "123@mail.ru";
    public static final String LOGIN_PASSWORD = "123456";
    public List<String> categories = List.of("Home", "Work", "Personal");
    public String noteCategory = categories.get(ThreadLocalRandom.current().nextInt(categories.size()));
}
