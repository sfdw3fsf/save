'use client';

import ModalConfirm from '@/components/common/ConfirmModal';
import CustomButton from '@/components/common/CustomButton';
import CustomRangePicker from '@/components/common/CustomRangePicker';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import CustomText from '@/components/common/Text';
import { recipientRequirePopup } from '@/components/popup/RecipientRequirePopup';
import { useOssuary } from '@/hook/ossuary/useOssuary';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { usePaging } from '@/hook/usePaging';
import { OssuaryListDTO } from '@/store/ossuary/dto/ossuary.dto';
import {
  createOssuaryMessageApi,
  getOssuaryEntireSummariesApi,
  getOssuaryListApi,
  ossuaryKeys,
} from '@/store/ossuary/ossuary.api';
import { ossuaryEntireListParamToFilter, ossuaryListRespToDto } from '@/store/ossuary/ossuary.service';
import { OssuaryListFilter, OssuaryListREQ } from '@/store/ossuary/request/ossuary.request';
import { ICONS } from '@/theme';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { KAKAO_MESSAGE_TYPE } from '@/utils/enums/common.enums';
import { PLACE_STATUS } from '@/utils/enums/ossuary/ossuary-room.enum';
import { OSSUARY_FILTER } from '@/utils/enums/ossuary/ossuary.enum';
import { getMonthRange } from '@/utils/helpers/date-time.helper';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { initialPagingState, PagingState } from '@/utils/types/paging.type';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import dayjs from 'dayjs';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { OssuaryColumns } from '../data-columns/OssuaryColumns';
import OssuarySelectNotificationModal from './OssuarySelectNotificationModal';
import OssuaryTableFilter from './OssuaryTableFilter';

const OssuaryEntire = () => {
  const { t: tOssuary } = useTranslation('ossuary');
  const { t: tMessage } = useTranslation('message');

  const searchParams = useSearchParams();
  const queryClient = useQueryClient();
  const { mutateDownloadExcel } = useDownloadExcel<OssuaryListREQ>({
    fileName: EXCEL_FILE_NAME.OSSUARY_MANAGEMENT_ALL,
    url: EXCEL_URL.OSSUARY_MANAGEMENT_ALL,
  });

  const {
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
  } = useOssuary<OssuaryListDTO>({ queryKey: ossuaryKeys.lists() });

  const [isOpenNotification, setIsOpenNotification] = useState<boolean>(false);
  const [startDate, endDate] = getMonthRange(new Date().getMonth() + 1);
  const [dateRange, setDateRange] = useState({
    start: startDate.startOf('day').valueOf(),
    end: endDate.endOf('day').valueOf(),
  });
  const [selectedFilter, setSelectedFilter] = useState<OSSUARY_FILTER>();

  const { initialFilter, initialPaging } = useMemo(() => {
    const initialFilter = ossuaryEntireListParamToFilter(searchParams);
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

  const selectedInfoIds = useMemo<number[]>(
    () => selectedRowRecords.reduce<number[]>((acc, cur) => acc.concat(cur.infoIds), []),
    [selectedRowRecords],
  );

  const placeIds = useMemo(() => selectedRowRecords.map((item: any) => item.placeId), [selectedRowRecords]);

  const { data: ossuaryEntireData } = useQuery({
    queryKey: ossuaryKeys.list(filter),
    queryFn: () => getOssuaryListApi(filter),
    select: (response) => {
      return { ...response.data, data: response.data.data.map(ossuaryListRespToDto) };
    },
    placeholderData: keepPreviousData,
  });

  const { data: ossuaryEntireSummariesData } = useQuery({
    queryKey: ossuaryKeys.list({ startDate: dateRange.start, endDate: dateRange.end }),
    queryFn: () => getOssuaryEntireSummariesApi({ startDate: dateRange.start, endDate: dateRange.end }),
    select: (response) => response.data.data,
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateCreateMessage } = useMutation({
    mutationFn: createOssuaryMessageApi,
    onSuccess: () => {
      handleSuccess('message');
      setIsOpenNotification(false);
      setSelectedRowRecords([]);
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: ossuaryKeys.lists() });
    },
    onError: handleError,
  });

  const onCreateMessage = (type: KAKAO_MESSAGE_TYPE) => {
    mutateCreateMessage({ columbariumIds: selectedInfoIds, type });
  };

  const onDownloadExcel = () => {
    mutateDownloadExcel({
      filter,
      ids: parseArrayToStringRequest(selectedRowKeys, false),
      startDate: dateRange.start,
      endDate: dateRange.end,
    });
  };

  const getSelectedItemClass = (type: OSSUARY_FILTER) => {
    return type === selectedFilter ? 'border-[1px] border-primary-900' : '';
  };

  const onSelectFilter = (newValue: OSSUARY_FILTER) => {
    const obj = filter.filter ? JSON.parse(filter?.filter ?? '') : {};
    if (newValue === selectedFilter) {
      setSelectedFilter(undefined);
      handleFilterChange({ filter: JSON.stringify({}) }, true);
    } else {
      setSelectedFilter(newValue);
      switch (newValue) {
        case OSSUARY_FILTER.CURRENT_OCCUPIED:
          handleFilterChange(
            {
              filter: JSON.stringify({
                ...obj,
                'status.in': [PLACE_STATUS.CHECK_IN],
                'columbariumInformations.some.registrationDate.lte': '',
                'columbariumInformations.some.registrationDate.gte': '',
                'columbariumInformations.some.expirationDate.lte': '',
                'columbariumInformations.some.expirationDate.gte': '',
              }),
            },
            true,
          );
          break;
        case OSSUARY_FILTER.OVERNIGHT:
          handleFilterChange(
            {
              filter: JSON.stringify({
                ...obj,
                'status.in': [PLACE_STATUS.CHECK_IN],
                'columbariumInformations.some.registrationDate.lte': '',
                'columbariumInformations.some.registrationDate.gte': '',
                'columbariumInformations.some.expirationDate.lte': dayjs().startOf('day').add(30, 'day').valueOf(),
                'columbariumInformations.some.expirationDate.gte': dayjs().startOf('day').valueOf(),
              }),
            },
            true,
          );
          break;
        case OSSUARY_FILTER.ADMISSION:
          handleFilterChange(
            {
              filter: JSON.stringify({
                ...obj,
                'status.in': [PLACE_STATUS.CHECK_IN],
                'columbariumInformations.some.registrationDate.lte': dateRange.end,
                'columbariumInformations.some.registrationDate.gte': dateRange.start,
                'columbariumInformations.some.expirationDate.lte': '',
                'columbariumInformations.some.expirationDate.gte': '',
              }),
            },
            true,
          );
          break;
        case OSSUARY_FILTER.EXPIRATION:
          handleFilterChange(
            {
              filter: JSON.stringify({
                ...obj,
                'status.in': [PLACE_STATUS.CHECK_OUT],
                'columbariumInformations.some.registrationDate.lte': '',
                'columbariumInformations.some.registrationDate.gte': '',
                'columbariumInformations.some.expirationDate.lte': dateRange.end,
                'columbariumInformations.some.expirationDate.gte': dateRange.start,
              }),
            },
            true,
          );
          break;
      }
    }
  };

  return (
    <div>
      <div className='flex gap-3 flex-wrap justify-center'>
        <div
          onClick={() => {
            onSelectFilter(OSSUARY_FILTER.CURRENT_OCCUPIED);
          }}
          className={`cursor-pointer flex flex-col items-center justify-center gap-3 border-[1px] border-neutral-40 rounded-lg w-[122px] h-[132px] px-3 py-[34px] ${getSelectedItemClass(OSSUARY_FILTER.CURRENT_OCCUPIED)}`}>
          <CustomText variant='label-1-normal'>{tOssuary('occupancy')}</CustomText>
          <div className='flex'>
            <span className='text-[24px] font-semibold leading-[20px] text-primary-800'>
              {ossuaryEntireSummariesData?.currentOccupied || 0}
            </span>
            <span className='text-[14px] font-medium leading-[20px] text-neutral-60'>{`/${ossuaryEntireSummariesData?.total || 0}`}</span>
          </div>
        </div>
        <div
          onClick={() => {
            onSelectFilter(OSSUARY_FILTER.OVERNIGHT);
          }}
          className={`cursor-pointer flex flex-col items-center justify-center gap-3 border-[1px] border-neutral-40 rounded-lg w-[122px] h-[132px] px-3 py-[34px] ${getSelectedItemClass(OSSUARY_FILTER.OVERNIGHT)}`}>
          <CustomText variant='label-1-normal'>{tOssuary('imminent')}</CustomText>

          <span className='text-[24px] font-semibold leading-[20px] text-blue-500'>
            {ossuaryEntireSummariesData?.currentOvernightStays || 0}
          </span>
        </div>

        <div className='flex flex-col border-[1px] border-neutral-40 rounded-lg p-4 justify-between w-[328px]'>
          <CustomRangePicker
            className='w-full'
            allowClear={false}
            defaultValue={[dateRange.start, dateRange.end]}
            onChange={(values) => {
              setDateRange({
                start: values[0] || startDate.startOf('day').valueOf(),
                end: values[1] || endDate.endOf('day').valueOf(),
              });
            }}
          />
          <div className='flex gap-4'>
            <div
              onClick={() => {
                onSelectFilter(OSSUARY_FILTER.ADMISSION);
              }}
              className={`cursor-pointer rounded-lg flex items-center justify-between flex-1 h-[50px] bg-neutral-20 px-3 py-[15px] ${getSelectedItemClass(OSSUARY_FILTER.ADMISSION)}`}>
              <CustomText variant='label-1-normal' className='!text-neutral-80'>
                {tOssuary('entry')}
              </CustomText>
              <CustomText variant='headline-1' className='!text-black'>
                {ossuaryEntireSummariesData?.admissionsDuringPeriod || 0}
              </CustomText>
            </div>
            <div
              onClick={() => {
                onSelectFilter(OSSUARY_FILTER.EXPIRATION);
              }}
              className={`cursor-pointer rounded-lg flex items-center justify-between flex-1 h-[50px] bg-neutral-20 px-3 py-[15px] ${getSelectedItemClass(OSSUARY_FILTER.EXPIRATION)}`}>
              <CustomText variant='label-1-normal' className='!text-neutral-80'>
                {tOssuary('expire')}
              </CustomText>
              <CustomText variant='headline-1' className='!text-black'>
                {ossuaryEntireSummariesData?.expirationDuringPeriod || 0}
              </CustomText>
            </div>
          </div>
        </div>
      </div>

      <div className='mt-8'>
        <TableFilter total={ossuaryEntireData?.total}>
          <OssuaryTableFilter onFilterChange={handleFilterChange} onDownloadExcel={onDownloadExcel} />
        </TableFilter>
      </div>

      <div className='mt-4' id='fixedTable'>
        <CustomTable<OssuaryListDTO>
          columns={OssuaryColumns({ onUpdateOssuary })}
          data={ossuaryEntireData?.data || []}
          showIndex
          showSelect
          onChangeTable={handleTableChange}
          onSelect={handleSelect}
          onSelectAll={handleSelectAll}
          paging={{
            total: ossuaryEntireData?.total,
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
                  openModalCheckOut(selectedRowKeys as number[]);
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
      <ModalConfirm
        isOpen={isOpenExtend}
        setIsOpen={setIsOpenExtend}
        onConfirm={onExtendOssuaryTime}
        isLoading={isUpdatingUpdateStatus}
        message={tMessage('ossuary.extend')}
      />
      <ModalConfirm
        isOpen={isOpenCheckOut}
        setIsOpen={setIsOpenCheckOut}
        onConfirm={() => onUpdateOssuaryStatus(PLACE_STATUS.CHECK_OUT, placeIds)}
        isLoading={isUpdating}
        message={tOssuary('would_check_out')}
      />
    </div>
  );
};

export default OssuaryEntire;
