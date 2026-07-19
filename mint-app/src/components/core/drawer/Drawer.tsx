import XIcon from '@/components/icons/XIcon';
import { motion } from 'framer-motion';
import { twMerge } from 'tailwind-merge';
import Divider from '../divider/Divider';
import IconButton from '../icon-button/IconButton';

type DrawerProps = {
  open: boolean;
  onClose: () => void;
  title?: string;
  children: React.ReactNode;
  childClassName?: string;
  className?: string;
  isDivider?: boolean;
  topSection?: React.ReactNode;
  xButton?: boolean;
};
const Drawer = ({
  open,
  title,
  onClose,
  children,
  className,
  childClassName,
  isDivider = false,
  topSection,
  xButton = true,
}: DrawerProps): JSX.Element => {
  if (!open) return <></>;
  return (
    <div
      className={twMerge(
        'fixed inset-0 flex z-50 h-screen w-screen bg-comp-fill-neutral-reverse bg-opacity-20 items-end justify-end p-4',
        className,
      )}>
      <motion.div
        initial={{ opacity: 0, translateX: '100%' }}
        animate={{ opacity: 1, translateX: 0 }}
        exit={{ opacity: 0, translateY: '100%' }}
        transition={{ duration: 0.01 }}
        className={twMerge('flex h-full flex-col w-full md:w-fit bg-white rounded-md transition-all', childClassName)}>
        <div className='flex w-full px-6 py-4 items-center'>
          <div className='flex flex-col gap-2'>
            {!!topSection && topSection}
            {!!title && <div className='text-black text-lg'>{title}</div>}
          </div>
          {xButton && (
            <IconButton
              icon={<XIcon className='text-slate-500' />}
              sizing='xs'
              keepIconStyle={true}
              variant='raw'
              onClick={onClose}
              className='ml-auto'
            />
          )}
        </div>
        {isDivider && <Divider />}

        <div className='flex overflow-hidden'>{children}</div>
      </motion.div>
    </div>
  );
};

export default Drawer;
