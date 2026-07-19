import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import SearchInput from '@/components/common/SearchInput';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useSettingWaitingRoom } from '@/hook/setting/useSettingWaitingRoom';
import { useArrangeOrdinal } from '@/hook/useArrangeOrdinal';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { SettingWaitingRoomDTO } from '@/store/setting/waiting-room/dto/setting-waiting-room.dto';
import {
  SettingWaitingRoomListFilter,
  SettingWaitingRoomUpdateREQ,
} from '@/store/setting/waiting-room/request/setting-waiting-room.request';
import {
  deleteWaitingRoomListApi,
  settingWaitingRoomKeys,
  updateWaitingRoomApi,
} from '@/store/setting/waiting-room/setting-waiting-room.api';
import { settingWaitingRoomListParamToFilter } from '@/store/setting/waiting-room/setting-waiting-room.service';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { WaitingRoomColumns } from '../data-columns/WaitingRoomColumns';

export const WaitingRoomList = () => {
  const searchParams = useSearchParams();
  const { arrangeOrdinal } = useArrangeOrdinal({ url: 'waiting-rooms', queryKey: settingWaitingRoomKeys.lists() });
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const queryClient = useQueryClient();

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<SettingWaitingRoomDTO>();

  const { initialFilter } = useMemo(() => {
    const initialFilter = settingWaitingRoomListParamToFilter(searchParams);
    return { initialFilter };
  }, []);

  const { filter, handleFilterChange, resetFilterPaging } = usePaging<SettingWaitingRoomListFilter>({
    initialFilter,
    debounceTime: 500,
  });

  const { settingWaitingRoomData } = useSettingWaitingRoom({ filter });

  const { mutate: mutateUpdateWaitingRoom } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingWaitingRoomUpdateREQ }) => updateWaitingRoomApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingWaitingRoomKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteWaitingRoom, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteWaitingRoomListApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: settingWaitingRoomKeys.lists() });
    },
    onError: handleError,
  });

  const onUpdateSettingWaitingRoom = (id: number, values: SettingWaitingRoomUpdateREQ) => {
    mutateUpdateWaitingRoom({ id, body: values });
  };

  const onDeleteSettingWaitingRoom = () => {
    mutateDeleteWaitingRoom(parseArrayToStringRequest(selectedRowKeys));
  };

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={settingWaitingRoomData?.total}>
          <SearchInput
            defaultValue={searchParams.get('search')?.toString()}
            onChange={(e) => {
              handleFilterChange({ search: e.target.value });
            }}
          />
        </TableFilter>
      </div>
      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<SettingWaitingRoomDTO>
          columns={WaitingRoomColumns({ onUpdateSettingWaitingRoom })}
          data={settingWaitingRoomData?.data || []}
          showSelect
          showPaging={false}
          showIndex
          handleReoder={arrangeOrdinal}
          extra={
            canEdit && (
              <DeleteButton
                onClick={() => {
                  openModal(selectedRowKeys as number[]);
                }}
              />
            )
          }
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
        />
      </div>
      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteSettingWaitingRoom} isLoading={isDeleting} />
    </>
  );
};
