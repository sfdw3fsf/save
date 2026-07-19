import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomModal from '@/components/common/modals/CustomModal';
import SearchInput from '@/components/common/SearchInput';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { SettingCorrespondentDTO } from '@/store/setting/correspondent/dto/setting-correspondent.dto';
import {
  SettingCorrespondentListFilter,
  SettingCorrespondentUpdateREQ,
} from '@/store/setting/correspondent/request/setting-correspondent.request';
import {
  deleteSettingCorrespondentListApi,
  getDetailSettingCorrespondentApi,
  getSettingCorrespondentApi,
  settingCorrespondentKeys,
  updateCorrespondentApi,
} from '@/store/setting/correspondent/setting-correspondent.api';
import {
  settingCorrespondentIntialValueForm,
  settingCorrespondentListParamToFilter,
  settingCorrespondentRespToDto,
} from '@/store/setting/correspondent/setting-correspondent.service';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { CorrespondentDetailModal } from '../components/CorrespondentDetailModal';
import { CorrespondentColumns } from '../data-columns/CorrespondentColumns';

export const CorrespondentList = () => {
  const { t: tSetting } = useTranslation('setting');
  const queryClient = useQueryClient();
  const searchParams = useSearchParams();
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);

  const correspondentId = useRef<number>(0);

  const methods = useForm<SettingCorrespondentDTO>({
    defaultValues: settingCorrespondentIntialValueForm,
  });
  const { handleSubmit, reset } = methods;

  const { initialFilter } = useMemo(() => {
    const initialFilter = settingCorrespondentListParamToFilter(searchParams);
    return { initialFilter };
  }, []);

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<SettingCorrespondentDTO>();

  const { filter, handleFilterChange, handleTableChange } = usePaging<SettingCorrespondentListFilter>({
    initialFilter,
    debounceTime: 500,
  });

  const { data: settingCorrespondentData } = useQuery({
    queryKey: settingCorrespondentKeys.list(filter),
    queryFn: () => getSettingCorrespondentApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(settingCorrespondentRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateUpdateCorrespondent, isPending: isUpdating } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingCorrespondentUpdateREQ }) => updateCorrespondentApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingCorrespondentKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateDeleteCorrespondent, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteSettingCorrespondentListApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: settingCorrespondentKeys.lists() });
    },
    onError: handleError,
  });

  const { data: settingDetailCorrespondentData } = useQuery({
    queryKey: settingCorrespondentKeys.detail(correspondentId.current),
    queryFn: () => getDetailSettingCorrespondentApi(correspondentId.current),
    select: (response) => {
      return response?.data.data && settingCorrespondentRespToDto(response?.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const onUpdateSettingCorrespondent = (id: number, values: SettingCorrespondentUpdateREQ) => {
    mutateUpdateCorrespondent({ id, body: values });
  };

  const onDeleteSettingCorrespondent = () => {
    mutateDeleteCorrespondent(parseArrayToStringRequest(selectedRowKeys));
  };

  const onSubmit = (data: SettingCorrespondentUpdateREQ) => {
    mutateUpdateCorrespondent({ id: correspondentId.current, body: data });
    setIsOpenDetail(false);
  };

  const onDetail = (id: number) => {
    correspondentId.current = id;
    setIsOpenDetail(true);
  };

  const onCloseDetail = () => {
    setIsOpenDetail(false);
  };

  useEffect(() => {
    reset(settingDetailCorrespondentData || settingCorrespondentIntialValueForm);
  }, [correspondentId.current, settingDetailCorrespondentData, reset]);

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={settingCorrespondentData?.total}>
          <SearchInput
            defaultValue={searchParams.get('search')?.toString()}
            onChange={(e) => {
              handleFilterChange({ search: e.target.value });
            }}
          />
        </TableFilter>
      </div>
      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<SettingCorrespondentDTO>
          columns={CorrespondentColumns({ onDetail, onUpdateSettingCorrespondent })}
          data={settingCorrespondentData?.data || []}
          showSelect
          showIndex
          paging={{
            total: settingCorrespondentData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          extra={
            canEdit && (
              <DeleteButton
                onClick={() => {
                  openModal(selectedRowKeys as number[]);
                }}
              />
            )
          }
        />
      </div>
      <FormProvider {...methods}>
        <form>
          <CustomModal
            onOk={handleSubmit(onSubmit)}
            onCancel={onCloseDetail}
            isOpen={isOpenDetail}
            isLoading={isUpdating}
            onClose={onCloseDetail}
            title={tSetting('correspondent.detail.title')}>
            <CorrespondentDetailModal />
          </CustomModal>
        </form>
      </FormProvider>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteSettingCorrespondent} isLoading={isDeleting} />
    </>
  );
};
