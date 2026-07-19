import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import DragHandle from '@/components/common/table/DragHandle';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { SettingHospitalSettlementMethodDTO } from '@/store/setting/hospital-settlement-method/dto/setting-hospital-settlement-method.dto';
import {
  SettingHospitalSettlementMethodUpdateREQ,
  SettingHospitalSettlementMethodUpdateStatusREQ,
} from '@/store/setting/hospital-settlement-method/request/setting-hospital-settlement-method.request';
import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { helperOption } from '@/utils/helpers/common.helper';
import { handleBlurUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { useTranslation } from 'react-i18next';

type HospitalSettlementMethodDataColumnsProps = {
  onUpdateSettingHospitalSettlementMethod: (id: number, value: SettingHospitalSettlementMethodUpdateREQ) => void;
  onUpdateStatusSettingHospitalSettlementMethod: (id: number, value: SettingHospitalSettlementMethodUpdateStatusREQ) => void;
};

export const HospitalSettleMethodColumns = ({
  onUpdateSettingHospitalSettlementMethod,
  onUpdateStatusSettingHospitalSettlementMethod,
}: HospitalSettlementMethodDataColumnsProps): TableColumnsType<SettingHospitalSettlementMethodDTO> => {
  const { t: tSetting } = useTranslation('setting');
  const { t: tCommon } = useTranslation('common');
  const { t: tEnum } = useTranslation('enum');
  const { canEdit } = useMenuPermission(MenuKeys.SETTING);

  const STATE_FILTER = helperOption('active_status', ACTIVE_STATE, tEnum);

  return [
    {
      title: <EditableHeader title={tSetting('hospital_settle_method.table.method')} />,
      dataIndex: 'name',
      width: 400,
      render: (text: string, record: SettingHospitalSettlementMethodDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          onBlur={handleBlurUpdate<SettingHospitalSettlementMethodUpdateREQ>(
            record.id,
            'name',
            text,
            onUpdateSettingHospitalSettlementMethod,
          )}
          type={InputEnum.TEXT}
          defaultValue={text}
        />
      ),
    },
    {
      title: <EditableHeader title={tSetting('hospital_settle_method.table.state')} />,
      dataIndex: 'status',
      width: 100,
      render: (value: ACTIVE_STATE, record: SettingHospitalSettlementMethodDTO) => {
        return (
          <CustomSelect
            disabled={!canEdit}
            variant='borderless'
            options={STATE_FILTER}
            defaultValue={value}
            onChange={handleChangeUpdate<SettingHospitalSettlementMethodUpdateREQ>(
              record.id,
              'status',
              value,
              onUpdateStatusSettingHospitalSettlementMethod,
            )}
          />
        );
      },
    },
    canEdit &&
      ({
        title: tCommon('table.sort'),
        key: 'array',
        className: 'ant-table-sort-column',
        width: 50,
        align: 'center',
        render: () => <DragHandle />,
      } as any),
  ];
};
