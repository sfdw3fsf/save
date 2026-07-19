import {
  getOssuaryRoomDetailApi,
  getOssuaryRoomNamesApi,
  ossuaryKeys,
  updateExtendOssuaryTimeApi,
  updateOssuaryInfoApi,
  updateOssuaryStatusApi,
} from '@/store/ossuary/ossuary.api';
import { ossuaryPlaceCatergoryToOptions, ossuaryRoomNameToOptions } from '@/store/ossuary/ossuary.service';

import { OssuaryUpdateREQ } from '@/store/ossuary/request/ossuary.request';
import { PLACE_STATUS } from '@/utils/enums/ossuary/ossuary-room.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { keepPreviousData, QueryKey, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { isNil } from 'lodash';

import { useMemo } from 'react';
import { useModalConfirm } from '../useModalConfirm';
import { useSelectTable } from '../useSelectTable';

type useOssuaryProps = {
  roomValue?: string;
  queryKey?: QueryKey;
  isCheckout?: PLACE_STATUS;
  invalidateQuery?: QueryKey;
};

export function useOssuary<T extends { key: React.Key }>({
  queryKey,
  invalidateQuery,
  roomValue = '',
  isCheckout,
}: useOssuaryProps) {
  const {
    isOpen: isOpenExtend,
    setIsOpen: setIsOpenExtend,
    openModal: openModalExtend,
    closeModal: closeModalExtend,
  } = useModalConfirm();
  const {
    isOpen: isOpenCheckOut,
    setIsOpen: setIsOpenCheckOut,
    openModal: openModalCheckOut,
    closeModal: closeModalCheckOut,
  } = useModalConfirm();
  const queryClient = useQueryClient();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll, selectedRowRecords, setSelectedRowRecords } =
    useSelectTable<T>(true);

  const { mutate: mutateUpdateOssuary } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: OssuaryUpdateREQ }) => updateOssuaryInfoApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: queryKey });
    },
    onError: handleError,
  });

  const { mutate: mutateUpdateOssuaryStatus, isPending: isUpdatingUpdateStatus } = useMutation({
    mutationFn: ({ ids, status }: { ids: string; status: PLACE_STATUS }) => updateOssuaryStatusApi(ids, status),
    onSuccess: () => {
      closeModalCheckOut();
      handleSuccess('update');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: queryKey });
      queryClient.invalidateQueries({ queryKey: invalidateQuery });
    },
    onError: handleError,
  });

  const { mutate: mutateExtendOssuaryTime, isPending: isUpdating } = useMutation({
    mutationFn: (ids: string) => updateExtendOssuaryTimeApi(ids),
    onSuccess: () => {
      closeModalExtend();
      handleSuccess('update');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: queryKey });
    },
    onError: handleError,
  });

  const { data: ossuaryRoomDetailData } = useQuery({
    queryKey: ossuaryKeys.list({ name: roomValue, isCheckout }),
    queryFn: () => getOssuaryRoomDetailApi(roomValue, isCheckout),
    select: (response) => {
      return response?.data && response.data.data;
    },
    placeholderData: keepPreviousData,
  });

  const ossuaryPlaceCatergoryOptions = useMemo(() => {
    if (isNil(ossuaryRoomDetailData)) return [];
    return ossuaryPlaceCatergoryToOptions(ossuaryRoomDetailData);
  }, [ossuaryRoomDetailData]);

  const { data: ossuaryRoomNamesData } = useQuery({
    queryKey: ossuaryKeys.lists(),
    queryFn: () => getOssuaryRoomNamesApi(),
    select: (response) => {
      return { ...response.data };
    },
    placeholderData: keepPreviousData,
  });

  const ossuaryRoomNameOptions = useMemo(() => {
    if (!ossuaryRoomNamesData) return [];
    return ossuaryRoomNameToOptions(ossuaryRoomNamesData?.data);
  }, [ossuaryRoomNamesData]);

  const onUpdateOssuary = (id: number, values: OssuaryUpdateREQ) => {
    mutateUpdateOssuary({ id, body: values });
  };

  const onUpdateOssuaryStatus = (status: PLACE_STATUS, ids?: number[]) => {
    mutateUpdateOssuaryStatus({ ids: parseArrayToStringRequest(ids ?? (selectedRowKeys as any)), status });
  };

  const onExtendOssuaryTime = () => {
    mutateExtendOssuaryTime(parseArrayToStringRequest(selectedRowKeys));
  };

  return {
    onUpdateOssuaryStatus,
    onUpdateOssuary,
    selectedRowKeys,
    setSelectedRowKeys,
    selectedRowRecords,
    setSelectedRowRecords,
    isOpenExtend,
    setIsOpenExtend,
    openModalExtend,
    handleSelect,
    handleSelectAll,
    isUpdatingUpdateStatus,
    isUpdating,
    onExtendOssuaryTime,
    isOpenCheckOut,
    setIsOpenCheckOut,
    openModalCheckOut,
    ossuaryRoomNameOptions,
    ossuaryPlaceCatergoryOptions,
  };
}
