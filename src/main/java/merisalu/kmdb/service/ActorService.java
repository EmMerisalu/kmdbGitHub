package merisalu.kmdb.service;

import merisalu.kmdb.dto.ActorDTO;
import java.util.List;
import merisalu.kmdb.dto.ActorsPatchRequest;

public interface ActorService {
    List<ActorDTO> getAllActors();
    ActorDTO getActorById(Long id);
    ActorDTO createActor(ActorDTO actorDTO);
    ActorDTO updateActor(Long id, ActorDTO actorDTO);
    void deleteActor(Long id, boolean force);
    List<ActorDTO> searchByName(String name);
    List<ActorDTO> patchActors(ActorsPatchRequest patchRequest);
}