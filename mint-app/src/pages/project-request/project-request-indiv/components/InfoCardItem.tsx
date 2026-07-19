import Tippy from '@tippyjs/react';
import { twMerge } from 'tailwind-merge';

type InfoCardProps = {
  label?: string;
  content?: string | React.ReactNode;
  className?: string;
  labelStyle?: string;
  contentStyle?: string;
  contentTooltip?: string;
};

const InfoCardItem = ({ label, content, className, labelStyle, contentStyle, contentTooltip }: InfoCardProps): JSX.Element => {
  return (
    <div className={twMerge('col-span-1 flex flex-col gap-1 my-2', className)}>
      <div className={twMerge('text-sm text-slate-500', labelStyle)}>{label}</div>
      {contentTooltip ? (
        <Tippy content={contentTooltip}>
          <div
            className={twMerge(
              'text-sm md:whitespace-pre-line md:break-words font-medium text-ellipsis overflow-hidden line-clamp-3',
              contentStyle,
            )}>
            {content}
          </div>
        </Tippy>
      ) : (
        <div
          className={twMerge(
            'text-sm md:whitespace-pre-line md:break-words font-medium text-ellipsis overflow-hidden line-clamp-3',
            contentStyle,
          )}>
          {content}
        </div>
      )}
    </div>
  );
};

export default InfoCardItem;
