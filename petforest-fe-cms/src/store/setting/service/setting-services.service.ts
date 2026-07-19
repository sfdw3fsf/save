import { SuppliesDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { MaterialRESP } from '@/store/material/response/material.response';
import { numberOrZero, orString, orUndefined } from '@/utils/parser/io.parser';
import { ServiceDefaultMaterialsDTO, SettingServicesDTO } from './dto/setting-services.dto';
import { SettingServicesListFilter } from './request/setting-services.request';
import { SettingServicesDefaultMaterialRESP, SettingServicesRESP } from './response/setting-services.response';

export const settingServicesRespToDto = (r: SettingServicesRESP): SettingServicesDTO => {
  return {
    key: r.id,
    id: r.id,
    name: orString(r.name),
    type: orString(r.type),
    color: orString(r.color),
    price: numberOrZero(r.price),
    serviceDefaultMaterials: orString(r.serviceDefaultMaterials.map((material) => material.name).join(' / ')),
  };
};

export function settingServicesListParamToFilter(searchParams: URLSearchParams): SettingServicesListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as SettingServicesListFilter;
}

export const materialRespToOptions = (d: MaterialRESP[]) => {
  return d.map((item) => ({
    text: item.name,
    label: item.name,
    value: item.id,
  }));
};

export const settingServicesDefaultMaterialRespToDto = (r: SettingServicesDefaultMaterialRESP): ServiceDefaultMaterialsDTO => {
  return {
    key: r.id,
    id: r.id,
    materialId: orUndefined(r.material?.id),
    quantity: numberOrZero(r.quantity),
  };
};

export const settingServicesDtoToOptions = (d: SettingServicesDTO[]) => {
  return d.map((item) => ({
    text: `${item.type} [${item.name}]`,
    label: `${item.type} [${item.name}]`,
    value: item.id,
  }));
};

export const reservationInventoriesRespToFormDto = (r: SettingServicesDefaultMaterialRESP[]): SuppliesDTO[] => {
  return r.map((item) => ({
    inventoryId: item.id,
    type: orString(item.material?.type),
    kind: item.material?.id,
    quantity: numberOrZero(Math.abs(item.quantity)),
    amount: 0,
    price: 0,
    isDefault: true,
  }));
};
