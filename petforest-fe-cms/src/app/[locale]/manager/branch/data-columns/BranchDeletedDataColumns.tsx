import { ManagerBranchDTO } from '@/store/manager/branch/dto/manager-branch.dto';
import { useTranslation } from 'react-i18next';

export const BranchDeletedDataColumns = () => {
  const { t: tBranch } = useTranslation('manager_branch');
  return [
    {
      title: tBranch('table.pnt'),
      dataIndex: 'id',
      width: 160,
      sorter: true,
    },
    {
      title: tBranch('table.branch_name'),
      dataIndex: 'name',
      width: 160,
    },
    {
      title: tBranch('table.color'),
      dataIndex: 'color',
      width: 160,
      render: (value: string, record: ManagerBranchDTO) => {
        return (
          <div className='flex items-center gap-2 ml-6'>
            <div
              className={`rounded-full border border-neutral-40 w-[22px] aspect-[1/1] `}
              style={{ background: '#' + value }}></div>
            {`# ${value}`}
          </div>
        );
      },
    },
    {
      title: tBranch('table.road'),
      dataIndex: 'roadAddress',
      width: 160,
    },
    {
      title: tBranch('table.street'),
      dataIndex: 'streetAddress',
      width: 160,
    },
    {
      title: tBranch('table.naver_map'),
      dataIndex: 'naverMap',
      width: 160,
    },
    {
      title: tBranch('table.kakao_map'),
      dataIndex: 'kakaoMap',
      width: 160,
    },
  ];
};
