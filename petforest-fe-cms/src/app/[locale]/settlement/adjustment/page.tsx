'use client';

import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import CustomButton from '@/components/common/CustomButton';
import DeleteButton from '@/components/common/DeleteButton';
import PageTitle from '@/components/common/page/PageTitle';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import TotalCard from '@/components/common/TotalCard';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { SettlementAdjustmentListDTO } from '@/store/settlement/dto/settlement.dto';
import {
  SettlementAdjustmentListFilter,
  SettlementAdjustmentListREQ,
  SettlementAdjustmentListUpdateREQ,
} from '@/store/settlement/request/settlement.request';
import {
  createSettlementAdjustmentListApi,
  deleteSettlementAdjustmentListApi,
  getSettlementAdjustmentListApi,
  getSettlementAdjustmentSummaryApi,
  settlementAdjustmentKeys,
  settlementAdjustmentSummaryKeys,
  updateSettlementAdjustmentListApi,
} from '@/store/settlement/settlement.api';
import {
  settlementAdjustmentListParamsToFilter,
  settlementAdjustmentListRespToDto,
  settlementAdjustmentSummaryRespToDto,
} from '@/store/settlement/settlement.service';
import { ICONS } from '@/theme';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { SettlementMethodType } from '@/utils/schemas/settlement/settlement.schema';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import SA_PaymentModal from './components/SA_PaymentModal';
import SA_TableFilter from './components/SA_TableFilter';
import SA_TableSummary from './components/SA_TableSummary';
import { SA_Columns } from './dataColumns/SA_Columns';

const page = () => {
  const { t: tSettlement } = useTranslation('settlement');

  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { mutateDownloadExcel } = useDownloadExcel<SettlementAdjustmentListREQ>({
    fileName: EXCEL_FILE_NAME.SETTLEMENT_MANAGEMENT,
    url: EXCEL_URL.SETTLEMENT_MANAGEMENT,
  });

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<SettlementAdjustmentListDTO>();

  const [selectedRecord, setSelectedRecord] = useState<SettlementAdjustmentListDTO>();

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = settlementAdjustmentListParamsToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<SettlementAdjustmentListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const { data: listData } = useQuery({
    queryKey: settlementAdjustmentKeys.list(filter),
    queryFn: () => getSettlementAdjustmentListApi(filter),
    select: ({ data }) => {
      return { ...data, data: data.data.map(settlementAdjustmentListRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const filterObject = JSON.parse(filter.filter || '{}');
  const startDate = filterObject['reservation.funeralInformation.funeralDate.gte'];
  const endDate = filterObject['reservation.funeralInformation.funeralDate.lte'];
  const reservationMethods = filterObject['reservationSettlements.some.reservationMethod.in'];

  const { data: summaryData } = useQuery({
    queryKey: [settlementAdjustmentSummaryKeys.all, startDate, endDate, reservationMethods],
    queryFn: () =>
      getSettlementAdjustmentSummaryApi({
        startDate: startDate?.toString(),
        endDate: endDate?.toString(),
        reservationMethods: reservationMethods,
      }),
    select: (response) => {
      return settlementAdjustmentSummaryRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateListUpdate, isPending: isUpdatingList } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettlementAdjustmentListUpdateREQ }) =>
      updateSettlementAdjustmentListApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      onRefetch();
    },
    onError: handleError,
  });

  const { mutate: mutateListDelete, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteSettlementAdjustmentListApi(ids),
    onSuccess: () => {
      closeModal();
      setSelectedRowKeys([]);

      handleSuccess('delete');
      onRefetch();
    },
    onError: handleError,
  });

  const { mutate: mutateListCreate } = useMutation({
    mutationFn: () => createSettlementAdjustmentListApi(),
    onSuccess: () => {
      handleSuccess('create');
      onRefetch();
    },
    onError: handleError,
  });

  const onListUpdate = (id: number, body: SettlementAdjustmentListUpdateREQ) => {
    mutateListUpdate({ id, body });
  };

  const onListDelete = () => {
    mutateListDelete(parseArrayToStringRequest(selectedRowKeys));
  };

  const onListCreate = async () => {
    mutateListCreate();
  };

  const onRefetch = () => {
    queryClient.invalidateQueries({ queryKey: settlementAdjustmentKeys.lists() });
    queryClient.invalidateQueries({ queryKey: settlementAdjustmentSummaryKeys.all });
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  return (
    <div>
      <PageTitle
        title={tSettlement('adjustment')}
        actions={
          <div className='flex gap-3 !h-[38px]'>
            <AddButton onClick={onListCreate} />
            <CustomButton
              onClick={() => {
                window.open('adjustment/additional-cost');
              }}
              type='ghost'
              icon={<Image src={ICONS.LOOK_GREEN} alt='look' />}
              className='flex-1 lg:flex-none'>
              {tSettlement('button.additional_cost')}
            </CustomButton>
          </div>
        }
      />

      <div className='inline-flex flex-wrap gap-3 mt-6'>
        <TotalCard title={tSettlement('card.total_number')} value={summaryData?.totalSettlements ?? 'N/A'} />
        <TotalCard title={tSettlement('card.total_amount')} value={summaryData?.totalAmount ?? 'N/A'} />
        <TotalCard title={tSettlement('card.total_receive')} value={summaryData?.totalRemainingAmount ?? 'N/A'} isRedNumber />
      </div>

      <div className='mt-6'>
        <TableFilter total={listData?.total}>
          <SA_TableFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
        </TableFilter>
      </div>

      <div className='mt-4' id='fixedTable'>
        <CustomTable<SettlementAdjustmentListDTO>
          columns={SA_Columns({
            onRecordSelect: setSelectedRecord,
            onListUpdate,
          })}
          data={listData?.data || []}
          showSelect
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
          extra={<DeleteButton onClick={() => openModal(selectedRowKeys as number[])} />}
          serialProps={{ fixed: 'left' }}
          summary={(data) => <SA_TableSummary data={data} />}
          getCheckboxProps={(record) => ({ disabled: !!record.funeralCode })}
        />
      </div>

      <SA_PaymentModal
        isOpen={selectedRecord !== undefined}
        onClose={() => setSelectedRecord(undefined)}
        isLoading={isUpdatingList}
        defaultValues={selectedRecord}
        onSubmit={(data: SettlementMethodType) => {
          onListUpdate(selectedRecord!.id, data);
          setSelectedRecord(undefined);
        }}
      />

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onListDelete} isLoading={isDeleting} />
    </div>
  );
};

export default page;
