package simple.payment.api.paystation.out;

import java.time.LocalDateTime;

public record CardApiValidateView(
        boolean isValid,
        String message,
        LocalDateTime requestAt
) {}
