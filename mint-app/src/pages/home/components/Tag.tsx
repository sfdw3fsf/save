import { twMerge } from 'tailwind-merge';

type TagProps = {
  isActive?: boolean;
  label: string;
  badgeLabel: number;
  onClick?: () => void;
};
const Tag = ({ isActive, badgeLabel, label, onClick }: TagProps) => {
  return (
    <button
      onClick={onClick}
      className={twMerge(
        'flex flex-nowrap items-center gap-2 rounded-lg bg-primary-600 w-fit px-3 py-2 text-white hover:bg-primary-200',
        !isActive && 'bg-slate-50 text-slate-500',
      )}>
      <p className='text-sm text-nowrap'>{label}</p>
      <div className={twMerge('px-2 pt-0.5 pb-1 text-slate-500 bg-slate-50 border-slate-200 rounded-full text-xs border')}>
        <p>{badgeLabel}</p>
      </div>
    </button>
  );
};

export default Tag;
