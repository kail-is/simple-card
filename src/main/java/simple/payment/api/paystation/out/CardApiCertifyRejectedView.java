package simple.payment.api.paystation.out;

import simple.payment.api.paystation.enumeration.PaymentState;

import java.time.LocalDateTime;

public record CardApiCertifyRejectedView(
        PaymentState state,
        String failReason,
        String cardNumber,
        String customerName,
        LocalDateTime requestAt
) {}
