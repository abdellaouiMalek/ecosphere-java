package tn.esprit.services;

import okhttp3.*;

import java.io.IOException;

public class HateSpeech {
    private static final String API_KEY = "sk-proj-RyE80Iu0pFHcB9Kxov89T3BlbkFJ8LSj2tMnA8cQVVqlDGOd";

    public static boolean containsInappropriateWords(String content) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"model\": \"text-detection-v5\", \"query\": \"" + content + "\"}");
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                // Analyser la réponse pour détecter les indications d'inappropriéité
                // Par exemple, vérifiez si la réponse contient des mots-clés indiquant l'inapproprié
                return responseBody.contains("inappropriate_word") || responseBody.contains("inappropriate");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
