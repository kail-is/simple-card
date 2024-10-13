package simple.payment.api.paystation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simple.payment.api.paystation.common.RejectedProcessException;
import simple.payment.api.paystation.enumeration.PaymentState;
import simple.payment.api.paystation.in.*;
import simple.payment.api.paystation.out.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static simple.payment.api.paystation.enumeration.PaymentState.*;

@RestController
@RequestMapping("/api/card")
public class CardApiController {

    private final Random random = new Random();

    @PostMapping("/certify")
    public ResponseEntity<?> certifyPayment(@RequestBody CardApiCertifyCommand command) throws InterruptedException {

        LocalDateTime requestAt = LocalDateTime.now();

        PaymentState state = getCertifyRandomState();

        try{
            someKindOfProcess(state);
        } catch (RejectedProcessException e) {

            String failReason = getCertifyFailReason(state);

            CardApiCertifyRejectedView view =  new CardApiCertifyRejectedView(
                    state,
                    failReason,
                    command.cardNumber(),
                    command.customerName(),
                    requestAt
            );

            return ResponseEntity.badRequest().body(view);
        }

        LocalDateTime certifiedAt = LocalDateTime.now();

        CardApiCertifyView response = new CardApiCertifyView(
                state,
                command.cardNumber(),
                command.customerName(),
                UUID.randomUUID().toString(),
                requestAt,
                certifiedAt
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/pay")
    public ResponseEntity<?> approvePayment(@RequestBody CardApiApproveCommand command) throws InterruptedException {

        LocalDateTime requestAt = LocalDateTime.now();

        PaymentState state = getApproveRandomState();

        try{
            someKindOfProcess(state);
        } catch (RejectedProcessException e) {

            String failReason = getApproveFailReason(state);

            CardApiApproveRejectView view =  new CardApiApproveRejectView(
                state,
                failReason,
                requestAt
            );

            return ResponseEntity.badRequest().body(view);
        }


        LocalDateTime approvedAt = LocalDateTime.now();

        CardApiApproveView view = new CardApiApproveView(
                state,
                command.price(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                requestAt,
                approvedAt
        );

        return ResponseEntity.ok(view);
    }


    @PostMapping("/cancel")
    public ResponseEntity<?> cancelPayment(@RequestBody CardApiCancelCommand command) throws InterruptedException {

        LocalDateTime requestAt = LocalDateTime.now();

        PaymentState state = getCancelRandomState();

        String failReason = getCancelFailReason(state);

        try{
            someKindOfProcess(state);
        } catch (RejectedProcessException e) {

            CardApiCancelRejectView view = new CardApiCancelRejectView(
                    state,
                    failReason,
                    command.paymentId(),
                    LocalDateTime.now()
            );

            return ResponseEntity.badRequest().body(view);

        }

        LocalDateTime canceledAt = LocalDateTime.now();

        CardApiCancelView view = new CardApiCancelView(
                state,
                command.paymentId(),
                command.transactionId(),
                UUID.randomUUID().toString(),
                requestAt,
                canceledAt
        );

        return ResponseEntity.ok(view);
    }


    private PaymentState getCertifyRandomState() {
        PaymentState[] states = new PaymentState[]{TIMEOUT, CERTIFIED, INVALID};
        return states[random.nextInt(states.length)];
    }

    private PaymentState getApproveRandomState() {
        PaymentState[] states = new PaymentState[]{TIMEOUT, APPROVED, REJECTED};
        return states[random.nextInt(states.length)];
    }

    private PaymentState getCancelRandomState() {
        PaymentState[] states = new PaymentState[]{TIMEOUT, CANCELED, REJECTED};
        return states[random.nextInt(states.length)];
    }

    private void someKindOfProcess(PaymentState state) throws InterruptedException, RejectedProcessException {

        Thread.sleep(500);

        if(state == TIMEOUT) {
            Thread.sleep(3000);
        }

        if(state == REJECTED || state == TIMEOUT ) {
            throw new RejectedProcessException();
        }


    }


    private String getCertifyFailReason(PaymentState state) {

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


    private String getCancelFailReason(PaymentState state) {

        if(state == TIMEOUT) {
            return "거래 시간을 초과했습니다.";
        }

        String[] failReasons = new String[]{
            "환불할 수 없는 거래입니다. 가맹점에 문의하세요.",
            "환불 가능 기한을 초과하였습니다.",
            "중복 환불을 시도했습니다."
        };
        return failReasons[random.nextInt(failReasons.length)];
    }

}

