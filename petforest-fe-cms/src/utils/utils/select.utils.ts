import { FilterOptionItem } from '../types/option.type';
import { SelectType } from '../types/post/select.type';

export const selectRespToDto = (r: SelectType): FilterOptionItem => {
  return {
    text: r.name,
    label: r.name,
    value: r.id,
  };
};
