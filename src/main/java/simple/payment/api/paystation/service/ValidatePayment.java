package simple.payment.api.paystation.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import simple.payment.api.paystation.common.RejectedProcessException;
import simple.payment.api.paystation.common.Utils;
import simple.payment.api.paystation.enumeration.PaymentState;
import simple.payment.api.paystation.in.CardApiValidateCommand;
import simple.payment.api.paystation.out.CardApiValidateView;

import java.time.LocalDateTime;
import java.util.Random;

import static simple.payment.api.paystation.enumeration.PaymentState.*;

@Component
public class ValidatePayment {

    public ResponseEntity<?> process(CardApiValidateCommand command) {

        PaymentState state = getValidateRandomState();
        LocalDateTime requestAt = LocalDateTime.now();
        String message = "유효한 카드입니다.";

        try {
            Utils.someKindOfProcess(state);
            return ResponseEntity.ok(createValidView(true, message, requestAt));
        } catch (RejectedProcessException | InterruptedException e) {
            message = getValidateFailReason(state);
            return ResponseEntity.badRequest().body(createValidView(false, message, requestAt));
        }

    }


    private final Random random = new Random();

    private PaymentState getValidateRandomState() {
        PaymentState[] states = new PaymentState[]{TIMEOUT, CERTIFIED, INVALID};
//        PaymentState[] states = new PaymentState[]{CERTIFIED};
        return states[random.nextInt(states.length)];
    }


    private CardApiValidateView createValidView(
            boolean isValid,
            String message,
            LocalDateTime requestAt) {

        return new CardApiValidateView(
                isValid,
                message,
                requestAt
        );
    }



    private String getValidateFailReason(PaymentState state) {

        if(state == TIMEOUT) {
            return "거래 시간을 초과했습니다.";
        }

        String[] failReasons = new String[]{
                "카드 정보가 일치하지 않습니다.",
                "만료된 카드 정보입니다.",
                "분실신고된 카드입니다.",
                "정지된 카드입니다.",
                "CVV 코드가 일치하지 않습니다."
        };

        return failReasons[random.nextInt(failReasons.length)];
    }


}

