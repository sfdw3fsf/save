import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomModal from '@/components/common/modals/CustomModal';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useFacility } from '@/hook/facility/useFacility';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useUser } from '@/hook/user/useUser';
import { useSelectTable } from '@/hook/useSelectTable';
import { FacilityHistoryFormDTO, FacilityHistoryListDTO } from '@/store/facility/dto/facility.dto';
import {
  createFacilityMaintenanceHistoriesApi,
  deleteFacilityMaintenanceHistoriesApi,
  facilityHistoryKeys,
  getFacilityMaintenanceHistoriesApi,
} from '@/store/facility/facility.api';
import {
  facilityDtoToCreateReq,
  facilityHistoryListParamToFilter,
  facilityHistoryRespToDto,
  facilityInitialFromValue,
} from '@/store/facility/facility.service';
import { FacilityHistoryCreateUpdateREQ, FacilityListFilter, FacilityListREQ } from '@/store/facility/request/facility.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { useUploadFile } from '@/utils/utils/upload-file-to-s3.util';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useRef } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import FacilityHistoryDetailModal from '../components/FacilityHistoryDetailModal';
import FacilityHistoryFilter from '../components/FacilityHistoryFilter';
import { FacilityHistoryColumns } from '../data-columns/FacilityHistoryColumns';

type FacilityHistoryProps = {
  isOpenDetail: boolean;
  setIsOpenDetail: (value: boolean) => void;
};

const FacilityHistory = ({ isOpenDetail, setIsOpenDetail }: FacilityHistoryProps) => {
  const { t: tCommon } = useTranslation('common');
  const { t: tFacility } = useTranslation('facility');
  const { managerOptions } = useUser();
  const { uploadFile } = useUploadFile();

  const queryClient = useQueryClient();
  const searchParams = useSearchParams();
  const { mutateDownloadExcel } = useDownloadExcel<FacilityListREQ>({
    fileName: EXCEL_FILE_NAME.FACILITY_MAINTAINANCE,
    url: EXCEL_URL.FACILITY_MAINTAINANCE,
  });

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<FacilityHistoryListDTO>();

  const facilityHistoryId = useRef<number>(0);
  const { companyOptions, facilityDetailHistoryData, mutateUpdateFacilityHistory } = useFacility({
    facilityHistoryId: facilityHistoryId.current,
    queryKey: facilityHistoryKeys.all,
  });

  const methods = useForm<FacilityHistoryFormDTO>({ defaultValues: facilityInitialFromValue });
  const { handleSubmit, reset, watch } = methods;

  const companyName = useRef<string>('');
  const companyManagerValue = watch('companyName');

  const { initialFilter } = useMemo(() => {
    const initialFilter = facilityHistoryListParamToFilter(searchParams);
    return { initialFilter };
  }, []);

  const { filter, handleFilterChange, handleTableChange } = usePaging<FacilityListFilter>({
    initialFilter,
    debounceTime: 500,
  });

  const { mutate: mutateCreateFacility } = useMutation({
    mutationFn: ({ body, photoIds }: { body: FacilityHistoryFormDTO; photoIds: number[] }) =>
      createFacilityMaintenanceHistoriesApi(facilityDtoToCreateReq(body, photoIds)),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: facilityHistoryKeys.lists() });
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { data: facilityHistoryData } = useQuery({
    queryKey: facilityHistoryKeys.list(filter),
    queryFn: () => getFacilityMaintenanceHistoriesApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(facilityHistoryRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDeleteFacilityHistory, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteFacilityMaintenanceHistoriesApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: facilityHistoryKeys.lists() });
    },
    onError: handleError,
  });

  const onUpdateFacilityHistory = (id: number, values: FacilityHistoryCreateUpdateREQ) => {
    mutateUpdateFacilityHistory({ id, body: values });
  };

  const onDeleteFacilityHistory = () => {
    mutateDeleteFacilityHistory(parseArrayToStringRequest(selectedRowKeys));
  };

  const onSubmit = async (data: FacilityHistoryFormDTO) => {
    if (facilityHistoryId.current === 0) {
      const maintenanceImageIds = data.maintenanceImages && ((await uploadFile({ files: data.maintenanceImages })) as number[]);
      mutateCreateFacility({ body: data, photoIds: maintenanceImageIds });
    } else {
      const maintenanceImageIds = data.maintenanceImages && ((await uploadFile({ files: data.maintenanceImages })) as number[]);
      mutateUpdateFacilityHistory({ id: facilityHistoryId.current, body: facilityDtoToCreateReq(data, maintenanceImageIds) });
    }
  };

  const onDetail = (id: number) => {
    facilityHistoryId.current = id;
    setIsOpenDetail(true);
  };

  const onClose = () => {
    setIsOpenDetail(false);
    facilityHistoryId.current = 0;
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  useEffect(() => {
    companyName.current = companyManagerValue;
  }, [companyManagerValue]);

  useEffect(() => {
    reset(facilityDetailHistoryData || facilityInitialFromValue);
  }, [facilityDetailHistoryData, reset]);

  return (
    <div className='mt-9'>
      <div className='mt-6'>
        <TableFilter total={facilityHistoryData?.total}>
          <FacilityHistoryFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
        </TableFilter>
      </div>

      <div className='mt-4 facilityTable' id='fixedTable'>
        <CustomTable<FacilityHistoryListDTO>
          columns={FacilityHistoryColumns({
            onDetail,
            companyOptions,
            managerOptions,
            onUpdateFacilityHistory,
          })}
          data={facilityHistoryData?.data || []}
          key={JSON.stringify({ companyOptions, managerOptions })}
          showPaging={false}
          showSelect
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
      <FormProvider {...methods}>
        <form>
          <CustomModal
            isOpen={isOpenDetail}
            onClose={onClose}
            onCancel={onClose}
            onOk={handleSubmit(onSubmit)}
            title={tFacility('tabs.repair')}>
            <FacilityHistoryDetailModal canEditFacilityType={true} />
          </CustomModal>
        </form>
      </FormProvider>
      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteFacilityHistory} isLoading={isDeleting} />
    </div>
  );
};

export default FacilityHistory;
