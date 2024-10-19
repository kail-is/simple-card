package simple.payment.api.paystation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simple.payment.api.paystation.in.*;
import simple.payment.api.paystation.service.*;

@RestController
@RequestMapping("/api/card")
public class CardApiController {

    private final ValidatePayment validatePayment;
    private final CertifyPayment certifyPayment;
    private final ApprovePayment approvePayment;
    private final CancelPayment cancelPayment;

    @PostMapping("/validate")
    public ResponseEntity<?> validatePayment(@RequestBody CardApiValidateCommand command) {
        return validatePayment.process(command);
    }


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


    public CardApiController(ValidatePayment validatePayment, CertifyPayment certifyPayment,
                             ApprovePayment approvePayment, CancelPayment cancelPayment) {
        this.validatePayment = validatePayment;
        this.certifyPayment = certifyPayment;
        this.approvePayment = approvePayment;
        this.cancelPayment = cancelPayment;
    }
}

