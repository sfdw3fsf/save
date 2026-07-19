import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { parseTimeStampToDayjs } from '@/utils/parser/date-time.parser';
import { numberOrZero, orString, orUndefined } from '@/utils/parser/io.parser';
import dayjs from 'dayjs';
import { SettingCorrespondentDTO } from '../setting/correspondent/dto/setting-correspondent.dto';
import {
  MaterialDTO,
  MaterialInventoryByMonthDTO,
  MaterialInventoryDTO,
  MaterialInventoryMonthlyDTO,
  MaterialInventoryMonthlyHistoryDTO,
} from './dto/material.dto';
import { MaterialDetailInput } from './input/material.input';
import {
  MaterialInventoryHistoryREQ,
  MaterialInventoryListFilter,
  MaterialInventoryListREQ,
  MaterialListFilter,
  MaterialUpdateREQ,
} from './request/material.request';
import {
  MaterialDetailRESP,
  MaterialInventoryByMonthRESP,
  MaterialInventoryMonthlyHistoryRESP,
  MaterialInventoryMonthlyRESP,
  MaterialInventoryRESP,
  MaterialNameRESP,
  MaterialRESP,
  MaterialTypesRESP,
} from './response/material.response';

export const materialIntialValueForm: MaterialDetailInput = {
  type: '',
  price: 0,
  note: '',
  vendorManagerName: '',
  vendorContact: '',
  vendorDepositAccount: '',
  vendorBusinessRegistration: '',
};

export function materialListParamsToFilter(searchParams: URLSearchParams): MaterialListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    filter: orUndefined(searchParams.get('filter')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as MaterialListFilter;
}

export const materialRespToDto = (r: MaterialRESP): MaterialDTO => {
  return {
    key: r.id,
    id: r.id,
    name: r.name,
    type: orString(r.type),
    price: numberOrZero(r.price),
    stockQuantity: r.stockQuantity,
    materialType: r.materialType,
    status: r.status,
  };
};

export const materialDetailRespToInput = (
  r: MaterialDetailRESP,
  correspondents?: SettingCorrespondentDTO[],
): MaterialDetailInput => {
  const correspondentInfo = correspondents?.find((item) => item.id === r.vendorId);
  const isDifferent = !!(
    r.vendorId &&
    correspondentInfo &&
    (r.vendorManagerName !== correspondentInfo.managerName ||
      r.vendorContact !== correspondentInfo.contact ||
      r.vendorDepositAccount !== correspondentInfo.depositAccount ||
      r.vendorBusinessRegistration !== correspondentInfo.businessRegistration)
  );

  return {
    type: r.type,
    price: r.price,
    note: r.note,
    vendorId: !isDifferent ? r.vendorId : undefined,
    vendorManagerName: orString(r.vendorManagerName),
    vendorContact: orString(r.vendorContact),
    vendorDepositAccount: orString(r.vendorDepositAccount),
    vendorBusinessRegistration: orString(r.vendorBusinessRegistration),
  };
};

export const materialDetailInputToRequest = (i: MaterialDetailInput): MaterialUpdateREQ => {
  const isDifferent =
    i.initialVendor &&
    (i.vendorManagerName !== i.initialVendor.managerName ||
      i.vendorContact !== i.initialVendor.contact ||
      i.vendorDepositAccount !== i.initialVendor.depositAccount ||
      i.vendorBusinessRegistration !== i.initialVendor.businessRegistration);

  return {
    type: i.type,
    price: i.price,
    vendorId: !isDifferent ? i.vendorId : undefined,
    vendorManagerName: i.vendorManagerName,
    vendorContact: i.vendorContact,
    vendorDepositAccount: i.vendorDepositAccount,
    vendorBusinessRegistration: i.vendorBusinessRegistration,
    note: i.note,
  };
};

//Inventory
export function materialInventoryListParamsToFilter(searchParams: URLSearchParams): MaterialInventoryListFilter {
  return {
    search: orUndefined(searchParams.get('searchDetail')),
    filter: orUndefined(searchParams.get('filter')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as MaterialInventoryListFilter;
}

export const materialInventoryHistoryListReqMapping = (req?: MaterialInventoryHistoryREQ) => {
  if (!req) return;
  const sortBy = (() => {
    switch (req.sortBy) {
      case 'type':
        return 'materialType';
      case 'name':
        return 'materialName';
      case 'reservationNumber':
        return 'code';
      case 'currentStock':
        return 'materialStockQuantity';
      default:
        return req.sortBy;
    }
  })();
  return { ...req, sortBy };
};

export const materialInventoryListReqMapping = (req?: MaterialInventoryListREQ) => {
  if (!req) return;
  const sortBy = (() => {
    switch (req.sortBy) {
      case 'reservation':
        return 'code';
      default:
        return req.sortBy;
    }
  })();
  return { ...req, sortBy };
};

export const materialInventoryRespToDto = (r: MaterialInventoryRESP): MaterialInventoryDTO => {
  return {
    key: r.id,
    id: r.id,
    quantity: r.quantity,
    registerDate: parseTimeStampToDayjs(r.registerDate),
    materialId: r.materialId,
    material: r.material,
    type: orString(r.material?.type),
    name: orString(r.material?.name),
    currentStock: Number(r.material?.stockQuantity || 0),
    note: orString(r.note),
    reservation: orString(r.reservation?.code),
    reservationNumber: orString(r.reservation?.code),
    reservationId: orString(r.reservation?.id),
    carryoverStock: r.carryoverStock,
  };
};

export const materialInventoryByMonthRespToDto = (r: MaterialInventoryByMonthRESP): MaterialInventoryByMonthDTO => {
  return {
    carryoverStock: r?.carryoverStock || 0,
    stockQuantity: r?.material.stockQuantity || 0,
  };
};

export const materialInventoryMonthlyRespToDto = (
  r: MaterialInventoryMonthlyRESP,
  index: number,
): MaterialInventoryMonthlyDTO => {
  return {
    ...r,
    key: index,
    recordedAt: r.recordedAt ? dayjs(Number(r.recordedAt)).format(DATE_FORMAT.DATE.MONTH_HYPHEN) : '',
  };
};

export function materialHistoryListParamsToFilter(searchParams: URLSearchParams): MaterialInventoryHistoryREQ {
  return {
    search: orUndefined(searchParams.get('search')),
    filter: JSON.stringify({
      'createdAt.gte': dayjs().startOf('month').valueOf(),
      'createdAt.lte': dayjs().endOf('month').valueOf(),
    }),

    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as MaterialInventoryHistoryREQ;
}

export const materialTypeDtoToOptions = (d: MaterialTypesRESP[]) => {
  return d.map((item) => ({
    text: item.type,
    label: item.type,
    value: item.type,
  }));
};

export const materialNameDtoToOptions = (d: MaterialNameRESP[]) => {
  return d.map((item) => ({
    text: item.name,
    label: item.name,
    value: item.id,
  }));
};

export const materialInventoryMonthlyHistoryRespToDto = (
  r: MaterialInventoryMonthlyHistoryRESP,
): MaterialInventoryMonthlyHistoryDTO => {
  return {
    ...r,
    key: r.name,
    carryoverStock: r.carryoverStock.toLocaleString(),
    stockQuantity: r.amount.toLocaleString(),
  };
};
