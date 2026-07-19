import CustomPhoneNumberInput from '@/components/common/CustomPhoneNumberInput';
import CustomSelect from '@/components/common/CustomSelect';
import { CustomTextInput } from '@/components/common/CustomTextInput';
import EditableHeader from '@/components/common/table/EditableHeader';
import { useMenuPermission } from '@/hook/auth/useMenuPermission';
import { ManagerUserDTO } from '@/store/manager/user/dto/manager-user.dto';
import { ManagerUserUpdatePermissionREQ, ManagerUserUpdateREQ } from '@/store/manager/user/request/manager-user.request';
import { MY_ROUTE } from '@/utils/constants/route.constant';
import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { InputEnum } from '@/utils/enums/input.enum';
import { USER_PERMISSION, USER_POSITION, USER_RECEIVE_RESERVATION_PERMISSION } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { helperFilter, helperOption } from '@/utils/helpers/common.helper';
import { helperMainBranchOptions } from '@/utils/helpers/mananger/user.helper';
import { FilterOptions } from '@/utils/types/option.type';
import { handleBlurUpdate, handleChangeUpdate } from '@/utils/utils/inline-update.util';
import { TableColumnsType } from 'antd';
import { ColumnGroupType } from 'antd/es/table';
import { ColumnFilterItem } from 'antd/es/table/interface';
import { ColumnType } from 'antd/lib/table';
import clsx from 'clsx';
import { debounce } from 'lodash';
import { useCallback } from 'react';
import { useTranslation } from 'react-i18next';

type UserDataColumnsProps = {
  branchOptions: FilterOptions;
  onUpdateManagerUser: (id: number, value: ManagerUserUpdateREQ) => void;
  onUpdateManagerUserPermission: (id: number, value: ManagerUserUpdatePermissionREQ) => void;
  onUpdateManagerUserBranches: (id: number, ids: number[]) => void;
  onUpdateManagerUserMainBranch: (id: number, branchId: number) => void;
};
export const UserDataColumns = ({
  branchOptions,
  onUpdateManagerUser,
  onUpdateManagerUserPermission,
  onUpdateManagerUserBranches,
  onUpdateManagerUserMainBranch,
}: UserDataColumnsProps): TableColumnsType<ManagerUserDTO> => {
  const { t: tUser } = useTranslation('manager_user');
  const { t: tEnum } = useTranslation('enum');
  const STATE_FILTER = helperOption('active_status', ACTIVE_STATE, tEnum);
  const { canEdit } = useMenuPermission(MenuKeys.MANAGER_USER);

  const debouncedUpdate = useCallback(
    debounce((id, value) => {
      onUpdateManagerUserBranches(id, value);
    }, 1000),
    [],
  );

  const menuColumns: (ColumnGroupType<ManagerUserDTO> | ColumnType<ManagerUserDTO>)[] = Object.entries(MY_ROUTE)
    .filter(([_, value]) => ![MY_ROUTE.NO_PERMISSION, MY_ROUTE.AUTH].includes(value as any))
    .map(([key, value]) => {
      if (typeof value === 'string') {
        return {
          title: <EditableHeader title={tEnum(`menu.${key}` as any)} />,
          dataIndex: key,
          width: 160,
          className: 'ant-column-center border-left-column',
          filters: helperFilter(
            value === MY_ROUTE.RECEIVE_RESERVATION ? 'user_receive_reservation_permission' : 'user_permission',
            value === MY_ROUTE.RECEIVE_RESERVATION ? USER_RECEIVE_RESERVATION_PERMISSION : USER_PERMISSION,
            tEnum,
          ),
          render: (values: { id: number; permission: USER_PERMISSION }, record: any) => {
            return (
              <CustomSelect
                disabled={!canEdit}
                variant='borderless'
                options={helperOption(
                  value === MY_ROUTE.RECEIVE_RESERVATION ? 'user_receive_reservation_permission' : 'user_permission',
                  value === MY_ROUTE.RECEIVE_RESERVATION ? USER_RECEIVE_RESERVATION_PERMISSION : USER_PERMISSION,
                  tEnum,
                )}
                value={values?.permission}
                onChange={(newValue) => {
                  onUpdateManagerUserPermission(record.id, { menuId: values.id, permission: newValue });
                }}
              />
            );
          },
          onCell: () => ({
            className: 'border-left-column',
          }),
        };
      } else if (typeof value === 'object') {
        return {
          title: <EditableHeader title={tEnum(`menu.${key}` as any)} />,
          className: 'ant-column-center border-left-column',
          children: Object.keys(value)
            .map((item, childIndex) => {
              return {
                title: tEnum(`menu.${item}` as any),
                dataIndex: item,
                width: 160,
                filters: helperFilter('user_permission', USER_PERMISSION, tEnum),
                className: 'ant-column-center',
                render: (value: { id: number; permission: USER_PERMISSION }, record: any) => (
                  <CustomSelect
                    disabled={!canEdit}
                    variant='borderless'
                    options={helperOption('user_permission', USER_PERMISSION, tEnum)}
                    value={value?.permission}
                    onChange={(newValue) => {
                      onUpdateManagerUserPermission(record.id, { menuId: value.id, permission: newValue });
                    }}
                  />
                ),
                onCell: () => ({
                  className: childIndex === 1 ? 'border-left-column' : '',
                }),
                onHeaderCell: () => ({
                  className: childIndex === 1 ? 'border-left-column' : '',
                }),
              };
            })
            .filter((sub) => sub.dataIndex !== 'self' && Object.keys(MenuKeys).includes(sub.dataIndex)),
        };
      }
      return null;
    })
    .filter(Boolean) as (ColumnGroupType<ManagerUserDTO> | ColumnType<ManagerUserDTO>)[];

  return [
    {
      title: <EditableHeader title={tUser('table.id')} />,
      dataIndex: 'username',
      width: 160,
      sorter: true,
      shouldCellUpdate: () => false,
      render: (text: string, record) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<ManagerUserUpdateREQ>(record.id, 'username', text, onUpdateManagerUser)}
        />
      ),
    },
    {
      title: <EditableHeader title={tUser('table.password')} />,
      dataIndex: 'password',
      width: 160,
      render: (text: string, record) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<ManagerUserUpdateREQ>(record.id, 'password', text, onUpdateManagerUser)}
        />
      ),
    },
    {
      title: <EditableHeader title={tUser('table.name')} />,
      dataIndex: 'fullName',
      width: 160,
      sorter: true,
      render: (text: string, record) => (
        <CustomTextInput
          readOnly={!canEdit}
          type={InputEnum.TEXT}
          defaultValue={text}
          onBlur={handleBlurUpdate<ManagerUserUpdateREQ>(record.id, 'fullName', text, onUpdateManagerUser)}
        />
      ),
    },
    {
      title: <EditableHeader title={tUser('table.contact')} />,
      dataIndex: 'phoneNumber',
      width: 160,
      render: (text: string, record) => (
        <CustomPhoneNumberInput
          value={record.phoneNumber}
          readOnly={!canEdit}
          onBlur={handleBlurUpdate<ManagerUserUpdateREQ>(record.id, 'phoneNumber', text, onUpdateManagerUser)}
        />
      ),
    },
    {
      title: <EditableHeader title={tUser('table.branches')} />,
      dataIndex: 'branches',
      width: 250,
      filters: branchOptions as ColumnFilterItem[],
      render: (value, record) => (
        <CustomSelect
          disabled={!canEdit}
          variant='borderless'
          options={branchOptions}
          mode='multiple'
          defaultValue={value}
          onChange={(value) => {
            debouncedUpdate(record.id, value);
          }}
        />
      ),
    },
    {
      title: <EditableHeader title={tUser('table.main_branch')} />,
      dataIndex: 'mainBranch',
      width: 160,
      filters: branchOptions as ColumnFilterItem[],
      render: (value, record) => (
        <CustomSelect
          disabled={!canEdit}
          variant='borderless'
          options={helperMainBranchOptions(record.userBranches)}
          value={value}
          onChange={(value) => {
            onUpdateManagerUserMainBranch(record.id, value);
          }}
        />
      ),
    },
    {
      title: <EditableHeader title={tUser('table.role')} />,
      dataIndex: 'position',
      width: 160,
      sorter: true,
      filters: helperFilter('manager.user_position', USER_POSITION, tEnum, true),
      render: (value: USER_POSITION | null, record) => (
        <CustomSelect
          disabled={!canEdit}
          variant='borderless'
          options={helperOption('manager.user_position', USER_POSITION, tEnum, undefined, true)}
          value={value ?? ''}
          onChange={handleChangeUpdate<ManagerUserUpdateREQ>(record.id, 'position', value, onUpdateManagerUser)}
        />
      ),
    },
    {
      title: tUser('table.creation_date'),
      dataIndex: 'createdAt',
      sorter: true,
      width: 160,
    },
    {
      title: tUser('table.access_date'),
      dataIndex: 'activatedAt',
      sorter: true,
      width: 160,
    },
    ...menuColumns,
    // {
    //   title: <EditableHeader title={tEnum(`menu.RECEIVE_RESERVATION` as any)} />,
    //   dataIndex: 'RECEIVE_RESERVATION',
    //   width: 160,
    //   className: 'ant-column-center border-left-column',
    //   filters: helperFilter('user_permission', USER_RECEIVE_RESERVATION_PERMISSION, tEnum),
    //   render: (value: { id: number; permission: USER_PERMISSION }, record: any) => {
    //     return (
    //       <CustomSelect
    //         disabled={!canEdit}
    //         variant='borderless'
    //         options={helperOption('user_permission', USER_RECEIVE_RESERVATION_PERMISSION, tEnum)}
    //         value={value?.permission}
    //         onChange={(newValue) => {
    //           onUpdateManagerUserPermission(record.id, { menuId: value.id, permission: newValue });
    //         }}
    //       />
    //     );
    //   },
    //   onCell: () => ({
    //     className: 'border-left-column',
    //   }),
    // },
    {
      title: <EditableHeader title={tUser('table.status')} />,
      dataIndex: 'status',
      width: 160,
      filters: helperFilter('active_status', ACTIVE_STATE, tEnum),
      render: (value: ACTIVE_STATE, record) => {
        return (
          <CustomSelect
            disabled={!canEdit}
            variant='borderless'
            classNameSelect={clsx(`${value ? '' : 'disabled-text'} `)}
            options={STATE_FILTER}
            defaultValue={value}
            onChange={handleChangeUpdate<ManagerUserUpdateREQ>(record.id, 'status', value, onUpdateManagerUser)}
          />
        );
      },
    },
  ];
};
