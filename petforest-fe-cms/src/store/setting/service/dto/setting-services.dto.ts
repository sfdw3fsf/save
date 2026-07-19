export type ServiceDefaultMaterialsDTO = {
  key: number;
  id: number;
  materialId: number;
  quantity: number;
};

export type SettingServicesDTO = {
  key: number;
  id: number;
  name: string;
  type: string;
  color: string;
  price: number;
  serviceDefaultMaterials: string;
};
