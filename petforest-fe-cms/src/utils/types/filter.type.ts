import { SORT_ORDER } from '../enums/sort-order.enum';

export type FilterREQ = {
  search?: string;
  sortOrder?: SORT_ORDER;
  sortBy?: string;
  filter?: string;
};
