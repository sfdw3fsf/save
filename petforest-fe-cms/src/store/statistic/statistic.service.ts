import { COLORS } from '@/theme';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import dayjs from 'dayjs';
import { TFunction } from 'i18next';
import {
  PetDataDTO,
  StatisticArticleDTO,
  StatisticBasicAdditionalCostDTO,
  StatisticBasicAgeDTO,
  StatisticBasicAnimalDTO,
  StatisticBasicFuneralServiceDTO,
  StatisticBasicFuneralTimeDTO,
  StatisticBasicLinkedHospitalDTO,
  StatisticBasicManagerDTO,
  StatisticBasicObituaryDTO,
  StatisticBasicPetBreedDTO,
  StatisticBasicRouteDTO,
  StatisticBasicSaleTypeDTO,
  StatisticBasicTransportDTO,
  StatisticBasicWeightDTO,
  StatisticByAeraDTO,
  StatisticByRegionDTO,
  StatisticRegionDTO,
} from './dto/statistic.dto';
import { StatisticBasicREQ, StatisticRegionREQ } from './request/statistic.request';
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

const colors = [
  COLORS.primary[1000],
  COLORS.primary[900],
  COLORS.primary[850],
  COLORS.primary[800],
  COLORS.primary[700],
  COLORS.primary[600],
  COLORS.primary[500],
  COLORS.primary[400],
  COLORS.primary[300],
  COLORS.primary[200],
  COLORS.primary[100],
  COLORS.primary[50],
  COLORS.primary[0],
];

const getDatesBetween = (startDate: number, endDate: number) => {
  const start = dayjs(startDate);
  const end = dayjs(endDate);
  const dates: string[] = [];

  let current = start;

  while (current.isBefore(end) || current.isSame(end, 'day')) {
    dates.push(current.format('YYYY-MM-DD'));
    current = current.add(1, 'day');
  }

  return dates;
};

export const statisticBasicAnimalResptToDto = (
  r: StatisticBasicAnimalRESP,
  filter: StatisticBasicREQ,
): StatisticBasicAnimalDTO => {
  const dates = getDatesBetween(filter.startDate, filter.endDate);

  const petChartData: PetDataDTO = {
    categories: [],
    data: [],
    names: [],
  };

  const petTypeCountsMap: { [key: string]: number[] } = {};

  dates.forEach((date, dateIndex) => {
    r.data.forEach((d) => {
      if (parseTimestampToDate(d.funeralDate) === date) {
        if (!petTypeCountsMap[d.petType]) {
          petTypeCountsMap[d.petType] = new Array(dates?.length).fill(0);
          petChartData.names.push(d.petType);
        }

        petTypeCountsMap[d.petType][dateIndex] += 1;
      }
    });

    petChartData.categories.push(date);
  });

  Object.entries(petTypeCountsMap).forEach(([petType, counts]) => {
    petChartData.data.push(counts);
  });

  return {
    total: r.total,
    totalByPetType: r.totalByPetType,
    data: petChartData,
  };
};

export const statisticBasicFuneralServiceResptToDto = (r: StatisticBasicFuneralServiceRESP): StatisticBasicFuneralServiceDTO => {
  return {
    total: r.total.toLocaleString(),
    data: r.data.map((item) => item.count),
    names: r.data.map(({ funeralService }) =>
      funeralService.split(' ').length === 1
        ? funeralService
        : [`[${funeralService.split(' ')[0]}]`, funeralService.split(' ').slice(1).join(' ')],
    ),
  };
};

export const statisticBasicFuneralServiceSalesResptToDto = (
  r: StatisticBasicFuneralServiceSalesRESP,
): StatisticBasicFuneralServiceDTO => {
  return {
    total: r.total.toLocaleString(),
    data: r.data.map((item) => item.totalAmount / 1000000),
    names: r.data.map(({ funeralService }) =>
      funeralService.split(' ').length === 1
        ? funeralService
        : [`[${funeralService.split(' ')[0]}]`, funeralService.split(' ').slice(1).join(' ')],
    ),
  };
};

export const statisticBasicFuneralTimeResptToDto = (r: StatisticBasicFuneralTimeRESP): StatisticBasicFuneralTimeDTO => {
  return {
    total: r.total.toLocaleString(),
    data: r.data.map((item) => item.count),
    names: r.data.map((item) => item.funeralTime),
  };
};

export const statisticBasicWeightResptToDto = (r: StatisticBasicWeightRESP): StatisticBasicWeightDTO => {
  return {
    total: r.total.toLocaleString(),
    data: r.data.map((item) => item.count),
    names: r.data.map((item) => item.petWeight),
  };
};

export const statisticBasicAgeResptToDto = (r: StatisticBasicAgeRESP): StatisticBasicAgeDTO => {
  return {
    total: r.total.toLocaleString(),
    data: r.data.map((item) => item.count),
    names: r.data.map((item) => item.petAge),
  };
};

export const statisticBasicTransportResptToDto = (
  r: StatisticBasicTransportRESP,
  tCommon: TFunction<'common', undefined>,
  tEnum: TFunction<'enum', undefined>,
): StatisticBasicTransportDTO => {
  return {
    total: r.total.toLocaleString(),
    totalUseOfTransport: r.totalUseOfTransport.toString(),
    data: r.data.sort((a, b) => b.count - a.count).map((item) => item.count),
    names: r.data
      .sort((a, b) => b.count - a.count)
      .map((item, index) => ({
        label: tEnum(`reservation.accompaniment_status.${item.reservationRoute}` as any),
        color: index % 2 === 0 ? COLORS.primary[1000] : COLORS.primary[400],
        textColor: index % 2 === 0 ? COLORS.white : COLORS.black,
      })),
    legends: r.data
      .sort((a, b) => b.count - a.count)
      .map((item, index) => ({
        label: tEnum(`reservation.accompaniment_status.${item.reservationRoute}` as any),
        value: `${item.count}${tCommon('cases')}`,
        color: index % 2 === 0 ? COLORS.primary[1000] : COLORS.primary[400],
      })),
  };
};

export const statisticBasicLinkedHospitalResptToDto = (
  r: StatisticBasicLinkedHospitalRESP,
  filter: StatisticBasicREQ,
): StatisticBasicLinkedHospitalDTO => {
  const dates = getDatesBetween(filter.startDate, filter.endDate);
  const linkedHospitalData = {
    data: [] as number[],
    names: [...dates],
    total: r.total.toLocaleString(),
    totalUseOfTransport: r.totalUseOfTransport.toString(),
  };

  linkedHospitalData.data = dates.map((date) => r.data.filter((item) => parseTimestampToDate(item) === date).length);

  return linkedHospitalData;
};

export const statisticBasicRouteResptToDto = (
  r: StatisticBasicRouteRESP | StatisticBasicVisitRouteRESP,
  tCommon: TFunction<'common', undefined>,
  tEnum: TFunction<'enum', undefined>,
): StatisticBasicRouteDTO => {
  if ((r as StatisticBasicRouteRESP).data[0]?.reservationRoute !== undefined) {
    return {
      total: r.total.toLocaleString(),
      data: r.data.sort((a, b) => b.count - a.count).map((item) => item.count),
      names: (r as StatisticBasicRouteRESP).data
        .sort((a, b) => b.count - a.count)
        .map((item, index) => ({
          label: tEnum(`reservation.booking.${item.reservationRoute}` as any),
          color: colors[index % 13],
          textColor: index < 2 ? COLORS.white : COLORS.black,
        })),
      legends: (r as StatisticBasicRouteRESP).data
        .sort((a, b) => b.count - a.count)
        .map((item, index) => ({
          label: tEnum(`reservation.booking.${item.reservationRoute}` as any),
          value: `${item.count}${tCommon('cases')}`,
          color: colors[index % 13],
        })),
    };
  }

  return {
    total: r.total.toLocaleString(),
    data: r.data.sort((a, b) => b.count - a.count).map((item) => item.count),
    names: (r as StatisticBasicVisitRouteRESP).data
      .sort((a, b) => b.count - a.count)
      .map((item, index) => ({
        label: tEnum(`reservation.visit.${item.visitRoute}` as any),
        color: colors[index % 13],
        textColor: index < 2 ? COLORS.white : COLORS.black,
      })),
    legends: (r as StatisticBasicVisitRouteRESP).data
      .sort((a, b) => b.count - a.count)
      .map((item, index) => ({
        label: tEnum(`reservation.visit.${item.visitRoute}` as any),
        value: `${item.count}${tCommon('cases')}`,
        color: colors[index % 13],
      })),
  };
};

export const statisticBasicPetBreedResptToDto = (r: StatisticBasicPetBreedRESP): StatisticBasicPetBreedDTO => {
  return {
    total: r.total.toLocaleString(),
    data: r.data.map((item) => item.count),
    names: r.data.map((item) => item.petBreed),
  };
};

export const statisticBasicAdditionalCostResptToDto = (
  r: StatisticBasicAdditionalCostRESP,
  tStatistic: TFunction<'statistic', undefined>,
): StatisticBasicAdditionalCostDTO => {
  const { total, ...values } = r;
  return {
    total: total.toLocaleString(),
    data: Object.values(values).map((item) => item),
    names: Object.keys(values).map((item) => tStatistic(`basic.cost.${item}` as any)),
  };
};

export const statisticBasicObituaryResptToDto = (r: StatisticBasicObituaryRESP): StatisticBasicObituaryDTO => {
  return {
    total: r.total.toLocaleString(),
    data: r.data.map((item) => item.count),
    names: r.data.map((item) => item.label),
  };
};

export const statisticBasicManagerResptToDto = (r: StatisticBasicManagerRESP): StatisticBasicManagerDTO[] => {
  return r.data.map((item, index) => ({
    key: index,
    managerName: item.managerName,
    count: item.count.toLocaleString(),
    percentage: item.percentage + '%',
  }));
};

export const statisticBasicSaleTypeResptToDto = (
  r: StatisticBasicSaleTypeRESP,
  tCommon: TFunction<'common', undefined>,
): StatisticBasicSaleTypeDTO => {
  return {
    total: r.total.toLocaleString(),
    data: r.data.sort((a, b) => b.count - a.count).map((item) => item.count),
    names: r.data
      .sort((a, b) => b.count - a.count)
      .map((item, index) => ({
        label: item.label,
        color: colors[index % 13],
        textColor: index < 2 ? COLORS.white : COLORS.black,
      })),
    legends: r.data
      .sort((a, b) => b.count - a.count)
      .map((item, index) => ({
        label: item.label,
        value: `${item.count}${tCommon('won')}`,
        color: colors[index % 13],
      })),
  };
};

//REGION

export const statisticRegionRespToDto = (r: StatisticRegionRESP): StatisticRegionDTO => {
  return {
    value: r.id,
    label: `${r.krName} (${r.total})`,
    children: r.districts?.map((item) => ({
      label: `${item.krName} (${item.total})`,
      value: item.id,
    })),
  };
};

export const statisticTotalByRegionRespToDto = (r: StatisticByRegionRESP): StatisticByRegionDTO => {
  return {
    total: r.total.toLocaleString(),
    names: r.data.map((item) => item.provinceName),
    data: r.data.map((item) => item.count),
  };
};

export const statisticTotalByAreaRespToDto = (r: StatisticByAreaRESP, filter: StatisticRegionREQ): StatisticByAeraDTO => {
  const dates = getDatesBetween(filter.startDate, filter.endDate);

  const result: StatisticByAeraDTO = {
    total: r.total.toLocaleString(),
    data: r.totalByRegion.map(() => Array(dates.length).fill(0)),
    names: dates.map((date) => date),
    legends: r.totalByRegion.map((regionData, index) => ({
      label: regionData.region,
      value: regionData.total.toLocaleString(),
      color: colors[index % 13],
    })),
  };

  r.data.forEach((entry) => {
    const entryDate = parseTimestampToDate(entry.funeralDate);
    const dateIndex = dates.indexOf(entryDate);
    const regionIndex = r.totalByRegion.findIndex((region) => region.region === entry.region);

    if (dateIndex !== -1 && regionIndex !== -1) {
      result.data[regionIndex][dateIndex] += 1;
    }
  });

  return result;
};

//ARTICLE
export const statisticArticleRespToDto = (r: StatisticArticleRESP): StatisticArticleDTO => {
  return {
    total: r.total.toLocaleString(),
    names: r.data
      .sort((a, b) => b.count - a.count)
      .map((item, index) => ({
        label: item.materialName,
        color: colors[index % 13],
        textColor: index < 2 ? COLORS.white : COLORS.black,
      })),
    data: r.data.sort((a, b) => b.count - a.count).map((item) => (item.count > 0 ? item.count : 0)),
    legends: r.data
      .sort((a, b) => b.count - a.count)
      .map((item, index) => ({
        label: item.materialName,
        value: item.count.toLocaleString(),
        color: colors[index % 13],
      })),
  };
};
