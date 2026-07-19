import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import CustomButton from '@/components/common/CustomButton';
import PageTitle from '@/components/common/page/PageTitle';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useBranch } from '@/hook/branch/useBranch';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { ManagerUserDTO } from '@/store/manager/user/dto/manager-user.dto';
import {
  createManagerUserApi,
  deleteManagerUserApi,
  getManagerUserListApi,
  managerUserKeys,
  managerUserRetiressKeys,
  updateManagerUserApi,
  updateManagerUserBranchesApi,
  updateManagerUserMainBranchApi,
  updateManagerUserPermissionApi,
} from '@/store/manager/user/manager-user-api';
import {
  managerUserCreateToReq,
  managerUserListParamToFilter,
  managerUserRespToDto,
  userFilterKeyMapping,
} from '@/store/manager/user/manager-user.service';
import {
  ManagerUserListFilter,
  ManagerUserUpdatePermissionREQ,
  ManagerUserUpdateREQ,
} from '@/store/manager/user/request/manager-user.request';
import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { DELETE_TYPE } from '@/utils/enums/common.enums';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { InboxOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { UserDataColumns } from '../data-columns/UserDataColumns';

type UserListProps = {
  onSetContent: () => void;
};

const UserList = ({ onSetContent }: UserListProps) => {
  const { t: tUser } = useTranslation('manager_user');

  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { branchOptions } = useBranch();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<ManagerUserDTO>();
  const { canEdit } = useMenuPermission(MenuKeys.MANAGER_USER);

  const { initialPaging, initialFilter } = useMemo(() => {
    const initialFilter = managerUserListParamToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialPaging, initialFilter };
  }, []);

  const { filter, handlePageChange, handleTableChange } = usePaging<ManagerUserListFilter>({
    initialPaging,
    initialFilter,
    debounceTime: 500,
    filterKeyMapping: userFilterKeyMapping(),
  });

  const { data: managerUserListData } = useQuery({
    queryKey: managerUserKeys.list(filter),
    queryFn: () => getManagerUserListApi({ ...filter, isDeleted: false }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(managerUserRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateCreateUser } = useMutation({
    mutationFn: () => createManagerUserApi(managerUserCreateToReq()),
    onSuccess: () => {
      handleSuccess('create');
      queryClient.invalidateQueries({ queryKey: managerUserKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateUser } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: ManagerUserUpdateREQ }) => updateManagerUserApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: managerUserKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateUserPermission } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: ManagerUserUpdatePermissionREQ }) => updateManagerUserPermissionApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: managerUserKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateUserBranches } = useMutation({
    mutationFn: ({ id, ids }: { id: number; ids: string }) => updateManagerUserBranchesApi(id, ids),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: managerUserKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateUserMainBranch } = useMutation({
    mutationFn: ({ id, branchId }: { id: number; branchId: number }) => updateManagerUserMainBranchApi(id, branchId),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: managerUserKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateRegisnateUser, isPending: isResignating } = useMutation({
    mutationFn: (ids: string) => deleteManagerUserApi(ids, DELETE_TYPE.SOFT),
    onSuccess: () => {
      closeModal();
      setSelectedRowKeys([]);
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: managerUserRetiressKeys.lists() });
      queryClient.invalidateQueries({ queryKey: managerUserKeys.lists() });
    },
    onError: handleError,
  });

  const onCreateUser = () => {
    mutateCreateUser();
  };

  const onUpdateManagerUser = (id: number, values: ManagerUserUpdateREQ) => {
    mutateUpdateUser({ id, body: values });
  };

  const onUpdateManagerUserPermission = (id: number, values: ManagerUserUpdatePermissionREQ) => {
    mutateUpdateUserPermission({ id, body: values });
  };

  const onUpdateManagerUserBranches = (id: number, ids: number[]) => {
    mutateUpdateUserBranches({ id, ids: parseArrayToStringRequest(ids) });
  };

  const onUpdateManagerUserMainBranch = (id: number, branchId: number) => {
    mutateUpdateUserMainBranch({ id, branchId });
  };

  const onResignateManagerUser = () => {
    mutateRegisnateUser(parseArrayToStringRequest(selectedRowKeys));
  };

  return (
    <div>
      <PageTitle title={tUser('user')} actions={canEdit && <AddButton onClick={onCreateUser} />} />

      <div className='mt-4'>
        <TableFilter total={managerUserListData?.total}></TableFilter>
      </div>

      <div className='mt-4' id='fixedTable'>
        <CustomTable<ManagerUserDTO>
          key={JSON.stringify({ branchOptions })}
          leftHeight={100}
          columns={UserDataColumns({
            branchOptions,
            onUpdateManagerUser,
            onUpdateManagerUserPermission,
            onUpdateManagerUserBranches,
            onUpdateManagerUserMainBranch,
          })}
          data={managerUserListData?.data || []}
          showSelect
          showIndex
          extra={
            <div className='grid grid-cols-2 gap-[12px]'>
              {canEdit && (
                <CustomButton
                  onClick={() => {
                    openModal(selectedRowKeys as number[]);
                  }}>
                  {tUser('button.resignation')}
                </CustomButton>
              )}
              <CustomButton onClick={onSetContent} type='ghost' icon={<InboxOutlined />}>
                {tUser('button.resign_list')}
              </CustomButton>
            </div>
          }
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          showPaging
          paging={{
            total: managerUserListData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          rowClassName={(record) => (record.status === ACTIVE_STATE.INACTIVE ? 'disabled-row-20 disabled-text' : '')}
        />
      </div>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onResignateManagerUser} isLoading={isResignating} />
    </div>
  );
};

export default UserList;
