package merisalu.kmdb.controller;

import merisalu.kmdb.dto.ActorDTO;
import merisalu.kmdb.service.ActorService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import merisalu.kmdb.dto.ActorsPatchRequest;

@RestController
@RequestMapping("/api/actors")
public class ActorController {
    
    private final ActorService actorService;
    
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<ActorDTO> getAllActors() {
        return actorService.getAllActors();
    }

    @GetMapping("/{id}")
    public ActorDTO getActorById(@PathVariable Long id) {
        return actorService.getActorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActorDTO createActor(@Valid @RequestBody ActorDTO actorDTO) {
        return actorService.createActor(actorDTO);
    }

    @PatchMapping("/batch")
    @ResponseStatus(HttpStatus.OK)
    public List<ActorDTO> patchActors(@Valid @RequestBody ActorsPatchRequest patchRequest) {
        return actorService.patchActors(patchRequest);
    }

    @PatchMapping("/{id}")
    public ActorDTO updateActor(@PathVariable Long id, @Valid @RequestBody ActorDTO actorDTO) {
        return actorService.updateActor(id, actorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActor(@PathVariable Long id, 
                          @RequestParam(required = false, defaultValue = "false") boolean force) {
        actorService.deleteActor(id, force);
    }

    @GetMapping("/search")
    public List<ActorDTO> searchActors(@RequestParam String name) {
        return actorService.searchByName(name);
    }
}