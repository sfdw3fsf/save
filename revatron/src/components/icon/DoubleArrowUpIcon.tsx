import { SVGProps } from 'react';
import { twMerge } from 'tailwind-merge';

const DoubleArrowUp = ({ className, ...props }: SVGProps<SVGSVGElement>): JSX.Element => (
  <svg
    width='12'
    height='14'
    viewBox='0 0 12 14'
    fill='none'
    xmlns='http://www.w3.org/2000/svg'
    className={twMerge('icon', className)}
    {...props}>
    <path d='M0.5 6.08398L1.7925 7.37648L6 3.17815L10.2075 7.37648L11.5 6.08398L6 0.583984L0.5 6.08398Z' fill='#2EA658' />
    <path d='M0.5 12.1247L1.7925 13.4172L6 9.21884L10.2075 13.4172L11.5 12.1247L6 6.62467L0.5 12.1247Z' fill='#2EA658' />
  </svg>
);

export default DoubleArrowUp;
