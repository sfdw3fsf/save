import { Nil } from '@/utils/types/utils.type';

export type ServiceDefaultMaterialsRESP = {
  name: string;
  id: number;
  type: string;
  price: number;
};

export type SettingServicesRESP = {
  id: number;
  name: Nil<string>;
  type: Nil<string>;
  color: Nil<string>;
  price: number;
  serviceDefaultMaterials: ServiceDefaultMaterialsRESP[];
  ordinal: Nil<string>;
};

export type SettingServicesDefaultMaterialRESP = {
  id: number;
  material: Nil<ServiceDefaultMaterialsRESP>;
  quantity: number;
  ordinal: Nil<string>;
};
