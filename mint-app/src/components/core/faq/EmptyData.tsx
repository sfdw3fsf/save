import EmptyImage from '@/assets/images/empty_list.png';
type EmptyDataProps = {
  label?: string;
};

export const EmptyData = ({ label }: EmptyDataProps): JSX.Element => {
  return (
    <div className='w-full  flex flex-col gap-6 justify-center items-center my-16'>
      <span className='size-28 shrink-0'>
        <img src={EmptyImage} alt='emptyFaq' />
      </span>
      <p className='text-sm text-slate-500 text-center mt-2'>{label}</p>
    </div>
  );
};
