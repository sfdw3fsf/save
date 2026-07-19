import Badge from '@/components/core/badge/Badge';
import SearchSection from '@/components/core/faq/SearchSection';
import PageContainer from '@/components/core/page-container/PageContainer';
import Pagination from '@/components/core/pagination/Pagination';
import Spinner from '@/components/core/spinner/Spinner';
import TableCard, { TableCardColumn } from '@/components/core/table/TableCard';
import PermissionAccept from '@/components/hocs/PermissionAccept';
import { getInquiryCategory } from '@/data/inquiry/inquiry.api';
import { useInquiry } from '@/data/inquiry/inquiry.service';
import { Inquiry } from '@/data/inquiry/response/inquiry.response';
import { PARAM_SCROLL_POSITION } from '@/utils/constants/data.constant';
import { INQUIRY_ANSWER_STATUS, INQUIRY_STATUS } from '@/utils/enums/data/inquiry-status.enum';
import { toLocaleDateString } from '@/utils/helpers/datetime.helper';
import { useQuery } from '@tanstack/react-query';
import { useEffect, useMemo, useRef } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { useLocation, useSearchParams } from 'react-router-dom';
import { InquiryRegistry } from './component/InquiryRegistry';

export const FORM_POSITION = 'form';

const InquiryPage = PermissionAccept((): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');

  const ref = useRef<HTMLDivElement>(null);

  const { control } = useForm();
  const { data, isLoading } = useInquiry();
  const [searchParams, setSearchParams] = useSearchParams();

  const location = useLocation();
  const shouldScrollToForm = searchParams.get(PARAM_SCROLL_POSITION) === FORM_POSITION;

  const totalElements = Number(searchParams.get('totalElements'));
  const { data: inquiriesCategory } = useQuery({
    queryKey: ['drop'],
    queryFn: () => getInquiryCategory(),
  });
  const dropdownItems = useMemo(
    () => [
      { label: tFaq('entire'), value: 'entire' },
      ...(inquiriesCategory?.data?.map((inquiry) => ({
        label: tFaq(`inquirySection.${inquiry.name.toLowerCase()}` as any).includes('.')
          ? inquiry.name
          : tFaq(`inquirySection.${inquiry.name.toLowerCase()}` as any),
        value: inquiry.name,
      })) || []),
    ],
    [inquiriesCategory?.data, tFaq],
  );

  type TableRowData = { [key: string]: string | number };

  const transformInquiryData = (data: Inquiry[]): TableRowData[] => {
    return data.map((item) => ({
      id: item.id,
      title: item.subject,
      categoryName: item.category.name,
      status: item.status,
      answerStatus: item.answerStatus,
      createdAt: toLocaleDateString(new Date(item.createdAt)),
    }));
  };
  const tableData = data ? transformInquiryData(data.data) : [];

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
        <Badge color='neutral' shape='square-round' label={`${data}`} className=' whitespace-nowrap'></Badge>
      ),
      classHeader: ' md:w-[160px]',
      classElement: ' md:w-[160px]',
    },
    {
      header: tFaq('table.title'),
      value: 'title',
      classHeader: ' md:w-[188px]',
      classElement: ' md:w-[188px]',
    },
    {
      header: tFaq('table.answerStatus'),
      value: 'answerStatus',
      renderStyle: (data) =>
        data === INQUIRY_ANSWER_STATUS.PENDING ? (
          <Badge color='neutral' shape='square-round' label={`${data}`}></Badge>
        ) : (
          <Badge color='green' shape='square-round' label={`${data}`}></Badge>
        ),
      classHeader: ' md:w-[141px]',
      classElement: ' md:w-[141px]',
    },
    {
      header: tFaq('table.situation'),
      value: 'status',
      renderStyle: (data) =>
        data === INQUIRY_STATUS.OLD ? (
          <Badge color='neutral' shape='square-round' label={`${data}`}></Badge>
        ) : (
          <Badge color='green' shape='square-round' label={`${data}`}></Badge>
        ),
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
  useEffect(() => {
    if (!shouldScrollToForm) return;
    ref.current?.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }, [shouldScrollToForm, location]);

  return (
    <PageContainer subtitle='Notices' showTitle title={`1:1 ${tFaq('inquiry')}`} withFooter>
      <div className='max-w-[768px] w-full flex justify-center flex-col'>
        <SearchSection control={control} dropdownItems={dropdownItems} />
        {!isLoading ? <TableCard columns={columns} data={tableData} /> : <Spinner />}
        {totalElements !== 0 && <Pagination isBgWhite />}
        <div ref={ref} className='w-full flex justify-center flex-col gap-3 text-center py-24'>
          <p className='text-base font-normal text-primary-500'>Notices</p>
          <h1 className='text-4xl font-medium text-slate-900'>1:1 {tFaq('inquiry')}</h1>
        </div>
        <InquiryRegistry />
      </div>
    </PageContainer>
  );
});

export default InquiryPage;
