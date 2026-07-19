import { orString, orUndefined } from '@/utils/parser/io.parser';
import { ManagerBranchDTO } from './dto/manager-branch.dto';
import { ManagerBranchListFilter } from './request/manager-branch.request';
import { ManagerBranchRESP } from './response/manager-branch.response';

export const managerBranchRespToDto = (r: ManagerBranchRESP): ManagerBranchDTO => {
  return {
    key: r.id,
    id: r.id,
    name: orString(r.name),
    color: orString(r.color),
    roadAddress: orString(r.roadAddress),
    streetAddress: orString(r.streetAddress),
    naverMap: orString(r.naverMap),
    kakaoMap: orString(r.kakaoMap),
  };
};

export function managerBranchListParamToFilter(searchParams: URLSearchParams): ManagerBranchListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    filter: orUndefined(searchParams.get('filter')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as ManagerBranchListFilter;
}

export const managerBranchDtoToOptions = (d: ManagerBranchDTO[]) => {
  return d
    .map((item) => ({
      text: item.name,
      value: item.id,
      label: item.name,
    }))
    .filter((item) => item.label);
};
