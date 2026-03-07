
import com.fluxbank.advancedfeaturesservice.fixeddeposit.dto.CreateFDRequest;
import com.fluxbank.advancedfeaturesservice.fixeddeposit.dto.FDResponse;
import com.fluxbank.advancedfeaturesservice.fixeddeposit.service.FixedDepositService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fd")
public class FixedDepositController {

    private final FixedDepositService fdService;

    public FixedDepositController(FixedDepositService fdService) {
        this.fdService = fdService;
    }

    @PostMapping
    public FDResponse createFD(@RequestBody CreateFDRequest request) {
        return fdService.createFD(request);
    }
}