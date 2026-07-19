import defaultAvatar from '@/assets/images/Avatar.png';
import { twMerge } from 'tailwind-merge';
type AvatarWithLabelProps = {
  size?: 'sm' | 'md' | 'lg';
  url?: string;
  label?: string;
  className?: string;
};
const theme = {
  sm: {
    cont: 'w-[24px] h-[24px]',
  },
  md: {
    cont: 'w-[40px] h-[40px]',
  },
  lg: {
    cont: 'w-[96px] h-[96px]',
  },
};
const AvatarWithLabel = ({ size = 'sm', url, label, className }: AvatarWithLabelProps): JSX.Element => {
  return (
    <div className='flex items-center gap-2'>
      <div className={twMerge('relative flex rounded-full w-[24px] h-[24px]', theme[size].cont, className)}>
        <img
          src={url || defaultAvatar}
          className='w-full h-full rounded-full object-cover'
          onError={(e: any) => {
            e.target.src = defaultAvatar; // Set fallback image on error
          }}
        />
      </div>
      {!!label && <div>{label}</div>}
    </div>
  );
};

export default AvatarWithLabel;
