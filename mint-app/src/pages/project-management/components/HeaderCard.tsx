import { useTranslation } from 'react-i18next';

type HeaderCardProps = {
  imgSrc?: string;
  content?: string;
  children?: React.ReactNode;
};
const HeaderCard = ({ imgSrc, content, children }: HeaderCardProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  return (
    <div className='flex flex-col gap-4'>
      <div className='text-slate-500 text-md md:text-lg'>{tCommon('project_management.matching_expert')}</div>
      {!!imgSrc && (
        <div className='size-[60px] shrink-0 self-center md:self-start'>
          <img src={imgSrc} className='w-full h-full object-cover' />
        </div>
      )}
      {!!content && <div className='text-slate-500 text-xs max-md:text-center whitespace-pre-line'>{content}</div>}
      {!!children && children}
    </div>
  );
};

export default HeaderCard;
