package tn.esprit.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private WebView stripeWebView;

    private WebEngine webEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stripe.apiKey = "sk_test_51PDyHcRwyGFgTalHSV15ShXa3kNwZwwjcGa1XafrGokLamVpSvCiN8njuEMNg4GP3TPnYS5ZL25ttwmnKcENhjQ800SUHUyCir";
        webEngine = stripeWebView.getEngine();
        webEngine.load(getClass().getResource("/stripe.html").toExternalForm()); // Load your HTML file containing Stripe.js
    }

    public PaymentIntent createPaymentIntent(int amount, String currency) throws StripeException {
        Stripe.apiKey = "sk_test_51PDyHcRwyGFgTalHSV15ShXa3kNwZwwjcGa1XafrGokLamVpSvCiN8njuEMNg4GP3TPnYS5ZL25ttwmnKcENhjQ800SUHUyCir";

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);

        return PaymentIntent.create(params);
    }

    @FXML
    private PaymentIntent handlePayment(int price) throws StripeException {
        String currency = "eur"; // Currency code
        String cardNumber = "4242424242424242";
        String expMonth = "12";
        String expYear = "2023";
        String cvc = "123";
        var stripe = Stripe.apiKey;

        //   Create a token with the card information

        PaymentIntent res = createPaymentIntent(price,"eur");
        return res;


    }
}
