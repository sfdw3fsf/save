import CustomIconButton from '@/components/common/CustomIconButton';
import CustomSelect from '@/components/common/CustomSelect';
import CustomFormInput from '@/components/common/form/CustomFormField';
import CustomText from '@/components/common/Text';
import { useFacility } from '@/hook/facility/useFacility';
import { useUser } from '@/hook/user/useUser';
import { FacilityHistoryFormDTO } from '@/store/facility/dto/facility.dto';
import { facilityHistoryKeys, facilityKeys, getDetailFacilityApi, getFacilityTypeApi } from '@/store/facility/facility.api';
import { facilityDetailRespToDto, facilityTypeDTOToOptions } from '@/store/facility/facility.service';
import { COLORS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { InfoCircleOutlined, PaperClipOutlined } from '@ant-design/icons';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { Col, Row } from 'antd';
import { isNil } from 'lodash';
import { useEffect, useMemo } from 'react';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

type FacilityHistoryDetailModalProps = {
  canEditFacilityType: boolean;
};

const FacilityHistoryDetailModal = ({ canEditFacilityType }: FacilityHistoryDetailModalProps) => {
  const { t: tFacility } = useTranslation('facility');
  const { control, setValue, watch } = useFormContext<FacilityHistoryFormDTO>();

  const { managerOptions } = useUser();
  const { companyOptions } = useFacility();

  const companyValue = watch('companyName');
  const facilityId = watch('facilityId') || 0;

  const { data: facilityTypeData } = useQuery({
    queryKey: facilityHistoryKeys.list({ companyName: companyValue }),
    queryFn: () => getFacilityTypeApi(companyValue),
    select: (response) => {
      return response.data.data;
    },
    placeholderData: keepPreviousData,
  });

  const { data: facilityDetailData } = useQuery({
    queryKey: facilityKeys.detail(facilityId),
    queryFn: () => getDetailFacilityApi(facilityId),
    select: (response) => {
      return response?.data && facilityDetailRespToDto(response?.data.data);
    },
    placeholderData: keepPreviousData,
    enabled: !!facilityId,
  });

  const facilityTypeOptions = useMemo(() => {
    if (isNil(facilityTypeData)) return [];
    return facilityTypeDTOToOptions(facilityTypeData);
  }, [facilityTypeData]);

  useEffect(() => {
    if (!facilityDetailData) return;
    setValue('companyManager', facilityDetailData.managerName);
    setValue('contact', facilityDetailData.contact);
    setValue('email', facilityDetailData.email);
    setValue('note', facilityDetailData.note);
  }, [facilityDetailData]);

  return (
    <div className='my-4 overflow-hidden'>
      <Row gutter={[24, 16]}>
        <Col span={12}>
          <CustomFormInput
            control={control}
            label={tFacility('history.inspection_date')}
            name='maintenanceDate'
            type={InputEnum.DATE_PICKER}
          />
        </Col>
        <Col span={12}>
          <CustomFormInput
            control={control}
            label={tFacility('history.repair_date')}
            name='maintenanceScheduleDate'
            type={InputEnum.DATE_PICKER}
          />
        </Col>
        <Col span={12}>
          <CustomFormInput
            control={control}
            name={'companyName'}
            type={InputEnum.OTHER}
            label={tFacility('history.company')}
            renderInput={({ value, onChange }: { value: number; onChange: (value: number) => void }) => (
              <CustomSelect
                value={value}
                disabled={!canEditFacilityType}
                optionsSelect={companyOptions}
                onChange={(selectedValue) => {
                  onChange(selectedValue);
                  setValue('facilityId', null);
                  setValue('companyManager', '');
                  setValue('contact', '');
                  setValue('email', '');
                  setValue('note', '');
                }}
              />
            )}
          />
        </Col>
        <Col span={12}>
          <CustomFormInput
            control={control}
            disabled={!canEditFacilityType}
            label={tFacility('history.facility')}
            name='facilityId'
            type={InputEnum.SELECT}
            optionItems={facilityTypeOptions}
          />
        </Col>
        <Col span={24}>
          <CustomFormInput
            control={control}
            disabled
            label={tFacility('history.company_manager')}
            name='companyManager'
            type={InputEnum.TEXT}
          />
        </Col>
        <Col span={12}>
          <CustomFormInput control={control} disabled label={tFacility('history.contact')} name='contact' type={InputEnum.TEXT} />
        </Col>
        <Col span={12}>
          <CustomFormInput control={control} disabled label={tFacility('history.email')} name='email' type={InputEnum.TEXT} />
        </Col>
        <Col span={24}>
          <CustomFormInput control={control} disabled label={tFacility('history.memo')} name='note' type={InputEnum.TEXT_AREA} />
        </Col>
        <Col span={24}>
          <CustomFormInput
            control={control}
            label={tFacility('history.manager')}
            name='managerId'
            type={InputEnum.SELECT}
            optionItems={managerOptions}
          />
        </Col>
        <Col span={24}>
          <CustomFormInput control={control} label={tFacility('history.title')} name='title' type={InputEnum.TEXT} />
        </Col>
        <Col span={24}>
          <CustomFormInput control={control} label={tFacility('history.content')} name='detail' type={InputEnum.TEXT_AREA} />
        </Col>
        <Col span={24}>
          <CustomFormInput<FacilityHistoryFormDTO>
            control={control}
            buttonUpload={<CustomIconButton icon={<PaperClipOutlined className='!text-neutral-70' />} />}
            name={'maintenanceImages'}
            type={InputEnum.IMAGE}
            bordered={true}
            multiple={true}
            maxCount={3}
            fileType='image'
            displayName={false}
            label={tFacility('history.photo.title')}
            classNameLabel='flex gap-1 items-center'
            placeholder={tFacility('history.photo.placeholder')}
            extraTitle={
              <>
                <InfoCircleOutlined style={{ color: COLORS.grey['grey-mist'] }} />
                <CustomText variant='caption-1' className='!text-grey-grey-light'>
                  {tFacility('history.photo.sub_title')}
                </CustomText>
              </>
            }
          />
        </Col>
      </Row>
    </div>
  );
};

export default FacilityHistoryDetailModal;
