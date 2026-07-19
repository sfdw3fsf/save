import CustomIconButton from '@/components/common/CustomIconButton';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { SettingCorrespondentDTO } from '@/store/setting/correspondent/dto/setting-correspondent.dto';
import { SettingCorrespondentUpdateREQ } from '@/store/setting/correspondent/request/setting-correspondent.request';
import { ICONS } from '@/theme';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleBlurUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type CorrespondentColumnsProps = {
  onDetail: (index: number) => void;
  onUpdateSettingCorrespondent: (id: number, body: SettingCorrespondentUpdateREQ) => void;
};

export const CorrespondentColumns = ({
  onDetail,
  onUpdateSettingCorrespondent,
}: CorrespondentColumnsProps): TableColumnsType<SettingCorrespondentDTO> => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  return [
    {
      title: <EditableHeader title={tSetting('correspondent.table.company_name')} />,
      dataIndex: 'name',
      width: 100,
      sorter: true,
      render: (text: string, record: SettingCorrespondentDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingCorrespondentUpdateREQ>(record.id, 'name', text, onUpdateSettingCorrespondent)}
        />
      ),
    },
    {
      title: <EditableHeader title={tSetting('correspondent.table.company_manager')} />,
      dataIndex: 'managerName',
      width: 100,
      sorter: true,
      render: (text: string, record: SettingCorrespondentDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingCorrespondentUpdateREQ>(record.id, 'managerName', text, onUpdateSettingCorrespondent)}
        />
      ),
    },
    {
      title: <EditableHeader title={tSetting('correspondent.table.contact')} />,
      dataIndex: 'contact',
      sorter: true,
      width: 100,
      render: (text: string, record: SettingCorrespondentDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingCorrespondentUpdateREQ>(record.id, 'contact', text, onUpdateSettingCorrespondent)}
        />
      ),
    },
    {
      title: <EditableHeader title={tSetting('correspondent.table.deposit_account')} />,
      dataIndex: 'depositAccount',
      sorter: true,
      render: (text: string, record: SettingCorrespondentDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingCorrespondentUpdateREQ>(
            record.id,
            'depositAccount',
            text,
            onUpdateSettingCorrespondent,
          )}
        />
      ),
      width: 100,
    },
    {
      title: <EditableHeader title={tSetting('correspondent.table.business_registration_number')} />,
      dataIndex: 'businessRegistration',
      render: (text: string, record: SettingCorrespondentDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<SettingCorrespondentUpdateREQ>(
            record.id,
            'businessRegistration',
            text,
            onUpdateSettingCorrespondent,
          )}
        />
      ),
      width: 100,
    },
    {
      title: tCommon('table.memo'),
      dataIndex: 'note',
      className: 'whitespace-nowrap',
      sorter: true,
      width: 100,
    },
    {
      title: tCommon('table.view'),
      className: 'ant-table-view-column',
      key: 'look',
      width: 50,
      fixed: 'right',
      align: 'center',
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
