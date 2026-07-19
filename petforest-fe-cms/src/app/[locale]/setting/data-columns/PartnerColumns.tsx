import CustomIconButton from '@/components/common/CustomIconButton';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { SettingPartnerDTO } from '@/store/setting/partner/dto/setting-partner.dto';
import { SettingPartnerUpdateREQ } from '@/store/setting/partner/request/setting-partner.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type PartnerColumnsProps = {
  onDetail: (id: number) => void;
  onUpdateSettingPartner: (id: number, value: SettingPartnerUpdateREQ) => void;
};

export const PartnerColumns = ({
  onDetail,
  onUpdateSettingPartner,
}: PartnerColumnsProps): TableColumnsType<SettingPartnerDTO> => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  return [
    {
      title: <EditableHeader title={tSetting('partner.table.name')} />,
      dataIndex: 'name',
      width: 200,
      render: (text: string, record: SettingPartnerDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          onBlur={handleBlurUpdate<SettingPartnerUpdateREQ>(record.id, 'name', text, onUpdateSettingPartner)}
          type={InputEnum.TEXT}
          defaultValue={text}
        />
      ),
      sorter: true,
    },
    {
      title: tCommon('table.memo'),
      dataIndex: 'note',
      sorter: true,
    },
    {
      title: tCommon('table.view'),
      className: 'ant-table-view-column',
      key: 'look',
      width: 50,
      align: 'center',
      fixed: 'right',
      render: (_, record, index) => (
        <CustomIconButton
          onClick={() => {
            onDetail(record.id);
          }}
          icon={<Image src={ICONS.LOOK} alt='Look' width={24} height={24} />}
        />
      ),
    },
  ];
};
