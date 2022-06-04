import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Converter {
    private final HttpClient client;


    Converter() {
        client = HttpClient.newHttpClient();
    }

    private double getRates(String currName) {
        double rateFromRub = 0;
        URI url = URI.create("https://api.exchangerate.host/latest?base=RUB&symbols=" + currName);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // проверяем, успешно ли обработан запрос
            if (response.statusCode() == 200) {
                JsonElement jsonElement = JsonParser.parseString(response.body());
                if (!jsonElement.isJsonObject()) { // проверяем, точно ли мы получили JSON-объект
                    System.out.println("Ответ от сервера не соответствует ожидаемому.");
                    return 0;
                }

                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonObject jsonObjectRates = jsonObject.get("rates").getAsJsonObject();
                try {
                    rateFromRub = jsonObjectRates.get(currName).getAsDouble();
                } catch (NullPointerException exc) {
                    rateFromRub = 0;
                }

            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }

        return rateFromRub;
    }

    void convert(double rubles, String currName) {
        double rateFromRub = getRates(currName);
        if (rateFromRub > 0) {

            System.out.printf("Ваши сбережения: %.2f %s\n", (rubles * rateFromRub), currName);
        } else {
            System.out.println("Такого кода валюты не существует");
        }
    }
}