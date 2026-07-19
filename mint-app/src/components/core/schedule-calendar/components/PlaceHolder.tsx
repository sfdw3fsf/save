type PlaceHolderProps = {
  title?: string;
};
const PlaceHolder = ({ title }: PlaceHolderProps): JSX.Element => {
  return (
    <div className='border-slate-200 border-[1px] rounded-lg w-fit flex flex-col py-2 px-4 items-center bg-white'>
      <div className='text-slate-500 font-medium text-sm'>{title}</div>
    </div>
  );
};
export default PlaceHolder;
