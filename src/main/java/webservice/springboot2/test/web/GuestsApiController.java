package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import webservice.springboot2.test.service.GuestsService;

@RequiredArgsConstructor
@Controller
public class GuestsApiController {
    private final GuestsService guestsService;

    @PostMapping("/api/v1/guests")
    public long LoginForGuests(){
        return guestsService.save();
    }
}
