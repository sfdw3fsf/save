type InfoListProps = {
  title: string;
  content: string;
};
const InfoList = ({ title, content }: InfoListProps): JSX.Element => {
  return (
    <div className='flex flex-col w-full gap-2 text-left text-md max-md:text-sm'>
      <div className='font-medium text-comp-text-default'>{title}</div>
      <div dangerouslySetInnerHTML={{ __html: content }} />
    </div>
  );
};

export default InfoList;
