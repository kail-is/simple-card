package simple.payment.api.paystation.in;

public record CardApiApproveCommand(
        String customerName,
        int installmentPeriod,
        Long price,
        String cardNumber,
        String cvc,
        String expireDate,
        String cardCompany
) {}