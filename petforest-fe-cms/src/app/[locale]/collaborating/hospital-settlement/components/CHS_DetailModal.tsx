import CustomButton from '@/components/common/CustomButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { useHospital } from '@/hook/collaborating-hospital/useHospital';
import { gethospitalSettlementPdfApi } from '@/store/collaborating-hospital/collaborating-hospital.api';
import { HospitalSettlementSendEmailREQ } from '@/store/collaborating-hospital/request/collaborating-hospital.request';
import { ButtonTypeEnum } from '@/utils/enums/button-type.enum';
import { InputEnum } from '@/utils/enums/input.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { parseTimestampToDate } from '@/utils/parser/date-time.parser';
import { DOWNLOAD_FUNERAL_STATEMENT } from '@/utils/types/collaborating/hospital-settlement.type';
import { downloadExcel } from '@/utils/utils/download-excel.util';
import { VerticalAlignBottomOutlined } from '@ant-design/icons';
import { useMutation } from '@tanstack/react-query';
import { useMemo } from 'react';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

export const CHS_DetailModal = () => {
  const { t: tSettlement } = useTranslation('collaborating_settlement');
  const { t: tCommon } = useTranslation('common');
  const { control, watch } = useFormContext<DOWNLOAD_FUNERAL_STATEMENT>();

  const formValues = watch();

  const { hospitalOptions } = useHospital(formValues.funeralDate[0], formValues.funeralDate[1]);

  const isFormFilled = useMemo(() => {
    return formValues.funeralDate && formValues.hospitalId;
  }, [formValues]);

  const { mutate: mutateDownloadPdf, isPending: isDownloading } = useMutation({
    mutationFn: ({ id, params }: { id: number; params: HospitalSettlementSendEmailREQ }) =>
      gethospitalSettlementPdfApi(id, params),
    onSuccess: (resp) => {
      const fileName =
        hospitalOptions.find((item) => item.value === formValues.hospitalId)?.label +
        '_' +
        parseTimestampToDate(formValues.funeralDate[0].toString()) +
        '_' +
        parseTimestampToDate(formValues.funeralDate[1].toString());
      downloadExcel(resp, fileName, 'pdf');
    },
    onError: handleError,
  });

  const onDownloadPdf = () => {
    if (isFormFilled && formValues.hospitalId) {
      const params: HospitalSettlementSendEmailREQ = { startDate: formValues.funeralDate[0], endDate: formValues.funeralDate[1] };
      mutateDownloadPdf({ id: formValues.hospitalId, params });
    }
  };

  return (
    <div className='flex flex-col py-4 gap-6'>
      <CustomFormInput
        name='hospitalId'
        type={InputEnum.SELECT_SEARCH}
        placeholder={tCommon('place_holder.select')}
        control={control}
        optionItems={hospitalOptions}
        label={tSettlement('detail.hospital')}
        showSearch
      />
      <CustomFormInput
        name='funeralDate'
        type={InputEnum.RANGE_PICKER}
        control={control}
        label={tSettlement('detail.funeral_date')}
      />
      {!isFormFilled && (
        <CustomText variant='label-1-normal' className='!text-neutral-70'>
          {tSettlement('detail.select_hospital_and_funeral_date')}
        </CustomText>
      )}

      {isFormFilled && (
        <CustomButton
          loading={isDownloading}
          onClick={onDownloadPdf}
          className='!h-[38px]'
          type={ButtonTypeEnum.DEFAULT}
          icon={<VerticalAlignBottomOutlined />}>
          {tSettlement('detail.download_funeral_statement')}
        </CustomButton>
      )}
    </div>
  );
};
