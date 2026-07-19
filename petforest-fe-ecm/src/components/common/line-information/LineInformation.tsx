type LineInformationProps = {
  data: { label: string; value?: string }[];
};
const LineInformation = ({ data }: LineInformationProps) => {
  return (
    data.length > 0 && (
      <div className='flex flex-col items-start w-full gap-3'>
        {data.map((item) => (
          <div key={item.label + item.value} className='flex items-start w-full'>
            <div className='text-[14px] font-medium w-[88px] text-grey-mist'>{item.label}</div>
            <div className='text-[14px] font-medium w-[88px] flex-1'>{item.value}</div>
          </div>
        ))}
      </div>
    )
  );
};

export default LineInformation;
