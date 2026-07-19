'use client';

import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import RegionCascader from '@/components/common/CustomCascader';
import { CustomTabsFilterButton } from '@/components/common/CustomTabsFilterButton';
import DeleteButton from '@/components/common/DeleteButton';
import PageTitle from '@/components/common/page/PageTitle';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import {
  createHospitalSaleListApi,
  deleteHospitalSaleListApi,
  getHospitalRegionSalesApi,
  getHospitalSaleListApi,
  hospitalSaleKeys,
  regionSalesKeys,
  updateHospitalSaleListApi,
} from '@/store/collaborating-hospital/collaborating-hospital.api';
import {
  hospitalRegionRespToDto,
  hospitalSaleListParamsToFilter,
  hospitalSaleRespToDto,
  regionFilterToReq,
} from '@/store/collaborating-hospital/collaborating-hospital.service';

import { useBranch } from '@/hook/branch/useBranch';
import { useHospital } from '@/hook/collaborating-hospital/useHospital';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useUser } from '@/hook/user/useUser';
import { HospitalSaleListDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import {
  HospitalSaleListFilter,
  HospitalSaleListREQ,
  HospitalSaleListUpdateREQ,
} from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { CollaboratingRegion } from '@/utils/types/collaborating/partner-hospital.type';
import { OptionItems } from '@/utils/types/option.type';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import CS_TableFilter from './components/CS_TableFilter';
import { CS_Columns } from './data-columns/CS_Columns';

const CS_Page = () => {
  const { t: tCommon } = useTranslation('common');
  const { t: tSaleHospital } = useTranslation('collaborating_sales');

  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { hospitalOptions } = useHospital();
  const { managerOptions } = useUser();
  const { branchOptions } = useBranch();
  const { mutateDownloadExcel } = useDownloadExcel<HospitalSaleListREQ>({
    fileName: EXCEL_FILE_NAME.HOSPITAL_SALES_MANAGEMENT,
    url: EXCEL_URL.LUCETTE_MANAGEMENT,
  });

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<HospitalSaleListDTO>();

  const [selectedRegion, setSelectedRegion] = useState<CollaboratingRegion[]>([]);

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = hospitalSaleListParamsToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<HospitalSaleListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const filterObject = JSON.parse(filter.filter || '{}');
  const startDateFilter = filterObject['visitedAt.gte'];
  const endDateFilter = filterObject['visitedAt.lte'];

  const branchTabs: OptionItems<number | string> = useMemo(() => {
    if (!branchOptions) return [];
    return [{ label: tCommon('entire'), value: 'All' }, ...branchOptions];
  }, [branchOptions, tCommon]);

  const { data: regionSalesData } = useQuery({
    queryKey: regionSalesKeys.lists(),
    queryFn: () => getHospitalRegionSalesApi(),
    select: (response) => {
      return response.data.data.map(hospitalRegionRespToDto);
    },
    placeholderData: keepPreviousData,
  });

  const { data: totalFuneralData } = useQuery({
    queryKey: [regionSalesKeys.lists(), startDateFilter, endDateFilter],
    queryFn: () => getHospitalRegionSalesApi(startDateFilter, endDateFilter),
    select: (response) => {
      return response.data.data[0];
    },
    placeholderData: keepPreviousData,
  });

  const { data: listData } = useQuery({
    queryKey: hospitalSaleKeys.list(filter),
    queryFn: () => getHospitalSaleListApi(filter),
    select: ({ data }) => {
      return { ...data, data: data.data.map(hospitalSaleRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateListUpdate } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: HospitalSaleListUpdateREQ }) => updateHospitalSaleListApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: hospitalSaleKeys.lists() });
    },
    onError: handleError,
  });

  const handleListUpdate = (id: number, body: HospitalSaleListUpdateREQ) => {
    mutateListUpdate({ id, body });
  };

  const { mutate: mutateListDelete, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteHospitalSaleListApi(ids),
    onSuccess: () => {
      closeModal();
      setSelectedRowKeys([]);

      handleSuccess('delete');
      queryClient.invalidateQueries({ queryKey: hospitalSaleKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateListCreate } = useMutation({
    mutationFn: () => createHospitalSaleListApi(),
    onSuccess: () => {
      handleSuccess('create');
      queryClient.invalidateQueries({ queryKey: hospitalSaleKeys.lists() });
    },
    onError: handleError,
  });

  const handleListDelete = () => {
    mutateListDelete(parseArrayToStringRequest(selectedRowKeys));
  };

  const handleListCreate = async () => {
    mutateListCreate();
  };

  const onChangeRegion = (value: CollaboratingRegion[]) => {
    const regionsReq = regionFilterToReq(value);
    handleFilterChange({ regions: regionsReq });
  };

  const onRemoveRegion = (value: CollaboratingRegion) => {
    const regionsReq = regionFilterToReq(selectedRegion.filter((item) => item !== value));
    handleFilterChange({ regions: regionsReq });
  };

  const onChangeTab = (value: number | string) => {
    const newTableFilter: any = {};
    newTableFilter['hospital.hospitalBranches.some.branchId.in'] = value === 'All' ? '' : [value];

    handleFilterChange({ filter: JSON.stringify(newTableFilter) });
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  return (
    <div>
      <PageTitle title={tSaleHospital('title')} isWrap={true} actions={<AddButton onClick={handleListCreate} />} />
      <CustomTabsFilterButton classname='!justify-start my-4' optionTabs={branchTabs} onChangeTabs={onChangeTab} />

      <div className='flex mb-4 py-2 w-full gap-2.5 max-w-[450px] max-md:max-w-none max-md:flex-col'>
        {/* Total Funerals */}
        <div className='border flex-1 border-neutral-40 p-3 rounded-lg flex flex-col items-start  gap-2'>
          <span className='text-sm font-medium text-[#0A0A0A]'>장례연계수</span>
          <span className='text-xl font-semibold text-orange-500'>{totalFuneralData?.total}</span>
        </div>
        {/* Total Visits */}
        <div className='border flex-1 border-neutral-40 p-3 rounded-lg flex flex-col items-start  gap-2'>
          <span className='text-sm font-medium text-[#0A0A0A]'>장례연계수</span>
          <span className='text-xl font-semibold text-primary-800'>{totalFuneralData?.totalFuneral}</span>
        </div>
      </div>

      <div className='mb-4'>
        <RegionCascader
          options={regionSalesData || []}
          selectedRegion={selectedRegion}
          setSelectedRegion={setSelectedRegion}
          showSelectedList
          onChange={onChangeRegion}
          onRemove={onRemoveRegion}
        />
      </div>
      <TableFilter total={listData?.total}>
        <CS_TableFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
      </TableFilter>

      <div className='mt-4' id='fixedTable'>
        <CustomTable<HospitalSaleListDTO>
          key={JSON.stringify({ branchOptions, hospitalOptions, managerOptions })}
          columns={CS_Columns({ onListUpdate: handleListUpdate, hospitalOptions, managerOptions })}
          data={listData?.data || []}
          showPaging
          showSelect
          showIndex
          paging={{
            total: listData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          extra={
            <DeleteButton
              onClick={() => {
                openModal(selectedRowKeys as number[]);
              }}
            />
          }
        />
      </div>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={handleListDelete} isLoading={isDeleting} />
    </div>
  );
};

export default CS_Page;
