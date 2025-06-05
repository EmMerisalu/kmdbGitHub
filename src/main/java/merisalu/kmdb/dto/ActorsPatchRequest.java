package merisalu.kmdb.dto;

import java.util.List;

public class ActorsPatchRequest {

    private List<ActorDTO> updateActors;
    private List<Long> removeActorIds;

    public List<ActorDTO> getUpdateActors() {
        return updateActors;
    }

    public void setUpdateActors(List<ActorDTO> updateActors) {
        this.updateActors = updateActors;
    }

    public List<Long> getRemoveActorIds() {
        return removeActorIds;
    }

    public void setRemoveActorIds(List<Long> removeActorIds) {
        this.removeActorIds = removeActorIds;
    }
}
