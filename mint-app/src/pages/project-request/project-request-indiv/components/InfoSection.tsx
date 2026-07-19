import InfoFillIcon from '@/components/icons/IconFillIcon';

type InfoSectionProps = {
  content: string;
};
const InfoSection = ({ content }: InfoSectionProps) => {
  return (
    <div className='flex flex-col w-full'>
      <div className='flex flex-row items-start gap-1'>
        <div className='flex items-center justify-center my-0.5'>
          <InfoFillIcon className='text-comp-icon-tertiary text-md shrink-0 size-4' />
        </div>
        <div className='text-comp-text-tertiary text-xs'>{content}</div>
      </div>
    </div>
  );
};

export default InfoSection;
