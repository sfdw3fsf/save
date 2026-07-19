'use client';

import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import RegionCascader from '@/components/common/CustomCascader';
import { CustomTabsFilterButton } from '@/components/common/CustomTabsFilterButton';
import DeleteButton from '@/components/common/DeleteButton';
import CustomModal from '@/components/common/modals/CustomModal';
import PageTitle from '@/components/common/page/PageTitle';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import CustomText from '@/components/common/Text';
import { useBranch } from '@/hook/branch/useBranch';
import { useHospitalSettlementMethod } from '@/hook/setting/useHospitalSettlementMethod';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import useRouterHandler from '@/hook/useRouterHandler';
import { useSelectTable } from '@/hook/useSelectTable';
import {
  createHospitalApi,
  deleteHospitalApi,
  getHospitalDetailApi,
  getHospitalListApi,
  getHospitalRegionApi,
  hospitalKeys,
  regionKeys,
  updateHospitalApi,
  updateMarkedHospitalApi,
} from '@/store/collaborating-hospital/collaborating-hospital.api';
import {
  hospitalDetailRespToDto,
  hospitalInformationInitialValues,
  hospitalInformationUpdateDtoToReq,
  hospitalListParamsToFilter,
  hospitalRegionRespToDtoType2,
  hospitalRespToDto,
  regionFilterToReq,
} from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalDetailDTO, HospitalDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import {
  HospitalListFilter,
  HospitalListREQ,
  HospitalUpdateREQ,
} from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { hospitalSchema } from '@/utils/schemas/collaborating-hosiptal/collaborating-hospital.schema';
import { CollaboratingRegion } from '@/utils/types/collaborating/partner-hospital.type';
import { OptionItems } from '@/utils/types/option.type';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { StarFilled, StarOutlined } from '@ant-design/icons';
import { yupResolver } from '@hookform/resolvers/yup';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { CPH_DetailModal } from './components/CPH_DetailModal';
import CPH_TableFilter from './components/CPH_TableFilter';
import { CPH_Columns } from './data-columns/CPH_Columns';

const CPH_Page = () => {
  const { t: tCommon } = useTranslation('common');
  const { t: tPartnerHospital } = useTranslation('collaborating_partner_hospital');
  const methods = useForm<HospitalDetailDTO>({
    defaultValues: hospitalInformationInitialValues,
    resolver: yupResolver(hospitalSchema) as any,
    context: { isOptionalEmail: true },
  });
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { branchOptions } = useBranch();
  const { settlementMethodOptions } = useHospitalSettlementMethod();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<HospitalDTO>();
  const { mutateDownloadExcel } = useDownloadExcel<HospitalListREQ>({
    fileName: EXCEL_FILE_NAME.HOSPITAL_MANAGEMENT_PARTNER,
    url: EXCEL_URL.HOSPITAL_MANAGEMENT_PARTNER,
  });
  const { handleURLPush } = useRouterHandler();

  const hospitalId = useRef<number>(0);

  const { reset, handleSubmit, watch } = methods;
  const [selectedRegion, setSelectedRegion] = useState<CollaboratingRegion[]>([]);
  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);
  const [isHidingFooter, setIsHiddingFooter] = useState<boolean>(false);

  const isMarkedWatch = watch('isMarked');

  const branchTabs: OptionItems<number | string> = useMemo(() => {
    if (!branchOptions) return [];
    return [{ label: tCommon('entire'), value: 'All' }, ...branchOptions];
  }, [branchOptions]);

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = hospitalListParamsToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<HospitalListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const date = useMemo(() => {
    const parsedFilter = JSON.parse(filter?.filter || '{}');
    const startDate = parsedFilter['createdAt.gte'];
    const endDate = parsedFilter['createdAt.lte'];

    if (startDate && endDate) {
      return dayjs(startDate).format(DATE_FORMAT.DATE.DOT_SHORTEN) + '_' + dayjs(endDate).format(DATE_FORMAT.DATE.DOT_SHORTEN);
    }

    return '';
  }, [filter?.filter]);

  const { data: hospitalListData } = useQuery({
    queryKey: hospitalKeys.list(filter),
    queryFn: () => getHospitalListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(hospitalRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: hospitalDetailData } = useQuery({
    queryKey: hospitalKeys.detail(hospitalId.current),
    queryFn: () => getHospitalDetailApi(hospitalId.current),
    select: (response) => {
      return response?.data.data && hospitalDetailRespToDto(response?.data.data);
    },
    enabled: hospitalId.current > 0,
    placeholderData: keepPreviousData,
  });

  const parsedFilter = JSON.parse(filter?.filter || '{}');
  const startDateFilter = parsedFilter['createdAt.gte'];
  const endDateFilter = parsedFilter['createdAt.lte'];

  const { data: regionData } = useQuery({
    queryKey: [regionKeys.lists(), startDateFilter, endDateFilter],
    queryFn: () => getHospitalRegionApi({ startDate: startDateFilter, endDate: endDateFilter }),
    select: (response) => {
      return response.data.data.map(hospitalRegionRespToDtoType2);
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateCreateHospital } = useMutation({
    mutationFn: () => createHospitalApi(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: hospitalKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateHospital } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: HospitalUpdateREQ }) => updateHospitalApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: hospitalKeys.all });
      queryClient.invalidateQueries({ queryKey: regionKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateMarkedHospital } = useMutation({
    mutationFn: (id: number) => updateMarkedHospitalApi(id),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: hospitalKeys.all });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteHospital, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteHospitalApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: hospitalKeys.lists() });
    },
    onError: handleError,
  });

  const hospitalName = useMemo(() => {
    if (hospitalId.current === 0) return '';
    return hospitalListData?.data.find((item) => item.id === hospitalId.current)?.name || '';
  }, [hospitalId.current, hospitalListData?.data]);

  const onDetail = (id: number) => {
    hospitalId.current = id;
    handleURLPush('id', id.toString());

    setIsOpenDetail(true);
  };

  const onStarClick = () => {
    mutateUpdateMarkedHospital(hospitalId.current);
  };

  const onSubmit = (values: HospitalDetailDTO) => {
    mutateUpdateHospital({ id: hospitalId.current, body: hospitalInformationUpdateDtoToReq(values) });
  };

  const onCloseDetail = () => {
    hospitalId.current = 0;
    handleURLPush('id', undefined);
    setIsOpenDetail(false);
  };

  const onCreateHospital = () => {
    mutateCreateHospital();
  };

  const onChangeTab = (value: number | string) => {
    const newTableFilter: any = {};
    newTableFilter['hospitalBranches.some.branchId.in'] = value === 'All' ? '' : [value];

    handleFilterChange({ filter: JSON.stringify(newTableFilter) });
  };

  const onChangeRegion = (value: CollaboratingRegion[]) => {
    const regionsReq = regionFilterToReq(value);
    handleFilterChange({ regions: regionsReq });
  };

  const onRemoveRegion = (value: CollaboratingRegion) => {
    const regionsReq = regionFilterToReq(selectedRegion.filter((item) => item !== value));
    handleFilterChange({ regions: regionsReq });
  };

  const onUpdateHospital = (id: number, values: HospitalUpdateREQ) => {
    mutateUpdateHospital({ id, body: values });
  };

  const onDeleteHospital = () => {
    mutateDeleteHospital(parseArrayToStringRequest(selectedRowKeys));
  };

  const onDownloadExcel = () => {
    const dynamicName = [EXCEL_FILE_NAME.HOSPITAL_MANAGEMENT_PARTNER, date].filter((i) => !!i).join('_');
    mutateDownloadExcel({
      filter,
      ids: parseArrayToStringRequest(selectedRowKeys, false),
      dynamicName,
    });
  };

  useEffect(() => {
    if (!hospitalDetailData) return;
    reset(hospitalDetailData);
  }, [hospitalDetailData, reset]);

  useEffect(() => {
    const detailId = searchParams.get('id');
    if (detailId) {
      hospitalId.current = Number(detailId);
      setIsOpenDetail(true);
    }
  }, []);

  return (
    <div>
      <PageTitle title={tPartnerHospital('title')} isWrap={true} actions={<AddButton onClick={onCreateHospital} />} />
      <CustomTabsFilterButton classname='!justify-start my-4' optionTabs={branchTabs} onChangeTabs={onChangeTab} />
      <div className='mb-4'>
        <RegionCascader
          options={regionData || []}
          selectedRegion={selectedRegion}
          setSelectedRegion={setSelectedRegion}
          showSelectedList
          onChange={onChangeRegion}
          onRemove={onRemoveRegion}
        />
      </div>
      <TableFilter total={hospitalListData?.total}>
        <CPH_TableFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
      </TableFilter>

      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<HospitalDTO>
          key={JSON.stringify({ branchOptions, settlementMethodOptions })}
          columns={CPH_Columns({ onDetail, branchOptions, settlementMethodOptions, onUpdateHospital })}
          data={hospitalListData?.data || []}
          showPaging
          showSelect
          extra={
            <DeleteButton
              onClick={() => {
                openModal(selectedRowKeys as number[]);
              }}
            />
          }
          paging={{
            total: hospitalListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>

      <FormProvider {...methods}>
        <form>
          <CustomModal
            isOpen={isOpenDetail}
            onOk={!isHidingFooter ? handleSubmit(onSubmit) : undefined}
            onClose={() => onCloseDetail()}
            onCancel={() => onCloseDetail()}
            title={
              <div className='flex gap-2'>
                <CustomText variant='headline-1' className='!font-bold'>
                  {hospitalName}
                </CustomText>
                <div onClick={onStarClick} className='cursor-pointer'>
                  {isMarkedWatch ? <StarFilled className='!text-primary-800' /> : <StarOutlined className='!text-neutral-60' />}
                </div>
              </div>
            }>
            <CPH_DetailModal id={hospitalId.current} setIsHiddingFooter={setIsHiddingFooter} />
          </CustomModal>
        </form>
      </FormProvider>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteHospital} isLoading={isDeleting} />
    </div>
  );
};

export default CPH_Page;
