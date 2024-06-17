package Educa.plus.Educa.controllers;

import Educa.plus.Educa.domain.feedbacks.EnviarFeedbackDTO;
import Educa.plus.Educa.domain.feedbacks.UpdateFeedbackDTO;
import Educa.plus.Educa.services.FeedbackServices;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedback")
public class FeedbackController {

    @Autowired
    private FeedbackServices feedbackServices;


    @GetMapping("/{idAtividade}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity showFeedbacksByAtividade(@PathVariable String idAtividade){
        return feedbackServices.showAllFromOneAtividade(idAtividade);
    }

    @PostMapping("/add")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity postFeedBackToAtividade(@RequestBody @Valid EnviarFeedbackDTO data){
        return feedbackServices.postAFeedback(data);
    }

    @DeleteMapping("/remove/{id}")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity removeFeedBack(@PathVariable String id){
        return feedbackServices.removeFeedback(id);
    }

    @PutMapping("/update/{id}")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity updateFeedback(@PathVariable String id ,@RequestBody UpdateFeedbackDTO data){
        return feedbackServices.updateFeedback(id, data);
    }
}
