import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomModal from '@/components/common/modals/CustomModal';
import SearchInput from '@/components/common/SearchInput';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useSettingPartner } from '@/hook/setting/useSettingSettingPartner';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { SettingPartnerDTO } from '@/store/setting/partner/dto/setting-partner.dto';
import { SettingPartnerListFilter, SettingPartnerUpdateREQ } from '@/store/setting/partner/request/setting-partner.request';
import {
  deleteSettingPartnerApi,
  settingPartnerKeys,
  updateSettingPartnerApi,
} from '@/store/setting/partner/setting-partner.api';
import { settingPartnerIntialValueForm, settingPartnerListParamToFilter } from '@/store/setting/partner/setting-partner.service';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { PartnerDetailModal } from '../components/PartnerDetailModal';
import { PartnerColumns } from '../data-columns/PartnerColumns';

export const PartnerList = () => {
  const { t: tSetting } = useTranslation('setting');
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<SettingPartnerDTO>();

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);

  const partnerId = useRef<number>(0);

  const methods = useForm<SettingPartnerDTO>({
    defaultValues: settingPartnerIntialValueForm,
  });

  const { handleSubmit, reset } = methods;

  const { initialFilter, initialPaging } = useMemo(() => {
    const initialFilter = settingPartnerListParamToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialFilter, initialPaging };
  }, []);

  const { filter, handleFilterChange, handlePageChange, handleTableChange } = usePaging<SettingPartnerListFilter>({
    initialFilter,
    initialPaging,
    debounceTime: 500,
  });

  const { settingPartnerData, settingDetailPartnerData } = useSettingPartner({ filter, partnerId: partnerId.current });

  const { mutate: mutateDeleteSettingPartner, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteSettingPartnerApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: settingPartnerKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateSettingPartner } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingPartnerUpdateREQ }) => updateSettingPartnerApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingPartnerKeys.lists() });
    },
    onError: handleError,
  });

  const onDeleteSettingPartner = () => {
    mutateDeleteSettingPartner(parseArrayToStringRequest(selectedRowKeys));
  };

  const onUpdateSettingPartner = (id: number, values: SettingPartnerUpdateREQ) => {
    mutateUpdateSettingPartner({ id, body: values });
  };

  const onSubmit = (data: SettingPartnerUpdateREQ) => {
    mutateUpdateSettingPartner({ id: partnerId.current, body: data });
    setIsOpenDetail(false);
  };

  const onDetail = (id: number) => {
    partnerId.current = id;
    setIsOpenDetail(true);
  };

  const onCloseDetail = () => {
    setIsOpenDetail(false);
  };

  useEffect(() => {
    reset(settingDetailPartnerData || settingPartnerIntialValueForm);
  }, [partnerId.current, settingDetailPartnerData, reset]);

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={settingPartnerData?.total}>
          <SearchInput
            defaultValue={searchParams.get('search')?.toString()}
            onChange={(e) => {
              handleFilterChange({ search: e.target.value });
            }}
          />
        </TableFilter>
      </div>
      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<SettingPartnerDTO>
          key={JSON.stringify(settingPartnerData?.data)}
          columns={PartnerColumns({ onDetail, onUpdateSettingPartner })}
          data={settingPartnerData?.data || []}
          showSelect
          showPaging
          paging={{
            total: settingPartnerData?.total,
            pageSize: filter.pageSize,
            pageNumber: filter.pageNumber,
          }}
          onPageNumberChange={handlePageChange}
          onChangeTable={handleTableChange}
          showIndex
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
            isOpen={isOpenDetail}
            onClose={onCloseDetail}
            onCancel={onCloseDetail}
            title={tSetting('partner.table.name')}>
            <PartnerDetailModal />
          </CustomModal>
        </form>
      </FormProvider>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteSettingPartner} isLoading={isDeleting} />
    </>
  );
};
