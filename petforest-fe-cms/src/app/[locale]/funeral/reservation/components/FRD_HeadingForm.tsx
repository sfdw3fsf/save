import CustomButton from '@/components/common/CustomButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import { useUser } from '@/hook/user/useUser';
import { ReservationDetailDTO } from '@/store/funeral/reservation/dto/reservation.dto';
import { getReservationPdfApi } from '@/store/funeral/reservation/reservation.api';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { InputEnum } from '@/utils/enums/input.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { downloadExcel } from '@/utils/utils/download-excel.util';
import { DownloadOutlined } from '@ant-design/icons';
import { useMutation } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { useFormContext, useWatch } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

export const FRD_HeadingForm = () => {
  const { t: tReservation } = useTranslation('funeral_reservation');
  const { control, watch } = useFormContext<ReservationDetailDTO>();
  const { managerOptions } = useUser();

  const reservationId = useWatch({
    control,
    name: 'id',
  });
  const funeralDate = watch('funeralInfo.funeralDate');
  const petName = watch('petInfo.petName');
  const funeralNumber = watch('code');
  const funeralDateFormat = dayjs(funeralDate).format(DATE_FORMAT.DATE.HYPHEN);

  const { mutate: mutateDownloadPdf, isPending: isDownloading } = useMutation({
    mutationFn: () => getReservationPdfApi(reservationId),
    onSuccess: (resp) => {
      downloadExcel(resp, `${funeralDateFormat}_${petName}_${funeralNumber}`, 'pdf');
    },
    onError: handleError,
  });

  const onDownloadPdf = () => {
    mutateDownloadPdf();
  };

  return (
    <div className='flex flex-wrap gap-y-4 gap-x-[10px] lg:gap-x-6 mb-3'>
      <CustomFormInput<ReservationDetailDTO>
        control={control}
        name={'manager'}
        type={InputEnum.SELECT}
        optionItems={managerOptions}
        label={tReservation('detail.manager')}
        className='flex-1 min-w-10'
      />
      <CustomFormInput
        control={control}
        name={'branch'}
        disabled
        type={InputEnum.TEXT}
        label={tReservation('detail.branch')}
        className='flex-1 min-w-10'
      />
      {
        <CustomButton
          type='text'
          disabled={!!!funeralNumber}
          loading={isDownloading || !!!funeralNumber}
          onClick={onDownloadPdf}
          icon={<DownloadOutlined className='!text-blue-600' />}
          className='!bg-blue-50 !text-blue-600 !border-blue-300 !h-[38px] self-end flex-1'>
          {tReservation('detail.download_certificate')}
        </CustomButton>
      }
    </div>
  );
};
