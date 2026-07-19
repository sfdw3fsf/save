'use client';
import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import CustomButton from '@/components/common/CustomButton';
import { CustomTabsFilterButton } from '@/components/common/CustomTabsFilterButton';
import DeleteButton from '@/components/common/DeleteButton';
import PageTitle from '@/components/common/page/PageTitle';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useBranch } from '@/hook/branch/useBranch';
import { useHospital } from '@/hook/collaborating-hospital/useHospital';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useUser } from '@/hook/user/useUser';
import { useSelectTable } from '@/hook/useSelectTable';
import {
  createHospitalDeliveryApi,
  deleteHospitalDeliveryApi,
  getHospitalDeliveryListApi,
  hospitalDeliveryKeys,
  updateHospitalDeliveryApi,
} from '@/store/collaborating-hospital/collaborating-hospital.api';
import {
  hospitalDeliveryListParamsToFilter,
  hospitalDeliveryRespToDto,
} from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalDeliveryDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import {
  HospitalDeliveryListFilter,
  HospitalDeliveryUpdateREQ,
} from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { ICONS } from '@/theme';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { ButtonTypeEnum } from '@/utils/enums/button-type.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { OptionItems } from '@/utils/types/option.type';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import dayjs from 'dayjs';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import CDPM_TableFilter from './components/CDPM_TableFilter';
import { CPHD_Columns } from './data-columns/CDPM_Columns';

const DeliveryPromotionalMaterialList = () => {
  const { t: tCommon } = useTranslation('common');
  const { t: tDeliveryPromotionalMaterial } = useTranslation('collaborating_delivery_promotional_material');
  const { branchOptions } = useBranch();
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { managerOptions } = useUser();
  const { hospitalOptions } = useHospital();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<HospitalDeliveryDTO>();
  const { mutateDownloadExcel } = useDownloadExcel<HospitalDeliveryListFilter>({
    fileName: EXCEL_FILE_NAME.HOSPITAL_PROMOTIONAL_MATERIALS,
    url: EXCEL_URL.HOSPITAL_PROMOTIONAL_MATERIALS,
  });

  const branchTabs: OptionItems<number | string> = useMemo(() => {
    if (!branchOptions) return [];
    return [{ label: tCommon('entire'), value: 'All' }, ...branchOptions];
  }, [branchOptions]);

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = hospitalDeliveryListParamsToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<HospitalDeliveryListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const { data: hospitalDeliveryListData } = useQuery({
    queryKey: hospitalDeliveryKeys.list(filter),
    queryFn: () => getHospitalDeliveryListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(hospitalDeliveryRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateCreateHospitalDelivery } = useMutation({
    mutationFn: () => createHospitalDeliveryApi(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: hospitalDeliveryKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateHospitalDelivery } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: HospitalDeliveryUpdateREQ }) => updateHospitalDeliveryApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: hospitalDeliveryKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteHospitalDelivery, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteHospitalDeliveryApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: hospitalDeliveryKeys.lists() });
    },
    onError: handleError,
  });

  const handleOpenSummaryTable = () => {
    const parsedFilter = JSON.parse(filter?.filter || '{}');
    const startDate = parsedFilter['requestedAt.gte'];
    const endDate = parsedFilter['requestedAt.lte'];
    window.open(
      `/collaborating/delivery-promotional-material/summary?startDate=${startDate}&endDate=${endDate}`,
      '_blank',
      'noopener,noreferrer',
    );
  };

  const onCreateHospitalDelivery = () => {
    mutateCreateHospitalDelivery();
  };

  const onChangeTab = (value: number | string) => {
    const newTableFilter: any = {};
    newTableFilter['hospital.hospitalBranches.some.branchId.in'] = value === 'All' ? '' : [value];

    handleFilterChange({ filter: JSON.stringify(newTableFilter) });
  };

  const onUpdateHospitalDelivery = (id: number, values: HospitalDeliveryUpdateREQ) => {
    mutateUpdateHospitalDelivery({ id, body: values });
  };

  const onDeleteHospitalDelivery = () => {
    mutateDeleteHospitalDelivery(parseArrayToStringRequest(selectedRowKeys));
  };

  const date = useMemo(() => {
    const parsedFilter = JSON.parse(filter?.filter || '{}');
    const startDate = parsedFilter['requestedAt.gte'];
    const endDate = parsedFilter['requestedAt.lte'];

    if (startDate && endDate) {
      return dayjs(startDate).format(DATE_FORMAT.DATE.DOT_SHORTEN) + '_' + dayjs(endDate).format(DATE_FORMAT.DATE.DOT_SHORTEN);
    }

    return '';
  }, [filter?.filter]);

  const onDownloadExcel = () => {
    const dynamicName = [EXCEL_FILE_NAME.HOSPITAL_PROMOTIONAL_MATERIALS, date].filter((i) => !!i).join('_');
    mutateDownloadExcel({
      filter,
      ids: parseArrayToStringRequest(selectedRowKeys, false),
      dynamicName,
    });
  };

  return (
    <>
      <PageTitle
        title={tDeliveryPromotionalMaterial('title')}
        actions={
          <>
            <AddButton onClick={onCreateHospitalDelivery} className='md:!block' />
            <CustomButton
              type={ButtonTypeEnum.GHOST}
              className='!hidden md:!inline-flex'
              onClick={() => handleOpenSummaryTable()}
              icon={<Image src={ICONS.LOOK_GREEN} width={20} height={20} alt='summary' />}>
              {tDeliveryPromotionalMaterial('button.sumary')}
            </CustomButton>
          </>
        }
      />
      <CustomTabsFilterButton classname='!justify-start my-4  md:flex' optionTabs={branchTabs} onChangeTabs={onChangeTab} />
      <div className='mt-6'>
        <TableFilter total={hospitalDeliveryListData?.total}>
          <CDPM_TableFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
        </TableFilter>
      </div>
      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<HospitalDeliveryDTO>
          key={JSON.stringify({ managerOptions, hospitalOptions })}
          columns={CPHD_Columns({ onUpdateHospitalDelivery, managerOptions, hospitalOptions })}
          data={hospitalDeliveryListData?.data || []}
          showSelect
          showIndex
          extra={
            <DeleteButton
              onClick={() => {
                openModal(selectedRowKeys as number[]);
              }}
            />
          }
          paging={{
            total: hospitalDeliveryListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteHospitalDelivery} isLoading={isDeleting} />
    </>
  );
};

export default DeliveryPromotionalMaterialList;
