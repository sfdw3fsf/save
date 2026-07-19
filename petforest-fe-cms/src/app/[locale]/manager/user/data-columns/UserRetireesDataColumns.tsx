import { ManagerUserRetireesDTO } from '@/store/manager/user/dto/manager-user.dto';
import { MY_ROUTE } from '@/utils/constants/route.constant';
import { ACTIVE_STATE } from '@/utils/enums/common.enum';
import { USER_PERMISSION, USER_POSITION, USER_RECEIVE_RESERVATION_PERMISSION } from '@/utils/enums/manager/manager.enum';
import { MenuKeys } from '@/utils/enums/side-menu-type';
import { helperFilter } from '@/utils/helpers/common.helper';
import { FilterOptions } from '@/utils/types/option.type';
import { TableColumnsType } from 'antd';
import { ColumnGroupType } from 'antd/es/table';
import { ColumnType } from 'antd/lib/table';
import { useTranslation } from 'react-i18next';

type UserRetireesDataColumnsProps = {
  branchOptions: FilterOptions;
};
export const UserRetireesDataColumns = ({
  branchOptions,
}: UserRetireesDataColumnsProps): TableColumnsType<ManagerUserRetireesDTO> => {
  const { t: tUser } = useTranslation('manager_user');
  const { t: tEnum } = useTranslation('enum');

  const menuColumns: (ColumnGroupType<ManagerUserRetireesDTO> | ColumnType<ManagerUserRetireesDTO>)[] = Object.entries(MY_ROUTE)
    .filter(([_, value]) => ![MY_ROUTE.NO_PERMISSION, MY_ROUTE.AUTH].includes(value as any))
    .map(([key, value]) => {
      if (typeof value === 'string') {
        return {
          title: tEnum(`menu.${key}` as any),
          dataIndex: key,
          width: 160,
          className: 'ant-column-center border-left-column',
          filters: helperFilter(
            value === MY_ROUTE.RECEIVE_RESERVATION ? 'user_receive_reservation_permission' : 'user_permission',
            value === MY_ROUTE.RECEIVE_RESERVATION ? USER_RECEIVE_RESERVATION_PERMISSION : USER_PERMISSION,
            tEnum,
          ),
          render: (values: { id: number; permission: USER_PERMISSION }, record: any) => {
            return tEnum(`user_permission.${values.permission}`);
          },
          onCell: () => ({
            className: 'border-left-column',
          }),
        };
      } else if (typeof value === 'object') {
        return {
          title: tEnum(`menu.${key}` as any),
          className: 'ant-column-center border-left-column',
          children: Object.keys(value)
            .map((item, childIndex) => {
              return {
                title: tEnum(`menu.${item}` as any),
                dataIndex: item,
                width: 160,
                filters: helperFilter('user_permission', USER_PERMISSION, tEnum),
                className: 'ant-column-center',
                render: (values: { id: number; permission: USER_PERMISSION }, record: any) => {
                  return tEnum(`user_permission.${values.permission}`);
                },
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
    .filter(Boolean) as (ColumnGroupType<ManagerUserRetireesDTO> | ColumnType<ManagerUserRetireesDTO>)[];

  return [
    {
      title: tUser('table.id'),
      dataIndex: 'username',
      width: 160,
      sorter: true,
    },
    {
      title: tUser('table.password'),
      dataIndex: 'password',
      width: 160,
    },
    {
      title: tUser('table.name'),
      dataIndex: 'fullName',
      width: 160,
      sorter: true,
    },
    {
      title: tUser('table.contact'),
      dataIndex: 'phoneNumber',
      width: 160,
    },
    {
      title: tUser('table.dob'),
      dataIndex: 'dateOfBirth',
      width: 160,
      sorter: true,
    },
    {
      title: tUser('table.branches'),
      dataIndex: 'branches',
      width: 250,
      filters: branchOptions,
    },
    {
      title: tUser('table.main_branch'),
      dataIndex: 'mainBranch',
      width: 160,
      filters: branchOptions,
    },
    {
      title: tUser('table.role'),
      dataIndex: 'position',
      width: 160,
      sorter: true,
      filters: helperFilter('manager.user_position', USER_POSITION, tEnum, true),
      render: (value: USER_POSITION) => value && <div>{tEnum(`manager.user_position.${value}`)}</div>,
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
    {
      title: tUser('table.status'),
      dataIndex: 'status',
      width: 160,
      filters: helperFilter('active_status', ACTIVE_STATE, tEnum),
      render: (value: ACTIVE_STATE) => <div>{tEnum(`active_status.${value}`)}</div>,
    },
  ];
};
