'use client';
import PageTitle from '@/components/common/page/PageTitle';
import { useBranch } from '@/hook/branch/useBranch';
import { useMaterial } from '@/hook/material/useMaterial';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { StatisticArticleREQ } from '@/store/statistic/request/statistic.request';
import { getStatisticArticleApi, getStatisticArticleDetailApi, statisticArticleKeys } from '@/store/statistic/statistic.api';
import { statisticArticleRespToDto } from '@/store/statistic/statistic.service';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import StatisticFilter from '../components/StatisticFilter';
import StatisticPieData from '../components/StatisticPieData';
import StatisticArticleDetail from './components/StatisticArticleDetail';

const page = () => {
  const { t: tMenu } = useTranslation('menu');
  const { t: tStatistic } = useTranslation('statistic');
  const { branchOptions } = useBranch();
  const { materialTypeOptions } = useMaterial();

  const [filter, setFilter] = useState<StatisticArticleREQ>({
    startDate: dayjs().startOf('month').valueOf(),
    endDate: dayjs().endOf('day').valueOf(),
    branchIds: [],
  });

  const [filterDetail, setFilterDetail] = useState<StatisticArticleREQ>({
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

  const { data: statisticArticleData } = useQuery({
    queryKey: statisticArticleKeys.list(filter),
    queryFn: () => getStatisticArticleApi(filter),
    select: (response) => {
      return response.data.data && statisticArticleRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: statisticArticleDetailData } = useQuery({
    queryKey: statisticArticleKeys.list(filterDetail),
    queryFn: () => getStatisticArticleDetailApi(filterDetail),
    select: (response) => {
      return response.data.data && statisticArticleRespToDto(response.data.data);
    },
  });

  //Download Excel
  const { mutateDownloadExcel } = useDownloadExcel<StatisticArticleREQ>({
    fileName: EXCEL_FILE_NAME.STATISTICS,
  });

  const onFilterChange = (filter: StatisticArticleREQ) => {
    setFilter(filter);
    setFilterDetail((pre) => ({ ...pre, ...filter }));
  };

  const onChangeMaterialType = (value: string) => {
    setFilterDetail((pre) => ({ ...pre, type: value }));
  };

  const onDownloadExcel = (url: string, title: string) => {
    mutateDownloadExcel({ filter, dynamicUrl: url, dynamicName: getFileName(title) });
  };

  const onDownloadExcelDetail = (url: string, title: string) => {
    mutateDownloadExcel({ filter: filterDetail, dynamicUrl: url, dynamicName: getFileName(title) });
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

  useEffect(() => {
    if (materialTypeOptions.length > 0) {
      setFilterDetail((pre) => ({
        ...pre,
        type: materialTypeOptions[0].value,
        branchIds: branchOptions.map((item) => item.value),
      }));
    }
  }, [materialTypeOptions, branchOptions]);

  return (
    <div>
      <PageTitle title={tMenu('article')} />
      <StatisticFilter filter={filter} onFilterChange={onFilterChange} />

      <div className='flex gap-6 mt-6 flex-col xl:flex-row'>
        <StatisticPieData
          title={tStatistic('article.number_items')}
          data={statisticArticleData?.data || []}
          categories={statisticArticleData?.names || []}
          legends={statisticArticleData?.legends || []}
          total={statisticArticleData?.total}
          totalTitle={tStatistic('total')}
          onDownloadExcel={() => {
            onDownloadExcel(EXCEL_URL.STATISTIC_ARTICLE, EXCEL_FILE_NAME.STATISTICS_ARTICLE);
          }}
        />
        <StatisticArticleDetail
          data={statisticArticleDetailData?.data || []}
          categories={statisticArticleDetailData?.names || []}
          legends={statisticArticleDetailData?.legends || []}
          total={statisticArticleDetailData?.total}
          onChangeMaterialType={onChangeMaterialType}
          filter={filterDetail}
          onDownloadExcel={() => {
            onDownloadExcelDetail(EXCEL_URL.STATISTIC_ARTICLE_DETAIL, EXCEL_FILE_NAME.STATISTICS_ARTICLE_DETAIL);
          }}
        />
      </div>
    </div>
  );
};

export default page;
