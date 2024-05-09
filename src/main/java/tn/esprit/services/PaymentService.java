package tn.esprit.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;

public class PaymentService {
    private static final String STRIPE_API_KEY = "sk_test_51PDyHcRwyGFgTalHSV15ShXa3kNwZwwjcGa1XafrGokLamVpSvCiN8njuEMNg4GP3TPnYS5ZL25ttwmnKcENhjQ800SUHUyCir";

    static {
        Stripe.apiKey = STRIPE_API_KEY;
    }

    /**
     * Process payment using Stripe API.
     *
     * @param token    The Stripe token representing the payment source (card).
     * @param amount   The amount to charge in cents.
     * @param currency The currency of the charge (e.g., "usd").
     * @return True if payment was successful, false otherwise.
     */
    public static boolean processPayment(String token, int amount, String currency) {
        try {
            // Create charge parameters
            ChargeCreateParams params = ChargeCreateParams.builder()
                    .setAmount(Long.valueOf(amount))
                    .setCurrency(currency)
                    .setSource(token)
                    .build();

            // Create the charge
            Charge charge = Charge.create(params);

            // Handle successful charge
            System.out.println("Payment processed successfully! Charge ID: " + charge.getId());
            return true;
        } catch (StripeException e) {
            // Handle errors
            System.err.println("Error processing payment: " + e.getMessage());
            return false;
        }
    }
}
