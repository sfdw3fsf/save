import { BranchDTO } from './dto/branch.dto';
import { BranchRESP } from './response/branch.response';

export const branchRespToDto = (r: BranchRESP): BranchDTO => {
  return {
    id: r.id,
    name: r.name,
  };
};
