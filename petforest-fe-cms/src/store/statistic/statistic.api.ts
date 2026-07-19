import { api } from '@/config/http';
import { BaseResponse } from '@/utils/types/response.type';
import { initKeys } from '@/utils/utils/query-key.utils';
import {
  StatisticArticleREQ,
  StatisticBasicREQ,
  StatisticRegionREQ,
  StatisticServiceFilterREQ,
} from './request/statistic.request';
import {
  StatisticArticleRESP,
  StatisticBasicAdditionalCostRESP,
  StatisticBasicAgeRESP,
  StatisticBasicAnimalRESP,
  StatisticBasicFuneralServiceRESP,
  StatisticBasicFuneralServiceSalesRESP,
  StatisticBasicFuneralTimeRESP,
  StatisticBasicLinkedHospitalRESP,
  StatisticBasicManagerRESP,
  StatisticBasicObituaryRESP,
  StatisticBasicPetBreedRESP,
  StatisticBasicRouteRESP,
  StatisticBasicSaleTypeRESP,
  StatisticBasicTransportRESP,
  StatisticBasicVisitRouteRESP,
  StatisticBasicWeightRESP,
  StatisticByAreaRESP,
  StatisticByRegionRESP,
  StatisticRegionRESP,
} from './response/statistic.response';

export const statisticBasicAnimalKeys = initKeys('statistic-basic-animal');
export const getStatisticBasicAnimalApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicAnimalRESP>>('statistics/basic/animals', { params });
};

export const statisticBasicFuneralServiceKeys = initKeys('statistic-basic-funeral-service');
export const getStatisticBasicFuneralServiceApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicFuneralServiceRESP>>('statistics/basic/funeral-service', { params });
};

export const statisticBasicFuneralServiceSalesKeys = initKeys('statistic-basic-funeral-service-sales');
export const getStatisticBasicFuneralServiceSalesApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicFuneralServiceSalesRESP>>('statistics/basic/funeral-service/sales', { params });
};

export const statisticBasicFuneralTimeKeys = initKeys('statistic-basic-funeral-time');
export const getStatisticBasicFuneralTimeApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicFuneralTimeRESP>>('statistics/basic/funeral-time', { params });
};

export const statisticBasicWeightKeys = initKeys('statistic-basic-weight');
export const getStatisticBasicWeightApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicWeightRESP>>('statistics/basic/pet-weight', { params });
};

export const statisticBasicAgeKeys = initKeys('statistic-basic-age');
export const getStatisticBasicAgeApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicAgeRESP>>('statistics/basic/pet-age', { params });
};

export const statisticBasicTransportKeys = initKeys('statistic-basic-transport');
export const getStatisticBasicTransportApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicTransportRESP>>('statistics/basic/use-of-transport', { params });
};

export const statisticBasicLinkedHospitalKeys = initKeys('statistic-basic-linked-hospital');
export const getStatisticBasicLinkedHospitalApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicLinkedHospitalRESP>>('statistics/basic/linked-to-hospital', { params });
};

export const statisticBasicRouteKeys = initKeys('statistic-basic-route');
export const getStatisticBasicRouteApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicRouteRESP>>('statistics/basic/reservation-route', { params });
};

export const statisticBasicVisitRouteKeys = initKeys('statistic-basic-visit');
export const getStatisticBasicVisitRouteApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicVisitRouteRESP>>('statistics/basic/visit-route', { params });
};

export const statisticBasicPetBreedKeys = initKeys('statistic-basic-pet-breed');
export const getStatisticBasicPetBreedApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicPetBreedRESP>>('statistics/basic/pet-breed', { params });
};

export const statisticBasicAdditionalCostKeys = initKeys('statistic-basic-additional-cost');
export const getStatisticBasicAdditionalCostApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicAdditionalCostRESP>>('statistics/basic/additional-cost', { params });
};

export const statisticBasicObituaryKeys = initKeys('statistic-basic-obituary');
export const getStatisticBasicObituaryApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicObituaryRESP>>('statistics/basic/obituary', { params });
};

export const statisticBasicManagerKeys = initKeys('statistic-basic-manager');
export const getStatisticBasicManagerApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicManagerRESP>>('statistics/basic/managers', { params });
};

export const statisticBasicSaleTypeKeys = initKeys('statistic-basic-sale-type');
export const getStatisticBasicSaleTypeApi = async (params: StatisticBasicREQ) => {
  return api.get<BaseResponse<StatisticBasicSaleTypeRESP>>('statistics/basic/sale-type', { params });
};

//REGION
export const statisticRegionKeys = initKeys('statistic-region');
export const getStatisticRegionApi = async (params: StatisticRegionREQ) => {
  return api.get<BaseResponse<StatisticRegionRESP[]>>('regions/statistic', { params });
};

export const statisticByRegionKeys = initKeys('statistic-by-region');
export const getStatisticByRegionApi = async (params: StatisticRegionREQ) => {
  return api.get<BaseResponse<StatisticByRegionRESP>>('statistics/region', { params });
};

export const statisticByAreaKeys = initKeys('statistic-by-area');
export const getStatisticByAreaApi = async (params: StatisticRegionREQ) => {
  return api.get<BaseResponse<StatisticByAreaRESP>>('statistics/region/detail/line-chart', { params });
};

//ARTICLE
export const statisticArticleKeys = initKeys('statistic-article');
export const getStatisticArticleApi = async (params: StatisticArticleREQ) => {
  return api.get<BaseResponse<StatisticArticleRESP>>('statistics/item', { params });
};

export const getStatisticArticleDetailApi = async (params: StatisticArticleREQ) => {
  return api.get<BaseResponse<StatisticArticleRESP>>('statistics/item/detail', { params });
};

//Filters - Checkboxs
export const statisticServiceFilterKeys = initKeys('statistic-service-filter');
export const getStatisticServiceFiltersApi = async (params: StatisticServiceFilterREQ) => {
  return api.get<BaseResponse<string[]>>('statistics/funeral-service-filter', { params });
};
