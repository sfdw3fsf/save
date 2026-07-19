import { getBoardsField } from '@/data/home/home-api';
import { Board as BoardType } from '@/data/home/response/home-partner.response';
import { useQuery } from '@tanstack/react-query';
import i18next from 'i18next';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';
type BoardItemProps = {
  image: string;
  department: string;
  expert: string;
  role: string;
};

const BoardItem = ({ department, expert, image, role }: BoardItemProps) => {
  return (
    <div
      // TODO: find better solution
      className={twMerge(
        'max-w-[140px] min-h-48 p-3 min-w-[140px] rounded-xl bg-white border shadow-sm inline-block mx-4',
        i18next.language == 'en' ? 'min-h-72' : '',
      )}>
      <img src={image} className='w-full min-h-[45px] h-[45px] object-contain mb-4 ' />
      <div className='flex flex-col justify-center items-center text-center gap-1'>
        <p className='text-xs font-bold text-primary-500 whitespace-pre-line break-keep'>{department}</p>
        <p className='text-slate-800 text-lg font-medium whitespace-pre-line break-keep'>{expert}</p>
        <p className='text-xs text-slate-500 font-normal whitespace-pre-line'>{role}</p>
      </div>
    </div>
  );
};

const CarouselContent = ({ BoardFields }: { BoardFields: BoardType[] }): JSX.Element => {
  return (
    <div className='inline-block max-w-max slide-animation'>
      {BoardFields.map((item, index) => (
        <BoardItem
          key={index}
          department={i18next.language == 'en' ? item.departmentInEnglish : item.departmentInKorean}
          expert={i18next.language == 'en' ? item.nameInEnglish : item.nameInKorean}
          image={item.url}
          role={i18next.language == 'en' ? item.roleInEnglish : item.roleInKorean}
        />
      ))}
    </div>
  );
};

const Board = () => {
  const { t: tCommon } = useTranslation('common');
  const { data: BoardFields } = useQuery({
    queryKey: ['getBoards'],
    queryFn: () =>
      getBoardsField({
        size: 100,
      }),
  });

  return (
    <div className='py-24 w-full flex flex-col gap-16 overflow-clip'>
      <div>
        <h2 className='text-primary-600 text-sm text-center mb-4 font-medium'>{tCommon('home.board')}</h2>
        <p className='text-slate-800 md:text-4xl text-3xl text-center font-bold md:px-0 px-5 mx-auto mb-16 whitespace-pre-line'>
          {tCommon('home.board_title')}
        </p>
      </div>
      <div className='overflow-hidden whitespace-nowrap w-full carousel'>
        {Array.isArray(BoardFields?.data) && <CarouselContent BoardFields={BoardFields.data} />}
        {Array.isArray(BoardFields?.data) && <CarouselContent BoardFields={BoardFields.data} />}
        <div className='w-96 h-40 right-0 top-0 absolute bg-gradient-to-r from-transparent to-white' />
      </div>
    </div>
  );
};

export default Board;
