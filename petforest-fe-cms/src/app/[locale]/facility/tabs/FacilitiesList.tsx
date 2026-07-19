import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomModal from '@/components/common/modals/CustomModal';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { FacilityDetailDTO, FacilityListDTO } from '@/store/facility/dto/facility.dto';
import {
  deleteFacilityListApi,
  facilityKeys,
  getDetailFacilityApi,
  getFacilityCompanyNamesApi,
  getFacilityListApi,
  updateFacilityListApi,
} from '@/store/facility/facility.api';
import {
  facilityCompanyNamesDtoToOptions,
  facilityDetailDtoToREQ,
  facilityDetailIntialFormValue,
  facilityDetailRespToDto,
  facilityListParamsToFilter,
  facilityRespToDto,
} from '@/store/facility/facility.service';
import { FacilityListFilter, FacilityListREQ, FacilityListUpdateREQ } from '@/store/facility/request/facility.request';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { facilitySchema } from '@/utils/schemas/facility/facility.schema';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { yupResolver } from '@hookform/resolvers/yup';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import FacilityDetailModal from '../components/FacilityDetailModal';
import FacilityTableFilter from '../components/FacilityTableFilter';
import { FacilityListColumns } from '../data-columns/FacilityListColumns';

const FacilityList = () => {
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { mutateDownloadExcel } = useDownloadExcel<FacilityListREQ>({
    fileName: EXCEL_FILE_NAME.FACILITY_MANAGEMENT,
    url: EXCEL_URL.FACILITY_MANAGEMENT,
  });

  const facilityId = useRef<number>(0);

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<FacilityListDTO>();

  const methods = useForm<FacilityDetailDTO>({
    defaultValues: facilityDetailIntialFormValue,
    resolver: yupResolver(facilitySchema) as any,
    context: {
      isOptionalEmail: true,
    },
  });
  const { handleSubmit, reset, watch, setValue } = methods;

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);
  const [isHidingFooter, setIsHiddingFooter] = useState<boolean>(true);

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = facilityListParamsToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<FacilityListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const { data: facilityCompanyNamesData } = useQuery({
    queryKey: facilityKeys.lists(),
    queryFn: () => getFacilityCompanyNamesApi(),
    select: (response) => {
      return { ...response.data };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDeleteFacilityList, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteFacilityListApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: facilityKeys.lists() });
    },
    onError: handleError,
  });

  const onDeleteFacilityList = () => {
    mutateDeleteFacilityList(parseArrayToStringRequest(selectedRowKeys));
  };

  const companyOptions = useMemo(() => {
    if (!facilityCompanyNamesData) return [];
    return facilityCompanyNamesDtoToOptions(facilityCompanyNamesData?.data);
  }, [facilityCompanyNamesData]);

  const { data: facilityListData } = useQuery({
    queryKey: facilityKeys.list(filter),
    queryFn: () => getFacilityListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(facilityRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: facilityDetailData } = useQuery({
    queryKey: facilityKeys.detail(facilityId.current),
    queryFn: () => getDetailFacilityApi(facilityId.current),
    select: (response) => {
      return response?.data && facilityDetailRespToDto(response?.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdateFacilityList } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: FacilityListUpdateREQ }) => updateFacilityListApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: facilityKeys.lists() });
    },
    onError: handleError,
  });

  const onUpdateFacilityList = (id: number, values: FacilityListUpdateREQ) => {
    mutateUpdateFacilityList({ id, body: values });
  };

  const onDetail = (id: number) => {
    facilityId.current = id;
    setIsOpenDetail(true);
  };

  const onSubmit = (data: FacilityDetailDTO) => {
    onUpdateFacilityList(facilityId.current, facilityDetailDtoToREQ(data));
    setIsOpenDetail(true);
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  useEffect(() => {
    reset(facilityDetailData || facilityDetailIntialFormValue);
  }, [facilityDetailData, reset]);

  return (
    <div className='mt-9'>
      <div className='mt-6'>
        <TableFilter total={facilityListData?.total}>
          <FacilityTableFilter filter={filter} onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
        </TableFilter>
      </div>

      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<FacilityListDTO>
          columns={FacilityListColumns({ onDetail, companyOptions, onUpdateFacilityList })}
          data={facilityListData?.data || []}
          showPaging
          showSelect
          paging={{
            total: facilityListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          key={JSON.stringify({ facilityListData, companyOptions })}
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
      <FormProvider {...methods}>
        <form>
          <CustomModal
            isOpen={isOpenDetail}
            onClose={() => {
              setIsOpenDetail(false);
            }}
            onCancel={() => {
              setIsOpenDetail(false);
            }}
            onOk={isHidingFooter ? undefined : handleSubmit(onSubmit)}
            title={facilityDetailData?.type || ''}>
            <FacilityDetailModal facilityId={facilityId.current} setIsHiddingFooter={setIsHiddingFooter} />
          </CustomModal>
        </form>
      </FormProvider>
      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteFacilityList} isLoading={isDeleting} />
    </div>
  );
};

export default FacilityList;
