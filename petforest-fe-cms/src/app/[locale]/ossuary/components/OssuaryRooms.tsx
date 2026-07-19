'use client';

import ModalConfirm from '@/components/common/ConfirmModal';
import CustomButton from '@/components/common/CustomButton';
import CustomModal from '@/components/common/modals/CustomModal';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import CustomText from '@/components/common/Text';
import { useOssuary } from '@/hook/ossuary/useOssuary';
import { usePaging } from '@/hook/usePaging';
import useResize from '@/hook/useResize';
import { OssuaryPlaceListDTO, UsageInformationFormDTO } from '@/store/ossuary/dto/ossuary.dto';
import {
  createOssuaryMessageApi,
  createOssuaryPlaceInformationApi,
  getOssuaryInfomationDetailApi,
  getOssuaryPlaceDetailListApi,
  getOssuaryRoomDetailApi,
  getRoomPlaceStatusDetailApi,
  ossuaryKeys,
  ossuaryStatusKeys,
  updateOssuaryPlaceInformationApi,
} from '@/store/ossuary/ossuary.api';
import {
  OssuaryInitialValue,
  ossuaryPlaceDetailListParamToFilter,
  ossuaryPlaceDetailRespToDto,
  ossuaryPlaceDtoToCreateReq,
  ossuaryPlaceInformationDetailRespToDto,
  ossuaryRoomDetailDataRespToDto,
} from '@/store/ossuary/ossuary.service';
import { OssuaryInformationCreateUpdateREQ, OssuaryListFilter, OssuaryListREQ } from '@/store/ossuary/request/ossuary.request';
import { ICONS } from '@/theme';
import { PLACE_STATUS } from '@/utils/enums/ossuary/ossuary-room.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { helperMappingPlaceColor } from '@/utils/helpers/ossuary.helper';
import { orString } from '@/utils/parser/io.parser';
import { CurrentPlaceValue, PlacesRoomInformation } from '@/utils/types/ossuary.type';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { isEmpty, isNil } from 'lodash';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useRef, useState } from 'react';

import { recipientRequirePopup } from '@/components/popup/RecipientRequirePopup';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { OSSUARY_PLACES_ROOMS } from '@/utils/constants/ossuary/ossuary-rooms.constant';
import { KAKAO_MESSAGE_TYPE } from '@/utils/enums/common.enums';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { ossuarySchema } from '@/utils/schemas/ossuary/ossuary.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { OssuaryRoomColumns } from '../data-columns/OssuaryRoomColumns';
import OssuaryItem from './OssuaryItem';
import OssuaryRoomDetailModal from './OssuaryRoomDetailModal';
import OssuaryRoomTableFilter from './OssuaryRoomTableFilter';
import OssuarySelectNotificationModal from './OssuarySelectNotificationModal';
import RoomTitle from './RoomTitle';

const OssuaryRooms = () => {
  const { t: tOssuary } = useTranslation('ossuary');
  const { t: tCommon } = useTranslation('common');
  const { t: tMessage } = useTranslation('message');

  const { isResponsive } = useResize();
  const searchParams = useSearchParams();
  const queryClient = useQueryClient();

  const [isHidingFooter, setIsHiddingFooter] = useState<boolean>(false);

  const currentIntial = {
    room: orString(searchParams.get('room')),
    place: searchParams.get('place') ? orString(searchParams.get('place')) : undefined,
    placeId: searchParams.get('placeId') ? Number(searchParams.get('placeId')) : undefined,
  };

  const methods = useForm<UsageInformationFormDTO>({
    defaultValues: {
      ...OssuaryInitialValue,
      room: currentIntial.room,
      placeId: currentIntial.placeId,
    },
    resolver: yupResolver(ossuarySchema) as any,
  });
  const { handleSubmit, reset } = methods;

  const [currentPlace, setCurrentPlace] = useState<CurrentPlaceValue>({
    room: currentIntial.room,
    place: currentIntial.place,
    placeId: currentIntial.placeId,
  });

  const { mutateDownloadExcel } = useDownloadExcel<OssuaryListREQ>({
    fileName: EXCEL_FILE_NAME.OSSUARY_MANAGEMENT_ROOM_DETAIL,
    url: `columbariums/${currentIntial.room}/places/${currentIntial.place}/exports`,
  });

  const {
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
    isUpdating,
    onExtendOssuaryTime,
  } = useOssuary<OssuaryPlaceListDTO>({ queryKey: ossuaryKeys.detail(currentPlace.placeId ?? 0) });

  const { onUpdateOssuaryStatus, isUpdatingUpdateStatus, isOpenCheckOut, setIsOpenCheckOut, openModalCheckOut } =
    useOssuary<OssuaryPlaceListDTO>({
      queryKey: ossuaryStatusKeys.detail(currentPlace.placeId ?? 0),
      invalidateQuery: ossuaryKeys.list({ name: currentIntial.room }),
    });

  const placeInformationId = useRef<number>(0);

  const [isOpenNotification, setIsOpenNotification] = useState<boolean>(false);
  const [selectedRoom, setSelectedRoom] = useState<PlacesRoomInformation[]>([]);
  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);

  const shouldShowRoomData = useMemo<boolean>(
    () => !isNil(currentPlace.place) && !isNil(currentPlace.placeId),
    [currentPlace.place, currentPlace.placeId],
  );

  const { initialFilter, initialPaging } = useMemo(() => {
    const initialFilter = ossuaryPlaceDetailListParamToFilter(searchParams);
    const initialPaging: PagingState = {
      pageSize: +(searchParams.get('pageSize') || initialPagingState.pageSize),
      pageNumber: +(searchParams.get('pageNumber') || initialPagingState.pageNumber),
    };
    return { initialFilter, initialPaging };
  }, []);

  const { filter, handleFilterChange, handlePageChange, handleTableChange } = usePaging<OssuaryListFilter>({
    initialFilter,
    initialPaging,
    debounceTime: 500,
  });

  const selectedInfoIds = useMemo<number[]>(() => selectedRowRecords.map((i) => i.id), [selectedRowRecords]);

  const { data: ossuaryRoomDetailData, refetch: refetchOssuaryRoomDetail } = useQuery({
    queryKey: ossuaryKeys.list({ name: currentIntial.room }),
    queryFn: () => getOssuaryRoomDetailApi(currentIntial.room),
    select: (response) => {
      return ossuaryRoomDetailDataRespToDto(response?.data.data);
    },
    placeholderData: keepPreviousData,
  });

  const { data: ossuaryPlaceListData } = useQuery({
    queryKey: [ossuaryKeys.detail(currentPlace.placeId || 0), filter],
    queryFn: () => (currentPlace.place ? getOssuaryPlaceDetailListApi(currentPlace.room, currentPlace.place, filter) : undefined),
    select: (response) => {
      return response?.data && { ...response.data, data: response.data.data.map(ossuaryPlaceDetailRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: ossuaryPlaceStatusData } = useQuery({
    queryKey: ossuaryStatusKeys.detail(currentPlace.placeId || 0),
    queryFn: () => (currentPlace.place ? getRoomPlaceStatusDetailApi(currentPlace.room, currentPlace.place) : undefined),
    select: (response) => {
      return response?.data?.data;
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateCreatePlaceInformation } = useMutation({
    mutationFn: (body: OssuaryInformationCreateUpdateREQ) => createOssuaryPlaceInformationApi(body),
    onSuccess: (response) => {
      queryClient.invalidateQueries({ queryKey: [ossuaryKeys.detail(currentPlace.placeId || 0), filter] });
      refetchOssuaryRoomDetail();

      placeInformationId.current = response.data.data.id;
      handleSuccess('create');
    },
    onError: handleError,
  });

  const { mutate: mutateUpdatePlaceInformation } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: OssuaryInformationCreateUpdateREQ }) =>
      updateOssuaryPlaceInformationApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: [ossuaryKeys.detail(currentPlace.placeId || 0), filter] });
      queryClient.invalidateQueries({ queryKey: ossuaryKeys.detail(placeInformationId.current) });
    },
    onError: handleError,
  });

  const { data: ossuaryPlaceInformationDetailData } = useQuery({
    queryKey: ossuaryKeys.detail(placeInformationId.current),
    queryFn: () => (placeInformationId.current !== 0 ? getOssuaryInfomationDetailApi(placeInformationId.current) : undefined),
    select: (response) => {
      return response?.data && { ...response.data, data: ossuaryPlaceInformationDetailRespToDto(response.data.data) };
    },
    placeholderData: keepPreviousData,
  });

  const onOpenDetail = (id?: number | null) => {
    placeInformationId.current = id || 0;
    setIsOpenDetail(true);
  };

  const onSubmit = (data: UsageInformationFormDTO) => {
    if (placeInformationId.current === 0) {
      mutateCreatePlaceInformation(ossuaryPlaceDtoToCreateReq(data));
    } else {
      mutateUpdatePlaceInformation({ id: placeInformationId.current, body: ossuaryPlaceDtoToCreateReq(data) });
    }
  };

  const onClose = () => {
    setIsOpenDetail(false);
    placeInformationId.current = 0;
  };

  const { mutate: mutateCreateMessage } = useMutation({
    mutationFn: createOssuaryMessageApi,
    onSuccess: () => {
      handleSuccess('message');
      setIsOpenNotification(false);
      setSelectedRowRecords([]);
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: [ossuaryKeys.detail(currentPlace.placeId || 0), filter] });
    },
    onError: handleError,
  });

  const onCreateMessage = (type: KAKAO_MESSAGE_TYPE) => {
    mutateCreateMessage({ columbariumIds: selectedInfoIds, type });
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false) });
  };

  useEffect(() => {
    const room = orString(searchParams.get('room'));
    const place = searchParams.get('place') ? orString(searchParams.get('place')) : undefined;
    const placeId = searchParams.get('placeId') ? Number(searchParams.get('placeId')) : undefined;

    setCurrentPlace({ room, place, placeId });

    reset({
      ...OssuaryInitialValue,
      room,
      placeId,
    });
  }, [searchParams]);

  const preValues = useRef<any>();

  useEffect(() => {
    const room = orString(searchParams.get('room'));
    const placeId = searchParams.get('placeId') ? Number(searchParams.get('placeId')) : undefined;

    const newData = ossuaryPlaceInformationDetailData?.data || {
      ...OssuaryInitialValue,
      room,
      placeId,
    };

    if (JSON.stringify(preValues.current) !== JSON.stringify(newData)) {
      reset(newData);
      preValues.current = newData;
    }
  }, [ossuaryPlaceInformationDetailData?.data, searchParams, reset]);

  useEffect(() => {
    const selectedRoom = OSSUARY_PLACES_ROOMS.find((item) => item.name === searchParams.get('room'))?.columbariumPlaces;
    setSelectedRoom(selectedRoom || []);
  }, [searchParams]);

  useEffect(() => {
    setSelectedRowRecords([]);
    setSelectedRowKeys([]);
  }, [currentPlace]);

  return (
    <div className='w-full'>
      <div className='w-full flex-col flex justify-center items-center '>
        <div className='w-full overflow-x-auto lg:w-fit flex-col  items-center justify-center'>
          {ossuaryRoomDetailData?.map((item, index) => (
            <div key={JSON.stringify(item)} className='flex items-center gap-[6px] mb-[6px] justify-end min-w-max'>
              {item.map((place) => (
                <OssuaryItem
                  key={`${index}-${place.id}`}
                  type={
                    !isEmpty(place.columbariumInformations) && place.status === PLACE_STATUS.CHECK_IN
                      ? helperMappingPlaceColor(place.remainingDays)
                      : undefined
                  }
                  place={place.number}
                  value={place.remainingDays}
                  id={place.id}
                  currentPlace={currentPlace}
                  setCurrentPlace={setCurrentPlace}
                />
              ))}
            </div>
          ))}
        </div>

        <div className='flex items-center justify-center w-full mt-[56px]'>
          {!shouldShowRoomData && (
            <CustomText variant='label-1-normal' className='!text-neutral-60'>
              {tOssuary('select_room')}
            </CustomText>
          )}
        </div>
      </div>

      {shouldShowRoomData && (
        <>
          <RoomTitle placeId={currentPlace.placeId || 0} onOpen={onOpenDetail} status={ossuaryPlaceStatusData?.status} />
          <div className='mt-4'>
            <TableFilter total={ossuaryPlaceListData?.total}>
              <OssuaryRoomTableFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
            </TableFilter>
          </div>
          <div className='mt-4'>
            <CustomTable<OssuaryPlaceListDTO>
              columns={OssuaryRoomColumns({ onOpenDetail, tOssuary, tCommon, onUpdateOssuary })}
              data={ossuaryPlaceListData?.data || []}
              showIndex
              showSelect
              serialProps={{ fixed: 'left' }}
              onChangeTable={handleTableChange}
              onSelect={handleSelect}
              onSelectAll={handleSelectAll}
              paging={{
                total: ossuaryPlaceListData?.total,
                pageSize: filter.pageSize,
                pageNumber: filter.pageNumber,
              }}
              selectedRowKeys={selectedRowKeys}
              onPageNumberChange={handlePageChange}
              extra={
                <div className='flex h-[38px] gap-3'>
                  <CustomButton
                    onClick={() => {
                      if (!selectedInfoIds.length) {
                        recipientRequirePopup();
                        return;
                      }
                      // if (
                      //   !ossuaryPlaceListData?.data
                      //     .filter((item) => selectedInfoIds.includes(item.id))
                      //     .every((item) => item.reservationId)
                      // ) {
                      //   recipientRequirePopup(tMessage('ossuary.notification'));
                      //   return;
                      // }
                      setIsOpenNotification(true);
                    }}
                    type='primary'
                    icon={<Image src={ICONS.NOTI_WHITE} alt='View' width={24} height={24} />}>
                    {tOssuary('button.select_notification')}
                  </CustomButton>
                  <CustomButton
                    onClick={() => {
                      openModalExtend(selectedRowKeys as number[]);
                    }}
                    type='ghost'
                    icon={<Image src={ICONS.CLOCK} alt='View' width={24} height={24} />}>
                    {tOssuary('button.extension')}
                  </CustomButton>
                  <CustomButton
                    onClick={() => {
                      openModalCheckOut([currentPlace.placeId ?? 0]);
                    }}
                    type='ghost'
                    icon={<Image src={ICONS.LOGOUT} alt='View' width={24} height={24} />}>
                    {tOssuary('button.exit')}
                  </CustomButton>
                </div>
              }
            />
          </div>

          <OssuarySelectNotificationModal
            isOpen={isOpenNotification}
            onClose={() => {
              setIsOpenNotification(false);
            }}
            onCreateMessage={onCreateMessage}
          />
          <FormProvider {...methods}>
            <form>
              <CustomModal
                isOpen={isOpenDetail}
                onOk={handleSubmit(onSubmit)}
                onCancel={onClose}
                onClose={onClose}
                isHideFooter={isHidingFooter}
                title={`${tOssuary('bongandang')}`}>
                <OssuaryRoomDetailModal
                  id={placeInformationId.current}
                  onHideButtons={(value: boolean) => {
                    setIsHiddingFooter(value);
                  }}
                />
              </CustomModal>
            </form>
          </FormProvider>
        </>
      )}
      <ModalConfirm
        isOpen={isOpenExtend}
        setIsOpen={setIsOpenExtend}
        onConfirm={onExtendOssuaryTime}
        isLoading={isUpdating}
        message={tMessage('ossuary.extend')}
      />
      <ModalConfirm
        isOpen={isOpenCheckOut}
        setIsOpen={setIsOpenCheckOut}
        onConfirm={() => onUpdateOssuaryStatus(PLACE_STATUS.CHECK_OUT, [currentPlace.placeId ?? 0])}
        isLoading={isUpdatingUpdateStatus}
        message={tOssuary('would_check_out')}
      />
    </div>
  );
};

export default OssuaryRooms;
