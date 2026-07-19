import { ICONS } from '@/theme';
import Image from 'next/image';
import { twMerge } from 'tailwind-merge';

type EditableHeaderProps = {
  title: string;
  className?: string;
};
const EditableHeader = ({ title, className }: EditableHeaderProps) => {
  return (
    <div className={twMerge('flex items-center gap-1 ant-merged-label', className)}>
      <span>{title}</span>
      <Image src={ICONS.PENCIL} alt='Look' width={16} height={16} />
    </div>
  );
};

export default EditableHeader;
