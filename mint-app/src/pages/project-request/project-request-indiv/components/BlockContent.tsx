import i18n, { I18nLanguage } from '@/i18n';
import { getNameByLanguage } from '@/utils/helpers/form.helper';
import { twMerge } from 'tailwind-merge';

type BlockContentProps = {
  title: string;
  content: {
    label: { name: string; nameInEnglish: string };
    allFields: { name: string; nameInEnglish: string };
  }[];
  className?: string;
};
const BlockContent = ({ title, content, className }: BlockContentProps): JSX.Element => {
  const currentCode = i18n.language as I18nLanguage;
  return (
    <div className={twMerge('flex w-full flex-col gap-3', className)}>
      <div className='font-medium text-comp-text-brand'>{title}</div>
      {content.map(({ label, allFields }, index) => {
        if (allFields?.name.length === 0 || allFields?.nameInEnglish.length === 0) return <></>;
        return (
          <div key={index} className='flex flex-col w-full gap-1'>
            <div className='text-comp-text-secondary'>{getNameByLanguage(currentCode, label)}</div>
            <div className='font-medium text-comp-text-default'>{getNameByLanguage(currentCode, allFields)}</div>
          </div>
        );
      })}
    </div>
  );
};

export default BlockContent;
