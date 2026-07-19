'use client';

import RegionCascader from '@/components/common/CustomCascader';
import PageTitle from '@/components/common/page/PageTitle';
import { useBranch } from '@/hook/branch/useBranch';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { regionFilterToReq } from '@/store/collaborating-hospital/collaborating-hospital.service';
import { StatisticRegionREQ } from '@/store/statistic/request/statistic.request';
import {
  getStatisticByAreaApi,
  getStatisticByRegionApi,
  getStatisticRegionApi,
  statisticByAreaKeys,
  statisticByRegionKeys,
  statisticRegionKeys,
} from '@/store/statistic/statistic.api';
import {
  statisticRegionRespToDto,
  statisticTotalByAreaRespToDto,
  statisticTotalByRegionRespToDto,
} from '@/store/statistic/statistic.service';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { CollaboratingRegion } from '@/utils/types/collaborating/partner-hospital.type';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import StatisticFilter from '../components/StatisticFilter';
import StatisticServices from '../components/StatisticServices';
import StatisticRegionAreaDetails from './components/StatisticRegionAreaDetails';

const page = () => {
  const { t: tMenu } = useTranslation('menu');
  const { t: tCommon } = useTranslation('common');
  const { t: tStatistic } = useTranslation('statistic');
  const { branchOptions } = useBranch();

  const [selectedRegion, setSelectedRegion] = useState<CollaboratingRegion[]>([]);

  const [filter, setFilter] = useState<StatisticRegionREQ>({
    startDate: dayjs().startOf('month').valueOf(),
    endDate: dayjs().endOf('day').valueOf(),
    branchIds: [],
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

  const { data: regionData } = useQuery({
    queryKey: statisticRegionKeys.list(filter),
    queryFn: () => getStatisticRegionApi(filter),
    select: (response) => {
      return response.data.data.map(statisticRegionRespToDto);
    },
    placeholderData: keepPreviousData,
  });

  const { data: totalByRegionData } = useQuery({
    queryKey: statisticByRegionKeys.list(filter),
    queryFn: () => getStatisticByRegionApi(filter),
    select: (response) => {
      return response.data.data && statisticTotalByRegionRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: totalByAreaData } = useQuery({
    queryKey: statisticByAreaKeys.list(filter),
    queryFn: () => getStatisticByAreaApi(filter),
    select: (response) => {
      return response.data.data && statisticTotalByAreaRespToDto(response.data.data, filter);
    },
    placeholderData: keepPreviousData,
  });

  //Download Excel
  const { mutateDownloadExcel } = useDownloadExcel<StatisticRegionREQ>({
    fileName: EXCEL_FILE_NAME.STATISTICS,
  });

  const onFilterChange = (filter: StatisticRegionREQ) => {
    setFilter(filter);
  };

  const onChangeRegion = (value: CollaboratingRegion[]) => {
    const regionsReq = regionFilterToReq(value);
    setFilter((pre) => ({ ...pre, regions: regionsReq }));
  };

  const onRemoveRegion = (value: CollaboratingRegion) => {
    const regionsReq = regionFilterToReq(selectedRegion.filter((item) => item !== value));
    setFilter((pre) => ({ ...pre, regions: regionsReq }));
  };

  const onDownloadExcel = (url: string, title: string) => {
    mutateDownloadExcel({ filter, dynamicUrl: url, dynamicName: getFileName(title) });
  };

  useEffect(() => {
    if (branchOptions.length > 0) {
      const initialFilter = {
        startDate: dayjs().startOf('month').valueOf(),
        endDate: dayjs().valueOf(),
        branchIds: branchOptions.map((item) => item.value),
      };
      setFilter((pre) => ({ ...pre, ...initialFilter }));
    }
  }, [branchOptions]);

  return (
    <div>
      <PageTitle title={tMenu('region')} />
      <StatisticFilter filter={filter} onFilterChange={onFilterChange} />

      <div className='mt-6'>
        <StatisticServices
          total={totalByRegionData?.total}
          title={tStatistic('region.total_by_region')}
          data={totalByRegionData?.data || []}
          categories={totalByRegionData?.names || []}
          totalTitle={tStatistic('total')}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_REGION, EXCEL_FILE_NAME.STATISTICS_REGION);
          }}
        />
      </div>

      <div className='mt-[40px]'>
        <RegionCascader
          options={regionData || []}
          selectedRegion={selectedRegion}
          setSelectedRegion={setSelectedRegion}
          showSelectedList
          onChange={onChangeRegion}
          onRemove={onRemoveRegion}
        />
      </div>

      <div className='mt-6'>
        <StatisticRegionAreaDetails
          total={totalByAreaData?.total}
          data={totalByAreaData?.data || []}
          categories={totalByAreaData?.names || []}
          legends={totalByAreaData?.legends || []}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_REGION_LINE, EXCEL_FILE_NAME.STATISTICS_REGION_AREA);
          }}
        />
      </div>
    </div>
  );
};

export default page;
