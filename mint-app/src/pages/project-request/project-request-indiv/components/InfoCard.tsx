import FormMessage from '@/components/core/form/form-message/FormMessage';
import { motion } from 'framer-motion';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

type InfoCardItemProps = {
  children?: React.ReactNode;
  className?: string;
  title?: string;
  leftSection?: React.ReactNode;
  isCollapse?: boolean;
  isError?: boolean;
  message?: string;
  childrenCont?: string;
  headerClassName?: string;
};
const InfoCard = ({
  children,
  className,
  title,
  isCollapse,
  leftSection,
  isError,
  message,
  childrenCont,
  headerClassName,
}: InfoCardItemProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const [open, setOpen] = useState(false);
  return (
    <div>
      <div
        className={twMerge(
          'flex w-full flex-col rounded-lg bg-white border-[1px] overflow-hidden',
          isError ? 'border-comp-border-danger-strong' : 'border-slate-200',
          className,
        )}>
        <div className={twMerge('flex w-full justify-between bg-slate-25 p-4', headerClassName)}>
          <div className='flex gap-3'>
            {!!leftSection && leftSection}
            <div className='block my-auto text-md whitespace-nowrap max-md:text-sm'>{title}</div>
          </div>
          {isCollapse && (
            <button form='_notexist' className='block my-auto text-sm text-primary-500' onClick={() => setOpen(!open)}>
              {open ? `[${tCommon('component.collapse')}]` : `[${tCommon('component.opening')}]`}
            </button>
          )}
        </div>
        {!isCollapse ? (
          <div className={twMerge('grid md:grid-cols-2 grid-cols-1 px-4 my-2 gap-x-6', childrenCont)}>{children}</div>
        ) : (
          <motion.div
            className='px-4 overflow-y-auto max-h-[346px] static-scrollbar'
            initial={{ height: 0, opacity: 0 }}
            animate={{ height: open ? 'auto' : 0, opacity: open ? 1 : 0 }}
            transition={{ duration: 0.3, ease: 'easeInOut' }}>
            {children}
          </motion.div>
        )}
      </div>
      {isError && !!message && <FormMessage className={'mt-2'} variant={'error'} message={message} />}
    </div>
  );
};

export default InfoCard;
