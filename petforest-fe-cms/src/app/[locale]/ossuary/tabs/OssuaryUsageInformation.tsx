import CustomButton from '@/components/common/CustomButton';
import CustomFormInput from '@/components/common/form/CustomFormField';
import { useOssuary } from '@/hook/ossuary/useOssuary';
import useDaumnAddress from '@/hook/useDaumnAddress';
import useResize from '@/hook/useResize';
import { OssuaryPlaceListDTO, UsageInformationFormDTO } from '@/store/ossuary/dto/ossuary.dto';
import { getOssuaryInformationPdfApi, getOssuaryRoomDetailApi, ossuaryKeys } from '@/store/ossuary/ossuary.api';
import { ossuaryPlaceCatergoryToOptions } from '@/store/ossuary/ossuary.service';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { downloadExcel } from '@/utils/utils/download-excel.util';
import { DownloadOutlined } from '@ant-design/icons';
import { keepPreviousData, useMutation, useQuery } from '@tanstack/react-query';
import { Col, Row } from 'antd';
import { isEmpty, isNil } from 'lodash';
import Image from 'next/image';
import { useMemo } from 'react';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

const OssuaryUsageInformation = ({ id }: { id: number }) => {
  const { t: tOssuary } = useTranslation('ossuary');
  const { t: tCommon } = useTranslation('common');
  const { control, getValues, watch, setValue } = useFormContext<UsageInformationFormDTO>();
  const { isResponsive } = useResize();
  const { ossuaryRoomNameOptions } = useOssuary<OssuaryPlaceListDTO>({});
  const { openPostcode } = useDaumnAddress();

  const reservationNumber = getValues('reservationNumber');
  const roomValue = watch('room');

  const { data: ossuaryRoomDetailData } = useQuery({
    queryKey: ossuaryKeys.list({ name: roomValue }),
    queryFn: () => getOssuaryRoomDetailApi(roomValue),
    select: (response) => {
      return response?.data.data;
    },
    placeholderData: keepPreviousData,
  });

  const { mutate: mutateDownloadPdf } = useMutation({
    mutationFn: () => getOssuaryInformationPdfApi(id),
    onSuccess: (resp) => {
      downloadExcel(resp, 'ossuary', 'pdf');
    },
    onError: handleError,
  });

  const onDownloadPdf = () => {
    mutateDownloadPdf();
  };

  const ossuaryPlaceCatergoryOptions = useMemo(() => {
    if (isNil(ossuaryRoomDetailData)) return [];
    return ossuaryPlaceCatergoryToOptions(ossuaryRoomDetailData);
  }, [ossuaryRoomDetailData]);

  const colSpan = useMemo(() => {
    return isResponsive ? 24 : 12;
  }, [isResponsive]);

  const onOpenPostCode = async () => {
    const data = await openPostcode();
    const address = data.address || '';
    setValue('address', address);
  };

  return (
    <div className='py-4 overflow-hidden'>
      <Row gutter={[24, 16]}>
        <Col span={24}>
          <CustomFormInput
            name='receiptNumber'
            control={control}
            disabled
            label={tOssuary('information.number')}
            type={InputEnum.TEXT}
          />
        </Col>
        {!isEmpty(reservationNumber) && (
          <Col span={24}>
            <CustomFormInput
              name='reservationNumber'
              control={control}
              disabled
              label={tOssuary('table.funeral_number')}
              type={InputEnum.TEXT}
            />
          </Col>
        )}

        <Col span={colSpan}>
          <CustomFormInput
            name='room'
            control={control}
            label={tOssuary('table.category')}
            requiredLabel={true}
            optionItems={ossuaryRoomNameOptions}
            type={InputEnum.SELECT}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            name='placeId'
            optionItems={ossuaryPlaceCatergoryOptions}
            control={control}
            requiredLabel={true}
            label={tOssuary('table.room_number')}
            type={InputEnum.SELECT}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            name='registrationDate'
            control={control}
            requiredLabel={true}
            label={tOssuary('table.entry_date')}
            type={InputEnum.DATE_PICKER}
            allowClear={false}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            name='expirationDate'
            control={control}
            label={tOssuary('table.expiration_date')}
            type={InputEnum.DATE_PICKER}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            name='childName'
            control={control}
            requiredLabel={true}
            placeholder=''
            disabled={!isEmpty(reservationNumber)}
            label={tOssuary('table.pet_name')}
            type={InputEnum.TEXT}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            name='guardianName'
            control={control}
            disabled={!isEmpty(reservationNumber)}
            requiredLabel={true}
            placeholder=''
            label={tOssuary('table.guardian')}
            type={InputEnum.TEXT}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            name='contact'
            disabled={!isEmpty(reservationNumber)}
            control={control}
            placeholder=''
            label={tOssuary('table.contact')}
            type={InputEnum.TEXT}
          />
        </Col>
        <Col span={colSpan}>
          <CustomFormInput
            name='emergencyContact'
            control={control}
            placeholder=''
            label={tOssuary('table.emergency_contact')}
            type={InputEnum.TEXT}
          />
        </Col>
        <Col span={24}>
          <CustomFormInput
            name='address'
            suffix={<Image src={ICONS.LOCATION} alt='Look' width={20} height={20} />}
            control={control}
            label={tCommon('address.title')}
            placeholder={tCommon('address.placeholder_search')}
            type={InputEnum.TEXT}
            onClick={onOpenPostCode}
            readOnly
          />
        </Col>
        <Col span={24}>
          <CustomFormInput
            name='addressDetail'
            control={control}
            placeholder={tCommon('address.placeholder_detail')}
            type={InputEnum.TEXT}
          />
        </Col>
        <Col span={24}>
          <CustomFormInput name='note' control={control} label={tCommon('memo')} type={InputEnum.TEXT_AREA} />
        </Col>
        {id !== 0 && (
          <Col span={24}>
            <CustomButton onClick={onDownloadPdf} type={'default'} icon={<DownloadOutlined />}>
              {tOssuary('detail.download_certificate')}
            </CustomButton>
          </Col>
        )}
      </Row>
    </div>
  );
};

export default OssuaryUsageInformation;
