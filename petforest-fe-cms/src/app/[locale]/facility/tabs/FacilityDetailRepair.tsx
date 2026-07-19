import AddButton from '@/components/common/AddButton';
import CenteredModal from '@/components/common/CenteredModal';
import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomTable from '@/components/common/table/CustomTable';
import { useFacility } from '@/hook/facility/useFacility';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useUser } from '@/hook/user/useUser';
import { useSelectTable } from '@/hook/useSelectTable';
import { FacilityDetailListDTO, FacilityHistoryFormDTO } from '@/store/facility/dto/facility.dto';
import {
  createFacilityMaintenanceHistoriesApi,
  deleteFacilityMaintenanceHistoriesApi,
  facilityKeys,
  getFacilityMaintenancesListApi,
} from '@/store/facility/facility.api';
import { facilityDetailListRespToDto, facilityDtoToCreateReq, facilityInitialFromValue } from '@/store/facility/facility.service';
import { FacilityHistoryCreateUpdateREQ, FacilityListFilter } from '@/store/facility/request/facility.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useEffect, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import FacilityHistoryDetailModal from '../components/FacilityHistoryDetailModal';
import FacilityTableDetailFilter from '../components/FacilityTableDetailFilter';
import { FacilityDetailRepairColumns } from '../data-columns/FacilityDetailRepairColumns';

type FacilityDetailRepairProps = {
  facilityId: number;
};

const FacilityDetailRepair = ({ facilityId }: FacilityDetailRepairProps) => {
  const { t: tCommon } = useTranslation('common');
  const { t: tFacility } = useTranslation('facility');

  const queryClient = useQueryClient();
  const facilityHistoryId = useRef<number>(0);
  const { managerOptions } = useUser();
  const { mutateDownloadExcel } = useDownloadExcel<FacilityListFilter>({
    fileName: EXCEL_FILE_NAME.FACILITY_MAINTAINANCE_DETAIL,
    url: `facilities/${facilityId}/maintenances/exports`,
  });

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<FacilityDetailListDTO>();

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);
  const methods = useForm<FacilityHistoryFormDTO>({ defaultValues: facilityInitialFromValue });
  const { handleSubmit, reset } = methods;

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<FacilityListFilter>({
    debounceTime: 500,
    isPushURL: false,
  });

  const { facilityDetailHistoryData, mutateUpdateFacilityHistory } = useFacility({
    facilityHistoryId: facilityHistoryId.current,
    queryKey: [facilityKeys.detail(facilityId), filter],
  });

  const { mutate: mutateCreateFacilityMaintenanceHistories } = useMutation({
    mutationFn: () => createFacilityMaintenanceHistoriesApi({ facilityId: facilityId }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [facilityKeys.detail(facilityId), filter] });
      handleSuccess('create');
      queryClient.invalidateQueries({ queryKey: facilityKeys.lists() });
    },
    onError: handleError,
  });

  const { data: facilityDetailListData } = useQuery({
    queryKey: [facilityKeys.detail(facilityId), filter],
    queryFn: () => getFacilityMaintenancesListApi(facilityId, filter),
    select: (response) => {
      return response?.data.data && { ...response?.data, data: response.data.data.map(facilityDetailListRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDeleteFacilityMaintenances, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteFacilityMaintenanceHistoriesApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: [facilityKeys.detail(facilityId), filter] });
      queryClient.invalidateQueries({ queryKey: facilityKeys.lists() });
    },
    onError: handleError,
  });

  const onUpdateFacilityMaintenances = (id: number, values: FacilityHistoryCreateUpdateREQ) => {
    mutateUpdateFacilityHistory({ id, body: { facilityId: facilityId, ...values } });
  };

  const onDetail = (id: number) => {
    facilityHistoryId.current = id;
    setIsOpenDetail(true);
  };

  const onClose = () => {
    setIsOpenDetail(false);
  };

  const onSubmit = (data: FacilityHistoryFormDTO) => {
    mutateUpdateFacilityHistory({ id: facilityHistoryId.current, body: facilityDtoToCreateReq(data) });
  };

  const onCreate = () => {
    mutateCreateFacilityMaintenanceHistories();
  };

  const onDeleteFacilityMaintenances = () => {
    mutateDeleteFacilityMaintenances(parseArrayToStringRequest(selectedRowKeys));
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  useEffect(() => {
    reset(facilityDetailHistoryData || facilityInitialFromValue);
  }, [facilityDetailHistoryData, reset]);

  return (
    <div className='mt-6'>
      <div className='flex gap-3'>
        <DeleteButton
          onClick={() => {
            openModal(selectedRowKeys as number[]);
          }}
        />
        <AddButton onClick={onCreate} />
      </div>

      <div className='mt-6'>
        <FacilityTableDetailFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
      </div>

      <div className='text-neutral-80 mt-4'>{`${tCommon('total')} ${facilityDetailListData?.total || 0}${tCommon('number')}`}</div>

      <div className='mb-6 mt-4'>
        <CustomTable<FacilityDetailListDTO>
          columns={FacilityDetailRepairColumns({ onDetail, onUpdateFacilityMaintenances, managerOptions })}
          data={facilityDetailListData?.data || []}
          key={JSON.stringify({ managerOptions, facilityDetailListData })}
          showSelect
          sticky
          showPaging
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          paging={{
            total: facilityDetailListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
        />
      </div>
      <FormProvider {...methods}>
        <form>
          <CenteredModal isOpen={isOpenDetail} onCancel={onClose} onOk={handleSubmit(onSubmit)} title={tFacility('tabs.repair')}>
            <FacilityHistoryDetailModal canEditFacilityType={false} />
          </CenteredModal>
        </form>
      </FormProvider>
      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteFacilityMaintenances} isLoading={isDeleting} />
    </div>
  );
};

export default FacilityDetailRepair;
