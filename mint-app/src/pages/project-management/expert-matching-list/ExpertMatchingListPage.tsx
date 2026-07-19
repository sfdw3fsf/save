import Button from '@/components/core/button/Button';
import Drawer from '@/components/core/drawer/Drawer';
import PageContainer from '@/components/core/page-container/PageContainer';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import UserCircleCheckIcon from '@/components/icons/UserCircleCheckIcon';
import { getAvailableExpertsAPI } from '@/data/project/project-indiv.api';
import { AvailableExpertsREQ } from '@/data/project/request/project.request';
import { ExpertRESP } from '@/data/project/response/project.response';
import { useBreakpoint } from '@/hooks/useBreakpoint';
import { useFilter } from '@/hooks/useFilter';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { withFallbackStr } from '@/utils/helpers/string.helper';
import { useQuery } from '@tanstack/react-query';
import { useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { twMerge } from 'tailwind-merge';
import AvatarWithLabel from '../components/AvatarWithLabel';
import ProjectTable, { ProjectTableColumn } from '../components/ProjectTable';
import SearchAndFilter from '../components/SearchAndFilter';
import Title from '../components/Title';
import ExpertProfile from './ExpertProfile';

const ExpertMatchingListPage = PermissionAccept(
  (): JSX.Element => {
    const { t: tCommon } = useTranslation('common');
    const { projectId } = useParams();
    const { hasMinWidthMd: isWeb } = useBreakpoint('md');

    // STATES
    const [searchValue, setSearchValue] = useState('');
    const [selectedExpert, setSelectedExpert] = useState<ExpertRESP | null>(null);

    // HOOKS
    const { queries, hasNone, onSearch, getPaginationConfigs } = useFilter<AvailableExpertsREQ>();

    // APIS
    const { data: data, isFetching: isFetchingExperts } = useQuery({
      queryKey: [QUERY_KEYS.PROJECTS.EXPERT_MATCHING, queries],
      queryFn: () => getAvailableExpertsAPI(+projectId!, queries),
      enabled: !hasNone || !!projectId,
    });

    const experts = useMemo(() => data?.data, [data?.data]); // replace when API is fixed

    // COLUMNS
    const columns = useMemo<ProjectTableColumn<keyof ExpertRESP, ExpertRESP>[]>(
      () => [
        {
          header: '',
          render: (_, record) => <AvatarWithLabel url={record?.avatar?.url} size='md' />,
          hidden: isWeb,
        },
        {
          header: tCommon('expert_matching.expert_name'),
          value: 'fullName',
          render: (_, record) => <AvatarWithLabel url={record?.avatar?.url} label={record?.fullName} className='max-md:hidden' />,
        },
        {
          header: tCommon('expert_matching.department'),
          value: 'department',
          render: (val) => <div>{withFallbackStr(val?.toString())}</div>,
        },
        {
          header: tCommon('expert_matching.detailed_field'),
          value: 'detailedField',
          render: (val) => <div>{withFallbackStr(val?.toString())}</div>,
        },
        {
          header: tCommon('expert_matching.personal_experience'),
          value: 'yearOfExperience',
          render: (val) => <div>{`${withFallbackStr(val?.toString())} ${tCommon('expert_matching.years')}`}</div>,
        },
        {
          header: '',
          render: (_, record) => (
            <div className='min-w-[20px]'>
              <Button
                className='md:border-0 p-0 w-full'
                leftIcon={<UserCircleCheckIcon />}
                onClick={(event) => {
                  event.stopPropagation(); // prevent the row click
                  setSelectedExpert(record as ExpertRESP);
                }}>
                {isWeb ? '' : tCommon('expert_matching.choose_this_expert')}
              </Button>
            </div>
          ),
        },
      ],
      [isWeb, tCommon],
    );
    return (
      <>
        <Drawer
          open={selectedExpert !== null}
          onClose={() => setSelectedExpert(null)}
          title={tCommon('expert_matching.expert_profile')}
          childClassName='md:w-1/2 w-full max-w-[480px]'>
          <ExpertProfile projectId={+projectId!} expert={selectedExpert!} onCancel={() => setSelectedExpert(null)} />
        </Drawer>
        <PageContainer
          title={tCommon('expert_matching.title')}
          isFullScreen
          hiddenScroll
          className='items-start lg:px-28 px-4 py-8'
          containerStyle={twMerge(selectedExpert !== null ? 'overflow-hidden h-[100vh]' : null)}>
          <div className='bg-white flex flex-col w-full p-8 rounded-xl border-[1px] border-slate-200'>
            <Title title={tCommon('expert_matching.title')} subTitle={tCommon('expert_matching.sub_title')} />
            <SearchAndFilter
              searchValue={searchValue}
              onSearch={(value) => {
                onSearch({ fullName: value });
                setSearchValue(value);
              }}
            />
            <ProjectTable
              data={experts as unknown as ExpertRESP[]}
              columns={columns}
              onRowClick={(row) => {
                setSelectedExpert(row as ExpertRESP);
              }}
              isLoading={isFetchingExperts}
              paginationConfigs={getPaginationConfigs(data?.pagination.totalPages || 0)}
            />
          </div>
        </PageContainer>
      </>
    );
  },
  [USER_ROLE.MEMBER],
  [],
);

export default ExpertMatchingListPage;
