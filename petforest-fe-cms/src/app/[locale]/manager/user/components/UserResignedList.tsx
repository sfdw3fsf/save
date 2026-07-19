import ModalConfirm from '@/components/common/ConfirmModal';
import CustomButton from '@/components/common/CustomButton';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import CustomText from '@/components/common/Text';
import { useBranch } from '@/hook/branch/useBranch';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { ManagerUserRetireesDTO } from '@/store/manager/user/dto/manager-user.dto';
import {
  deleteManagerUserApi,
  getManagerUserListApi,
  managerUserKeys,
  managerUserRetiressKeys,
  restoreManagerUserApi,
} from '@/store/manager/user/manager-user-api';
import { managerUserListParamToFilter, managerUserRetireesRespToDto } from '@/store/manager/user/manager-user.service';
import { ManagerUserListFilter } from '@/store/manager/user/request/manager-user.request';
import { COLORS } from '@/theme';
import { DELETE_TYPE } from '@/utils/enums/common.enums';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { ArrowLeftOutlined, DeleteOutlined, UndoOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { UserRetireesDataColumns } from '../data-columns/UserRetireesDataColumns';

type UserResignedListProps = {
  onSetContent: () => void;
};
const UserResignedList = ({ onSetContent }: UserResignedListProps) => {
  const { t: tUser } = useTranslation('manager_user');
  const { t: tCommon } = useTranslation('common');

  const searchParams = useSearchParams();
  const { branchOptions } = useBranch();
  const queryClient = useQueryClient();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<ManagerUserRetireesDTO>();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const {
    isOpen: isOpenRestore,
    setIsOpen: setIsOpenRestore,
    openModal: openModalRestore,
    closeModal: closeModalRestore,
  } = useModalConfirm();

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
  });

  const { data: managerUserRetireesListData } = useQuery({
    queryKey: managerUserRetiressKeys.list(filter),
    queryFn: () => getManagerUserListApi({ ...filter, isDeleted: true }),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(managerUserRetireesRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateRestoreUser, isPending: isRestoring } = useMutation({
    mutationFn: (ids: string) => restoreManagerUserApi(ids),
    onSuccess: () => {
      closeModalRestore();
      setSelectedRowKeys([]);
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: managerUserKeys.lists() });
      queryClient.invalidateQueries({ queryKey: managerUserRetiressKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteUser, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteManagerUserApi(ids, DELETE_TYPE.HARD),
    onSuccess: () => {
      closeModal();
      setSelectedRowKeys([]);
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: managerUserKeys.lists() });
      queryClient.invalidateQueries({ queryKey: managerUserRetiressKeys.lists() });
    },
    onError: handleError,
  });

  const onRestoreManagerUser = () => {
    mutateRestoreUser(parseArrayToStringRequest(selectedRowKeys as number[]));
  };

  const onDeleteManagerUser = () => {
    mutateDeleteUser(parseArrayToStringRequest(selectedRowKeys as number[]));
  };

  return (
    <div>
      <div onClick={onSetContent} className='cursor-pointer flex items-center gap-2'>
        <ArrowLeftOutlined style={{ fontSize: '16px', color: COLORS.neutral[100] }} />
        <CustomText variant='title-3'>{tUser('resign_list')}</CustomText>
      </div>

      <div className='mt-4'>
        <TableFilter total={managerUserRetireesListData?.total}></TableFilter>
      </div>

      <div className='mt-4' id='fixedTable'>
        <CustomTable<ManagerUserRetireesDTO>
          columns={UserRetireesDataColumns({ branchOptions })}
          data={managerUserRetireesListData?.data || []}
          showSelect
          showIndex
          extra={
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
          }
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteManagerUser} isLoading={isDeleting} />

      <ModalConfirm
        message={tCommon('message.restore')}
        isOpen={isOpenRestore}
        setIsOpen={setIsOpenRestore}
        onConfirm={onRestoreManagerUser}
        isLoading={isRestoring}
      />
    </div>
  );
};

export default UserResignedList;
