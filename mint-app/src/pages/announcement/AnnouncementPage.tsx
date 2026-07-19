import Badge from '@/components/core/badge/Badge';
import SearchSection from '@/components/core/faq/SearchSection';
import PageContainer from '@/components/core/page-container/PageContainer';
import Pagination from '@/components/core/pagination/Pagination';
import Spinner from '@/components/core/spinner/Spinner';
import TableCard, { TableCardColumn } from '@/components/core/table/TableCard';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import { getAnnouncementCategoriesAPI } from '@/data/announcement/announcement.api';
import { useAnnouncement } from '@/data/announcement/announcement.service';
import { AnnouncementData } from '@/data/announcement/response/announcement.response';
import { toLocaleDateString } from '@/utils/helpers/datetime.helper';
import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { useSearchParams } from 'react-router-dom';

const AnnouncementPage = PermissionAccept((): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  const { control } = useForm();
  const [searchParams] = useSearchParams();
  const { data, isLoading } = useAnnouncement();
  const totalElements = Number(searchParams.get('totalElements'));

  const { data: announcementCategories } = useQuery({
    queryKey: ['drop'],
    queryFn: () => getAnnouncementCategoriesAPI(),
  });
  const dropdownItems = useMemo(
    () => [
      { label: tFaq('entire'), value: 'entire' },
      ...(announcementCategories?.data?.map((announcement) => ({
        label: tFaq(`announcementDetail.${announcement.name.toLowerCase()}` as any).includes('.')
          ? announcement.name
          : tFaq(`announcementDetail.${announcement.name.toLowerCase()}` as any),
        value: announcement.name,
      })) || []),
    ],
    [announcementCategories?.data, tFaq],
  );

  type TableRowData = { [key: string]: string | number };

  const transformAnnouncementData = (data: AnnouncementData[]): TableRowData[] => {
    return data.map((item) => ({
      id: item.id,
      title: item.title,
      categoryName: tFaq(`announcementDetail.${item.category.name.toLowerCase()}` as any).includes('.')
        ? item.category.name
        : tFaq(`announcementDetail.${item.category.name.toLowerCase()}` as any),
      status: item.status,
      createdAt: toLocaleDateString(new Date(item.createdAt)),
      attachmentCount: item.attachments.length,
    }));
  };
  const tableData = data ? transformAnnouncementData(data.data) : [];

  // Define table columns
  const columns: TableCardColumn[] = [
    {
      header: tFaq('table.number'),
      value: 'id',

      classHeader: 'md:text-center md:w-[70px]',
      classElement: 'md:text-center md:w-[70px]',
    },
    {
      header: tFaq('table.division'),
      value: 'categoryName',
      renderStyle: (data) => (
        <Badge color='neutral' shape='square-round' label={`${data}`} className='whitespace-nowrap max-w-[132px] w-full' />
      ),
      classHeader: ' md:w-[145px]',
      classElement: ' md:w-[145px]',
    },
    {
      header: tFaq('table.title'),
      value: 'title',
      renderStyle: (data) => <span className='max-w-[397px] w-full  text-sm text-slate-500'>{data}</span>,
      classHeader: ' md:w-[351px]',
      classElement: ' md:w-[351px]',
    },
    {
      header: tFaq('table.situation'),
      value: 'status',
      renderStyle: (data) => (data ? <Badge color='green' shape='square-round' label={`${data}`} /> : null),
      classHeader: ' md:w-[76px]',
      classElement: ' md:w-[76px]',
    },
    {
      header: tFaq('table.creationDate'),
      value: 'createdAt',
      renderStyle: (data) => <span className='lg:whitespace-nowrap text-center text-sm font-normal text-slate-800'>{data}</span>,
      classHeader: ' md:w-[126px]',
      classElement: ' md:w-[126px]',
    },
  ];

  return (
    <PageContainer
      // hiddenScrolls
      isFullScreen
      subtitle='Notices'
      showTitle
      title={tFaq('announcementDetail.announcement')}
      withFooter>
      <div className='max-w-[768px] w-full pb-12'>
        <SearchSection control={control} dropdownItems={dropdownItems} localeName='announcementDetail' />
        {!isLoading ? <TableCard columns={columns} data={tableData} /> : <Spinner />}
        {totalElements !== 0 && <Pagination isBgWhite />}
      </div>
    </PageContainer>
  );
});

export default AnnouncementPage;
