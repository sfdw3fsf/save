import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

type ContentProps = {
  className?: string;
  mainContent?: any;
};

export const ContentDetail = ({ className, mainContent }: ContentProps): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');

  return (
    <div className={twMerge('flex flex-col p-6 border border-b-slate-200 bg-white max-w-[720px] w-full rounded-lg', className)}>
      {mainContent}
    </div>
  );
};
