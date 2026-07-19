'use client';
import PageTitle from '@/components/common/page/PageTitle';
import { useTranslation } from 'react-i18next';
import StatisticFilter from '../components/StatisticFilter';
import StatisticServices from '../components/StatisticServices';
import StatisticBasicFuneralByLeader from './components/basic/StatisticBasicFuneralByLeader';

import { useBranch } from '@/hook/branch/useBranch';
import { useSettingVarieties } from '@/hook/setting/useSettingVarieties';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { StatisticBasicREQ } from '@/store/statistic/request/statistic.request';
import {
  getStatisticBasicAdditionalCostApi,
  getStatisticBasicAgeApi,
  getStatisticBasicAnimalApi,
  getStatisticBasicFuneralServiceApi,
  getStatisticBasicFuneralServiceSalesApi,
  getStatisticBasicFuneralTimeApi,
  getStatisticBasicLinkedHospitalApi,
  getStatisticBasicManagerApi,
  getStatisticBasicObituaryApi,
  getStatisticBasicPetBreedApi,
  getStatisticBasicRouteApi,
  getStatisticBasicSaleTypeApi,
  getStatisticBasicTransportApi,
  getStatisticBasicVisitRouteApi,
  getStatisticBasicWeightApi,
  getStatisticServiceFiltersApi,
  statisticBasicAdditionalCostKeys,
  statisticBasicAgeKeys,
  statisticBasicAnimalKeys,
  statisticBasicFuneralServiceKeys,
  statisticBasicFuneralServiceSalesKeys,
  statisticBasicFuneralTimeKeys,
  statisticBasicLinkedHospitalKeys,
  statisticBasicManagerKeys,
  statisticBasicObituaryKeys,
  statisticBasicPetBreedKeys,
  statisticBasicRouteKeys,
  statisticBasicSaleTypeKeys,
  statisticBasicTransportKeys,
  statisticBasicVisitRouteKeys,
  statisticBasicWeightKeys,
  statisticServiceFilterKeys,
} from '@/store/statistic/statistic.api';
import {
  statisticBasicAdditionalCostResptToDto,
  statisticBasicAgeResptToDto,
  statisticBasicAnimalResptToDto,
  statisticBasicFuneralServiceResptToDto,
  statisticBasicFuneralServiceSalesResptToDto,
  statisticBasicFuneralTimeResptToDto,
  statisticBasicLinkedHospitalResptToDto,
  statisticBasicManagerResptToDto,
  statisticBasicObituaryResptToDto,
  statisticBasicPetBreedResptToDto,
  statisticBasicRouteResptToDto,
  statisticBasicSaleTypeResptToDto,
  statisticBasicTransportResptToDto,
  statisticBasicWeightResptToDto,
} from '@/store/statistic/statistic.service';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { STATISTICBASIC_FUNERAL_SERVICE_NAME } from '@/utils/enums/statistic/statistic.enum';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useEffect, useMemo, useState } from 'react';
import StatisticPieData from '../components/StatisticPieData';
import StatisticBasicAnimalByDay from './components/basic/StatisticBasicAnimalByDay';
import StatisticBasicConnectedHospital from './components/basic/StatisticBasicConnectedHospital';
import StatisticBasicObituary from './components/basic/StatisticBasicObituary';
import StatisticBasicTransport from './components/basic/StatisticBasicTransport';
import StatisticBasicVarieties from './components/basic/StatisticBasicVarieties';

const page = () => {
  const { t: tMenu } = useTranslation('menu');
  const { t: tCommon } = useTranslation('common');
  const { t: tEnum } = useTranslation('enum');
  const { t: tStatistic } = useTranslation('statistic');
  const { branchOptions } = useBranch();
  const { petTypeOptions } = useSettingVarieties({});

  const [filter, setFilter] = useState<StatisticBasicREQ>({
    startDate: dayjs().startOf('month').valueOf(),
    endDate: dayjs().endOf('day').valueOf(),
    branchIds: [],
    funeralServiceNames: Object.keys(STATISTICBASIC_FUNERAL_SERVICE_NAME) as STATISTICBASIC_FUNERAL_SERVICE_NAME[],
  });
  const [filterFuneralService, setFilterFuneralService] = useState<StatisticBasicREQ>({
    startDate: dayjs().startOf('month').valueOf(),
    endDate: dayjs().endOf('day').valueOf(),
    branchIds: [],
    funeralServiceNames: Object.keys(STATISTICBASIC_FUNERAL_SERVICE_NAME) as STATISTICBASIC_FUNERAL_SERVICE_NAME[],
  });
  const [filterFuneralServiceSales, setFilterFuneralServiceSales] = useState<StatisticBasicREQ>({
    startDate: dayjs().startOf('month').valueOf(),
    endDate: dayjs().endOf('day').valueOf(),
    branchIds: [],
    funeralServiceNames: Object.keys(STATISTICBASIC_FUNERAL_SERVICE_NAME) as STATISTICBASIC_FUNERAL_SERVICE_NAME[],
  });
  const [filterPetBreed, setFilterPetBreed] = useState<StatisticBasicREQ>({
    startDate: dayjs().startOf('month').valueOf(),
    endDate: dayjs().endOf('day').valueOf(),
    branchIds: [],
    petTypeIds: [],
  });

  const date = useMemo(() => {
    return filter.startDate && filter.endDate
      ? parseTimestampToDate(filter.startDate.toString(), DATE_FORMAT.DATE.DOT_SHORTEN) +
          '_' +
          parseTimestampToDate(filter.endDate.toString(), DATE_FORMAT.DATE.DOT_SHORTEN)
      : '';
  }, [filter.startDate, filter.endDate]);

  const branchLabels = useMemo(
    () => branchOptions.filter((i) => filter.branchIds.includes(i.value)).map((i) => i.label),
    [filter.branchIds],
  );

  const getFileName = (title: string) => {
    return [...branchLabels, title, date].filter((i) => !!i).join('_');
  };

  const { data: statisticBasicAnimalData } = useQuery({
    queryKey: statisticBasicAnimalKeys.list(filter),
    queryFn: () => getStatisticBasicAnimalApi(filter),
    select: (response) => {
      return statisticBasicAnimalResptToDto(response.data.data, filter);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicFuneralServiceData } = useQuery({
    queryKey: statisticBasicFuneralServiceKeys.list(filterFuneralService),
    queryFn: () => getStatisticBasicFuneralServiceApi(filterFuneralService),
    select: (response) => {
      return statisticBasicFuneralServiceResptToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicFuneralServiceSalesData } = useQuery({
    queryKey: statisticBasicFuneralServiceSalesKeys.list(filterFuneralServiceSales),
    queryFn: () => getStatisticBasicFuneralServiceSalesApi(filterFuneralServiceSales),
    select: (response) => {
      return statisticBasicFuneralServiceSalesResptToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicFuneralTimeData } = useQuery({
    queryKey: statisticBasicFuneralTimeKeys.list(filter),
    queryFn: () => getStatisticBasicFuneralTimeApi(filter),
    select: (response) => {
      return statisticBasicFuneralTimeResptToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicWeightData } = useQuery({
    queryKey: statisticBasicWeightKeys.list(filter),
    queryFn: () => getStatisticBasicWeightApi(filter),
    select: (response) => {
      return statisticBasicWeightResptToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicAgeData } = useQuery({
    queryKey: statisticBasicAgeKeys.list(filter),
    queryFn: () => getStatisticBasicAgeApi(filter),
    select: (response) => {
      return statisticBasicAgeResptToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicTransportData } = useQuery({
    queryKey: statisticBasicTransportKeys.list(filter),
    queryFn: () => getStatisticBasicTransportApi(filter),
    select: (response) => {
      return statisticBasicTransportResptToDto(response.data.data, tCommon, tEnum);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicLinkedHospitalData } = useQuery({
    queryKey: statisticBasicLinkedHospitalKeys.list(filter),
    queryFn: () => getStatisticBasicLinkedHospitalApi(filter),
    select: (response) => {
      return statisticBasicLinkedHospitalResptToDto(response.data.data, filter);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicRouteData } = useQuery({
    queryKey: statisticBasicRouteKeys.list(filter),
    queryFn: () => getStatisticBasicRouteApi(filter),
    select: (response) => {
      return statisticBasicRouteResptToDto(response.data.data, tCommon, tEnum);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicVisitRouteData } = useQuery({
    queryKey: statisticBasicVisitRouteKeys.list(filter),
    queryFn: () => getStatisticBasicVisitRouteApi(filter),
    select: (response) => {
      return statisticBasicRouteResptToDto(response.data.data, tCommon, tEnum);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicPetBreedData } = useQuery({
    queryKey: statisticBasicPetBreedKeys.list(filterPetBreed),
    queryFn: () => getStatisticBasicPetBreedApi(filterPetBreed),
    select: (response) => {
      return statisticBasicPetBreedResptToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicAdditionalCostData } = useQuery({
    queryKey: statisticBasicAdditionalCostKeys.list(filter),
    queryFn: () => getStatisticBasicAdditionalCostApi(filter),
    select: (response) => {
      return statisticBasicAdditionalCostResptToDto(response.data.data, tStatistic);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicObituaryData } = useQuery({
    queryKey: statisticBasicObituaryKeys.list(filter),
    queryFn: () => getStatisticBasicObituaryApi(filter),
    select: (response) => {
      return statisticBasicObituaryResptToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicManagerData } = useQuery({
    queryKey: statisticBasicManagerKeys.list(filter),
    queryFn: () => getStatisticBasicManagerApi(filter),
    select: (response) => {
      return statisticBasicManagerResptToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticBasicSaleTypeData } = useQuery({
    queryKey: statisticBasicSaleTypeKeys.list(filter),
    queryFn: () => getStatisticBasicSaleTypeApi(filter),
    select: (response) => {
      return statisticBasicSaleTypeResptToDto(response.data.data, tCommon);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticServiceFilters } = useQuery({
    queryKey: statisticServiceFilterKeys.list(filter.branchIds),
    queryFn: () => getStatisticServiceFiltersApi({ branchIds: filter.branchIds }),
    select: (response) => {
      return response.data.data as string[];
    },
    placeholderData: keepPreviousData,
  });

  //Download Excel
  const { mutateDownloadExcel } = useDownloadExcel<StatisticBasicREQ>({
    fileName: EXCEL_FILE_NAME.STATISTICS,
  });

  const onFilterChange = (filter: StatisticBasicREQ) => {
    setFilter(filter);
    setFilterFuneralService((pre) => ({ ...pre, ...filter }));
    setFilterFuneralServiceSales((pre) => ({ ...pre, ...filter }));
    setFilterPetBreed((pre) => ({ ...pre, ...filter }));
  };

  const onChangeFilterFuneralService = (filter: StatisticBasicREQ) => {
    setFilterFuneralService(filter);
  };

  const onChangeFilterFuneralServiceSales = (filter: StatisticBasicREQ) => {
    setFilterFuneralServiceSales(filter);
  };

  const onChangeFilterPetBreed = (filter: StatisticBasicREQ) => {
    setFilterPetBreed(filter);
  };

  const onDownloadExcel = (url: string, title: string) => {
    mutateDownloadExcel({ filter, dynamicUrl: url, dynamicName: getFileName(title) });
  };

  const onDownloadExcelFuneralService = (url: string, title: string) => {
    mutateDownloadExcel({ filter: filterFuneralService, dynamicUrl: url, dynamicName: getFileName(title) });
  };

  const onDownloadExcelFuneralSales = (url: string, title: string) => {
    mutateDownloadExcel({ filter: filterFuneralServiceSales, dynamicUrl: url, dynamicName: getFileName(title) });
  };

  const onDownloadExcelPetBreed = (url: string, title: string) => {
    mutateDownloadExcel({ filter: filterPetBreed, dynamicUrl: url, dynamicName: getFileName(title) });
  };

  useEffect(() => {
    if (branchOptions.length > 0) {
      const initialFilter = {
        startDate: dayjs().startOf('month').valueOf(),
        endDate: dayjs().endOf('day').valueOf(),
        branchIds: branchOptions.map((item) => item.value),
      };
      setFilter((pre) => ({ ...pre, ...initialFilter }));
    }
  }, [branchOptions]);

  useEffect(() => {
    if (petTypeOptions.length > 0) {
      const initialFilter = {
        petTypeIds: petTypeOptions.map((item) => item.value),
      };
      setFilterPetBreed((pre) => ({ ...pre, branchIds: branchOptions.map((item) => item.value), ...initialFilter }));
    }
  }, [petTypeOptions, branchOptions]);

  useEffect(() => {
    setFilter((pre) => ({ ...pre, funeralServiceNames: statisticServiceFilters }));
    setFilterFuneralService((pre) => ({ ...pre, funeralServiceNames: statisticServiceFilters }));
    setFilterFuneralServiceSales((pre) => ({ ...pre, funeralServiceNames: statisticServiceFilters }));
  }, [statisticServiceFilters]);

  return (
    <div>
      <PageTitle title={tMenu('basic')} />
      <StatisticFilter filter={filter} onFilterChange={onFilterChange} />
      <StatisticBasicAnimalByDay
        data={statisticBasicAnimalData}
        onDownloadExcel={() => {
          onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_ANIMALS, EXCEL_FILE_NAME.STATISTICS_BASIC_ANIMAL_BY_DATE);
        }}
      />

      <div className='mt-6'>
        <StatisticBasicFuneralByLeader data={statisticBasicManagerData} />
      </div>

      <div className='flex flex-col justify-between gap-6 mt-6 xl:flex-row'>
        <StatisticServices
          total={statisticBasicFuneralServiceData?.total}
          title={tStatistic('basic.number_funeral_services')}
          data={statisticBasicFuneralServiceData?.data || []}
          categories={statisticBasicFuneralServiceData?.names || []}
          showCheckbox
          unit=''
          onFilterChange={onChangeFilterFuneralService}
          filter={filterFuneralService}
          onDownloadExcel={() => {
            onDownloadExcelFuneralService(EXCEL_URL.STATISTIC_BASIC_SERVICE, EXCEL_FILE_NAME.STATISTICS_BASIC_FUNERAL_SERVICE);
          }}
          checkboxs={statisticServiceFilters}
        />
        <StatisticServices
          total={statisticBasicFuneralServiceSalesData?.total}
          title={tStatistic('basic.number_sale_services')}
          data={statisticBasicFuneralServiceSalesData?.data || []}
          categories={statisticBasicFuneralServiceSalesData?.names || []}
          showCheckbox
          unit='M'
          totalUnit='won'
          onFilterChange={onChangeFilterFuneralServiceSales}
          filter={filterFuneralServiceSales}
          onDownloadExcel={() => {
            onDownloadExcelFuneralSales(EXCEL_URL.STATISTIC_BASIC_SERVICE_SALE, EXCEL_FILE_NAME.STATISTICS_BASIC_NUMBER_SALES);
          }}
          checkboxs={statisticServiceFilters}
        />
      </div>

      <div className='flex flex-col justify-between gap-6 mt-6 xl:flex-row'>
        <StatisticPieData
          title={tStatistic('basic.sales_by_type')}
          data={statisticBasicSaleTypeData?.data || []}
          categories={statisticBasicSaleTypeData?.names || []}
          legends={statisticBasicSaleTypeData?.legends || []}
          total={statisticBasicSaleTypeData?.total}
          totalUnit='won'
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_SALE_TYPE, EXCEL_FILE_NAME.STATISTICS_BASIC_SALES_BY_TYPE);
          }}
        />
        <StatisticServices
          total={statisticBasicAdditionalCostData?.total}
          title={tStatistic('basic.additional_cost')}
          data={statisticBasicAdditionalCostData?.data || []}
          categories={statisticBasicAdditionalCostData?.names || []}
          totalUnit='won'
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_ADDITIONAL_COST, EXCEL_FILE_NAME.STATISTICS_BASIC_ADDITIONAL_COST);
          }}
        />
      </div>

      <div className='flex flex-col justify-between gap-6 mt-6 xl:flex-row'>
        <StatisticBasicObituary
          data={statisticBasicObituaryData?.data || []}
          categories={statisticBasicObituaryData?.names || []}
          title={tStatistic('basic.obituary')}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_OBITUARY, EXCEL_FILE_NAME.STATISTICS_BASIC_OBITUARY);
          }}
        />
        <StatisticServices
          total={statisticBasicFuneralTimeData?.total}
          data={statisticBasicFuneralTimeData?.data || []}
          categories={statisticBasicFuneralTimeData?.names || []}
          title={tStatistic('basic.funeral_time')}
          totalTitle={tStatistic('basic.total_funeral')}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_FUNERAL_TIME, EXCEL_FILE_NAME.STATISTICS_BASIC_FUNERAL_TIME);
          }}
        />
        <StatisticServices
          totalTitle={tStatistic('basic.total_funeral')}
          total={statisticBasicWeightData?.total}
          data={statisticBasicWeightData?.data || []}
          categories={statisticBasicWeightData?.names || []}
          title={tStatistic('basic.weight')}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_WEIGHT, EXCEL_FILE_NAME.STATISTICS_BASIC_WEIGHT);
          }}
        />
      </div>

      <div className='flex flex-col justify-between gap-6 mt-6 xl:flex-row'>
        <StatisticServices
          totalTitle={tStatistic('basic.total_funeral')}
          total={statisticBasicAgeData?.total}
          data={statisticBasicAgeData?.data || []}
          categories={statisticBasicAgeData?.names || []}
          title={tStatistic('basic.child_age')}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_AGE, EXCEL_FILE_NAME.STATISTICS_BASIC_AGE);
          }}
        />
        <StatisticBasicVarieties
          total={statisticBasicPetBreedData?.total}
          data={statisticBasicPetBreedData?.data || []}
          categories={statisticBasicPetBreedData?.names || []}
          filter={filterPetBreed}
          onFilterChange={onChangeFilterPetBreed}
          onDownloadExcel={() => {
            onDownloadExcelPetBreed(EXCEL_URL.STATISTIC_BASIC_BREED, EXCEL_FILE_NAME.STATISTICS_BASIC_BREED);
          }}
        />
      </div>

      <div className='flex flex-col justify-between gap-6 mt-6 xl:flex-row'>
        <StatisticBasicTransport
          data={statisticBasicTransportData?.data || []}
          categories={statisticBasicTransportData?.names || []}
          legends={statisticBasicTransportData?.legends || []}
          totalTransport={statisticBasicTransportData?.totalUseOfTransport}
          total={statisticBasicTransportData?.total}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_TRANSPORT, EXCEL_FILE_NAME.STATISTICS_BASIC_TRANSPORT);
          }}
        />
        <StatisticBasicConnectedHospital
          data={statisticBasicLinkedHospitalData?.data || []}
          categories={statisticBasicLinkedHospitalData?.names || []}
          total={statisticBasicLinkedHospitalData?.total}
          totalConnections={statisticBasicLinkedHospitalData?.totalUseOfTransport}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_LINKED_HOSPITAL, EXCEL_FILE_NAME.STATISTICS_BASIC_LINKED_HOSPITAL);
          }}
        />
      </div>
      <div className='flex flex-col justify-between gap-6 mt-6 xl:flex-row'>
        <StatisticPieData
          title={tStatistic('basic.reservation_route_title')}
          data={statisticBasicRouteData?.data || []}
          categories={statisticBasicRouteData?.names || []}
          legends={statisticBasicRouteData?.legends || []}
          total={statisticBasicRouteData?.total}
          totalTitle={tStatistic('total')}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_RESERVATION_ROUTE, EXCEL_FILE_NAME.STATISTICS_BASIC_RESERVATION_ROUTE);
          }}
        />
        <StatisticPieData
          title={tStatistic('basic.visit_route')}
          data={statisticBasicVisitRouteData?.data || []}
          categories={statisticBasicVisitRouteData?.names || []}
          legends={statisticBasicVisitRouteData?.legends || []}
          total={statisticBasicVisitRouteData?.total}
          totalTitle={tStatistic('total')}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_BASIC_VISIT_ROUTE, EXCEL_FILE_NAME.STATISTICS_BASIC_VISIT_ROUTE);
          }}
        />
      </div>
    </div>
  );
};

export default page;
