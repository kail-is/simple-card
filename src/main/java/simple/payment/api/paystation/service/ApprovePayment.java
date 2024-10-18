package simple.payment.api.paystation.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import simple.payment.api.paystation.common.RejectedProcessException;
import simple.payment.api.paystation.common.Utils;
import simple.payment.api.paystation.enumeration.PaymentState;
import simple.payment.api.paystation.in.CardApiApproveCommand;
import simple.payment.api.paystation.out.CardApiApproveRejectView;
import simple.payment.api.paystation.out.CardApiApproveView;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static simple.payment.api.paystation.enumeration.PaymentState.*;

@Component
public class ApprovePayment {

    public ResponseEntity<?> process(CardApiApproveCommand command) {

        PaymentState state = getApproveRandomState();
        LocalDateTime requestAt = LocalDateTime.now();

        try {
            Utils.someKindOfProcess(state);
            return ResponseEntity.ok(createApproveSuccessView(state, command, requestAt));
        } catch (RejectedProcessException | InterruptedException e) {
            return ResponseEntity.badRequest().body(createApproveRejectedView(state, command, requestAt));
        }

    }


    private CardApiApproveView createApproveSuccessView(
            PaymentState state,
            CardApiApproveCommand command,
            LocalDateTime requestAt) {

        LocalDateTime approvedAt = LocalDateTime.now();

        return new CardApiApproveView(
                state,
                command.price(),
                command.sellerId(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                requestAt,
                approvedAt
        );
    }

    private CardApiApproveRejectView createApproveRejectedView(
            PaymentState state,
            CardApiApproveCommand command,
            LocalDateTime requestAt) {

        String failReason = getApproveFailReason(state);

        return new CardApiApproveRejectView(
                state,
                failReason,
                requestAt
        );

    }

    private final Random random = new Random();
    private PaymentState getApproveRandomState() {
//        PaymentState[] states = new PaymentState[]{TIMEOUT, APPROVED, REJECTED};

        PaymentState[] states = new PaymentState[]{APPROVED};
        return states[new Random().nextInt(states.length)];
    }

    private String getApproveFailReason(PaymentState state) {

        if(state == TIMEOUT) {
            return "거래 시간을 초과했습니다.";
        }

        String[] failReasons = new String[]{
                "결제 요청 카드와 인증 정보가 일치하지 않습니다.",
                "유효한 인증 카드 번호가 아닙니다.",
                "만료된 인증 번호입니다.",
                "잔액이 부족합니다.",
                "일별 결제 한도를 초과했습니다.",
                "월별 결제 한도를 초과했습니다.",
                "중복 결제를 시도했습니다."
        };

        return failReasons[random.nextInt(failReasons.length)];
    }

}

