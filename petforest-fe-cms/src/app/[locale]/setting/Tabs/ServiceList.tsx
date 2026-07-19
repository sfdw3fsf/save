import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import CustomModal from '@/components/common/modals/CustomModal';
import SearchInput from '@/components/common/SearchInput';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useArrangeOrdinal } from '@/hook/useArrangeOrdinal';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { usePaging } from '@/hook/usePaging';
import { useSelectTable } from '@/hook/useSelectTable';
import { SettingServicesDTO } from '@/store/setting/service/dto/setting-services.dto';
import { SettingServicesListFilter, SettingServiceUpdateREQ } from '@/store/setting/service/request/setting-services.request';
import {
  deleteServiceListApi,
  getSettingServiceApi,
  getSettingServiceMaterialApi,
  settingServiceKeys,
  updateSettingServiceApi,
} from '@/store/setting/service/setting-service.api';
import {
  settingServicesDefaultMaterialRespToDto,
  settingServicesListParamToFilter,
  settingServicesRespToDto,
} from '@/store/setting/service/setting-services.service';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { ServiceDetailModal } from '../components/ServiceDetailModal';
import { ServiceColumns } from '../data-columns/ServiceColumns';

export const ServiceList = () => {
  const { t: tSetting } = useTranslation('setting');
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const serviceMaterialId = useRef<number>(0);
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<SettingServicesDTO>();

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);

  const { arrangeOrdinal } = useArrangeOrdinal({ url: 'funeral-services', queryKey: settingServiceKeys.lists() });

  const { initialFilter } = useMemo(() => {
    const initialFilter = settingServicesListParamToFilter(searchParams);
    return { initialFilter };
  }, []);

  const { filter, handleFilterChange } = usePaging<SettingServicesListFilter>({
    initialFilter,
    debounceTime: 500,
  });

  const { data: settingServicesData } = useQuery({
    queryKey: settingServiceKeys.list(filter),
    queryFn: () => getSettingServiceApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(settingServicesRespToDto), total: response.data.data.length };
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDeleteService, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteServiceListApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: settingServiceKeys.lists() });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateService } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingServiceUpdateREQ }) => updateSettingServiceApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingServiceKeys.lists() });
    },
    onError: handleError,
  });

  const { data: settingMaterialServiceDetailData } = useQuery({
    queryKey: settingServiceKeys.detail(serviceMaterialId.current),
    queryFn: () => getSettingServiceMaterialApi(serviceMaterialId.current),
    select: (response) => {
      return {
        data: response?.data.data.map(settingServicesDefaultMaterialRespToDto) ?? [],
        total: response?.data.data.length || 0,
      };
    },
    placeholderData: keepPreviousData,
  });

  const onDeleteSettingService = () => {
    mutateDeleteService(parseArrayToStringRequest(selectedRowKeys));
  };

  const onUpdateSettingService = (id: number, values: SettingServiceUpdateREQ) => {
    mutateUpdateService({ id, body: values });
  };

  const onDetail = (id: number) => {
    serviceMaterialId.current = id;
    setIsOpenDetail(true);
  };

  const onClose = () => {
    setIsOpenDetail(false);
  };

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={settingServicesData?.total}>
          <SearchInput
            defaultValue={searchParams.get('search')?.toString()}
            onChange={(e) => {
              handleFilterChange({ search: e.target.value });
            }}
          />
        </TableFilter>
      </div>
      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<SettingServicesDTO>
          // key={JSON.stringify(settingServicesData?.data.map((item) => item.serviceDefaultMaterials))}
          columns={ServiceColumns({ onDetail, onUpdateSettingService })}
          data={settingServicesData?.data || []}
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
      <CustomModal isOpen={isOpenDetail} onClose={onClose} title={tSetting('service.table.article')}>
        <ServiceDetailModal
          serviceId={serviceMaterialId.current}
          serviceDefaultMaterial={settingMaterialServiceDetailData?.data || []}
        />
      </CustomModal>
      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteSettingService} isLoading={isDeleting} />
    </>
  );
};
