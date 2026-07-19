type PetTypeCount = {
  petType: string;
  count: number;
  percentage: number;
};

export type PetDataDTO = {
  data: number[][];
  categories: string[];
  names: string[];
};

export type StatisticBasicAnimalDTO = {
  total: number;
  totalByPetType: PetTypeCount[];
  data: PetDataDTO;
};

export type StatisticBasicFuneralServiceDTO = {
  total: string;
  data: number[];
  names: (string | string[])[];
};

export type StatisticBasicFuneralTimeDTO = {
  total: string;
  data: number[];
  names: string[];
};

export type StatisticBasicWeightDTO = {
  total: string;
  data: number[];
  names: string[];
};

export type StatisticBasicAgeDTO = {
  total: string;
  data: number[];
  names: string[];
};

export type StatisticBasicTransportDTO = {
  total: string;
  totalUseOfTransport: string;
  data: number[];
  names: {
    label: string;
    color: string;
    textColor: string;
  }[];
  legends: {
    label: string;
    value: string;
    color: string;
  }[];
};

export type StatisticBasicLinkedHospitalDTO = {
  total: string;
  totalUseOfTransport: string;
  data: number[];
  names: string[];
};

export type StatisticBasicRouteDTO = {
  total: string;
  data: number[];
  names: {
    label: string;
    color: string;
    textColor: string;
  }[];
  legends: {
    label: string;
    value: string;
    color: string;
  }[];
};

export type StatisticBasicPetBreedDTO = {
  total: string;
  data: number[];
  names: string[];
};

export type StatisticBasicAdditionalCostDTO = {
  total: string;
  data: number[];
  names: string[];
};

export type StatisticBasicObituaryDTO = {
  total: string;
  data: number[];
  names: string[];
};

export type StatisticBasicManagerDTO = {
  key: number;
  managerName: string;
  count: string;
  percentage: string;
};

export type StatisticBasicSaleTypeDTO = {
  total: string;
  data: number[];
  names: {
    label: string;
    color: string;
    textColor: string;
  }[];
  legends: {
    label: string;
    value: string;
    color: string;
  }[];
};

//REGION
export type RegionDTO = {
  value: number;
  label: string;
};

export type StatisticRegionDTO = RegionDTO & {
  children: RegionDTO[];
};

export type StatisticByRegionDTO = {
  total: string;
  names: string[];
  data: number[];
};

export type StatisticByAeraDTO = {
  total: string;
  data: number[][];
  names: string[];
  legends: {
    label: string;
    value: string;
    color: string;
  }[];
};

//ARTICLE
export type StatisticArticleDTO = {
  total: string;
  data: number[];
  names: { label: string; color: string; textColor?: string }[];
  legends: {
    label: string;
    value: string;
    color: string;
  }[];
};
