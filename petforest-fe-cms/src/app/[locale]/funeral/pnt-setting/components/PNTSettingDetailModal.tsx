import CustomFormInput from '@/components/common/form/CustomFormField';
import FormMessage from '@/components/common/form/FormMessage';
import { ListData } from '@/components/common/ListData';
import CustomText from '@/components/common/Text';
import { ReservationPhotoFile } from '@/store/funeral/reservation-photo/dto/reservation-photo.dto';
import { getReservationPhotoDetailApi, reservationPhotoKeys } from '@/store/funeral/reservation-photo/reservation-photo.api';
import { reservationPhotoDetailRespToDto } from '@/store/funeral/reservation-photo/reservation-photo.service';
import { ROOM_TYPE } from '@/utils/enums/funeral/funeral-mngt.enums';
import { InputEnum } from '@/utils/enums/input.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { PntSettingInfoType } from '@/utils/schemas/funeral/pnt-setting.schema';
import { OptionItem } from '@/utils/types/option.type';
import { useQuery } from '@tanstack/react-query';
import { isNil } from 'lodash';
import { useEffect, useMemo } from 'react';
import { useController, useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { PhotoItem } from './PhotoDetailItem';

type PNTSettingDetailModalProps = {
  reservationPhotoOptions: OptionItem<number>[];
};

export const PNTSettingDetailModal = ({ reservationPhotoOptions }: PNTSettingDetailModalProps) => {
  const { t: tPNTSetting } = useTranslation('funeral_pnt_setting');
  const { t: tEnum } = useTranslation('enum');

  const { control, watch, formState } = useFormContext<PntSettingInfoType>();
  const { field: selectedFiles } = useController({ name: 'files', control });
  const reservationPhotoId = watch('reservationPhotoId');

  const roomTypeOptions = helperOption('reservation.room_type', ROOM_TYPE, tEnum);

  const {
    data: reservationPhotoDetail,
    isFetching: isFetchingReservationPhotoDetail,
    isLoading: isLoadingReservationPhotoDetail,
  } = useQuery({
    queryKey: reservationPhotoKeys.detail(reservationPhotoId),
    queryFn: () => getReservationPhotoDetailApi(reservationPhotoId),
    select: (response) => {
      return response?.data?.data && reservationPhotoDetailRespToDto(response.data.data);
    },
    enabled: !!reservationPhotoId,
  });

  const fileOptions = useMemo(
    () => (!isNil(reservationPhotoId) && reservationPhotoDetail?.files) || [],
    [reservationPhotoId, reservationPhotoDetail],
  );

  const onSelectedFilesChange = (id: number) => {
    const newFiles = [...selectedFiles.value];
    const index = newFiles.indexOf(id);
    if (index !== -1) {
      newFiles.splice(index, 1);
    } else {
      newFiles.push(id);
    }
    selectedFiles.onChange(newFiles);
  };

  useEffect(() => {
    if (!formState.isDirty) return;
    selectedFiles.onChange([]);
  }, [reservationPhotoId]);

  return (
    <form id='pnt-setting-info-form' className='py-[8px] flex flex-col gap-4'>
      <div>
        <CustomFormInput
          name='name'
          control={control}
          type={InputEnum.SELECT}
          label={tPNTSetting('detail.display.title')}
          optionItems={roomTypeOptions}
        />
      </div>
      <div>
        <CustomFormInput
          name='reservationPhotoId'
          control={control}
          type={InputEnum.SELECT}
          optionItems={reservationPhotoOptions}
          label={tPNTSetting('detail.reservation_matching.title')}
          placeholder={tPNTSetting('detail.reservation_matching.placeholder')}
        />
      </div>

      <div>
        <CustomText variant='label-1-normal' className='!font-semibold'>
          {tPNTSetting('detail.select_image.title')}
        </CustomText>
        <div className='mt-[10px]'>
          <FormMessage className='mb-1' variant='error' translateKey={formState?.errors?.files?.message as any} />
          {fileOptions.length > 0 ? (
            <ListData<ReservationPhotoFile>
              data={fileOptions}
              renderItem={(item: ReservationPhotoFile, index) => (
                <PhotoItem
                  key={index}
                  item={item.url}
                  index={item.id}
                  onClickCheckbox={onSelectedFilesChange}
                  checked={selectedFiles.value?.includes(item.id)}
                />
              )}
              keyExtractor={(item) => item.id}
              gap={12}
              numberOfColumns={3}
            />
          ) : (
            !isLoadingReservationPhotoDetail &&
            !isFetchingReservationPhotoDetail && (
              <CustomText variant='label-1-normal'>{tPNTSetting('detail.select_image.no_image')}</CustomText>
            )
          )}
        </div>
      </div>
    </form>
  );
};
