import ModalConfirm from '@/components/common/ConfirmModal';
import DeleteButton from '@/components/common/DeleteButton';
import SearchInput from '@/components/common/SearchInput';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import { useArrangeOrdinal } from '@/hook/useArrangeOrdinal';
import { usePaging } from '@/hook/usePaging';
import { SettingVarietiesDTO } from '@/store/setting/varieties/dto/setting-varieties.dto';
import {
  SettingVarietiesListFilter,
  SettingVarietiesUpdateREQ,
} from '@/store/setting/varieties/request/setting-varieties.request';
import {
  deleteSettingVarietiesApi,
  settingVarietiesKeys,
  updateSettingVarietiesApi,
} from '@/store/setting/varieties/setting-varieties.api';

import CustomModal from '@/components/common/modals/CustomModal';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { useSettingVarieties } from '@/hook/setting/useSettingVarieties';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { useSelectTable } from '@/hook/useSelectTable';
import { settingVarietiesListParamToFilter } from '@/store/setting/varieties/setting-varieties.service';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useSearchParams } from 'next/navigation';
import { useMemo, useRef, useState } from 'react';
import VarietyDetailModal from '../components/VarietyDetailModal';
import { VarietiesColumns } from '../data-columns/VarietiesColumns';

export const VarietiesList = () => {
  const searchParams = useSearchParams();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<SettingVarietiesDTO>();
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);
  const varietyId = useRef<number>(0);

  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);

  const queryClient = useQueryClient();

  const { arrangeOrdinal } = useArrangeOrdinal({ url: 'pet-types', queryKey: settingVarietiesKeys.lists() });

  const { initialFilter } = useMemo(() => {
    const initialFilter = settingVarietiesListParamToFilter(searchParams);
    return { initialFilter };
  }, []);

  const { filter, handleFilterChange } = usePaging<SettingVarietiesListFilter>({
    initialFilter,
    debounceTime: 500,
  });

  const { settingVarietiesData, settingVarietyBreedDetailData } = useSettingVarieties({ filter, varietyId: varietyId.current });

  const { mutate: mutateDeleteSettingVarieties, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteSettingVarietiesApi(ids),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: settingVarietiesKeys.lists() });
    },
  });
  const { mutate: mutateUpdateSettingVarieties } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: SettingVarietiesUpdateREQ }) => updateSettingVarietiesApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: settingVarietiesKeys.lists() });
    },
    onError: handleError,
  });

  const varietyName = useMemo(() => {
    return settingVarietiesData?.data.find((item) => item.id === varietyId.current)?.name;
  }, [varietyId.current, settingVarietiesData?.data]);

  const onDeleteSettingVarieties = () => {
    mutateDeleteSettingVarieties(parseArrayToStringRequest(selectedRowKeys));
  };

  const onUpdateSettingVarieties = (id: number, values: SettingVarietiesUpdateREQ) => {
    mutateUpdateSettingVarieties({ id, body: values });
  };

  const onDetail = (id: number) => {
    varietyId.current = id;
    setIsOpenDetail(true);
  };

  const onClose = () => {
    setIsOpenDetail(false);
  };

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={settingVarietiesData?.total}>
          <SearchInput
            defaultValue={searchParams.get('name')?.toString()}
            onChange={(e) => {
              handleFilterChange({ name: e.target.value });
            }}
          />
        </TableFilter>
      </div>
      <div className='mt-4 ' id='fixedTable'>
        <CustomTable<SettingVarietiesDTO>
          columns={VarietiesColumns({ onUpdateSettingVarieties, onDetail })}
          data={settingVarietiesData?.data || []}
          showSelect
          showPaging={false}
          className='!max-w-[100vw]'
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
          handleReoder={arrangeOrdinal}
        />
      </div>

      <CustomModal isOpen={isOpenDetail} onClose={onClose} title={varietyName}>
        <VarietyDetailModal varietyId={varietyId.current} varietyBreeds={settingVarietyBreedDetailData?.data || []} />
      </CustomModal>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteSettingVarieties} isLoading={isDeleting} />
    </>
  );
};
