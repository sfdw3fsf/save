import CustomFormInput from '@/components/common/form/CustomFormField';
import { FacilityDetailDTO } from '@/store/facility/dto/facility.dto';
import { InputEnum } from '@/utils/enums/input.enum';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

const FacilityDetailInformation = () => {
  const { t: tFacility } = useTranslation('facility');
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');
  const { control } = useFormContext<FacilityDetailDTO>();

  return (
    <div className='mt-4'>
      <div className='grid grid-cols-1 md:grid-cols-2 gap-6'>
        <div>
          <CustomFormInput control={control} name='companyName' type={InputEnum.TEXT} label={tCommon('table.facility.company')} />
        </div>
        <div>
          <CustomFormInput
            control={control}
            name='managerName'
            type={InputEnum.TEXT}
            label={tFacility('table.company_manager')}
          />
        </div>
        <div>
          <CustomFormInput control={control} name='contact' type={InputEnum.TEXT} label={tFacility('table.contact')} />
        </div>
        <div>
          <CustomFormInput<FacilityDetailDTO>
            control={control}
            name='email'
            type={InputEnum.TEXT}
            label={tFacility('table.email')}
          />
        </div>
        <div className='col-span-1 md:col-span-2'>
          <CustomFormInput
            control={control}
            name='note'
            type={InputEnum.TEXT_AREA}
            isResize={false}
            classNameInput='!h-[120px]'
            label={tCommon('memo')}
          />
        </div>
      </div>
    </div>
  );
};

export default FacilityDetailInformation;
