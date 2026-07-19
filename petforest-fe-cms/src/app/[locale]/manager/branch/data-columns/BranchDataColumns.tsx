import CustomColorPicker from '@/components/common/CustomColorPicker';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { ManagerBranchDTO } from '@/store/manager/branch/dto/manager-branch.dto';
import { ManagerBranchUpdateREQ } from '@/store/manager/branch/request/manager-branch.request';
import { InputEnum } from '@/utils/enums/input.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { handleBlurUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { useRef } from 'react';
import { useTranslation } from 'react-i18next';

type BranchDataColumnsProps = {
  onUpdateManagerBranch: (id: number, value: ManagerBranchUpdateREQ) => void;
};

export const branchDataColumns = ({ onUpdateManagerBranch }: BranchDataColumnsProps) => {
  const { t: tBranch } = useTranslation('manager_branch');
  const colorRef = useRef<string>();
  const { canEdit } = useMenuPermission(MenuKeys.MANAGER_BRANCH);

  return [
    {
      title: tBranch('table.pnt'),
      dataIndex: 'id',
      width: 160,
      sorter: true,
    },
    {
      title: <EditableHeader title={tBranch('table.branch_name')} />,
      dataIndex: 'name',
      width: 160,
      render: (text: string, record: ManagerBranchDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<ManagerBranchUpdateREQ>(record.id, 'name', text, onUpdateManagerBranch)}
        />
      ),
    },
    {
      title: <EditableHeader title={tBranch('table.color')} />,
      dataIndex: 'color',
      width: 160,
      render: (value: string, record: ManagerBranchDTO) => {
        return (
          <CustomColorPicker
            disabled={!canEdit}
            value={value}
            onChange={handleChangeUpdate<ManagerBranchUpdateREQ>(record.id, 'color', value, onUpdateManagerBranch)}
          />
        );
      },
    },
    {
      title: <EditableHeader title={tBranch('table.road')} />,
      dataIndex: 'roadAddress',
      width: 160,
      render: (text: string, record: ManagerBranchDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<ManagerBranchUpdateREQ>(record.id, 'roadAddress', text, onUpdateManagerBranch)}
        />
      ),
    },
    {
      title: <EditableHeader title={tBranch('table.street')} />,
      dataIndex: 'streetAddress',
      width: 160,
      render: (text: string, record: ManagerBranchDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<ManagerBranchUpdateREQ>(record.id, 'streetAddress', text, onUpdateManagerBranch)}
        />
      ),
    },
    {
      title: <EditableHeader title={tBranch('table.naver_map')} />,
      dataIndex: 'naverMap',
      width: 160,
      render: (text: string, record: ManagerBranchDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<ManagerBranchUpdateREQ>(record.id, 'naverMap', text, onUpdateManagerBranch)}
        />
      ),
    },
    {
      title: <EditableHeader title={tBranch('table.kakao_map')} />,
      dataIndex: 'kakaoMap',
      width: 160,
      render: (text: string, record: ManagerBranchDTO) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<ManagerBranchUpdateREQ>(record.id, 'kakaoMap', text, onUpdateManagerBranch)}
        />
      ),
    },
  ];
};
