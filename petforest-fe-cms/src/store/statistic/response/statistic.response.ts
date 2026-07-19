import { STATISTICBASIC_FUNERAL_SERVICE_NAME } from '@/utils/enums/statistic/statistic.enum';

type PetTypeCount = {
  petType: string;
  count: number;
  percentage: number;
};

type PetData = {
  funeralDate: string;
  petType: string;
};

export type StatisticBasicAnimalRESP = {
  total: number;
  totalByPetType: PetTypeCount[];
  data: PetData[];
};

type StatisticFuneralService = {
  funeralService: STATISTICBASIC_FUNERAL_SERVICE_NAME;
  count: number;
};

type StatisticFuneralServiceSales = {
  funeralService: STATISTICBASIC_FUNERAL_SERVICE_NAME;
  totalAmount: number;
};

type StatisticFuneralTime = {
  funeralTime: string;
  count: number;
};

type StatisticWeight = {
  petWeight: string;
  count: number;
};

type StatisticAge = {
  petAge: string;
  count: number;
};

type StatisticTransport = {
  reservationRoute: string;
  count: number;
};

type StatisticRoute = {
  reservationRoute: string;
  count: number;
};

type StatisticVisitRoute = {
  visitRoute: string;
  count: number;
};

type StatisticPetBreed = {
  petBreed: string;
  count: number;
};

type StatisticObituary = {
  label: string;
  count: number;
};

type StatisticManager = {
  managerName: string;
  count: number;
  percentage: number;
};

type StatisticSaleType = {
  label: string;
  count: number;
};

export type StatisticBasicFuneralServiceRESP = {
  total: number;
  data: StatisticFuneralService[];
};

export type StatisticBasicFuneralServiceSalesRESP = {
  total: number;
  data: StatisticFuneralServiceSales[];
};

export type StatisticBasicFuneralTimeRESP = {
  total: number;
  data: StatisticFuneralTime[];
};

export type StatisticBasicWeightRESP = {
  total: number;
  data: StatisticWeight[];
};

export type StatisticBasicAgeRESP = {
  total: number;
  data: StatisticAge[];
};

export type StatisticBasicTransportRESP = {
  total: number;
  totalUseOfTransport: number;
  data: StatisticTransport[];
};

export type StatisticBasicLinkedHospitalRESP = {
  total: number;
  totalUseOfTransport: number;
  data: string[];
};

export type StatisticBasicRouteRESP = {
  total: number;
  data: StatisticRoute[];
};

export type StatisticBasicVisitRouteRESP = {
  total: number;
  data: StatisticVisitRoute[];
};

export type StatisticBasicPetBreedRESP = {
  total: number;
  data: StatisticPetBreed[];
};

export type StatisticBasicAdditionalCostRESP = {
  total: number;
  transportCost: number;
  overweightCost: number;
  columbariumCost: number;
  temporaryPlacementCost: number;
  carbonPlateCost: number;
  otherCost: number;
};

export type StatisticBasicObituaryRESP = {
  total: number;
  data: StatisticObituary[];
};

export type StatisticBasicManagerRESP = {
  total: number;
  data: StatisticManager[];
};

export type StatisticBasicSaleTypeRESP = {
  total: number;
  data: StatisticSaleType[];
};

//REGION
type Region = {
  id: number;
  krName: string;
  total: number;
};

export type StatisticRegionRESP = Region & {
  districts: Region[];
};

export type StatisticByRegionRESP = {
  total: number;
  data: {
    provinceName: string;
    count: number;
  }[];
};

export type StatisticByAreaRESP = {
  total: number;
  data: {
    region: string;
    funeralDate: string;
  }[];
  totalByRegion: [
    {
      region: string;
      total: number;
    },
  ];
};

//ARTICLE
export type StatisticArticleRESP = {
  total: number;
  data: {
    materialName: string;
    count: number;
    percentage: number;
  }[];
};
