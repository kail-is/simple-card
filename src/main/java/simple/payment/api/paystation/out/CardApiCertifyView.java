package simple.payment.api.paystation.out;

import simple.payment.api.paystation.enumeration.PaymentState;

import java.time.LocalDateTime;

public record CardApiCertifyView(
        PaymentState state,
        String cardNumber,
        String customerName,
        LocalDateTime requestAt,
        LocalDateTime certifiedAt
) {}
