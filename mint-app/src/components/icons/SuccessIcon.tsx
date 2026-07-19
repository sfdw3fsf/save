import { SVGProps } from 'react';
import { twMerge } from 'tailwind-merge';

const SuccessIcon = ({ className, ...props }: SVGProps<SVGSVGElement>): JSX.Element => (
  <svg viewBox='0 0 120 120' xmlns='http://www.w3.org/2000/svg' className={twMerge('icon', className)} {...props}>
    <path
      d='M60 120C93.1371 120 120 93.1371 120 60C120 26.8629 93.1371 0 60 0C26.8629 0 0 26.8629 0 60C0 93.1371 26.8629 120 60 120Z'
      fill='currentColor'
    />
    <path
      d='M48.1547 83.6356L30.8112 66.9189C27.2194 63.4566 27.1139 57.7384 30.5762 54.1466C34.0391 50.5537 39.7573 50.4499 43.3491 53.9116L53.8261 64.0099L75.0477 39.4584C78.3104 35.6846 84.0149 35.2682 87.7887 38.5315C91.5635 41.7936 91.9782 47.4981 88.7161 51.2724L61.2584 83.0389C57.8754 86.9518 51.8913 87.2365 48.1547 83.6356Z'
      fill='white'
    />
  </svg>
);

export default SuccessIcon;
