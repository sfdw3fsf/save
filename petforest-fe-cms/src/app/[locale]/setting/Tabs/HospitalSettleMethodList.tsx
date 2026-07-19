import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import SearchInput from '@/components/common/SearchInput';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useArrangeOrdinal } from '@/hook/useArrangeOrdinal';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { SettingHospitalSettlementMethodDTO } from '@/store/setting/hospital-settlement-method/dto/setting-hospital-settlement-method.dto';
import {
  SettingHospitalSettlementMethodListFilter,
  SettingHospitalSettlementMethodUpdateREQ,
  SettingHospitalSettlementMethodUpdateStatusREQ,
} from '@/store/setting/hospital-settlement-method/request/setting-hospital-settlement-method.request';

import {
  deleteHospitalSettlementMethodListApi,
  getHospitalSettlementMethodListApi,
  settingHospitalSettlementMethodKeys,
  updateHospitalSettlementMethodApi,
  updateStatusHospitalSettlementMethodApi,
} from '@/store/setting/hospital-settlement-method/setting-hospital-settlement-method.api';
import {
  settingHospitalSettlementMethodListParamToFilter,
  settingHospitalSettlementMethodRespToDto,
} from '@/store/setting/hospital-settlement-method/setting-hospital-settlement-method.service';
import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';

import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { HospitalSettleMethodColumns } from '../data-columns/HospitalSettleMethodColumns';

export const HospitalSettleMethodList = () => {
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } =
    useSelectTable<SettingHospitalSettlementMethodDTO>();

  const [revertData, setRevertData] = useState<boolean>(false);
  const { arrangeOrdinal } = useArrangeOrdinal({
    url: 'hospital-entitlements',
    queryKey: settingHospitalSettlementMethodKeys.lists(),
    onError: () => setRevertData((prev) => !prev),
  });

  const { initialFilter } = useMemo(() => {
    const initialFilter = settingHospitalSettlementMethodListParamToFilter(searchParams);
    return { initialFilter };
  }, []);

  const { filter, handleFilterChange } = usePaging<SettingHospitalSettlementMethodListFilter>({
    initialFilter,
    debounceTime: 500,
  });

  const { data: settingHospitalSettlementMethodData } = useQuery({
    queryKey: settingHospitalSettlementMethodKeys.list(filter),
    queryFn: () => getHospitalSettlementMethodListApi(filter),
    select: (response) => {
      return {
        data: response.data.data.map(settingHospitalSettlementMethodRespToDto),
        total: response.data.data.length,
      };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDeleteHospitalSettlementMethod, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteHospitalSettlementMethodListApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: settingHospitalSettlementMethodKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateHospitalSettlementMethod } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingHospitalSettlementMethodUpdateREQ }) =>
      updateHospitalSettlementMethodApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingHospitalSettlementMethodKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateStatusHospitalSettlementMethod } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingHospitalSettlementMethodUpdateStatusREQ }) =>
      updateStatusHospitalSettlementMethodApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingHospitalSettlementMethodKeys.lists() });
    },
    onError: handleError,
  });

  const onUpdateStatusSettingHospitalSettlementMethod = (id: number, values: SettingHospitalSettlementMethodUpdateREQ) => {
    mutateUpdateStatusHospitalSettlementMethod({ id, body: values });
  };

  const onUpdateSettingHospitalSettlementMethod = (id: number, values: SettingHospitalSettlementMethodUpdateREQ) => {
    mutateUpdateHospitalSettlementMethod({ id, body: values });
  };

  const onDeleteSettingHospitalSettlementMethod = () => {
    mutateDeleteHospitalSettlementMethod(parseArrayToStringRequest(selectedRowKeys));
  };

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={settingHospitalSettlementMethodData?.total}>
          <SearchInput
            defaultValue={searchParams.get('search')?.toString()}
            onChange={(e) => {
              handleFilterChange({ search: e.target.value });
            }}
          />
        </TableFilter>
      </div>
      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<SettingHospitalSettlementMethodDTO>
          key={`${revertData}}`}
          columns={HospitalSettleMethodColumns({
            onUpdateSettingHospitalSettlementMethod,
            onUpdateStatusSettingHospitalSettlementMethod,
          })}
          rowClassName={(record) => (record.status === ACTIVE_STATE.INACTIVE ? 'disabled-row disabled-text' : '')}
          data={settingHospitalSettlementMethodData?.data || []}
          showSelect
          handleReoder={arrangeOrdinal}
          showPaging={false}
          showIndex
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
      <ModalConfirm
        isOpen={isOpen}
        setIsOpen={setIsOpen}
        onConfirm={onDeleteSettingHospitalSettlementMethod}
        isLoading={isDeleting}
      />
    </>
  );
};
