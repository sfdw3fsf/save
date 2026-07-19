import ModalConfirm from '@/components/common/ConfirmModal';
import CustomButton from '@/components/common/CustomButton';
import DeleteButton from '@/components/common/DeleteButton';
import CustomTable from '@/components/common/table/CustomTable';
import TableFilter from '@/components/common/table/TableFilter';
import CustomPagination from '@/components/table/CustomPagination';
import { useMaterial } from '@/hook/material/useMaterial';
import { useReservationTime } from '@/hook/reservation-time/useReservationTime';
import { useSettingService } from '@/hook/setting/useSettingService';
import { useSettingVarieties } from '@/hook/setting/useSettingVarieties';
import useDownloadExcel from '@/hook/useDownloadExcel';
import { useModalConfirm } from '@/hook/useModalConfirm';
import useRouterHandler from '@/hook/useRouterHandler';
import { useSelectTable } from '@/hook/useSelectTable';
import { FuneralMNGTReservationListDTO, ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import {
  ReservationListFilter,
  ReservationListREQ,
  ReservationUpdateDetailREQ,
  ReservationUpdateFunaralInformationREQ,
  ReservationUpdatePetInformationREQ,
  ReservationUpdateREQ,
} from '@/store/funeral/reservation/request/reservation.request';
import {
  deleteReservationListApi,
  funeralDeletedReservationKeys,
  funeralReservationKeys,
  getReservationDetailApi,
  restoreReservationApi,
  updateReservationApi,
  updateReservationDetailApi,
  updateReservationFuneralInformationApi,
  updateReservationPetInformationApi,
} from '@/store/funeral/reservation/reservation.api';
import {
  reservationDetailDtoToReq,
  reservationDetailFormInitialValues,
  reservationDetailRespToFormDto,
} from '@/store/funeral/reservation/reservation.service';
import { useReservationStore } from '@/store/funeral/reservation/reservation.store';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { EXCEL_FILE_NAME } from '@/utils/constants/excel-name.constant';
import { EXCEL_URL } from '@/utils/constants/excel-url.constant';
import { DELETE_TYPE } from '@/utils/enums/common.enums';
import { FUNERAL_RESERVATION_CONTENT } from '@/utils/enums/funeral/funeral-mngt.enums';
import { DETAIL_FUNERAL_STATUS_OPTIONS } from '@/utils/enums/funeral/reservation-mngt.enums';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { parseArrayToStringRequest } from '@/utils/parser/array-request.parser';
import { reservationSchema } from '@/utils/schemas/reservation/reservation.schema';
import { useUploadFile } from '@/utils/utils/upload-file-to-s3.util';
import { UndoOutlined } from '@ant-design/icons';
import { yupResolver } from '@hookform/resolvers/yup';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { TablePaginationConfig } from 'antd';
import { FilterValue, SorterResult, TableCurrentDataSource } from 'antd/es/table/interface';
import dayjs from 'dayjs';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import ReservationDetailModal from '../components/ReservationDetailModal';
import ReservationMobile from '../components/ReservationMobile';
import ReservationModal from '../components/ReservationModal';
import ReservationTableFilter from '../components/ReservationTableFilter';
import { ReservationColumns } from '../data-columns/ReservationColumns';
import { ReservationDeletedColumns } from '../data-columns/ReservationDeletedColumns';

type ReservationListProps = {
  total: number;
  data: FuneralMNGTReservationListDTO[];
  isDeletedList?: boolean;
  isHiddenExtraButton?: boolean;
  filter: ReservationListREQ;
  handleFilterChange: DebouncedFunc<(filter: ReservationListFilter) => void>;
  handleTableChange: (
    pagination: TablePaginationConfig,
    filters: Record<string, FilterValue | null>,
    sorter: SorterResult<ReservationListFilter> | SorterResult<ReservationListFilter>[],
    extra: TableCurrentDataSource<ReservationListFilter>,
  ) => void;
  handlePageChange: (paging: { pageNumber?: number; pageSize?: number }) => void;
  onRefresh?: () => void;
};

const ReservationContent = ({
  handleTableChange,
  handlePageChange,
  total,
  filter,
  isHiddenExtraButton = false,
  isDeletedList = false,
  data,
  handleFilterChange,
  onRefresh,
}: ReservationListProps) => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { t: tCommon } = useTranslation('common');

  const queryClient = useQueryClient();
  const searchParams = useSearchParams();
  const { mutateDownloadExcel } = useDownloadExcel<ReservationListREQ>({
    fileName: EXCEL_FILE_NAME.RESERVATION,
    url: EXCEL_URL.RESERVATION,
  });
  const reservationId = useRef<number>(0);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const { reservationTimeOptions } = useReservationTime();
  const { handleURLPush } = useRouterHandler();
  const { petTypeOptions } = useSettingVarieties({});
  const { serviceOptions } = useSettingService({});
  const {
    isOpen: isOpenRestore,
    setIsOpen: setIsOpenRestore,
    openModal: openModalRestore,
    closeModal: closeModalRestore,
  } = useModalConfirm();
  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const { selectedRowKeys, setSelectedRowKeys, handleSelect, handleSelectAll } = useSelectTable<FuneralMNGTReservationListDTO>();
  const { setSelectedReservationKeys } = useReservationStore();

  const { materialNameData } = useMaterial();
  const { uploadFile } = useUploadFile();
  const [isOpenDetail, setIsOpenDetail] = useState<boolean>(false);

  const date = useMemo(() => {
    const parsedFilter = JSON.parse(filter?.filter || '{}');
    const startDate = parsedFilter['funeralInformation.funeralDate.gte'];
    const endDate = parsedFilter['funeralInformation.funeralDate.lte'];

    if (startDate && endDate) {
      return dayjs(startDate).format(DATE_FORMAT.DATE.DOT_SHORTEN) + '_' + dayjs(endDate).format(DATE_FORMAT.DATE.DOT_SHORTEN);
    }

    return '';
  }, [filter?.filter]);

  const methods = useForm<ReservationDetailDTO>({
    defaultValues: reservationDetailFormInitialValues,
    resolver: yupResolver(reservationSchema) as any,
    context: {
      isOptionalEmail: true,
    },
  });
  const { handleSubmit, reset } = methods;

  const { data: reservationDetailData } = useQuery({
    queryKey: funeralReservationKeys.detail(reservationId.current),
    queryFn: () => getReservationDetailApi(reservationId.current),
    select: (response) => {
      return response?.data.data && reservationDetailRespToFormDto(response?.data.data, materialNameData);
    },
    placeholderData: keepPreviousData,
  });

  const { mutateAsync: mutateDeleteReservation, isPending: isDeleting } = useMutation({
    mutationFn: (ids: string) => deleteReservationListApi(ids, isDeletedList ? DELETE_TYPE.HARD : DELETE_TYPE.SOFT),
    onSuccess: () => {
      closeModal();
      handleSuccess('delete');
      setSelectedRowKeys([]);
      queryClient.invalidateQueries({ queryKey: funeralReservationKeys.lists() });
    },
    onError: handleError,
  });

  const { mutateAsync: mutateRestoreReservation, isPending: isRestoring } = useMutation({
    mutationFn: (ids: string) => restoreReservationApi(ids),
    onSuccess: () => {
      closeModalRestore();
      setSelectedRowKeys([]);
      handleSuccess('update');
      queryClient.invalidateQueries({ queryKey: funeralReservationKeys.lists() });
      queryClient.invalidateQueries({ queryKey: funeralDeletedReservationKeys.lists() });
    },
    onError: handleError,
  });

  const { mutateAsync: mutateUpdatePetInformation } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: ReservationUpdatePetInformationREQ }) =>
      updateReservationPetInformationApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
    },
    onError: handleError,
  });

  const { mutateAsync: mutateUpdateReservationDetail } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: ReservationUpdateDetailREQ }) => updateReservationDetailApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
      setIsLoading(false);
    },
    onError: (err) => {
      setIsLoading(false);
      handleError(err as any);
    },
  });

  const { mutateAsync: mutateUpdateFuneralInformation } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: ReservationUpdateFunaralInformationREQ }) =>
      updateReservationFuneralInformationApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
    },
    onError: handleError,
  });

  const { mutateAsync: mutateUpdateReservationInformation } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: ReservationUpdateREQ }) => updateReservationApi(id, body),
    onSuccess: () => {
      handleSuccess('update');
    },
  });

  const onUpdateReservationPetInformation = async (id: number, value: ReservationUpdatePetInformationREQ) => {
    await mutateUpdatePetInformation({ id, body: value });
  };

  const onUpdateReservationFuneralInformation = async (id: number, value: ReservationUpdateFunaralInformationREQ) => {
    await mutateUpdateFuneralInformation({ id, body: value });
  };

  const onUpdateReservationInformation = async (id: number, value: ReservationUpdateREQ) => {
    await mutateUpdateReservationInformation({ id, body: value });
  };

  const onUpdateReservationDetailApi = async (id: number, value: ReservationUpdateDetailREQ) => {
    await mutateUpdateReservationDetail({ id, body: value });
  };

  const onSubmit = async (data: ReservationDetailDTO) => {
    setIsLoading(true);
    try {
      const uploadPromises = data.reservationPhoto.attachments?.map((file) => uploadFile({ files: [file] })) || [];

      const uploadResults = await Promise.all(uploadPromises);

      const attachmentIds = uploadResults.flat() as number[];

      const reservationReq = reservationDetailDtoToReq(data, reservationDetailData, attachmentIds);
      await onUpdateReservationDetailApi(reservationId.current, reservationReq);
      onRefresh && onRefresh();
    } catch (error: any) {
      handleError(error);
    } finally {
      setIsLoading(false);
    }
  };

  const onOpenDetail = (id: number) => {
    handleURLPush('id', id.toString());
    reservationId.current = id;
    setIsOpenDetail(true);
  };

  const onCloseDetail = () => {
    reservationId.current = 0;
    handleURLPush('id', undefined);
    reset(reservationDetailFormInitialValues);
    setIsOpenDetail(false);
  };

  const onDeleteReservation = () => {
    mutateDeleteReservation(parseArrayToStringRequest(selectedRowKeys));
  };

  const onRestoreManagerBranch = () => {
    mutateRestoreReservation(parseArrayToStringRequest(selectedRowKeys));
  };

  const onDownloadExcel = () => {
    const dynamicName = [date, EXCEL_FILE_NAME.RESERVATION].filter((i) => !!i).join('_');

    if (!isDeletedList) {
      mutateDownloadExcel({ filter, ids: parseArrayToStringRequest(selectedRowKeys, false), dynamicName });
    }
  };

  useEffect(() => {
    reset(reservationDetailData);
  }, [reservationDetailData, reset]);

  useEffect(() => {
    const detailId = searchParams.get('id');
    if (detailId) {
      reservationId.current = Number(detailId);
      setIsOpenDetail(true);
    }
  }, []);

  useEffect(() => {
    setSelectedReservationKeys(selectedRowKeys as number[]);
  }, [selectedRowKeys]);

  return (
    <>
      <div className='mt-6'>
        <TableFilter total={total}>
          <ReservationTableFilter
            onFilterChange={handleFilterChange}
            onDownloadExcel={onDownloadExcel}
            isDeleted={isDeletedList}
          />
        </TableFilter>
      </div>
      <div className='mt-4'>
        <div className='block lg:hidden'>
          {data.map((item) => (
            <ReservationMobile key={item.id} data={item} onOpenDetail={onOpenDetail} />
          ))}
          <CustomPagination
            paging={{
              total: total,
              pageSize: filter.pageSize,
              pageNumber: filter.pageNumber,
            }}
            onPageNumberChange={handlePageChange}
          />
        </div>

        <div className='small-table hidden lg:block ' id='fixedTable'>
          <CustomTable<FuneralMNGTReservationListDTO>
            columns={
              isDeletedList
                ? ReservationDeletedColumns({
                    onDetail: onOpenDetail,
                  })
                : ReservationColumns({
                    onDetail: onOpenDetail,
                    reservationTimeOptions,
                    petTypeOptions,
                    serviceOptions,
                    onUpdateReservationPetInformation,
                    onUpdateReservationFuneralInformation,
                    onUpdateReservationInformation,
                  })
            }
            data={data}
            key={JSON.stringify({ reservationTimeOptions, petTypeOptions, serviceOptions })}
            showSelect
            showIndex
            onPageNumberChange={handlePageChange}
            onChangeTable={handleTableChange}
            paging={{
              total: total,
              pageSize: filter.pageSize,
              pageNumber: filter.pageNumber,
            }}
            rowClassName={(record) =>
              record['funeralInformation.processStatus'] === DETAIL_FUNERAL_STATUS_OPTIONS.DEADLINE
                ? 'disabled-row disabled-text custom-row-background'
                : 'custom-row-background'
            }
            onSelect={handleSelect}
            onSelectAll={handleSelectAll}
            selectedRowKeys={selectedRowKeys}
            extra={
              !isHiddenExtraButton && (
                <div className='grid grid-cols-2 gap-[12px] sm:grid-cols-4'>
                  {isDeletedList && (
                    <CustomButton
                      onClick={() => {
                        openModalRestore(selectedRowKeys as number[]);
                      }}
                      icon={<UndoOutlined />}>
                      {tReservation('button.restore')}
                    </CustomButton>
                  )}
                  <DeleteButton
                    onClick={() => {
                      openModal(selectedRowKeys as number[]);
                    }}
                  />
                  {!isDeletedList && (
                    <CustomButton onClick={() => handleURLPush('content', FUNERAL_RESERVATION_CONTENT.DELETE_LIST)}>
                      {tReservation('button.delete_list')}
                    </CustomButton>
                  )}
                </div>
              )
            }
            onRow={(record) =>
              ({
                className: 'custom-row-background',
                style: { '--row-color': record.color },
              }) as any
            }
          />
        </div>
      </div>
      <FormProvider {...methods}>
        <form>
          <ReservationModal
            isOpen={isOpenDetail}
            onOk={isDeletedList ? undefined : handleSubmit(onSubmit)}
            onCancel={onCloseDetail}
            onClose={onCloseDetail}
            isLoading={isLoading}
            title={reservationDetailData?.petInfo.petName || ''}>
            <ReservationDetailModal />
          </ReservationModal>
        </form>
      </FormProvider>
      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDeleteReservation} isLoading={isDeleting} />
      <ModalConfirm
        message={tCommon('message.restore')}
        isOpen={isOpenRestore}
        setIsOpen={setIsOpenRestore}
        onConfirm={onRestoreManagerBranch}
        isLoading={isRestoring}
      />
    </>
  );
};

export default ReservationContent;
