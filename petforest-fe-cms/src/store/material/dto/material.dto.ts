import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { Dayjs } from 'dayjs';
import { MaterialHistory } from '../response/material.response';

export type MaterialDTO = {
  key: number;
  id: number;
  name: string;
  type: string;
  stockQuantity: number;
  price: number;
  materialType: MATERIAL_TYPE;
  status: ACTIVE_STATE;
};

export type MaterialDetailDTO = {
  id: number;
  name: string;
  type: string;
  price: number;
  stockQuantity: number;
  note: string;
  materialType: string;
  vendorId: number;
  vendorName: string;
  vendorManagerName: string;
  vendorContact: string;
  vendorDepositAccount: string;
  vendorBusinessRegistration: string;
  vendorNote: string;
};

//Inventory
export type MaterialInventoryDTO = {
  key: number;
  id: number;
  quantity: number;
  registerDate: Dayjs | null;
  materialId: number;
  material: MaterialHistory | null;
  type: string;
  name: string;
  currentStock: number;
  note: string | null;
  reservation: string;
  reservationNumber: string;
  reservationId: number;
  carryoverStock: number;
};

export type MaterialInventoryByMonthDTO = {
  carryoverStock: number;
  stockQuantity: number;
};

export type MaterialInventoryMonthlyDTO = {
  key: number;
  recordedAt: string;
  carryoverStock: number;
  endingInventory: number;
  totalAmount: number;
};

export type MaterialInventoryMonthlyHistoryDTO = {
  key: string;
  carryoverStock: string;
  stockQuantity: string;
  name: string;
  type: string;
};
