import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { recipientRequirePopup } from '@/components/popup/RecipientRequirePopup';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { useAuthStore } from '@/store/auth/auth.store';
import { StockOrderListDTO } from '@/store/stock-order/dto/stock-order.dto';
import {
  StockOrderListFilter,
  StockOrderListREQ,
  StockOrderListUpdateREQ,
} from '@/store/stock-order/request/stock-order.request';
import {
  deleteStockOrderListApi,
  getStockOrderListApi,
  sendMessageStockOrderApi,
  stockOrderKeys,
  updateStockOrderListApi,
} from '@/store/stock-order/stock-order.api';
import { stockOrderRespToDto } from '@/store/stock-order/stock-order.service';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import StockTableDetailFilter from './components/StockTableDetailFilter';
import { StockListColumns } from './data-columns/StockListColumns';

const DEFAULT_DATE_RANGE: [number, number] = [
  dayjs().startOf('day').subtract(1, 'month').valueOf(),
  dayjs().endOf('day').valueOf(),
];

const StockList = () => {
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { mutateDownloadExcel } = useDownloadExcel<StockOrderListREQ>({
    fileName: EXCEL_FILE_NAME.STOCK_MANAGEMENT,
    url: EXCEL_URL.STOCK_MANAGEMENT,
  });
  const { selectedBranchId } = useAuthStore();

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<StockOrderListDTO>();

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = {
      filter: JSON.stringify({
        'funeralDate.gte': DEFAULT_DATE_RANGE[0],
        'funeralDate.lte': DEFAULT_DATE_RANGE[1],
      }),
    };
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<StockOrderListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const { data: listData } = useQuery({
    queryKey: stockOrderKeys.list(filter),
    queryFn: () => getStockOrderListApi(filter),
    select: ({ data }) => {
      return { ...data, data: data.data.map(stockOrderRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateListUpdate } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: StockOrderListUpdateREQ }) => updateStockOrderListApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: stockOrderKeys.lists() });
    },
    onError: handleError,
  });
  const handleListUpdate = (id: number, body: StockOrderListUpdateREQ) => {
    mutateListUpdate({ id, body });
  };

  const { mutate: mutateListDelete, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteStockOrderListApi(ids),
    onSuccess: () => {
      closeModal();
      setSelectedRowKeys([]);

      handleSuccess('delete');
      queryClient.invalidateQueries({ queryKey: stockOrderKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateSendStockMessages } = useMutation({
    mutationFn: (ids: number[]) => sendMessageStockOrderApi({ stockOrderIds: ids }),
    onSuccess: () => {
      handleSuccess('message');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: stockOrderKeys.lists() });
    },
    onError: handleError,
  });

  const handleListDelete = () => {
    mutateListDelete(parseArrayToStringRequest(selectedRowKeys));
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  const handleSendNoti = () => {
    if (selectedRowKeys.length === 0) {
      recipientRequirePopup();
      return;
    }
    mutateSendStockMessages(selectedRowKeys as number[]);
  };

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={listData?.total}>
          <StockTableDetailFilter
            onFilterChange={handleFilterChange}
            onDownloadExcel={onDownloadExcel}
            onSendNoti={handleSendNoti}
          />
        </TableFilter>
      </div>

      <div className='mt-4' id='fixedTable'>
        <CustomTable<StockOrderListDTO>
          key={JSON.stringify({ selectedBranchId })}
          columns={StockListColumns({ onListUpdate: handleListUpdate })}
          data={listData?.data || []}
          showPaging
          showSelect
          showIndex
          paging={{
            total: listData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          selectedRowKeys={selectedRowKeys}
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
    </>
  );
};

export default StockList;
