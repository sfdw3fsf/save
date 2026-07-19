import { SVGProps } from 'react';
import { twMerge } from 'tailwind-merge';

const ArrowUpRightIcon = ({ className, ...props }: SVGProps<SVGSVGElement>): JSX.Element => (
  <svg
    width='24'
    height='25'
    viewBox='0 0 24 25'
    fill='none'
    xmlns='http://www.w3.org/2000/svg'
    className={twMerge('icon', className)}
    {...props}>
    <path
      d='M7 17.6992L17 7.69922M17 7.69922H7M17 7.69922V17.6992'
      stroke='#101828'
      strokeWidth='2'
      strokeLinecap='round'
      strokeLinejoin='round'
      fill='currentColor'
    />
  </svg>
);

export default ArrowUpRightIcon;
