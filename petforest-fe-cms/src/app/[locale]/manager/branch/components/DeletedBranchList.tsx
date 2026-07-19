'use client';

import CustomButton from '@/components/common/CustomButton';
import CustomTable from '@/components/common/table/CustomTable';
import { ManagerBranchDTO } from '@/store/manager/branch/dto/manager-branch.dto';
import { ManagerBranchListFilter } from '@/store/manager/branch/request/manager-branch.request';

import ModalConfirm from '@/components/common/ConfirmModal';
import TableFilter from '@/components/common/table/TableFilter';
import { recipientRequirePopup } from '@/components/popup/RecipientRequirePopup';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import {
  deleteManagerBranchApi,
  getManagerBranchListApi,
  managerBranchKeys,
  restoreManagerBranchApi,
} from '@/store/manager/branch/manager-branch.api';
import { managerBranchListParamToFilter, managerBranchRespToDto } from '@/store/manager/branch/manager-branch.service';
import { DELETE_TYPE } from '@/utils/enums/common.enums';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { DeleteOutlined, UndoOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { BranchDeletedDataColumns } from '../data-columns/BranchDeletedDataColumns';
import BranchTableFilter from './BranchTableFilter';

const DeletedBranchList = () => {
  const { t: tCommon } = useTranslation('common');

  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const {
    isOpen: isOpenRestore,
    setIsOpen: setIsOpenRestore,
    openModal: openModalRestore,
    closeModal: closeModalRestore,
  } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<ManagerBranchDTO>();
  const { canEdit } = useMenuPermission(MenuKeys.MANAGER_BRANCH);

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = managerBranchListParamToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleTableChange, handleFilterChange } = usePaging<ManagerBranchListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const { mutate: mutateRestoreBranch, isPending: isRestoring } = useMutation({
    mutationFn: (ids: string) => restoreManagerBranchApi(ids),
    onSuccess: () => {
      closeModalRestore();
      handleSuccess('update');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: managerBranchKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteBranch, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteManagerBranchApi(ids, DELETE_TYPE.HARD),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: managerBranchKeys.lists() });
    },
    onError: handleError,
  });

  const { data: managerBranchListData } = useQuery({
    queryKey: managerBranchKeys.list(filter),
    queryFn: () => getManagerBranchListApi({ ...filter, isDeleted: true }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(managerBranchRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const onDeleteManagerBranch = () => {
    if (selectedRowKeys.length <= 0) {
      recipientRequirePopup();
    } else {
      mutateDeleteBranch(parseArrayToStringRequest(selectedRowKeys));
    }
  };

  const onRestoreManagerBranch = () => {
    mutateRestoreBranch(parseArrayToStringRequest(selectedRowKeys));
  };

  return (
    <>
      <div className='mt-4'>
        <TableFilter total={managerBranchListData?.total}>
          <BranchTableFilter onFilterChange={handleFilterChange} />
        </TableFilter>
      </div>
      <div className='mt-4' id='fixedTable'>
        <CustomTable<ManagerBranchDTO>
          columns={BranchDeletedDataColumns()}
          data={managerBranchListData?.data || []}
          showSelect
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          showIndex
          extra={
            canEdit && (
              <div className='grid grid-cols-2 gap-[12px]'>
                <CustomButton
                  onClick={() => {
                    openModalRestore(selectedRowKeys as number[]);
                  }}
                  icon={<UndoOutlined />}>
                  {tCommon('button.restore')}
                </CustomButton>
                <CustomButton
                  onClick={() => {
                    openModal(selectedRowKeys as number[]);
                  }}
                  icon={<DeleteOutlined />}>
                  {tCommon('button.permanent_delete')}
                </CustomButton>
              </div>
            )
          }
          paging={{
            total: managerBranchListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
        />
      </div>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteManagerBranch} isLoading={isDeleting} />

      <ModalConfirm
        message={tCommon('message.restore')}
        isOpen={isOpenRestore}
        setIsOpen={setIsOpenRestore}
        onConfirm={onRestoreManagerBranch}
        isLoading={isRestoring}
      />
    </>
  );
};

export default DeletedBranchList;
