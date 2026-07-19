import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomTable from '@/components/common/table/CustomTable';
import TotalCard, { ValueTotalCardProps } from '@/components/common/TotalCard';
import { useHospitalSettlementMethod } from '@/hook/setting/useHospitalSettlementMethod';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import {
  createHospitalSettlementApi,
  deleteHospitalSettlementApi,
  getHospitalDetailSettlementApi,
  getHospitalDetailSettlemenTotaltApi,
  hospitalDetailSettlementKeys,
  hospitalDetailSettlementTotalKeys,
  updateHospitalSettlementApi,
} from '@/store/collaborating-hospital/collaborating-hospital.api';
import {
  hospitalDetailSettlementRespToDto,
  hospitalDetailSettlementTotalRespToDto,
} from '@/store/collaborating-hospital/collaborating-hospital.service';
import { HospitalDetailSettlementDTO } from '@/store/collaborating-hospital/dto/collaborating-hospital.dto';
import {
  HospitalDetailSettlementCreateREQ,
  HospitalDetailSettlementListFilter,
  HospitalDetailSettlementUpdateREQ,
} from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import CPH_SettlementFilter from '../components/CPH_SettlementFilter';
import { CPH_SettlementDetailColumns } from '../data-columns/CPHD_SettlementDetailColumns';

type CPHD_SettlementDetailProps = {
  id: number;
};
export const CPHD_SettlementDetail = ({ id }: CPHD_SettlementDetailProps) => {
  const { t: tCommon } = useTranslation('common');
  const { t: tPartnerHospital } = useTranslation('collaborating_partner_hospital');
  const { settlementMethodOptions } = useHospitalSettlementMethod();

  const queryClient = useQueryClient();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();

  const { initialPaging } = useMemo(() => {
    const initialPaging: PagingState = {
      pageSize: initialPagingState.pageSize,
      pageNumber: initialPagingState.pageNumber,
    };
    return { initialPaging };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<HospitalDetailSettlementListFilter>({
    initialPaging,
    debounceTime: 500,
    isPushURL: false,
  });
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<HospitalDetailSettlementDTO>();

  const { data: hospitalDetailSettlementData } = useQuery({
    queryKey: hospitalDetailSettlementKeys.list(filter),
    queryFn: () => getHospitalDetailSettlementApi(id, filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(hospitalDetailSettlementRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: hospitalDetailSettlementTotal } = useQuery({
    queryKey: hospitalDetailSettlementTotalKeys.list(filter),
    queryFn: () => getHospitalDetailSettlemenTotaltApi(id),
    select: (response) => {
      return hospitalDetailSettlementTotalRespToDto(response.data.data, tPartnerHospital);
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateCreateHospitalSettlement } = useMutation({
    mutationFn: (body: HospitalDetailSettlementCreateREQ) => createHospitalSettlementApi(body),
    onSuccess: () => {
      handleSuccess('create');
      queryClient.invalidateQueries({ queryKey: hospitalDetailSettlementKeys.all });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateHospitalSettlement } = useMutation({
    mutationFn: ({ settlementId, body }: { settlementId: number; body: HospitalDetailSettlementUpdateREQ }) =>
      updateHospitalSettlementApi(settlementId, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: hospitalDetailSettlementKeys.all });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteHospitalSettlement, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteHospitalSettlementApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: hospitalDetailSettlementKeys.all });
    },
    onError: handleError,
  });

  const onCreateHospitalSettlement = () => {
    mutateCreateHospitalSettlement({ hospitalId: id });
  };

  const onDeleteHospitalSettlement = () => {
    mutateDeleteHospitalSettlement(parseArrayToStringRequest(selectedRowKeys));
  };

  const onUpdateHospitalDetailSettlement = (settlementId: number, values: HospitalDetailSettlementUpdateREQ) => {
    mutateUpdateHospitalSettlement({ settlementId, body: { ...values, hospitalId: id } });
  };

  const mockDataCount: ValueTotalCardProps[] = [
    { text: tPartnerHospital('detail.case'), count: 12 },
    { text: tPartnerHospital('detail.case'), count: 20 },
  ];

  const mockDataCountCard = [
    { title: '운영지원금', value: mockDataCount },
    { title: '현장할인', value: mockDataCount },
    { title: '마일리지', value: mockDataCount },
    { title: '용품찬조', value: mockDataCount },
  ];

  return (
    <div className='mt-6'>
      <div className='grid grid-cols-2 md:grid-cols-3 gap-3'>
        {hospitalDetailSettlementTotal?.map((item, index) => (
          <TotalCard
            key={index}
            className='!w-full'
            textClassName='!text-[15px]'
            title={item.title}
            value={item.value}
            separator='/'
          />
        ))}
      </div>
      <div className='flex gap-3 mt-6'>
        <DeleteButton
          onClick={() => {
            openModal(selectedRowKeys as number[]);
          }}
        />
        <AddButton onClick={onCreateHospitalSettlement} />
      </div>

      <div className='flex gap-4 flex-col mt-6'>
        <CPH_SettlementFilter onFilterChange={handleFilterChange} onDownloadExcel={() => {}} />
        <div className='text-neutral-80 mt-4'>{`${tCommon('total')} ${hospitalDetailSettlementData?.total || 0}${tCommon('number')}`}</div>
        <CustomTable<HospitalDetailSettlementDTO>
          columns={CPH_SettlementDetailColumns({ onUpdateHospitalDetailSettlement, settlementMethodOptions, id })}
          data={hospitalDetailSettlementData?.data || []}
          showPaging
          showSelect
          paging={{
            total: hospitalDetailSettlementData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          getCheckboxProps={(record) => {
            return {
              disabled: !record.canTakeAction,
            };
          }}
        />
      </div>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteHospitalSettlement} isLoading={isDeleting} />
    </div>
  );
};
