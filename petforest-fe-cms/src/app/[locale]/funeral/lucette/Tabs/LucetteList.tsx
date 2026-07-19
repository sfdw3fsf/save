import CountInfoCard from '@/components/common/CountInfoCard';
import CustomModal from '@/components/common/modals/CustomModal';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { recipientRequirePopup } from '@/components/popup/RecipientRequirePopup';
import { useSettingVarietiesBreed } from '@/hook/setting/useSettingVarietiesBreed';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { usePaging } from '@/hook/usePaging';
import { useUser } from '@/hook/user/useUser';
import { useSelectTable } from '@/hook/useSelectTable';
import { LucetteDetailDTO, LucetteListDTO } from '@/store/funeral/lucette/dto/lucette.dto';
import {
  createLucetteMessageApi,
  getLucetteDetailApi,
  getLucetteListApi,
  getLucettePdfApi,
  getLucetteSummaryApi,
  lucetteKeys,
  lucetteScheduleKeys,
  lucetteSummaryKeys,
  updateLucetteListApi,
} from '@/store/funeral/lucette/lucette.api';
import {
  lucetteDetailRespToDto,
  lucetteListParamsToFilter,
  lucetteListRespToDto,
  lucetteSummaryRespToDto,
} from '@/store/funeral/lucette/lucette.service';
import { LucetteListFilter, LucetteListREQ, LucetteListUpdateREQ } from '@/store/funeral/lucette/request/lucette.request';
import { getMaterialNameOptionApi, materialLucetteKeys } from '@/store/material/material.api';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { LucetteProcessMap } from '@/utils/constants/funeral/lucette/lucctte.constant';
import { LUCETTE_CATEGORY } from '@/utils/enums/funeral/lucette-mngt.enums';
import { MATERIAL_TYPE } from '@/utils/enums/material/material.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { orNull } from '@/utils/parser/io.parser';
import { lucetteInfoSchema, LucetteInfoType } from '@/utils/schemas/funeral/lucette.schema';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { downloadExcel } from '@/utils/utils/download-excel.util';
import { yupResolver } from '@hookform/resolvers/yup';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { LucetteDetailModal } from '../components/LucetteDetailModal';
import LucetteTableFilter from '../components/LucetteTableFilter';
import { LucetteColumns } from '../data-columns/LucetteColumns';

const DEFAULT_DATE_RANGE: [number, number] = [
  dayjs().startOf('day').subtract(1, 'month').valueOf(),
  dayjs().endOf('day').valueOf(),
];

const LucetteList = () => {
  const { t: tLucette } = useTranslation('funeral_lucette');

  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { mutateDownloadExcel } = useDownloadExcel<LucetteListREQ>({
    fileName: EXCEL_FILE_NAME.LUCETTE_MANAGEMENT,
    url: EXCEL_URL.LUCETTE_MANAGEMENT,
  });

  const { managerDirectorOptions } = useUser();
  const { breedNameOptions } = useSettingVarietiesBreed();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<LucetteListDTO>();
  const methods = useForm<LucetteInfoType>({
    defaultValues: lucetteInfoSchema.getDefault(),
    resolver: yupResolver(lucetteInfoSchema),
  });

  const [selectedLucette, setSelectedLucette] = useState<LucetteDetailDTO | null>(null);
  const [selectedProcess, setSelectedProcess] = useState<LUCETTE_CATEGORY>();

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = lucetteListParamsToFilter(searchParams, {
      filter: JSON.stringify({
        'reservation.funeralInformation.funeralDate.gte': DEFAULT_DATE_RANGE[0],
        'reservation.funeralInformation.funeralDate.lte': DEFAULT_DATE_RANGE[1],
      }),
    });
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<LucetteListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const filterDateRange = useMemo<[number, number]>(() => {
    let start, end;
    try {
      const obj = JSON.parse(filter?.filter ?? '');
      start = obj['reservation.funeralInformation.funeralDate.gte'];
      end = obj['reservation.funeralInformation.funeralDate.lte'];
    } catch (e) {
      console.error(e);
    }
    if (start && end) return [+start, +end];
    return DEFAULT_DATE_RANGE;
  }, [filter]);

  const { data: listData } = useQuery({
    queryKey: lucetteKeys.list(filter),
    queryFn: () => getLucetteListApi(filter),
    select: ({ data }) => {
      return { ...data, data: data.data.map(lucetteListRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: summaryData } = useQuery({
    queryKey: lucetteSummaryKeys.list(filter),
    queryFn: () =>
      getLucetteSummaryApi({
        startDate: filterDateRange[0],
        endDate: filterDateRange[1],
      }),
    select: (response) => {
      return response?.data?.data && lucetteSummaryRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDetailFetch } = useMutation({
    mutationFn: getLucetteDetailApi,
    onSuccess: (response) => {
      setSelectedLucette(orNull(response?.data?.data && lucetteDetailRespToDto(response.data.data)));
    },
    onError: () => {
      setSelectedLucette(null);
    },
  });

  const { mutate: mutateListUpdate } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: LucetteListUpdateREQ }) => updateLucetteListApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      onRefetch();
    },
    onError: handleError,
  });

  const { mutate: mutateCreateMessage } = useMutation({
    mutationFn: createLucetteMessageApi,
    onSuccess: () => {
      handleSuccess('create');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: lucetteKeys.lists() });
    },
    onError: handleError,
  });

  const { data: materialNames = [] } = useQuery({
    queryKey: materialLucetteKeys.lists(),
    queryFn: () => getMaterialNameOptionApi(MATERIAL_TYPE.LUCETTE),
    select: (response) => {
      return response?.data?.data && response.data.data;
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDownloadPdf } = useMutation({
    mutationFn: (id: number) => getLucettePdfApi(id),
    onSuccess: (resp) => {
      downloadExcel(resp, 'lucette', 'pdf');
    },
    onError: handleError,
  });

  const onDownloadPdf = (id: number) => {
    mutateDownloadPdf(id);
  };

  const onListUpdate = (id: number, body: LucetteListUpdateREQ) => {
    mutateListUpdate({ id, body });
  };

  const onSubmit = (data: LucetteInfoType) => {
    if (!selectedLucette) return;
    const request: LucetteListUpdateREQ = {
      jewelry: data.jewelry,
      deliveryStatus: data.deliveryStatus,
      address: data.funeralAddress,
      addressDetail: data.detailAddress,
      tycheDeliveryDate: data.tycheDeliveryDate?.valueOf(),
      petForestIndiaDate: data.petForestIndiaDate?.valueOf(),
    };
    onListUpdate(selectedLucette.id, request);
  };

  const onRefetch = () => {
    queryClient.invalidateQueries({ queryKey: lucetteKeys.lists() });
    queryClient.invalidateQueries({ queryKey: lucetteSummaryKeys.all });
    queryClient.invalidateQueries({ queryKey: lucetteScheduleKeys.all });
    setSelectedLucette(null);
  };

  const onCreateMessage = () => {
    if (!selectedRowKeys.length) {
      recipientRequirePopup();
      return;
    }
    mutateCreateMessage(selectedRowKeys as number[]);
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  const onSelectProcess = (value?: LUCETTE_CATEGORY) => {
    setSelectedProcess(value);
    const obj = JSON.parse(filter?.filter ?? '');
    handleFilterChange({ filter: JSON.stringify({ ...obj, 'process.in': value ? [value] : undefined }) }, true);
  };

  useEffect(() => {
    const defaultValues = lucetteInfoSchema.getDefault();
    methods.reset({
      jewelry: selectedLucette?.jewelry ?? defaultValues.jewelry,
      tycheDeliveryDate: selectedLucette?.tycheDeliveryDate ?? defaultValues.tycheDeliveryDate,
      petForestIndiaDate: selectedLucette?.petForestIndiaDate ?? defaultValues.petForestIndiaDate,
      deliveryStatus: selectedLucette?.deliveryStatus ?? defaultValues.deliveryStatus,
      funeralAddress: selectedLucette?.address ?? defaultValues.funeralAddress,
      detailAddress: selectedLucette?.addressDetail ?? defaultValues.detailAddress,
    });
  }, [selectedLucette, methods.reset]);

  return (
    <>
      <div className='mt-6'>
        <CountInfoCard
          className='w-[196px]'
          title={tLucette('lucette.count.total')}
          value={summaryData?.total ?? 0}
          childrenValue={Object.entries(summaryData?.detail ?? {}).map(([key, value]) => ({
            title: tLucette(`lucette.count.${key}`, key),
            value: value,
            isSelected: selectedProcess === LucetteProcessMap[key as keyof typeof LucetteProcessMap],
            onSelectProcess: () => {
              if (selectedProcess === LucetteProcessMap[key as keyof typeof LucetteProcessMap]) {
                onSelectProcess();
              } else {
                onSelectProcess(LucetteProcessMap[key as keyof typeof LucetteProcessMap]);
              }
            },
          }))}
        />
      </div>
      <div className='mt-6'>
        <TableFilter total={listData?.total}>
          <LucetteTableFilter
            defaultDateRange={filterDateRange}
            onFilterChange={handleFilterChange}
            onCreateMessage={onCreateMessage}
            onDownloadExcel={onDownloadExcel}
          />
        </TableFilter>
      </div>

      <div className='mt-4' id='fixedTable'>
        <CustomTable<LucetteListDTO>
          key={JSON.stringify({ managerDirectorOptions, breedNameOptions })}
          columns={LucetteColumns({
            onRecordSelect: mutateDetailFetch,
            onListUpdate,
            managerOptions: managerDirectorOptions,
            materialNames,
            breedNameOptions,
            onDownloadPdf,
          })}
          data={listData?.data || []}
          showSelect
          selectedRowKeys={selectedRowKeys}
          showIndex
          showPaging
          paging={{
            total: listData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          serialProps={{ fixed: 'left' }}
          className='lucette-table'
        />
      </div>

      <FormProvider {...methods}>
        <CustomModal
          isOpen={selectedLucette !== null}
          onOk={methods.handleSubmit(onSubmit)}
          onClose={() => setSelectedLucette(null)}
          onCancel={() => setSelectedLucette(null)}
          title={tLucette('lucette.detail.title')}>
          <LucetteDetailModal />
        </CustomModal>
      </FormProvider>
    </>
  );
};

export default LucetteList;
