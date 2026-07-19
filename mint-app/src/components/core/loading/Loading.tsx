import { twMerge } from 'tailwind-merge';
import Spinner from '../spinner/Spinner';

type LoadingProps = {
  className?: string;
};
const Loading = ({ className }: LoadingProps): JSX.Element => {
  return (
    <div
      className={twMerge(
        'fixed inset-0 z-[9999] min-h-screen min-w-screen bg-white grid place-content-center transition-all',
        className,
      )}>
      <Spinner raw />
    </div>
  );
};

export default Loading;
