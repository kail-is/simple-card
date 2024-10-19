package simple.payment.api.paystation.in;

public record CardApiValidateCommand(
        String cardNumber,
        String cvc,
        String expireDate,
        String cardCompany,
        String customerName
) {}

