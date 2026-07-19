import CustomText from './Text';

type StatusProps = {
  isSuccess: boolean;
  status: string;
  noBackground?: boolean;
};
const Status = ({ isSuccess, status, noBackground }: StatusProps) => {
  return (
    <span
      className={`py-1 px-[10px] ${
        noBackground
          ? 'bg-transparent'
          : isSuccess
            ? 'bg-primary-0 rounded-lg border-[1px] border-primary-500'
            : 'bg-red-semantic-10 rounded-lg border-[1px] border-red-semantic-200'
      }`}>
      <CustomText
        className={`${noBackground ? '' : isSuccess ? '!text-primary-900' : '!text-red-semantic-600'}`}
        variant='label-1-normal'>
        {status}
      </CustomText>
    </span>
  );
};

export default Status;
