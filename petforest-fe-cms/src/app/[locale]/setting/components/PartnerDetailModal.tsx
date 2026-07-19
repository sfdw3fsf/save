import CustomFormInput from '@/components/common/form/CustomFormField';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { SettingPartnerDTO } from '@/store/setting/partner/dto/setting-partner.dto';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { useFormContext } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

export const PartnerDetailModal = () => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { control } = useFormContext<SettingPartnerDTO>();
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  return (
    <div className='mt-4 flex gap-3 flex-col'>
      <CustomFormInput<SettingPartnerDTO>
        control={control}
        readOnly={!canEdit}
        name='name'
        type={InputEnum.TEXT}
        label={tSetting('partner.table.name')}
      />
      <CustomFormInput<SettingPartnerDTO>
        control={control}
        readOnly={!canEdit}
        name='note'
        type={InputEnum.TEXT_AREA}
        label={tCommon('memo')}
      />
    </div>
  );
};
