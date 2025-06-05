package merisalu.kmdb.service.impl;

import org.springframework.stereotype.Service;
import merisalu.kmdb.dto.ActorDTO;
import merisalu.kmdb.dto.ActorsPatchRequest;
import merisalu.kmdb.exception.GlobalExceptionHandler;
import merisalu.kmdb.model.Actor;
import merisalu.kmdb.repository.ActorRepository;
import merisalu.kmdb.service.ActorService;
import merisalu.kmdb.mapper.ActorMapper;  // <-- import custom mapper

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {
    
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;  // <-- inject custom mapper
    
    public ActorServiceImpl(ActorRepository actorRepository, ActorMapper actorMapper) {
        this.actorRepository = actorRepository;
        this.actorMapper = actorMapper;
    }

    @Override
    public List<ActorDTO> getAllActors() {
        return actorRepository.findAll().stream()
                .map(actorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ActorDTO getActorById(Long id) {
        Actor actor = actorRepository.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                "Actor not found with id: " + id
            ));
        return actorMapper.toDTO(actor);
    }

    @Override
    public ActorDTO createActor(ActorDTO actorDTO) {
        if (actorRepository.findByNameContainingIgnoreCase(actorDTO.getName()).stream()
            .anyMatch(existingActor -> existingActor.getName().equalsIgnoreCase(actorDTO.getName()))) {
            throw new GlobalExceptionHandler.DuplicateResourceException(
                "Actor with name '" + actorDTO.getName() + "' already exists.");
        }

        Actor actor = actorMapper.toEntity(actorDTO);
        Actor savedActor = actorRepository.save(actor);
        return actorMapper.toDTO(savedActor);
    }

    @Override
    public ActorDTO updateActor(Long id, ActorDTO actorDTO) {
        Actor existingActor = actorRepository.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                "Actor not found with id: " + id
            ));
        
        actorMapper.updateEntity(actorDTO, existingActor);
        Actor updatedActor = actorRepository.save(existingActor);
        return actorMapper.toDTO(updatedActor);
    }

    @Override
    public void deleteActor(Long id, boolean force) {
        Actor actor = actorRepository.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(
                "Actor not found with id: " + id
            ));
        
        if (!force && !actor.getMovies().isEmpty()) {
            throw new GlobalExceptionHandler.OperationNotAllowedException(
                "Cannot delete actor with associated movies. Use force=true."
            );
        }
        actorRepository.delete(actor);
    }

    @Override
    public List<ActorDTO> searchByName(String name) {
        return actorRepository.findByNameContainingIgnoreCase(name).stream()
                .map(actorMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
public List<ActorDTO> patchActors(ActorsPatchRequest patchRequest) {
    // Remove actors if any
    if (patchRequest.getRemoveActorIds() != null) {
        for (Long id : patchRequest.getRemoveActorIds()) {
            // Force delete to avoid issues with associated movies
            deleteActor(id, true);
        }
    }

    List<ActorDTO> updatedActors = new java.util.ArrayList<>();
    if (patchRequest.getUpdateActors() != null) {
        for (ActorDTO actorDTO : patchRequest.getUpdateActors()) {
            // Use existing update logic
            ActorDTO updated = updateActor(actorDTO.getId(), actorDTO);
            updatedActors.add(updated);
        }
    }

    return updatedActors;
}
}
