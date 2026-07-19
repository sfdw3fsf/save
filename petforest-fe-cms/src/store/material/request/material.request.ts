import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { FilterREQ } from '@/utils/types/filter.type';
import { PagingREQ } from '@/utils/types/paging.type';

export type MaterialCreateREQ = {
  materialType: MATERIAL_TYPE;
};

export type MaterialListFilter = FilterREQ;

export type MaterialListREQ = {
  materialType: MATERIAL_TYPE;
} & MaterialListFilter;

export type MaterialUpdateREQ = {
  name?: string;
  type?: string;
  vendorId?: number;
  note?: string;
  price?: number;
  vendorName?: string;
  vendorManagerName?: string;
  vendorContact?: string;
  vendorDepositAccount?: string;
  vendorBusinessRegistration?: string;
  vendorNote?: string;
};

export type MaterialUpdateStatusREQ = {
  materialType?: MATERIAL_TYPE;
  status?: ACTIVE_STATE;
};

//Inventory
export type MaterialInventoryCreateREQ = {
  quantity: number;
};

export type MaterialInventoryListFilter = FilterREQ;

export type MaterialInventoryListREQ = {
  materialType?: MATERIAL_TYPE;
} & MaterialInventoryListFilter &
  PagingREQ;

export type MaterialInventoryUpdateREQ = {
  registerDate?: string;
  quantity?: number;
  cost?: number;
  note?: string;
};

export type MaterialInventoryMonthlyREQ = PagingREQ;

export type MaterialInventoryHistoryFilter = {
  materialType: MATERIAL_TYPE;
} & FilterREQ;

export type MaterialInventoryHistoryREQ = MaterialInventoryHistoryFilter & PagingREQ;

export type MaterialListNameREQ = {
  materialType: MATERIAL_TYPE;
  type?: string;
};

export type MaterialInventoryMonthlyHistoryREQ = {
  materialType?: MATERIAL_TYPE;
  date?: string;
};
