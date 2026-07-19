import CustomFormInput from '@/components/common/form/CustomFormField';
import { InputEnum } from '@/utils/enums/input.enum';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

export const CorrespondentDetailModal = () => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { control } = useFormContext();

  return (
    <div className='mt-4 flex gap-3 flex-col'>
      <CustomFormInput control={control} name='name' type={InputEnum.TEXT} label={tSetting('correspondent.table.company_name')} />
      <CustomFormInput
        control={control}
        name='managerName'
        type={InputEnum.TEXT}
        label={tSetting('correspondent.table.company_manager')}
      />
      <CustomFormInput control={control} name='contact' type={InputEnum.TEXT} label={tSetting('correspondent.table.contact')} />
      <CustomFormInput
        control={control}
        name='depositAccount'
        type={InputEnum.TEXT}
        label={tSetting('correspondent.table.deposit_account')}
      />
      <CustomFormInput
        control={control}
        name='businessRegistration'
        type={InputEnum.TEXT}
        label={tSetting('correspondent.table.business_registration_number')}
      />
      <CustomFormInput control={control} name='note' type={InputEnum.TEXT_AREA} label={tCommon('memo')} />
    </div>
  );
};
