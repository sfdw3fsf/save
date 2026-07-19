'use client';
import AddButton from '@/components/common/AddButton';
import ModalConfirm from '@/components/common/ConfirmModal';
import CustomButton from '@/components/common/CustomButton';
import CustomIconButton from '@/components/common/CustomIconButton';
import { ListData } from '@/components/common/ListData';
import CustomModal from '@/components/common/modals/CustomModal';
import PageTitle from '@/components/common/page/PageTitle';
import CustomText from '@/components/common/Text';
import { useModalConfirm } from '@/hook/useModalConfirm';
import { PntSettingDetailDTO, PntSettingListDTO } from '@/store/funeral/reservation-photo/dto/pnt-setting.dto';
import { PntSettingDetailUpdateREQ } from '@/store/funeral/reservation-photo/request/pnt-setting.request';
import {
  createPntSettingDetailApi,
  deletePntSettingDetailApi,
  getPntSettingDetailApi,
  getPntSettingsListApi,
  getReservationPhotoListApi,
  pntSettingKeys,
  reservationPhotoKeys,
  updatePntSettingDetailApi,
} from '@/store/funeral/reservation-photo/reservation-photo.api';
import {
  pntSettingDetailRespToDto,
  pntSettingListRespToDto,
  reservationPhotoListRespToOption,
} from '@/store/funeral/reservation-photo/reservation-photo.service';
import { ButtonTypeEnum } from '@/utils/enums/button-type.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { orNull } from '@/utils/parser/io.parser';
import { pntSettingInfoSchema, PntSettingInfoType } from '@/utils/schemas/funeral/pnt-setting.schema';
import { DeleteOutlined, PlayCircleOutlined } from '@ant-design/icons';
import { yupResolver } from '@hookform/resolvers/yup';
import { keepPreviousData, useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import Image from 'next/image';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import PNTSetting from './components/PNTSetting';
import { PNTSettingDetailModal } from './components/PNTSettingDetailModal';

export default function Home() {
  const { t: tPNTSetting } = useTranslation('funeral_pnt_setting');

  const queryClient = useQueryClient();

  const { isOpen, setIsOpen, openModal, closeModal } = useModalConfirm();
  const methods = useForm<PntSettingInfoType>({
    defaultValues: pntSettingInfoSchema.getDefault(),
    resolver: yupResolver(pntSettingInfoSchema),
  });

  const [selectedItem, setSelectedItem] = useState<PntSettingDetailDTO | null | undefined>(null);
  // obj = open for edit | undefined = open for create | null = close

  const {
    data: listData = [],
    isFetching,
    isLoading,
  } = useQuery({
    queryKey: pntSettingKeys.lists(),
    queryFn: () => getPntSettingsListApi(),
    select: (response) => {
      return response?.data?.data && response.data.data.map(pntSettingListRespToDto);
    },
    placeholderData: keepPreviousData,
  });

  const { data: reservationPhotoOptions = [] } = useQuery({
    queryKey: reservationPhotoKeys.lists(),
    queryFn: () => getReservationPhotoListApi(),
    select: ({ data }) => {
      return data.data.map(reservationPhotoListRespToOption);
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDetailFetch } = useMutation({
    mutationFn: getPntSettingDetailApi,
    onSuccess: (response) => {
      setSelectedItem(orNull(response?.data?.data && pntSettingDetailRespToDto(response.data.data)));
    },
    onError: () => {
      setSelectedItem(null);
    },
  });

  const { mutate: mutateDetailUpdate } = useMutation({
    mutationFn: ({ id, body }: { id: number; body: PntSettingDetailUpdateREQ }) => updatePntSettingDetailApi(id, body),
    onSuccess: () => {
      if (selectedItem) {
        handleSuccess('update');
      } else if (selectedItem === undefined) {
        handleSuccess('create');
      }
      onRefetch();
    },
    onError: handleError,
  });

  const { mutate: mutateDetailCreate } = useMutation({
    mutationFn: () => createPntSettingDetailApi(),
    onSuccess: (response) => {
      const id = response?.data?.data?.id;
      const request = getRequest(methods.watch());
      onDetailUpdate(id, request);
    },
    onError: handleError,
  });

  const { mutate: mutateDetailDelete, isPending: isDeleting } = useMutation({
    mutationFn: deletePntSettingDetailApi,
    onSuccess: () => {
      closeModal();

      handleSuccess('delete');
      onRefetch();
    },
    onError: handleError,
  });

  const onSubmit = (data: PntSettingInfoType) => {
    if (selectedItem === null) return;

    if (selectedItem) {
      const id = selectedItem.id;
      const request = getRequest(data);
      onDetailUpdate(id, request);
    } else {
      onDetailCreate();
    }
  };

  const onDetailUpdate = (id: number, body: PntSettingDetailUpdateREQ) => {
    mutateDetailUpdate({ id, body });
  };

  const onDetailCreate = async () => {
    mutateDetailCreate();
  };

  const onDetailDelete = () => {
    if (selectedItem === null) return;
    if (selectedItem) {
      const id = selectedItem.id;
      mutateDetailDelete(id);
    }
  };

  const onRefetch = () => {
    queryClient.invalidateQueries({ queryKey: pntSettingKeys.lists() });
    setSelectedItem(null);
  };

  const onPreview = () => {
    window.open(
      `/funeral/pnt-setting/preview?id=${methods.watch('reservationPhotoId')}&ids=${methods.watch('files').join(',')}&room=${methods.watch('name')}`,
      '_blank',
      `noopener,noreferrer`,
    );
  };

  const getRequest = (data: PntSettingInfoType) => {
    let request: PntSettingDetailUpdateREQ = {
      name: data.name,
      reservationPhotoId: data.reservationPhotoId,
    };
    if (data.files?.length > 0) {
      request = { ...request, files: data.files as number[] };
    }
    return request;
  };

  useEffect(() => {
    if (selectedItem === null) return;
    const defaultValues = pntSettingInfoSchema.getDefault();
    methods.reset({
      name: selectedItem?.name ?? defaultValues.name,
      reservationPhotoId: selectedItem?.reservation?.id ?? defaultValues.reservationPhotoId,
      files:
        selectedItem?.reservation?.files
          ?.map((file) => (file.usedInMemorialRoom ? file.id : null))
          ?.filter((file) => file !== null) ?? defaultValues.files,
    });
  }, [selectedItem]);

  return (
    <div>
      <PageTitle title={tPNTSetting('title')} actions={<AddButton onClick={() => setSelectedItem(undefined)} />} />
      <div className='mt-4'>
        {listData.length > 0 ? (
          <ListData<PntSettingListDTO>
            data={listData}
            renderItem={(item: PntSettingListDTO, index) => (
              <PNTSetting
                key={item.id}
                displayName={item.name}
                onClick={() => mutateDetailFetch(item.id)}
                petName={item.reservation?.petName}
                selectedImage={item.reservation?.files?.[0]?.url}
                guardianName={item.reservation?.guardianName}
                index={index}
              />
            )}
            keyExtractor={(item) => item.id}
            numberOfColumns={5}
            styleContainer='flex flex-wrap'
          />
        ) : (
          !isLoading &&
          !isFetching && (
            <div className='flex items-center flex-col gap-2'>
              <Image src='/images/noData.svg' alt='pntSettingNoData' width={120} height={120} />
              <CustomText variant='label-1-normal' className='!text-neutral-80'>
                {tPNTSetting('no_register')}
              </CustomText>
            </div>
          )
        )}
      </div>

      <FormProvider {...methods}>
        <CustomModal
          key={JSON.stringify(selectedItem)}
          isOpen={selectedItem !== null}
          onOk={methods.handleSubmit(onSubmit)}
          onClose={() => setSelectedItem(null)}
          onCancel={() => setSelectedItem(null)}
          extraFooter={
            <>
              {!!methods.watch('reservationPhotoId') && (
                <CustomButton
                  type={ButtonTypeEnum.GHOST}
                  onClick={() => onPreview()}
                  disabled={!methods.watch('files')?.length}
                  icon={<PlayCircleOutlined className='!text-primary-1000' />}>
                  {tPNTSetting('detail.preview')}
                </CustomButton>
              )}
              {!!selectedItem && (
                <CustomIconButton
                  className='!bg-error-10 !border shrink-0 !border-error-500'
                  icon={<DeleteOutlined className='!text-error-500' />}
                  onClick={() => openModal()}
                />
              )}
            </>
          }
          title={tPNTSetting('title')}>
          <PNTSettingDetailModal reservationPhotoOptions={reservationPhotoOptions} />
        </CustomModal>
      </FormProvider>

      <ModalConfirm isOpen={isOpen} setIsOpen={setIsOpen} onConfirm={onDetailDelete} isLoading={isDeleting} />
    </div>
  );
}
