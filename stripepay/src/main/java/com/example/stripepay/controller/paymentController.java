package com.example.stripepay.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class paymentController {
    @Value("${stripe.api.key}")
    private String stripeApiKey;
    @PostMapping("/create-payment-intent")
    public String createPaymentIntent(@RequestParam Integer amount) {
        Stripe.apiKey=stripeApiKey;
        try {
            PaymentIntent intent = PaymentIntent.create(
                    new PaymentIntentCreateParams.Builder()
                            .setCurrency("usd")
                            .setAmount((long)amount+100)
                            .build());

            return generateResponse( intent.getClientSecret());
        } catch (StripeException e) {

            return generateResponse("Payment failed: " + e.getMessage());
        }
    }
    private String generateResponse(String clientSecret){
        return "{\"clientSecret\":\""+clientSecret + "\"}";
    }

}

