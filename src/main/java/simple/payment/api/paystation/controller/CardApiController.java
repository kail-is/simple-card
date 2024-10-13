package simple.payment.api.paystation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simple.payment.api.paystation.in.*;
import simple.payment.api.paystation.service.*;

@RestController
@RequestMapping("/api/card")
public class CardApiController {

    private final CertifyPayment certifyPayment;
    private final ApprovePayment approvePayment;
    private final CancelPayment cancelPayment;


    @PostMapping("/certify")
    public ResponseEntity<?> certifyPayment(@RequestBody CardApiCertifyCommand command) {
        return certifyPayment.process(command);
    }


    @PostMapping("/pay")
    public ResponseEntity<?> approvePayment(@RequestBody CardApiApproveCommand command) {
        return approvePayment.process(command);
    }


    @PostMapping("/cancel")
    public ResponseEntity<?> cancelPayment(@RequestBody CardApiCancelCommand command) {
        return cancelPayment.process(command);
    }


    public CardApiController(CertifyPayment certifyPayment, ApprovePayment approvePayment, CancelPayment cancelPayment) {
        this.certifyPayment = certifyPayment;
        this.approvePayment = approvePayment;
        this.cancelPayment = cancelPayment;
    }
}

