'use client';

import CustomButton from '@/components/common/CustomButton';
import DeleteButton from '@/components/common/DeleteButton';
import CustomTable from '@/components/common/table/CustomTable';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { ManagerBranchDTO } from '@/store/manager/branch/dto/manager-branch.dto';
import { managerBranchListParamToFilter, managerBranchRespToDto } from '@/store/manager/branch/manager-branch.service';
import { ManagerBranchListFilter, ManagerBranchUpdateREQ } from '@/store/manager/branch/request/manager-branch.request';

import ModalConfirm from '@/components/common/ConfirmModal';
import TableFilter from '@/components/common/table/TableFilter';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useModalConfirm } from '@/hook/useModalConfirm';
import useRouterHandler from '@/hook/useRouterHandler';
import {
  deleteManagerBranchApi,
  getManagerBranchListApi,
  managerBranchKeys,
  updateManagerBranchApi,
} from '@/store/manager/branch/manager-branch.api';
import { DEFAULT_BRANCHES_IDS } from '@/utils/constants/manager/branch-constant';
import { DELETE_TYPE } from '@/utils/enums/common.enums';
import { MANAGER_BRANCH_CONTENT } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { branchDataColumns } from '../data-columns/BranchDataColumns';
import BranchTableFilter from './BranchTableFilter';

const BranchList = () => {
  const { t: tBranch } = useTranslation('manager_branch');
  const { handleURLPush } = useRouterHandler();
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
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

  const { filter, handlePageChange, handleFilterChange, handleTableChange } = usePaging<ManagerBranchListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
  });

  const { data: managerBranchListData } = useQuery({
    queryKey: managerBranchKeys.list(filter),
    queryFn: () => getManagerBranchListApi({ ...filter, isDeleted: false }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(managerBranchRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdateBranch } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: ManagerBranchUpdateREQ }) => updateManagerBranchApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: managerBranchKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteBranch, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteManagerBranchApi(ids, DELETE_TYPE.SOFT),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: managerBranchKeys.lists() });
    },
    onError: handleError,
  });

  const onUpdateManagerBranch = (id: number, values: ManagerBranchUpdateREQ) => {
    mutateUpdateBranch({ id, body: values });
  };

  const onDeleteManagerBranch = () => {
    mutateDeleteBranch(parseArrayToStringRequest(selectedRowKeys));
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
          columns={branchDataColumns({ onUpdateManagerBranch })}
          data={managerBranchListData?.data || []}
          showSelect
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          showIndex
          getCheckboxProps={(record) => {
            return {
              disabled: DEFAULT_BRANCHES_IDS.includes(record.id),
            };
          }}
          extra={
            <div className='grid grid-cols-2 gap-[12px]'>
              {canEdit && (
                <DeleteButton
                  onClick={() => {
                    openModal(selectedRowKeys as number[]);
                  }}
                />
              )}
              <CustomButton onClick={() => handleURLPush('content', MANAGER_BRANCH_CONTENT.DELETE_LIST, false)}>
                {tBranch('button.deleted_list')}
              </CustomButton>
            </div>
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
    </>
  );
};

export default BranchList;
