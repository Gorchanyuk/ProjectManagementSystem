package digital.design.management.system.service;

import digital.design.management.system.dto.team.ProjectTeamDTO;
import digital.design.management.system.dto.team.ProjectTeamDeleteDTO;
import digital.design.management.system.dto.team.ProjectTeamOutDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectTeamService {

    List<ProjectTeamOutDTO> getAllParty(UUID projectUid);

    ProjectTeamOutDTO addParticipant(ProjectTeamDTO projectTeamDTO);

    void deleteParticipant(ProjectTeamDeleteDTO deleteDTO);


}
