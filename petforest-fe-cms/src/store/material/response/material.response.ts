import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';

export type MaterialRESP = {
  id: number;
  name: string;
  type: string | null;
  stockQuantity: number;
  materialType: MATERIAL_TYPE;
  ordinal: number;
  price: number;
  status: ACTIVE_STATE;
};

export type MaterialDetailRESP = {
  id: number;
  name: string;
  type: string;
  price: number;
  stockQuantity: number;
  note: string;
  materialType: string;
  vendorId: number;
  vendorName: string | null;
  vendorManagerName: string | null;
  vendorContact: string | null;
  vendorDepositAccount: string | null;
  vendorBusinessRegistration: string | null;
  vendorNote: string | null;
};

//Inventory
export type MaterialReservation = {
  id: number;
  code: string;
} | null;

export type MaterialHistory = {
  id: number;
  name: string;
  type: string;
  stockQuantity: number;
};

export type MaterialInventoryRESP = {
  id: number;
  quantity: number;
  registerDate: string;
  materialId: number;
  material: MaterialHistory | null;
  note: string | null;
  reservation: MaterialReservation;
  carryoverStock: number;
};

export type MaterialInventoryByMonthRESP = {
  carryoverStock: number;
  material: {
    stockQuantity: number;
  };
} | null;

export type MaterialInventoryMonthlyRESP = {
  recordedAt: string;
  carryoverStock: number;
  endingInventory: number;
  totalAmount: number;
};

export type MaterialTypesRESP = {
  type: string;
};

export type MaterialNameRESP = {
  id: number;
  name: string;
};

export type MaterialGroupTypeRESP = {
  [key: string]: {
    id: number;
    name: string;
  }[];
};

export type MaterialInventoryMonthlyHistoryRESP = {
  recordedAt: string;
  carryoverStock: number;
  amount: number;
  name: string;
  type: string;
};
